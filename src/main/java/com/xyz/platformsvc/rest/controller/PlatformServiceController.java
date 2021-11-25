package com.xyz.platformsvc.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.platformsvc.helper.PlatformServiceHelper;
import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.rest.model.Theater;
import com.xyz.platformsvc.rest.model.TheaterMovieCatalog;
import com.xyz.platformsvc.rest.model.show.ShowSchedule;
import com.xyz.platformsvc.rest.model.view.CatalogSearch;
import com.xyz.platformsvc.rest.model.view.ScheduleSearch;
import com.xyz.platformsvc.rest.model.view.ScheduleSearchResult;
import com.xyz.platformsvc.rest.model.view.TheaterSearch;
import com.xyz.platformsvc.rest.model.view.TheaterShowSearchResult;

@SpringBootApplication
@RestController
@RequestMapping("/platformsvc")
public class PlatformServiceController implements PlatformServiceAPI {

	@Autowired
	PlatformServiceHelper platformServiceHelper;

	@Override
	@PostMapping(value = "/movies", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Movie createMovie(@RequestBody Movie movie) {
		return platformServiceHelper.createMovie(movie);
	}
	
	@GetMapping(value = "/movies/{movieId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Movie getMovie(@PathVariable("movieId") Long movieId) {
		return null;
	}
	
	@PostMapping(value = "/theaters", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Theater createTheater(@RequestBody Theater theater) {
		return platformServiceHelper.createTheater(theater);
	}

	@Override
	public Theater getTheater(Long id) {
		return null;
	}
	
	@Override
	@PostMapping(value = "/theatermoviecatalog", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public TheaterMovieCatalog createTheaterMovieCatalog(@RequestBody TheaterMovieCatalog theaterMovieCatalog) {
		return platformServiceHelper.createTheaterMovieCatalog(theaterMovieCatalog);
	}

	@Override
	@PostMapping(value = "/theatermoviecatalog/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public TheaterMovieCatalog searchTheaterMovieCatalog(@RequestBody CatalogSearch catalogSearch) {
		return platformServiceHelper.findTheaterMovieCatalog(catalogSearch).get();
	}
	
	@PostMapping(value = "/schedules", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ShowSchedule createShowSchedule(@RequestBody ShowSchedule theaterShowSchedule) {
		ShowSchedule newTheaterShow = platformServiceHelper.createShowSchedule(theaterShowSchedule);
		return newTheaterShow;
	}
	
	@GetMapping(value = "/schedules/{scheduleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ShowSchedule getShowSchedule(@PathVariable("scheduleId") Long scheduleId) {
		return platformServiceHelper.getShowSchedule(scheduleId);
	}
	
	@PutMapping(value = "/schedules/{scheduleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ShowSchedule updateShowSchedule(@PathVariable("scheduleId") Long scheduleId, @RequestBody ShowSchedule theaterShowSchedule) {
		return platformServiceHelper.updateShowSchedule(scheduleId, theaterShowSchedule);
	}
	
	@DeleteMapping(value = "/schedules/{scheduleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteShowSchedule(@PathVariable("scheduleId") Long scheduleId) {
		platformServiceHelper.deleteShowSchedule(scheduleId);
	}

	@Override
	@PostMapping(value = "/theaters/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public TheaterShowSearchResult searchTheaters(@RequestBody TheaterSearch search) {
		return platformServiceHelper.findTheaters(search).get();
	}

	@Override
	@PostMapping(value = "/schedules/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ScheduleSearchResult searchSchedules(@RequestBody ScheduleSearch scheduleSearch) {
		return platformServiceHelper.findSchedules(scheduleSearch);
	} 
	
}
