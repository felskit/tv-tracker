﻿using System;

namespace TVTracker.WebAPI.Models
{
	public class EpisodeViewModel
	{
		public int id { get; set; }

		public string name { get; set; }

		public string showName { get; set; }

		public string image { get; set; }

		public string summary { get; set; }

		public short runtime { get; set; }

		public int season { get; set; }

		public int episode { get; set; }

		public string source { get; set; }

		public DateTime airstamp {get; set;}
	}
}