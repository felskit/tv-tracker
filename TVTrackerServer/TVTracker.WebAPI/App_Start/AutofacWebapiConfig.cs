using Autofac;
using Autofac.Integration.WebApi;
using System.Reflection;
using System.Web.Http;
using TVTracker.Entity.Entity;

namespace TVTracker.WebAPI.App_Start
{
	public class AutofacWebapiConfig
	{
		public static IContainer Container;
		public static void Initialize(HttpConfiguration config)
		{
			Initialize(config, RegisterServices(new ContainerBuilder()));
		}

		public static void Initialize(HttpConfiguration config, IContainer container)
		{
			config.DependencyResolver = new AutofacWebApiDependencyResolver(container);
		}

		private static IContainer RegisterServices(ContainerBuilder builder)
		{
			builder.RegisterApiControllers(Assembly.GetExecutingAssembly());

			// EF Context
			builder.RegisterType<TVTrackerContext>()
				   .As<ITVTrackerContext>()
				   .InstancePerRequest();

			Container = builder.Build();
			return Container;
		}
	}
}