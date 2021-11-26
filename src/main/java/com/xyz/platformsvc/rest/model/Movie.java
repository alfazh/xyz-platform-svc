package com.xyz.platformsvc.rest.model;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.xyz.dal.entity.movie.Genre;
import com.xyz.dal.entity.movie.Language;
import com.xyz.dal.entity.movie.MovieFormat;

public class Movie extends RepresentationModel<Movie> {

	private Long movieId;
	private String name;
	private String description;
	private Genre genre;
	private String imageLink;
	private Language language;
	private Integer runTime;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate releaseDate;

	private MovieFormat movieFormat;

	public Long getMovieId() {
		return movieId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Genre getGenre() {
		return genre;
	}

	public String getImageLink() {
		return imageLink;
	}

	public Language getLanguage() {
		return language;
	}

	public Integer getRunTime() {
		return runTime;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public MovieFormat getMovieFormat() {
		return movieFormat;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setMovieFormat(MovieFormat movieFormat) {
		this.movieFormat = movieFormat;
	}

}
