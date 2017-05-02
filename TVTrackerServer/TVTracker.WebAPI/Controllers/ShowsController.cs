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
using TVTracker.Entity.Entity.Models;
using TVTracker.WebAPI.Infrastructure;
using TVTracker.WebAPI.Models;

namespace TVTracker.WebAPI.Controllers
{
	[RoutePrefix("api/shows")]
	public class ShowsController : ApiControllerBase
	{
		private readonly ITVTrackerContext context;

		public ShowsController(ITVTrackerContext context)
		{
			this.context = context;
		}

		[AllowAnonymous]
		[Route("list/{filter}")]
		public async Task<HttpResponseMessage> GetFilteredList(HttpRequestMessage request, string filter)
		{
			return CreateHttpResponse(request, () =>
			{
				HttpResponseMessage response = null;
				var shows = this.context.Shows.Where(x => x.name.Contains(filter)).ToList();
				var showsVm = Mapper.Map<List<ListShowViewModel>>(shows);
				response = request.CreateResponse(HttpStatusCode.OK, showsVm);

				return response;
			});
		}

		[AllowAnonymous]
		[Route("{id:int}")]
		public async Task<HttpResponseMessage> GetShow(HttpRequestMessage request, int id, int userId)
		{
			return CreateHttpResponse(request, () =>
			{
				HttpResponseMessage response = null;
				var show = this.context.Shows.Where(x => x.id == id).Single();
				var showVm = Mapper.Map<ShowViewModel>(show);
				//foreach (var episode in showVm.episodes)
				//{
				//	if(context.WatchedEpisodes.SingleOrDefault(x => x.userId == userId && x.episodeId == episode.id) != null)
				//	{
				//		episode.watched = true;
				//	}
				//}
				response = request.CreateResponse(HttpStatusCode.OK, showVm);

				return response;
			});
		}

	}
}