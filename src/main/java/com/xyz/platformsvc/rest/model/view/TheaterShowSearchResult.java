package com.xyz.platformsvc.rest.model.view;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.xyz.platformsvc.rest.model.Movie;

public class TheaterShowSearchResult extends RepresentationModel<TheaterShowSearchResult> {

	private final Movie movie;

	private final List<TheaterShowView> theaters;

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
