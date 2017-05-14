using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;
using TVTracker.Entity.Entity;
using TVTracker.WebAPI.App_Start;
using TVTracker.WebAPI.Infrastructure.Mappings;

namespace TVTracker.WebAPI
{
	public class WebApiApplication : System.Web.HttpApplication
	{
		protected void Application_Start()
		{
			AreaRegistration.RegisterAllAreas();
			GlobalConfiguration.Configure(WebApiConfig.Register);
			FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
			RouteConfig.RegisterRoutes(RouteTable.Routes);
			BundleConfig.RegisterBundles(BundleTable.Bundles);
			AutofacWebapiConfig.Initialize(GlobalConfiguration.Configuration);
			AutoMapperConfiguration.Configure();
			//Task.Run(() => sendNotification());
		}

		public static async void sendNotification()
		{
			await Task.Delay(new TimeSpan(0, 0, 10));
			string applicationID = "AAAAGj9D_RI:APA91bGcW59W17ooFVZrs9JXaeFgWNvFLBL0gfm84vB4dWkwJIom5lB7pL-75m_LozOOQTpuqVCLpOXqf3h8c8qB1U6w966WEvWAEzS8tavKGQM1qHBMdIHHRSWubqLkNY8aKQ4O0pf8";

			string senderId = "112730570002";

			TVTrackerContext context = new TVTrackerContext();

			foreach (var token in context.Tokens)
			{
				if (token.DeviceToken == null) continue;
				try
				{
					string deviceId = token.DeviceToken;

					WebRequest tRequest = WebRequest.Create("https://fcm.googleapis.com/fcm/send");
					tRequest.Method = "post";
					tRequest.ContentType = "application/json";
					var data = new
					{
						to = deviceId,
						notification = new
						{
							body = "A new episode of Twoja Stara is starting soon.",
							title = "TVTracker",
							sound = "Enabled"
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
}
