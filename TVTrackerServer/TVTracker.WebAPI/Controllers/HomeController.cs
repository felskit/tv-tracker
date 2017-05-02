﻿using AutoMapper;
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

		[AllowAnonymous]
		public async Task<HttpResponseMessage> GetEpisodes(HttpRequestMessage request, int userId)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				var favourites = await this.context.Favourites.Where(x => x.UserId == userId).ToListAsync();
				var episodes = await this.context.Episodes.Where(x => favourites.SingleOrDefault(y => y.ShowId == x.ShowId) != null && x.airstamp >= DateTime.Now)
														  .OrderBy(x => x.airstamp).Take(listCount).ToListAsync();
				var episodesVm = Mapper.Map<List<HomeEpisodeViewModel>>(episodes);
				response = request.CreateResponse(HttpStatusCode.OK, episodesVm);

				return response;
			});
		}
	}
}