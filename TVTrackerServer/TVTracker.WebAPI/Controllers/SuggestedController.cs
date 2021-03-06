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
	[RoutePrefix("api/suggested")]
	public class SuggestedController : ApiControllerBase
	{
		private readonly ITVTrackerContext context;

		private int listCount = 20;

		public SuggestedController(ITVTrackerContext context)
		{
			this.context = context;
		}

		[RequireHttps]
		[HttpPost]
		[AllowAnonymous]
		public async Task<HttpResponseMessage> GetSuggested(HttpRequestMessage request, [FromBody] int userId)
		{
			return await CreateHttpResponse(request, async () =>
			{
				HttpResponseMessage response = null;
				var shows = this.context.Favourites.Where(x => x.UserId == userId).Select(x => x.show);
				var dictionary = new Dictionary<string, int>();
				foreach (var show in shows)
				{
					var genres = show.genres.Split(new string[] { ", " }, StringSplitOptions.None);
					foreach (var genre in genres)
					{
						if(!dictionary.ContainsKey(genre))
						{
							dictionary.Add(genre, 1);
						}
						else
						{
							dictionary[genre]++;
						}
					}
				}

				List<Show> suggestedShows = null;
				if (dictionary.Count > 0)
				{
					var frequentGenres = new List<string>();
					var orderedDictionary = dictionary.OrderByDescending(x => x.Value).ToList();
					var maxCount = orderedDictionary[0].Value;
					for( int i =0; i < orderedDictionary.Count; i++)
					{
						if (orderedDictionary[i].Value != maxCount)
						{
							break;
						}
						frequentGenres.Add(orderedDictionary[i].Key);
					}

					suggestedShows = await this.context.Shows.Where(x => frequentGenres.Any(f => x.genres.Contains(f)) && 
																	!shows.Any(y => y.id == x.id))
																	.OrderByDescending(x => x.rating).Take(listCount).ToListAsync();
				}
				else
				{
					suggestedShows = await this.context.Shows.OrderByDescending(x => x.rating).Take(listCount).ToListAsync();
				}

				var showsVm = Mapper.Map<List<ListShowViewModel>>(suggestedShows);
				response = request.CreateResponse(HttpStatusCode.OK, showsVm);

				return response;
			});
		}
	}
}