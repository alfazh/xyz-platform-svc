package com.xyz.platformsvc.rest.model;

public class TheaterMovieCatalog {

	private Long id;

	private Theater theater;

	private Movie movie;

	public Theater getTheater() {
		return theater;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setTheater(Theater theater) {
		this.theater = theater;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
