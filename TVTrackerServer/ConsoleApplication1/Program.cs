using System;
using TVTracker.Entity.Entity;

namespace ConsoleApplication1
{
	class Program
	{
		static void Main(string[] args)
		{
			var context = new TVTrackerContext();
			foreach(var show in context.Shows)
			{
				Console.Out.WriteLine(show.name);
			}
		}
	}
}
