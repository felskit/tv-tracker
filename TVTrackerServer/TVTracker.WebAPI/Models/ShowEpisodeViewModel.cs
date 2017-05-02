using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TVTracker.WebAPI.Models
{
	public class ShowEpisodeViewModel
	{
		public int id { get; set; }

		public string name { get; set; }

		public bool watched { get; set; }
	}
}