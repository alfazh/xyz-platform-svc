package com.xyz.platformsvc.rest.model;

import java.time.LocalTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

public class Show extends RepresentationModel<Show> {

	private Long id;

	@JsonFormat(pattern = "HH:mm")
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	private LocalTime time;

	private TheaterScreen theaterScreen;

	private List<SeatClassPriceGroup> showPricingList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalTime getTime() {
		return time;
	}

	public TheaterScreen getTheaterScreen() {
		return theaterScreen;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public void setTheaterScreen(TheaterScreen theaterScreen) {
		this.theaterScreen = theaterScreen;
	}

	public List<SeatClassPriceGroup> getShowPricingList() {
		return showPricingList;
	}

	public void setShowPricingList(List<SeatClassPriceGroup> showPricingList) {
		this.showPricingList = showPricingList;
	}

}
