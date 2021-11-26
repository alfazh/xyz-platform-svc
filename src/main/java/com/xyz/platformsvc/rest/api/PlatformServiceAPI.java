package com.xyz.platformsvc.rest.api;

import org.springframework.http.ResponseEntity;

import com.xyz.platformsvc.exception.InvalidRequestException;
import com.xyz.platformsvc.exception.PlatformServiceException;
import com.xyz.platformsvc.exception.ResourceNotFoundException;
import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.rest.model.Theater;
import com.xyz.platformsvc.rest.model.TheaterMovieCatalog;
import com.xyz.platformsvc.rest.model.search.CatalogSearch;
import com.xyz.platformsvc.rest.model.search.ScheduleSearch;
import com.xyz.platformsvc.rest.model.search.ScheduleSearchResult;
import com.xyz.platformsvc.rest.model.search.TheaterSearch;
import com.xyz.platformsvc.rest.model.search.TheaterShowSearchResult;
import com.xyz.platformsvc.rest.model.show.ShowSchedule;

public interface PlatformServiceAPI {

	// movie
	ResponseEntity<Movie> createMovie(Movie movie) throws InvalidRequestException, PlatformServiceException;

	Movie getMovie(Long movieId) throws ResourceNotFoundException;

	// theater
	ResponseEntity<Theater> createTheater(Theater theater) throws InvalidRequestException, PlatformServiceException;

	Theater getTheater(Long theaterId) throws ResourceNotFoundException;

	//theater catalog
	ResponseEntity<TheaterMovieCatalog> createTheaterMovieCatalog(TheaterMovieCatalog theaterMovieCatalog) throws InvalidRequestException, PlatformServiceException;
	
	TheaterMovieCatalog searchTheaterMovieCatalog(CatalogSearch catalogSearch) throws ResourceNotFoundException;
	
	// show schedules
	ResponseEntity<ShowSchedule> createShowSchedule(ShowSchedule theaterShowSchedule) throws InvalidRequestException, PlatformServiceException;

	ShowSchedule getShowSchedule(Long showScheduledId) throws ResourceNotFoundException;

	ResponseEntity<ShowSchedule> updateShowSchedule(Long showScheduleId, ShowSchedule theaterShowSchedule) throws  ResourceNotFoundException, InvalidRequestException, PlatformServiceException;

	ResponseEntity<String> deleteShowSchedule(Long showScheduleId) throws ResourceNotFoundException, PlatformServiceException;

	//search calls
	ScheduleSearchResult searchSchedules(ScheduleSearch scheduleSearch) throws InvalidRequestException;
	
	TheaterShowSearchResult searchTheaters(TheaterSearch search) throws InvalidRequestException;
	
}