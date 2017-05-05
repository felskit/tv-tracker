namespace TVTracker.WebAPI.Models
{
	public class UserViewModel
	{
		public UserViewModel (int id)
		{
			this.id = id;
		}

		public int id { get; set; }
	}
}