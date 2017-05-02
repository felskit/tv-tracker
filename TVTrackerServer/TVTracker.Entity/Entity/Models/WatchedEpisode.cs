using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TVTracker.Entity.Entity.Models
{
	public class WatchedEpisode : Entity
	{
		public int userId { get; set; }

		public int episodeId { get; set; }
	}
}
