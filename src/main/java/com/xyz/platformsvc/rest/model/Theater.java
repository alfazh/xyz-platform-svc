package com.xyz.platformsvc.rest.model;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.xyz.dal.entity.theater.TheaterType;

public class Theater extends RepresentationModel<Theater>{

	private Long theaterId;

	private String name;

	private String address;

	private String city;

	private TheaterType type;

	private List<TheaterScreen> screens;

	public Long getTheaterId() {
		return theaterId;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public TheaterType getType() {
		return type;
	}

	public List<TheaterScreen> getScreens() {
		return screens;
	}

	public void setTheaterId(Long theaterId) {
		this.theaterId = theaterId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setType(TheaterType type) {
		this.type = type;
	}

	public void setScreens(List<TheaterScreen> screens) {
		this.screens = screens;
	}

}
