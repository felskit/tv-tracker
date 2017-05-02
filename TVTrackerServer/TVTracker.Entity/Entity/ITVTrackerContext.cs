using System.Data.Entity;
using TVTracker.Entity.Entity.Models;

namespace TVTracker.Entity.Entity
{
	public interface ITVTrackerContext
	{
		IDbSet<User> Users { get; set; }

		IDbSet<Show> Shows { get; set; }

		IDbSet<Episode> Episodes { get; set; }

		IDbSet<Favourite> Favourites { get; set; }

		IDbSet<WatchedEpisode> WatchedEpisodes { get; set; }
	}
}
