using AutoMapper;
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
using TVTracker.WebAPI.Infrastructure;
using TVTracker.WebAPI.Models;

namespace TVTracker.WebAPI.Controllers
{
	[RoutePrefix("api/favourites")]
	public class FavouritesController : ApiControllerBase
	{
		private readonly ITVTrackerContext context;

		public FavouritesController(ITVTrackerContext context)
		{
			this.context = context;
		}

		[AllowAnonymous]
		public async Task<HttpResponseMessage> GetFavourites(HttpRequestMessage request, int userId)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				var shows = await this.context.Favourites.Where(x => x.UserId == userId).Select(x => x.show).ToListAsync();
				var showsVm = Mapper.Map<List<ListShowViewModel>>(shows);
				response = request.CreateResponse(HttpStatusCode.OK, showsVm);

				return response;
			});
		}

		[AllowAnonymous]
		[HttpPost]
		public async Task<HttpResponseMessage> AddFavourite(HttpRequestMessage request, int userId, int showId)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				string message = null;
				if((await context.Favourites.SingleOrDefaultAsync(x => x.UserId == userId && x.ShowId == showId)) == null)
				{
					context.Favourites.Add(new Entity.Entity.Models.Favourite() { UserId = userId, ShowId = showId });
					message = "Show added to favourites";
				}
				else
				{
					message = "This show is already in your favourites";
				}
				response = request.CreateResponse(HttpStatusCode.OK, message);

				return response;
			});
		}

		[AllowAnonymous]
		[HttpPost]
		public async Task<HttpResponseMessage> RemoveFavourite(HttpRequestMessage request, int userId, int showId)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				string message = "Show was not in your favourites";
				var favourite = await context.Favourites.SingleOrDefaultAsync(x => x.UserId == userId && x.ShowId == showId);
				if (favourite != null)
				{
					context.Favourites.Remove(favourite);
					message = "Show was removed from your favourites";
				}
				response = request.CreateResponse(HttpStatusCode.OK, message);

				return response;
			});
		}
	}
}