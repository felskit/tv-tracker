using AutoMapper;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
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
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				var episode = await this.context.Episodes.Where(x => x.id == id).SingleAsync();
				var episodeVm = Mapper.Map<EpisodeViewModel>(episode);
				response = request.CreateResponse(HttpStatusCode.OK, episodeVm);

				return response;
			});
		}

		[AllowAnonymous]
		[Route("watched")]
		[HttpPost]
		public async Task<HttpResponseMessage> SetWatched(HttpRequestMessage request, int id, int userId, bool watched)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				var watchedEpisode = await this.context.WatchedEpisodes.SingleOrDefaultAsync(x => x.episodeId == id && x.userId == userId);
				var message = "";

				if(watched)
				{
					if(watchedEpisode == null)
					{
						this.context.WatchedEpisodes.Add(new Entity.Entity.Models.WatchedEpisode() { episodeId = id, userId = userId });
						message = "Episode added to watched episodes.";
					}
				}
				else
				{
					if(watchedEpisode != null)
					{
						this.context.WatchedEpisodes.Remove(watchedEpisode);
						message = "Episode removed from watched episodes";
					}
				}

				response = request.CreateResponse(HttpStatusCode.OK, message);
				return response;
			});
		}
	}
}