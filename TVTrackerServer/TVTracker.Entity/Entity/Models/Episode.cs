using Newtonsoft.Json.Linq;
using System;

namespace TVTracker.Entity.Entity.Models
{
	public class Episode : Entity
	{
		public Episode() { }

		public Episode(JToken json, int showId)
		{
			ShowId = showId;
			name = (string)json["name"];
			season = (int)(json["season"].Type == JTokenType.Null ? 0 : json["season"]);
			number = (int)(json["number"].Type == JTokenType.Null ? 0 : json["number"]);
			airdate = (string)(json["airdate"]);
			airtime = (string)(json["airtime"]);
			airstamp = (DateTime?)(json["airstamp"]);
			runtime = (short)(json["runtime"].Type == JTokenType.Null ? 0 : json["runtime"]);
			imageOriginal = (string)(json["image"].HasValues ? json["image"]["original"] : "");
			imageMedium = (string)(json["image"].HasValues ? json["image"]["medium"] : "");
			summary = (string)(json["summary"]);
			source = (string)(json["_links"]["self"]["href"]);
		}

		public int ShowId { get; set; }

		public string name { get; set; }

		public int season { get; set; }

		public int number { get; set; }

		public string airdate { get; set; }

		public string airtime { get; set; }

		public DateTime? airstamp { get; set; }

		public short runtime { get; set; }

		public string imageOriginal { get; set; }

		public string imageMedium { get; set; }

		public string summary { get; set; }

		public string source { get; set; }

		public virtual Show show { get; set; }
	}
}
