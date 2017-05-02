namespace TVTracker.Entity.Entity.Models
{
	public class Favourite : Entity
	{
		public int UserId { get; set; }

		public int ShowId { get; set; }

		public virtual Show show { get; set; }
	}
}
