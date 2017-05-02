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
	[RoutePrefix("api/episodes")]
	public class EpisodesController : ApiControllerBase
	{
		private readonly ITVTrackerContext context;

		public EpisodesController(ITVTrackerContext context)
		{
			this.context = context;
		}

		[AllowAnonymous]
		[Route("{id:int}")]
		public async Task<HttpResponseMessage> GetEpisode(HttpRequestMessage request, int id)
		{
			return CreateHttpResponse(request, () =>
			{
				HttpResponseMessage response = null;
				var episode = this.context.Episodes.Where(x => x.id == id).Single();
				var episodeVm = Mapper.Map<EpisodeViewModel>(episode);
				response = request.CreateResponse(HttpStatusCode.OK, episodeVm);

				return response;
			});
		}
	}
}