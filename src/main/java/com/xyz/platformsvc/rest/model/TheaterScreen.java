package com.xyz.platformsvc.rest.model;

import org.springframework.hateoas.RepresentationModel;

public class TheaterScreen extends RepresentationModel<TheaterScreen>{

	private Integer id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
