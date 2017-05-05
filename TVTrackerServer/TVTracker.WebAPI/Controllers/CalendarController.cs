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
	[RoutePrefix("api/calendar")]
	public class CalendarController : ApiControllerBase
	{
		private readonly ITVTrackerContext context;

		public CalendarController(ITVTrackerContext context)
		{
			this.context = context;
		}

		[AllowAnonymous]
		public async Task<HttpResponseMessage> GetCalendar(HttpRequestMessage request, int userId, int month, int year)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				var favourites = await this.context.Favourites.Where(x => x.UserId == userId).ToListAsync();
				var episodes = await this.context.Episodes.Where(x => favourites.Any(y => y.id == x.id) &&
																 x.airstamp.HasValue && x.airstamp.Value.Month == month && x.airstamp.Value.Year == year).ToListAsync();
				var episodesVm = Mapper.Map<List<CalendarEpisodeViewModel>>(episodes);
				response = request.CreateResponse(HttpStatusCode.OK, episodesVm);

				return response;
			});
		}
	}
}