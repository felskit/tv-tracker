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

namespace TVTracker.WebAPI.Controllers
{
	[RoutePrefix("api/shows")]
	public class ShowsController : ApiControllerBase
	{
		private readonly ITVTrackerContext context;

		public ShowsController()
		{
			this.context = new TVTrackerContext();
		}

		[AllowAnonymous]
		[Route("list/{filter}")]
		public async Task<HttpResponseMessage> GetFilteredList(HttpRequestMessage request, string filter)
		{
			return CreateHttpResponse(request, () =>
			{
				//TODO Replace with DTO objects
				HttpResponseMessage response = null;
				var shows = this.context.Shows.Where(x => x.name.Contains(filter)).Select(x => x.name).ToList();
				response = request.CreateResponse(HttpStatusCode.OK, shows);

				return response;
			});
		}

	}
}