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

		[RequireHttps]
		[HttpPost]
		[AllowAnonymous]
		public async Task<HttpResponseMessage> GetCalendar(HttpRequestMessage request, CalendarDataViewModel data)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				var favourites = this.context.Favourites.Where(x => x.UserId == data.userId);
				var episodes = await this.context.Episodes.Where(x => favourites.Any(y => y.ShowId == x.ShowId) &&
																 x.airstamp.HasValue && x.airstamp.Value.Month == data.month && x.airstamp.Value.Year == data.year).ToListAsync();
				episodes = episodes.GroupBy(x => new { x.ShowId, x.airstamp }).Select(x => x.OrderBy(y => y.number).First()).ToList();
				var episodesVm = Mapper.Map<List<CalendarEpisodeViewModel>>(episodes);
				response = request.CreateResponse(HttpStatusCode.OK, episodesVm);

				return response;
			});
		}
	}
}