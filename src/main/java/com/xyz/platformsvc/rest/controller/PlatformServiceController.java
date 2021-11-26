package com.xyz.platformsvc.rest.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.platformsvc.exception.InvalidRequestException;
import com.xyz.platformsvc.exception.PlatformServiceException;
import com.xyz.platformsvc.exception.ResourceNotFoundException;
import com.xyz.platformsvc.helper.PlatformService;
import com.xyz.platformsvc.rest.api.PlatformServiceAPI;
import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.rest.model.Theater;
import com.xyz.platformsvc.rest.model.TheaterMovieCatalog;
import com.xyz.platformsvc.rest.model.search.CatalogSearch;
import com.xyz.platformsvc.rest.model.search.ScheduleSearch;
import com.xyz.platformsvc.rest.model.search.ScheduleSearchResult;
import com.xyz.platformsvc.rest.model.search.TheaterSearch;
import com.xyz.platformsvc.rest.model.search.TheaterShowSearchResult;
import com.xyz.platformsvc.rest.model.show.ShowSchedule;

@SpringBootApplication
@RestController
@RequestMapping("/platformsvc")
public class PlatformServiceController implements PlatformServiceAPI {

	@Autowired
	PlatformService platformServiceHelper;

	@Override
	@PostMapping(value = "/movies", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) throws InvalidRequestException, PlatformServiceException {
		Movie newMovie = platformServiceHelper.createMovie(movie);
		URI location = URI.create("/movies"+newMovie.getMovieId());
		return ResponseEntity.created(location).body(newMovie);
	}
	
	@GetMapping(value = "/movies/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Movie getMovie(@PathVariable("movieId") Long movieId) throws ResourceNotFoundException {
		return platformServiceHelper.getMovie(movieId);
	}
	
	@PostMapping(value = "/theaters", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) throws InvalidRequestException, PlatformServiceException {
		Theater newTheater = platformServiceHelper.createTheater(theater);
		URI location = URI.create("/theaters"+newTheater.getTheaterId());
		return ResponseEntity.created(location).body(newTheater);
	}

	@Override
	@GetMapping(value = "/theaters/{theaterId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Theater getTheater(@PathVariable("theaterId") Long theaterId) throws ResourceNotFoundException{
		return platformServiceHelper.getTheater(theaterId);
	}
	
	@Override
	@PostMapping(value = "/theatermoviecatalog", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TheaterMovieCatalog> createTheaterMovieCatalog(@RequestBody TheaterMovieCatalog theaterMovieCatalog) throws InvalidRequestException, PlatformServiceException {
		TheaterMovieCatalog newCatalog = platformServiceHelper.createTheaterMovieCatalog(theaterMovieCatalog);
		URI location = URI.create("/theatermoviecatalog"+newCatalog.getId());
		return ResponseEntity.created(location).body(newCatalog);
	}

	@Override
	@PostMapping(value = "/theatermoviecatalog/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public TheaterMovieCatalog searchTheaterMovieCatalog(@RequestBody CatalogSearch catalogSearch) throws ResourceNotFoundException {
		return platformServiceHelper.findTheaterMovieCatalog(catalogSearch);
	}
	
	@PostMapping(value = "/showschedules", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ShowSchedule> createShowSchedule(@RequestBody ShowSchedule showSchedule) throws InvalidRequestException, PlatformServiceException {
		ShowSchedule newShowSchedule = platformServiceHelper.createShowSchedule(showSchedule);
		URI location = URI.create("/showschedules"+newShowSchedule.getShowScheduleId());
		return ResponseEntity.created(location).body(newShowSchedule);
	}
	
	@GetMapping(value = "/showschedules/{scheduleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ShowSchedule getShowSchedule(@PathVariable("scheduleId") Long scheduleId) throws ResourceNotFoundException {
		return platformServiceHelper.getShowSchedule(scheduleId);
	}
	
	@PutMapping(value = "/showschedules/{scheduleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ShowSchedule> updateShowSchedule(@PathVariable("scheduleId") Long scheduleId, @RequestBody ShowSchedule theaterShowSchedule) throws ResourceNotFoundException, InvalidRequestException, PlatformServiceException {
		ShowSchedule updatedShowSchedule = platformServiceHelper.updateShowSchedule(scheduleId, theaterShowSchedule);
		return ResponseEntity.ok().body(updatedShowSchedule);
	}
	
	@DeleteMapping(value = "/showschedules/{scheduleId}", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<String> deleteShowSchedule(@PathVariable("scheduleId") Long scheduleId) throws ResourceNotFoundException, PlatformServiceException{
		platformServiceHelper.deleteShowSchedule(scheduleId);
		return ResponseEntity.ok("The show schedule has been successfully deleted");
	}

	@Override
	@PostMapping(value = "/theaters/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public TheaterShowSearchResult searchTheaters(@RequestBody TheaterSearch search) throws InvalidRequestException{
		return platformServiceHelper.findTheaters(search);
	}

	@Override
	@PostMapping(value = "/showschedules/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ScheduleSearchResult searchSchedules(@RequestBody ScheduleSearch scheduleSearch) throws InvalidRequestException {
		return platformServiceHelper.findSchedules(scheduleSearch);
	} 
	
}
