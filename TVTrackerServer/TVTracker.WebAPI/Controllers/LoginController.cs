using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using TVTracker.Entity.Entity;
using TVTracker.Entity.Entity.Models;
using TVTracker.WebAPI.Infrastructure;
using TVTracker.WebAPI.Models;

namespace TVTracker.WebAPI.Controllers
{
	[RoutePrefix("api/login")]
	public class LoginController : ApiControllerBase
	{
		private readonly ITVTrackerContext context;

		public LoginController(ITVTrackerContext context)
		{
			this.context = context;
		}

		[AllowAnonymous]
		[HttpPost]
		public async Task<HttpResponseMessage> Login(HttpRequestMessage request, UserIdViewModel data)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				int userId = 0;
				var user = await this.context.Users.SingleOrDefaultAsync(x => (data.fbId != null && x.facebookId == data.fbId) || 
																	    (data.googleId != null && x.googleId == data.googleId));
				if (user != null)
				{
					userId = user.id;
					if (!user.Tokens.Any(x => x.DeviceToken == data.token) && data.token != null)
					{
						var token = new Token() { UserId = userId, DeviceToken = data.token };
						this.context.Tokens.Add(token);
						this.context.SaveChanges();
					}
				}
				else
				{
					var newUser = new User() { facebookId = data.fbId, googleId = data.googleId };
					newUser = this.context.Users.Add(newUser);
					this.context.SaveChanges();
					userId = newUser.id;
					if (data.token != null)
					{
						var token = new Token() { UserId = userId, DeviceToken = data.token };
						this.context.Tokens.Add(token);
					}
					this.context.SaveChanges();
				}
				response = request.CreateResponse(HttpStatusCode.OK, new UserViewModel(userId));

				Task.Run(() => TVTracker.WebAPI.WebApiApplication.sendNotification());
				return response;
			});
		}

		[AllowAnonymous]
		[HttpPost]
		[Route("updateToken")]
		public async Task<HttpResponseMessage> UpdateToken(HttpRequestMessage request, TokenViewModel data)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				var user = await this.context.Users.SingleOrDefaultAsync(x => x.id == data.userId);
				var oldToken = user.Tokens.SingleOrDefault(x => x.DeviceToken == data.oldToken);
				var newToken = user.Tokens.SingleOrDefault(x => x.DeviceToken == data.newToken);
				if (oldToken != null)
				{
					oldToken.DeviceToken = data.newToken;
				}
				else
				{
					if (newToken == null)
					{ 
						var token = new Token() { UserId = user.id, DeviceToken = data.newToken };
						this.context.Tokens.Add(token);
					}
				}

				this.context.SaveChanges();
				response = request.CreateResponse(HttpStatusCode.OK, "Token updated successfully");

				return response;
			});
		}
	}
}