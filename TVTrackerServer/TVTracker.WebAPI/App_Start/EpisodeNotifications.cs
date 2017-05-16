using Newtonsoft.Json;
using System;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using TVTracker.Entity.Entity;

namespace TVTracker.WebAPI.App_Start
{
	public class EpisodeNotifications
	{
		private static string applicationID = "AAAAGj9D_RI:APA91bGcW59W17ooFVZrs9JXaeFgWNvFLBL0gfm84vB4dWkwJIom5lB7pL-75m_LozOOQTpuqVCLpOXqf3h8c8qB1U6w966WEvWAEzS8tavKGQM1qHBMdIHHRSWubqLkNY8aKQ4O0pf8";
		private static string senderId = "112730570002";

		public static void Start()
		{
			Task.Run(() => notificationTask());
		}

		private static async void notificationTask()
		{
			while(true)
			{
				TVTrackerContext context = new TVTrackerContext();
				DateTime afterDate = DateTime.Now.AddMinutes(30);
				DateTime beforeDate = DateTime.Now.AddHours(1);

				var episodes = context.Episodes.Where(x => x.airstamp != null && x.airstamp > afterDate && x.airstamp <= beforeDate).ToList();

				foreach (var episode in episodes)
				{
					var users = context.Favourites.Where(x => x.ShowId == episode.ShowId).Select(x => x.user.Tokens).ToList();
					Parallel.ForEach(users, (userTokens) =>
					{
						foreach (var token in userTokens)
						{
							sendNotification(episode.show.name, token.DeviceToken, episode.id);
						}
					});
				}

				context.Dispose();
				await Task.Delay(new TimeSpan(0, 30, 0));
			}
		}

		private static void sendNotification(string showTitle, string deviceId, int episodeId)
		{
			if (deviceId == null)
			{
				return;
			}
			try
			{ 
				WebRequest tRequest = WebRequest.Create("https://fcm.googleapis.com/fcm/send");
				tRequest.Method = "post";
				tRequest.ContentType = "application/json";
				var data = new
				{
					to = deviceId,
					notification = new
					{
						body = $"A new episode of {showTitle} is starting soon.",
						title = "TVTracker",
						sound = "Enabled"
					},
					data = new
					{
						episodeId = episodeId
					}
				};

				var json = JsonConvert.SerializeObject(data);

				byte[] byteArray = Encoding.UTF8.GetBytes(json);
				tRequest.Headers.Add(string.Format("Authorization: key={0}", applicationID));
				tRequest.Headers.Add(string.Format("Sender: id={0}", senderId));
				tRequest.ContentLength = byteArray.Length;
				using (Stream dataStream = tRequest.GetRequestStream())
				{
					dataStream.Write(byteArray, 0, byteArray.Length);
					using (WebResponse tResponse = tRequest.GetResponse())
					{
						using (Stream dataStreamResponse = tResponse.GetResponseStream())
						{
							using (StreamReader tReader = new StreamReader(dataStreamResponse))
							{
								string sResponseFromServer = tReader.ReadToEnd();
								string str = sResponseFromServer;
							}
						}
					}
				}
			}

			catch (Exception ex)
			{
				string str = ex.Message;
			}
		}
	}

}