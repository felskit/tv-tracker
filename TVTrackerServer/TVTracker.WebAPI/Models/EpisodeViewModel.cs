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

		public string airdate { get; set; }

		public string airtime { get; set; }

		public int season { get; set; }

		public int episode { get; set; }
		
		public string source { get; set; }
	}
}