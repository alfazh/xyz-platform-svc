package com.xyz.platformsvc.rest.model.show;

import java.time.LocalDate;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.rest.model.Theater;

public class ShowSchedule extends RepresentationModel<ShowSchedule> {

	private Long showScheduleId;

	private Theater theater;

	private Movie movie;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate date;

	private List<Show> showScheduleList;

	private List<SeatClassPriceGroup> showPricingList;

	public Long getShowScheduleId() {
		return showScheduleId;
	}

	public List<Show> getShowScheduleList() {
		return showScheduleList;
	}

	public List<SeatClassPriceGroup> getShowPricingList() {
		return showPricingList;
	}

	public void setShowScheduleId(Long showScheduleId) {
		this.showScheduleId = showScheduleId;
	}

	public void setShowScheduleList(List<Show> showScheduleList) {
		this.showScheduleList = showScheduleList;
	}

	public void setShowPricingList(List<SeatClassPriceGroup> showPricingList) {
		this.showPricingList = showPricingList;
	}

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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
