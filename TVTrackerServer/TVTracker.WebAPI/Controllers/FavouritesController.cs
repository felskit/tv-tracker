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

		[RequireHttps]
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

		[RequireHttps]
		[AllowAnonymous]
		[HttpPost]
		[Route("add")]
		public async Task<HttpResponseMessage> AddFavourite(HttpRequestMessage request, FavouritesViewModel data)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				string message = null;
				if((await context.Favourites.SingleOrDefaultAsync(x => x.UserId == data.userId && x.ShowId == data.showId)) == null)
				{
					context.Favourites.Add(new Entity.Entity.Models.Favourite() { UserId = data.userId, ShowId = data.showId });
					message = "Show added to favourites";
				}
				else
				{
					message = "This show is already in your favourites";
				}

				this.context.SaveChanges();
				response = request.CreateResponse(HttpStatusCode.OK, message);
				return response;
			});
		}

		[AllowAnonymous]
		[HttpPost]
		[Route("remove")]
		public async Task<HttpResponseMessage> RemoveFavourite(HttpRequestMessage request, FavouritesViewModel data)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				string message = "Show was not in your favourites";
				var favourite = await context.Favourites.SingleOrDefaultAsync(x => x.UserId == data.userId && x.ShowId == data.showId);
				if (favourite != null)
				{
					context.Favourites.Remove(favourite);
					message = "Show was removed from your favourites";
				}

				this.context.SaveChanges();
				response = request.CreateResponse(HttpStatusCode.OK, message);
				return response;
			});
		}
	}
}