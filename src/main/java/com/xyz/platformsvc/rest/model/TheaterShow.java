package com.xyz.platformsvc.rest.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class TheaterShow extends RepresentationModel<TheaterShow> {

	private Long theaterShowId;

	private Theater theater;

	private Movie movie;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate date;

	private List<ShowSchedule> showSchedule;

	public Long getTheaterShowId() {
		return theaterShowId;
	}

	public Theater getTheater() {
		return theater;
	}

	public Movie getMovie() {
		return movie;
	}

	public LocalDate getDate() {
		return date;
	}

	public List<ShowSchedule> getShowSchedule() {
		return showSchedule;
	}

	public void setTheaterShowId(Long theaterShowId) {
		this.theaterShowId = theaterShowId;
	}

	public void setTheater(Theater theater) {
		this.theater = theater;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setShowSchedule(List<ShowSchedule> showSchedule) {
		this.showSchedule = showSchedule;
	}

}
