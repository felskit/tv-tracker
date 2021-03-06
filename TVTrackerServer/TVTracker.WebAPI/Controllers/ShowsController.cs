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

		[RequireHttps]
		[AllowAnonymous]
		[Route("list/{filter}")]
		public async Task<HttpResponseMessage> GetFilteredList(HttpRequestMessage request, string filter)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				var shows = await this.context.Shows.Where(x => x.name.Contains(filter)).ToListAsync();
				var showsVm = Mapper.Map<List<ListShowViewModel>>(shows);
				response = request.CreateResponse(HttpStatusCode.OK, showsVm);

				return response;
			});
		}

		[RequireHttps]
		[HttpPost]
		[AllowAnonymous]
		public async Task<HttpResponseMessage> GetShow(HttpRequestMessage request, GetShowDataViewModel data)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				var show = await this.context.Shows.Where(x => x.id == data.id).SingleAsync();
				var showVm = Mapper.Map<ShowViewModel>(show);
				foreach (var episode in showVm.episodes)
				{
					if (context.WatchedEpisodes.SingleOrDefault(x => x.userId == data.userId && x.episodeId == episode.id) != null)
					{
						episode.watched = true;
					}
				}
				showVm.favourite = context.Favourites.Any(x => x.ShowId == data.id && x.UserId == data.userId);
				response = request.CreateResponse(HttpStatusCode.OK, showVm);

				return response;
			});
		}

	}
}