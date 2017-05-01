using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using TVTracker.Entity.Entity.Models;

namespace TVTracker.Entity.Entity
{
	public class TVTrackerContextInitializer : DropCreateDatabaseIfModelChanges<TVTrackerContext>
	{
		protected override void Seed(TVTrackerContext context)
		{
			var request = WebRequest.Create("http://api.tvmaze.com/updates/shows");
			var response = request.GetResponse();
			var responseStream = response.GetResponseStream();
			var reader = new StreamReader(responseStream);

			var responseString = reader.ReadToEnd();
			reader.Close();
			responseStream.Close();

			var seriesList = JsonConvert.DeserializeObject<Dictionary<string, long>>(responseString);
			var idToAdd = new Queue<string>();
			foreach (var series in seriesList)
			{
				idToAdd.Enqueue(series.Key);
			}
			while (idToAdd.Count > 0)
			{
				var seriesId = idToAdd.Dequeue();
				try
				{
					request = WebRequest.Create($"http://api.tvmaze.com/shows/{seriesId}");
					response = request.GetResponse();
					responseStream = response.GetResponseStream();
					reader = new StreamReader(responseStream);
					responseString = reader.ReadToEnd();
					reader.Close();
					responseStream.Close();

					var jobject = JObject.Parse(responseString);

					var show = new Show(jobject);
					context.Shows.Add(show);
					context.SaveChanges();

					AddEpisodes(context, show.id);
				}
				catch (WebException)
				{
					idToAdd.Enqueue(seriesId);
					Thread.Sleep(5000);
				}
			}

			base.Seed(context);
		}

		private void AddEpisodes(TVTrackerContext context, int showId)
		{
			var request = WebRequest.Create($"http://api.tvmaze.com/shows/{showId}/episodes");
			var response = request.GetResponse();
			var responseStream = response.GetResponseStream();
			var reader = new StreamReader(responseStream);

			var responseString = reader.ReadToEnd();
			reader.Close();
			responseStream.Close();

			var jobjects = JArray.Parse(responseString);
			foreach (var jobject in jobjects.Children())
			{
				var episode = new Episode(jobject, showId);
				context.Episodes.Add(episode);
			}

			context.SaveChanges();
		}
	}
}
