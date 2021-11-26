package com.xyz.platformsvc.helper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.xyz.platformsvc.service.MovieService;
import com.xyz.platformsvc.service.QueryService;
import com.xyz.platformsvc.service.ShowScheduleService;
import com.xyz.platformsvc.service.TheaterCatalogService;
import com.xyz.platformsvc.service.TheaterService;

@Component
public class PlatformServiceHelper {
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private TheaterService theaterService;

	@Autowired
	private ShowScheduleService showScheduleService;

	@Autowired
	private QueryService queryService;
	
	@Autowired
	private TheaterCatalogService theaterCatalogService;
	
	public Movie createMovie(Movie movie) throws PlatformServiceException {
		return movieService.createMovie(movie);
	}
	
	public Movie getMovie(Long movieId) throws ResourceNotFoundException {
		return movieService.getMovie(movieId);
	}

	public Theater createTheater(Theater theater) throws PlatformServiceException {
		return theaterService.createTheater(theater);
	}
	
	public Theater getTheater(Long theaterId) throws ResourceNotFoundException {
		return theaterService.getTheater(theaterId);
	}

	public TheaterMovieCatalog createTheaterMovieCatalog(TheaterMovieCatalog theaterMovieCatalog) throws PlatformServiceException {
		return theaterCatalogService.createTheaterCatalog(theaterMovieCatalog);
	}
	
	public ShowSchedule getShowSchedule(Long showScheduleId) throws ResourceNotFoundException {
		return showScheduleService.getShowSchedule(showScheduleId);
	}
	
	public List<ShowSchedule> getShowSchedule(Long theaterId, Long movieId, LocalDate date) {
		return showScheduleService.getShowSchedule(theaterId, movieId, date);
	}
	
	public ShowSchedule createShowSchedule(ShowSchedule showSchedule) throws PlatformServiceException {
		return showScheduleService.createShowSchedule(showSchedule);
	}

	public ShowSchedule updateShowSchedule(Long showScheduleId, ShowSchedule showSchedule) throws ResourceNotFoundException, InvalidRequestException, PlatformServiceException {
		return showScheduleService.updateShowSchedule(showScheduleId, showSchedule);
	}
	
	public void deleteShowSchedule(Long showScheduleId) throws ResourceNotFoundException, PlatformServiceException{
		showScheduleService.deleteShowSchedule(showScheduleId);
	}
	
	public TheaterMovieCatalog findTheaterMovieCatalog(CatalogSearch catalogSearch) throws ResourceNotFoundException {
		Optional<TheaterMovieCatalog> theaterMovieCatalog  = queryService.findTheaterMovieCatalog(catalogSearch.getTheaterId(), catalogSearch.getMovieId());
		if(theaterMovieCatalog.isEmpty()) {
			throw new ResourceNotFoundException(String.format("Fail to lookup theater movie catalog for movieId: %d, theater: %d",catalogSearch.getMovieId(), catalogSearch.getTheaterId()));
		}
		return theaterMovieCatalog.get();
	}

	public TheaterShowSearchResult findTheaters(TheaterSearch search) {
		return  queryService.findTheaters(search.getMovieId(), search.getDate(), search.getCity());
	}
	
	public ScheduleSearchResult findSchedules(ScheduleSearch scheduleSearch) {
		return queryService.findSchedules(scheduleSearch);
	}
}
