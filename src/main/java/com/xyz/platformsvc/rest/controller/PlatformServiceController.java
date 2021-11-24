package com.xyz.platformsvc.rest.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.platformsvc.helper.PlatformServiceHelper;
import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.rest.model.Theater;
import com.xyz.platformsvc.rest.model.TheaterShow;

@SpringBootApplication
@RestController
@RequestMapping("/platformsvc")
public class PlatformServiceController {

	@Autowired
	PlatformServiceHelper platformServiceHelper;

	@PostMapping(value = "/movies", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	private Movie createMovie(@RequestBody Movie movie) {
		Movie newMovie = platformServiceHelper.createMovie(movie);
		newMovie.add(
				linkTo(PlatformServiceController.class).slash("movies").slash(newMovie.getMovieId()).withSelfRel());
		return newMovie;
	}

	@PostMapping(value = "/theaters", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	private Theater createTheater(@RequestBody Theater theater) {
		Theater newTheater = platformServiceHelper.createTheater(theater);
		newTheater.add(linkTo(PlatformServiceController.class).slash("theaters").slash(newTheater.getTheaterId())
				.withSelfRel());
		return newTheater;
	}

	@PostMapping(value = "/theaters/{theaterId}/shows", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	private TheaterShow createShow(@PathVariable("theaterId") Long theaterId, @RequestBody TheaterShow theaterShow) {
		TheaterShow newTheaterShow = platformServiceHelper.createTheaterShow(theaterShow);
		newTheaterShow.add(linkTo(PlatformServiceController.class).slash("theatershows").slash(newTheaterShow.getTheaterShowId()).withSelfRel());
		return newTheaterShow;
	}
}
