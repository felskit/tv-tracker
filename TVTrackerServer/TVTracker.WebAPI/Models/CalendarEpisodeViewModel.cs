using System;

namespace TVTracker.WebAPI.Models
{
	public class CalendarEpisodeViewModel
	{
		public int episodeId { get; set; }

		public string showName { get; set; }

		public int beginMonth { get; set; }

		public int beginYear { get; set; }

		public DateTime startStamp { get; set; }

		public DateTime endStamp { get; set; }
	}
}