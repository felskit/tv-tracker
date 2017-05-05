namespace TVTracker.WebAPI.Models
{
	public class CalendarEpisodeViewModel
	{
		public int episodeId { get; set; }

		public string showName { get; set; }

		public int beginDay { get; set; }

		public int beginMonth { get; set; }

		public int beginYear { get; set; }

		public int beginHour { get; set; }

		public int beginMinute { get; set; }

		public int endDay { get; set; }

		public int endMonth { get; set; }

		public int endYear { get; set; }

		public int endHour { get; set; }

		public int endMinute { get; set; }
	}
}