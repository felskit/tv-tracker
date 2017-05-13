namespace TVTracker.WebAPI.Models
{
	public class TokenViewModel
	{
		public int userId { get; set; }
		public string oldToken { get; set; }
		public string newToken { get; set; }
	}
}