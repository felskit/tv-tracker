using System.Data.Entity;
using TVTracker.Entity.Entity.Models;

namespace TVTracker.Entity.Entity
{
	public class TVTrackerContext : DbContext, ITVTrackerContext
	{
		public TVTrackerContext() : base()
		{
			Database.SetInitializer(new TVTrackerContextInitializer());
		}

		public virtual IDbSet<User> Users { get; set; }
		public virtual IDbSet<Show> Shows { get; set; }
		public virtual IDbSet<Episode> Episodes { get; set; }
		public virtual IDbSet<Favourite> Favourites { get; set; }
		public virtual IDbSet<WatchedEpisode> WatchedEpisodes { get; set; }
	}
}
