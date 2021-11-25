package com.xyz.platformsvc.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.time.LocalDate;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;

import com.xyz.platformsvc.rest.controller.PlatformServiceController;

public class ResourceLinkGenerator {
	
	public static final LinkRelation SELF = LinkRelation.of("self");
	public static final LinkRelation MOVIE = LinkRelation.of("movie");
	public static final LinkRelation THEATER = LinkRelation.of("theater");
	public static final LinkRelation THEATER_SCREEN = LinkRelation.of("theater-screen");
	public static final LinkRelation SHOW = LinkRelation.of("show");
	
	public static Link getMovieLink(Long movieId, LinkRelation linkRelation) {
		return linkTo(PlatformServiceController.class).slash("movies").slash(movieId).withRel(linkRelation);
	}
	
	public static Link getTheaterLink(Long theaterId, LinkRelation linkRelation) {
		return linkTo(PlatformServiceController.class).slash("theaters").slash(theaterId).withRel(linkRelation);
	}

	public static Link getShowLink(Long theaterId, Long movieId, Long showId, LinkRelation linkRelation) {
		return linkTo(PlatformServiceController.class)
				.slash("theaters").slash(theaterId)
				.slash("movies").slash(movieId)
				.slash("shows").slash(showId)
				.withRel(linkRelation);
	}
	
	public static Link getShowScheduleLink(Long theaterId, Long movieId, LocalDate date, LinkRelation linkRelation) {
		return linkTo(PlatformServiceController.class)
				.slash("theaters").slash(theaterId)
				.slash("movies").slash(movieId)
				.slash("schedule").slash(date)
				.withRel(linkRelation);
	}
	
	public static Link getTheaterScreenLink(Long theaterId, Integer screenId, LinkRelation linkRelation) {
		return linkTo(PlatformServiceController.class).slash("theaters").slash(theaterId).slash("screens").slash(screenId).withRel(linkRelation);
	}
	
}
