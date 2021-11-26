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

@Component
public class PlatformService {
	
	@Autowired
	private MovieOpsHelper movieOpsHelper;
	
	@Autowired
	private TheaterOpsHelper theaterOpsHelper;

	@Autowired
	private ShowScheduleOpsHelper showScheduleOpsHelper;

	@Autowired
	private QueryViewOpsHelper queryOpsHelper;
	
	@Autowired
	private TheaterCatalogOpsHelper theaterCatalogOpsHelper;
	
	public Movie createMovie(Movie movie) throws PlatformServiceException {
		return movieOpsHelper.createMovie(movie);
	}
	
	public Movie getMovie(Long movieId) throws ResourceNotFoundException {
		return movieOpsHelper.getMovie(movieId);
	}

	public Theater createTheater(Theater theater) throws PlatformServiceException {
		return theaterOpsHelper.createTheater(theater);
	}
	
	public Theater getTheater(Long theaterId) throws ResourceNotFoundException {
		return theaterOpsHelper.getTheater(theaterId);
	}

	public TheaterMovieCatalog createTheaterMovieCatalog(TheaterMovieCatalog theaterMovieCatalog) throws PlatformServiceException {
		return theaterCatalogOpsHelper.createTheaterCatalog(theaterMovieCatalog);
	}
	
	public ShowSchedule getShowSchedule(Long showScheduleId) throws ResourceNotFoundException {
		return showScheduleOpsHelper.getShowSchedule(showScheduleId);
	}
	
	public List<ShowSchedule> getShowSchedule(Long theaterId, Long movieId, LocalDate date) {
		return showScheduleOpsHelper.getShowSchedule(theaterId, movieId, date);
	}
	
	public ShowSchedule createShowSchedule(ShowSchedule showSchedule) throws PlatformServiceException {
		return showScheduleOpsHelper.createShowSchedule(showSchedule);
	}

	public ShowSchedule updateShowSchedule(Long showScheduleId, ShowSchedule showSchedule) throws ResourceNotFoundException, InvalidRequestException, PlatformServiceException {
		return showScheduleOpsHelper.updateShowSchedule(showScheduleId, showSchedule);
	}
	
	public void deleteShowSchedule(Long showScheduleId) throws ResourceNotFoundException, PlatformServiceException{
		showScheduleOpsHelper.deleteShowSchedule(showScheduleId);
	}
	
	public TheaterMovieCatalog findTheaterMovieCatalog(CatalogSearch catalogSearch) throws ResourceNotFoundException {
		Optional<TheaterMovieCatalog> theaterMovieCatalog  = queryOpsHelper.findTheaterMovieCatalog(catalogSearch.getTheaterId(), catalogSearch.getMovieId());
		if(theaterMovieCatalog.isEmpty()) {
			throw new ResourceNotFoundException(String.format("Fail to lookup theater movie catalog for movieId: %d, theater: %d",catalogSearch.getMovieId(), catalogSearch.getTheaterId()));
		}
		return theaterMovieCatalog.get();
	}

	public TheaterShowSearchResult findTheaters(TheaterSearch search) {
		return  queryOpsHelper.findTheaters(search.getMovieId(), search.getDate(), search.getCity());
	}
	
	public ScheduleSearchResult findSchedules(ScheduleSearch scheduleSearch) {
		return queryOpsHelper.findSchedules(scheduleSearch);
	}
}
