package com.xyz.platformsvc.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.xyz.dal.entity.movie.MovieEntity;
import com.xyz.dal.entity.show.ShowPricingEntity;
import com.xyz.dal.entity.show.ShowScheduleEntity;
import com.xyz.dal.entity.show.ShowSeatAllocationEntity;
import com.xyz.dal.entity.show.TheaterShowEntity;
import com.xyz.dal.entity.theater.TheaterEntity;
import com.xyz.dal.entity.theater.TheaterScreenEntity;
import com.xyz.platformsvc.rest.controller.PlatformServiceController;
import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.rest.model.SeatingClassGroup;
import com.xyz.platformsvc.rest.model.ShowSchedule;
import com.xyz.platformsvc.rest.model.Theater;
import com.xyz.platformsvc.rest.model.TheaterScreen;
import com.xyz.platformsvc.rest.model.TheaterShow;

public class TheaterShowMapper implements DataMapper<TheaterShowEntity, TheaterShow> {

	@Override
	public TheaterShowEntity toEntity(TheaterShow theaterShow) {
		TheaterShowEntity showEntity = new TheaterShowEntity();

		// movie
		MovieEntity movieEntity = new MovieEntity();
		movieEntity.setMovieId(theaterShow.getMovie().getMovieId());
		showEntity.setMovie(movieEntity);

		// theater
		TheaterEntity theaterEntity = new TheaterEntity();
		theaterEntity.setTheaterId(theaterShow.getTheater().getTheaterId());
		showEntity.setTheater(theaterEntity);

		// show schedules
		LocalDate showDate = theaterShow.getDate();
		List<ShowScheduleEntity> showScheduleEntityList = new ArrayList<>();
		for (ShowSchedule showSchedule : theaterShow.getShowSchedule()) {
			ShowScheduleEntity scheduleEntity = new ShowScheduleEntity();
			// showEntity
			scheduleEntity.setShow(showEntity);
			// date
			scheduleEntity.setDate(showDate);
			// time
			scheduleEntity.setTime(showSchedule.getTime());
			// screen
			TheaterScreenEntity screenEntity = new TheaterScreenEntity();
			screenEntity.setScreenId(showSchedule.getTheaterScreen().getId());
			scheduleEntity.setScreen(screenEntity);

			// showpricing
			List<ShowPricingEntity> showPricingEntityList = new ArrayList<>();
			Set<ShowSeatAllocationEntity> showSeatAllocationEntityList = new HashSet<>();
			List<SeatingClassGroup> seatingData = showSchedule.getSeatingData();
			for (SeatingClassGroup seatingGroup : seatingData) {
				seatingGroup.getSeatingRowData().stream().forEach(r -> {
					ShowPricingEntity showPricingEntity = new ShowPricingEntity();
					showPricingEntity.setPrice(seatingGroup.getPrice());
					showPricingEntity.setSeatClass(seatingGroup.getSeatClass());
					showPricingEntity.setSeatRow(r.getRow());
					showPricingEntity.setShowSchedule(scheduleEntity);
					showPricingEntityList.add(showPricingEntity);
					
					showSeatAllocationEntityList.addAll(getSeatAllocationList(scheduleEntity, r.getRow(), r.getNumSeats()));
				});
			}
			scheduleEntity.setShowPricing(showPricingEntityList);
			scheduleEntity.setShowSeatAllocation(showSeatAllocationEntityList);
			
			showScheduleEntityList.add(scheduleEntity);
		}
		
		showEntity.setShowSchedule(showScheduleEntityList);
		
		return showEntity;
	}

	private Set<? extends ShowSeatAllocationEntity> getSeatAllocationList(ShowScheduleEntity scheduleEntity, String row,
			int numSeats) {
		return	IntStream.rangeClosed(1, numSeats).mapToObj(i -> ShowSeatAllocationEntity.of(scheduleEntity, row, i)).collect(Collectors.toSet());
	}

	@Override
	public TheaterShow toRestObj(TheaterShowEntity showEntity) {
		TheaterShow show = new TheaterShow();
		//id 
		show.setTheaterShowId(showEntity.getTheaterShowId());
		//movie
		Movie movie = new Movie();
		movie.setMovieId(showEntity.getMovie().getMovieId());
		movie.setName(showEntity.getMovie().getName());
		movie.add(linkTo(PlatformServiceController.class).slash("movies").slash(showEntity.getMovie().getMovieId()).withSelfRel());
		show.setMovie(movie);
		//theater
		Theater theater = new Theater();
		theater.setTheaterId(showEntity.getTheater().getTheaterId());
		theater.setName(showEntity.getTheater().getName());
		theater.add(linkTo(PlatformServiceController.class).slash("theaters").slash(showEntity.getTheater().getTheaterId()).withSelfRel());
		show.setTheater(theater);
		
		List<ShowSchedule> showScheduleList = new ArrayList<>();
		for(ShowScheduleEntity scheduleEntity : showEntity.getShowSchedule()) {
			ShowSchedule showSchedule = new ShowSchedule();
			//theater screeen
			TheaterScreen screen = new TheaterScreen();
			screen.setId(scheduleEntity.getScreen().getScreenId());
			screen.setName(scheduleEntity.getScreen().getName());
			screen.add(linkTo(PlatformServiceController.class).slash("theaters").slash(showEntity.getTheater().getTheaterId()).slash("screens").slash(screen.getId()).withSelfRel());
			showSchedule.setTheaterScreen(screen);
			//time
			showSchedule.setTime(scheduleEntity.getTime());
			showScheduleList.add(showSchedule);
		}
		show.setShowSchedule(showScheduleList);
		
		return show;
	}

}
