using AutoMapper;
using System;
using System.Collections.Generic;
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
			return CreateHttpResponse(request, () =>
			{
				HttpResponseMessage response = null;
				var shows = this.context.Favourites.Where(x => x.UserId == userId).Select(x => x.Show).ToList();
				var showsVm = Mapper.Map<List<ListShowViewModel>>(shows);
				response = request.CreateResponse(HttpStatusCode.OK, showsVm);

				return response;
			});
		}
	}
}