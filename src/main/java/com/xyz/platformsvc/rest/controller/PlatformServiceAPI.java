package com.xyz.platformsvc.rest.controller;

import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.rest.model.Theater;
import com.xyz.platformsvc.rest.model.TheaterMovieCatalog;
import com.xyz.platformsvc.rest.model.show.ShowSchedule;
import com.xyz.platformsvc.rest.model.view.CatalogSearch;
import com.xyz.platformsvc.rest.model.view.ScheduleSearch;
import com.xyz.platformsvc.rest.model.view.ScheduleSearchResult;
import com.xyz.platformsvc.rest.model.view.TheaterSearch;
import com.xyz.platformsvc.rest.model.view.TheaterShowSearchResult;

public interface PlatformServiceAPI {

	// movie
	Movie createMovie(Movie movie);

	Movie getMovie(Long movieId);

	// theater
	Theater createTheater(Theater theater);

	Theater getTheater(Long theaterId);

	//theater catalog
	TheaterMovieCatalog createTheaterMovieCatalog(TheaterMovieCatalog theaterMovieCatalog);
	
	TheaterMovieCatalog searchTheaterMovieCatalog(CatalogSearch catalogSearch);
	
	// show schedules
	ShowSchedule createShowSchedule(ShowSchedule theaterShowSchedule);

	ShowSchedule getShowSchedule(Long showScheduledId);

	ShowSchedule updateShowSchedule(Long showScheduleId, ShowSchedule theaterShowSchedule);

	void deleteShowSchedule(Long showScheduleId);

	ScheduleSearchResult searchSchedules(ScheduleSearch scheduleSearch);
	
	//views 
	TheaterShowSearchResult searchTheaters(TheaterSearch search);
	
}