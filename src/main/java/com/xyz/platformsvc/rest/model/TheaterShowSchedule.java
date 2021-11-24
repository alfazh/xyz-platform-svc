package com.xyz.platformsvc.rest.model;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

public class TheaterShowSchedule extends RepresentationModel<TheaterShowSchedule>{

	private Long showScheduleId;

	private TheaterMovieCatalog theaterMovieCatalog;

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

	public TheaterMovieCatalog getTheaterMovieCatalog() {
		return theaterMovieCatalog;
	}

	public void setTheaterMovieCatalog(TheaterMovieCatalog theaterMovieCatalog) {
		this.theaterMovieCatalog = theaterMovieCatalog;
	}

	public void setShowScheduleList(List<Show> showScheduleList) {
		this.showScheduleList = showScheduleList;
	}

	public void setShowPricingList(List<SeatClassPriceGroup> showPricingList) {
		this.showPricingList = showPricingList;
	}

}
