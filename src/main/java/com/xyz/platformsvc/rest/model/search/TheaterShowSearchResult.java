package com.xyz.platformsvc.rest.model.search;

import java.util.Collections;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.xyz.platformsvc.rest.model.Movie;

public class TheaterShowSearchResult extends RepresentationModel<TheaterShowSearchResult> {

	private final Movie movie;

	private final List<TheaterShowView> theaters;

	public static final TheaterShowSearchResult EMPTY = new TheaterShowSearchResult(null, Collections.emptyList());
	
	public TheaterShowSearchResult(Movie movie, List<TheaterShowView> theaters) {
		super();
		this.movie = movie;
		this.theaters = theaters;
	}

	public Movie getMovie() {
		return movie;
	}

	public List<TheaterShowView> getTheaters() {
		return theaters;
	}

	

}
