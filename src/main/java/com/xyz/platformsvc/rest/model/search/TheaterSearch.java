package com.xyz.platformsvc.rest.model.search;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class TheaterSearch {

	private Long movieId;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate date;

	private String city;

	public Long getMovieId() {
		return movieId;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getCity() {
		return city;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
