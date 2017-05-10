using AutoMapper;
using System.Text;
using TVTracker.Entity.Entity.Models;
using TVTracker.WebAPI.Models;

namespace TVTracker.WebAPI.Infrastructure.Mappings
{
	public class DomainToViewModelMappingProfile : Profile
	{
		public override string ProfileName
		{
			get { return "DomainToViewModelMappings"; }
		}

		public DomainToViewModelMappingProfile()
		{
			this.CreateMap<Show, ListShowViewModel>()
				.ForMember(vm => vm.id, map => map.MapFrom(x => x.id))
				.ForMember(vm => vm.name, map => map.MapFrom(x => x.name))
				.ForMember(vm => vm.image, map => map.MapFrom(x => x.imageMedium));

			this.CreateMap<Episode, ShowEpisodeViewModel>()
				.ForMember(vm => vm.id, map => map.MapFrom(x => x.id))
				.ForMember(vm => vm.name, map => map.MapFrom(x => $"{x.season}x{x.number} {x.name}"));
			this.CreateMap<Show, ShowViewModel>()
				.ForMember(vm => vm.id, map => map.MapFrom(x => x.id))
				.ForMember(vm => vm.name, map => map.MapFrom(x => x.name))
				.ForMember(vm => vm.summary, map => map.MapFrom(x => x.summary))
				.ForMember(vm => vm.image, map => map.MapFrom(x => x.imageOriginal))
				.ForMember(vm => vm.premiered, map => map.MapFrom(x => x.premiered))
				.ForMember(vm => vm.genres, map => map.MapFrom(x => x.genres))
				.ForMember(vm => vm.status, map => map.MapFrom(x => x.status))
				.ForMember(vm => vm.network, map => map.MapFrom(x => x.network))
				.ForMember(vm => vm.runtime, map => map.MapFrom(x => x.runtime))
				.ForMember(vm => vm.rating, map => map.MapFrom(x => x.rating))
				.ForMember(vm => vm.episodes, map => map.MapFrom(x => x.episodes));

			this.CreateMap<Episode, EpisodeViewModel>()
				.ForMember(vm => vm.id, map => map.MapFrom(x => x.id))
				.ForMember(vm => vm.name, map => map.MapFrom(x => x.name))
				.ForMember(vm => vm.showName, map => map.MapFrom(x => x.show.name))
				.ForMember(vm => vm.summary, map => map.MapFrom(x => x.summary))
				.ForMember(vm => vm.image, map => map.MapFrom(x => string.IsNullOrEmpty(x.imageOriginal) ? x.show.imageOriginal : x.imageOriginal))
				.ForMember(vm => vm.runtime, map => map.MapFrom(x => x.runtime))
				.ForMember(vm => vm.season, map => map.MapFrom(x => x.season))
				.ForMember(vm => vm.episode, map => map.MapFrom(x => x.number))
				.ForMember(vm => vm.airdate, map => map.MapFrom(x => x.airdate))
				.ForMember(vm => vm.airtime, map => map.MapFrom(x => x.airtime));

			this.CreateMap<Episode, CalendarEpisodeViewModel>()
				.ForMember(vm => vm.episodeId, map => map.MapFrom(x => x.id))
				.ForMember(vm => vm.showName, map => map.MapFrom(x => x.show.name))
				.ForMember(vm => vm.beginDay, map => map.MapFrom(x => x.airstamp.Value.Day))
				.ForMember(vm => vm.beginMonth, map => map.MapFrom(x => x.airstamp.Value.Month))
				.ForMember(vm => vm.beginYear, map => map.MapFrom(x => x.airstamp.Value.Year))
				.ForMember(vm => vm.beginHour, map => map.MapFrom(x => x.airstamp.Value.Hour))
				.ForMember(vm => vm.beginMinute, map => map.MapFrom(x => x.airstamp.Value.Minute))
				.ForMember(vm => vm.endDay, map => map.MapFrom(x => x.airstamp.Value.AddMinutes(x.runtime).Day))
				.ForMember(vm => vm.endMonth, map => map.MapFrom(x => x.airstamp.Value.AddMinutes(x.runtime).Month))
				.ForMember(vm => vm.endYear, map => map.MapFrom(x => x.airstamp.Value.AddMinutes(x.runtime).Year))
				.ForMember(vm => vm.endHour, map => map.MapFrom(x => x.airstamp.Value.AddMinutes(x.runtime).Hour))
				.ForMember(vm => vm.endMinute, map => map.MapFrom(x => x.airstamp.Value.AddMinutes(x.runtime).Minute));

			this.CreateMap<Episode, HomeEpisodeViewModel>()
				.ForMember(vm => vm.episodeId, map => map.MapFrom(x => x.id))
				.ForMember(vm => vm.showId, map => map.MapFrom(x => x.ShowId))
				.ForMember(vm => vm.name, map => map.MapFrom(x => $"{x.show.name} {x.season}x{x.number}"))
				.ForMember(vm => vm.image, map => map.MapFrom(x => string.IsNullOrEmpty(x.imageOriginal) ? x.show.imageOriginal : x.imageOriginal));
		}
	}
}