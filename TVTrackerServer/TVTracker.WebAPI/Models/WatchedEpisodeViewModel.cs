using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TVTracker.WebAPI.Models
{
	public class WatchedEpisodeViewModel
	{
		public int userId { get; set; }

		public int id { get; set; }

		public bool watched { get; set; }
	}
}