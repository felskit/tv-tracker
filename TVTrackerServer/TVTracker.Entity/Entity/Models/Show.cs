using Newtonsoft.Json.Linq;
using System.Collections.Generic;

namespace TVTracker.Entity.Entity.Models
{
	public class Show : Entity
	{

		public Show() { }

		public Show(JObject json)
		{
			apiId = (string)json["id"];
			name = (string)json["name"];
			type = (string)json["type"];
			language = (string)json["language"];
			genres = string.Join(", ", json["genres"].ToObject<string[]>());
			status = (string)json["status"];
			runtime = (short)(json["runtime"].Type == JTokenType.Null ? 0 : json["runtime"]);
			premiered = (string)json["premiered"];
			rating = (float)(json["rating"]["average"].Type == JTokenType.Null ? 0.0f : json["rating"]["average"]);
			imageOriginal = (string)(json["image"].HasValues ? json["image"]["original"] : "");
			imageMedium = (string)(json["image"].HasValues ? json["image"]["medium"] : "");
			summary = (string)json["summary"];
			updated = (long)json["updated"];
			network = (string)(json["network"].HasValues ? json["network"]["name"] : json["webChannel"]["name"]);
			source = (string)(json["_links"]["self"]["href"]);
		}

		public string apiId { get; set; }

		public string name { get; set; }

		public string type { get; set; }

		public string language { get; set; }

		public string genres { get; set; }

		public string status { get; set; }

		public short runtime { get; set; }
		
		public string premiered { get; set; }

		public float rating { get; set; }

		public string imageOriginal { get; set; }

		public string imageMedium { get; set; }

		public string summary { get; set; }

		public long updated { get; set; }

		public string network { get; set; }

		public string source { get; set; }

		public virtual ICollection<Episode> episodes { get; set; }
	}
}
