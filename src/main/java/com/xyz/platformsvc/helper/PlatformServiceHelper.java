package com.xyz.platformsvc.helper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.rest.model.Theater;
import com.xyz.platformsvc.rest.model.TheaterMovieCatalog;
import com.xyz.platformsvc.rest.model.show.ShowSchedule;
import com.xyz.platformsvc.rest.model.view.CatalogSearch;
import com.xyz.platformsvc.rest.model.view.ScheduleSearch;
import com.xyz.platformsvc.rest.model.view.ScheduleSearchResult;
import com.xyz.platformsvc.rest.model.view.TheaterSearch;
import com.xyz.platformsvc.rest.model.view.TheaterShowSearchResult;

@Component
public class PlatformServiceHelper {
	
	@Autowired
	private MovieOpsHelper movieOpsHelper;
	
	@Autowired
	private TheaterOpsHelper theaterOpsHelper;

	@Autowired
	private ShowScheduleOpsHelper showScheduleOpsHelper;

	@Autowired
	private QueryViewOpsHelper queryOpsHelper;
	
	private TheaterCatalogOpsHelper theaterCatalogOpsHelper;
	
	public Movie createMovie(Movie movie) {
		return movieOpsHelper.createMovie(movie);
	}

	public Theater createTheater(Theater theater) {
		return theaterOpsHelper.createTheater(theater);
	}

	public TheaterMovieCatalog createTheaterMovieCatalog(TheaterMovieCatalog theaterMovieCatalog) {
		return theaterCatalogOpsHelper.createTheaterCatalog(theaterMovieCatalog);
	}
	
	public ShowSchedule getShowSchedule(Long showScheduleId) {
		return showScheduleOpsHelper.getShowSchedule(showScheduleId);
	}
	
	public List<ShowSchedule> getShowSchedule(Long theaterId, Long movieId, LocalDate date) {
		return showScheduleOpsHelper.getShowSchedule(theaterId, movieId, date);
	}
	
	public ShowSchedule createShowSchedule(ShowSchedule showSchedule) {
		return showScheduleOpsHelper.createShowSchedule(showSchedule);
	}

	public ShowSchedule updateShowSchedule(Long showScheduleId, ShowSchedule showSchedule) {
		return showScheduleOpsHelper.updateShowSchedule(showScheduleId, showSchedule);
	}
	
	public void deleteShowSchedule(Long showScheduleId) {
		showScheduleOpsHelper.deleteShowSchedule(showScheduleId);
	}

	public Optional<TheaterShowSearchResult> findTheaters(TheaterSearch search) {
		return queryOpsHelper.findTheaters(search.getMovieId(), search.getDate(), search.getCity()); 
	}

	public Optional<TheaterMovieCatalog> findTheaterMovieCatalog(CatalogSearch catalogSearch) {
		return queryOpsHelper.findTheaterMovieCatalog(catalogSearch.getTheaterId(), catalogSearch.getMovieId());
	}

	public ScheduleSearchResult findSchedules(ScheduleSearch scheduleSearch) {
		return queryOpsHelper.findSchedules(scheduleSearch); 
	}
}
