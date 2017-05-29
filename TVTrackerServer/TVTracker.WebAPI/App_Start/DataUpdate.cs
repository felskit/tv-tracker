using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using System.Web;
using TVTracker.Entity.Entity;

namespace TVTracker.WebAPI.App_Start
{
	public class DataUpdate
	{
		public static void Start()
		{
			Task.Run(() => DataUpdateTask());
		}

		private static async void DataUpdateTask()
		{
			while (true)
			{
				await Task.Delay(new TimeSpan(7, 0, 0, 0));

				var request = WebRequest.Create("http://api.tvmaze.com/updates/shows");
				var response = request.GetResponse();
				var responseStream = response.GetResponseStream();
				var reader = new StreamReader(responseStream);

				var responseString = reader.ReadToEnd();
				reader.Close();
				responseStream.Close();

				var seriesList = JsonConvert.DeserializeObject<Dictionary<string, long>>(responseString);

				var context = new TVTrackerContext();

				foreach (var show in context.Shows)
				{
					if (seriesList.ContainsKey(show.apiId) && seriesList[show.apiId] > show.updated)
					{
						request = WebRequest.Create($"http://api.tvmaze.com/shows/{show.apiId}");
						response = request.GetResponse();
						responseStream = response.GetResponseStream();
						reader = new StreamReader(responseStream);
						responseString = reader.ReadToEnd();
						reader.Close();
						responseStream.Close();

						var jobject = JObject.Parse(responseString);

						show.UpdateWith(jobject);

						UpdateEpisodes(context, show.apiId, show.id);

						context.SaveChanges();
					}
				}

				context.Dispose();
			}
		}


		private static void UpdateEpisodes(TVTrackerContext context, string apiId, int showId)
		{
			var request = WebRequest.Create($"http://api.tvmaze.com/shows/{apiId}/episodes");
			var response = request.GetResponse();
			var responseStream = response.GetResponseStream();
			var reader = new StreamReader(responseStream);

			var responseString = reader.ReadToEnd();
			reader.Close();
			responseStream.Close();

			var episodes = context.Episodes.Where(x => x.ShowId == showId);

			var jobjects = JArray.Parse(responseString);
			foreach (var jobject in jobjects.Children())
			{
				var episode = episodes.Single(x => x.apiId == (string)jobject["id"]);
				episode.UpdateWith(jobject);
			}
		}
	}
}