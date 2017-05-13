using System.Collections.Generic;

namespace TVTracker.Entity.Entity.Models
{
	public class User : Entity
	{
		public string googleId { get; set; }

		public string facebookId { get; set; }

		public virtual ICollection<Token> Tokens { get; set; }
	}
}
