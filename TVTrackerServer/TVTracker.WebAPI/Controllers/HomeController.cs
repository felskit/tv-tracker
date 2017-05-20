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
	[RoutePrefix("api/home")]
	public class HomeController : ApiControllerBase
	{
		private readonly ITVTrackerContext context;

		private int listCount = 10;

		public HomeController(ITVTrackerContext context)
		{
			this.context = context;
		}

		[RequireHttps]
		[HttpPost]
		[AllowAnonymous]
		public async Task<HttpResponseMessage> GetEpisodes(HttpRequestMessage request, [FromBody] int userId)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				var favourites = this.context.Favourites.Where(x => x.UserId == userId);
				var episodes = await this.context.Episodes.Where(x => favourites.Any(y => y.ShowId == x.ShowId) && x.airstamp >= DateTime.Now)
														  .OrderBy(x => x.airstamp).Take(listCount).ToListAsync();
				var episodesVm = Mapper.Map<List<HomeEpisodeViewModel>>(episodes);
				response = request.CreateResponse(HttpStatusCode.OK, episodesVm);

				return response;
			});
		}
	}
}