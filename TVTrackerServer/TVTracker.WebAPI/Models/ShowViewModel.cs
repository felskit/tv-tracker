using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TVTracker.WebAPI.Models
{
	public class ShowViewModel
	{
		public int id { get; set; }

		public string name { get; set; }

		public string image { get; set; }

		public string summary { get; set; }

		public string premiered { get; set; }

		public string[] genres { get; set; }

		public string status { get; set; }

		public string network { get; set; }

		public short runtime { get; set; }

		public float rating { get; set; }

		public string source { get; set; }

		public List<ShowEpisodeViewModel> episodes { get; set; }
	}
}