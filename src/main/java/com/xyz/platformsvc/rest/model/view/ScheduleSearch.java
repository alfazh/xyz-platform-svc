package com.xyz.platformsvc.rest.model.view;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class ScheduleSearch {

	private Long theaterMovieCatalogId;

	private Long theaterId;

	private Long movieId;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate date;

	public Long getTheaterMovieCatalogId() {
		return theaterMovieCatalogId;
	}

	public Long getTheaterId() {
		return theaterId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setTheaterMovieCatalogId(Long theaterMovieCatalogId) {
		this.theaterMovieCatalogId = theaterMovieCatalogId;
	}

	public void setTheaterId(Long theaterId) {
		this.theaterId = theaterId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
