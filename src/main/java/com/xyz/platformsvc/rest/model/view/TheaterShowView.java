package com.xyz.platformsvc.rest.model.view;

import java.time.LocalTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

public class TheaterShowView extends RepresentationModel<TheaterShowView>{

	private Long id;
	
	private String name;
	
	private List<LocalTime> showTimes;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<LocalTime> getShowTimes() {
		return showTimes;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setShowTimes(List<LocalTime> showTimes) {
		this.showTimes = showTimes;
	}
	
}
