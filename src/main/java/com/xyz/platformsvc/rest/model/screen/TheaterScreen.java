package com.xyz.platformsvc.rest.model.screen;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

public class TheaterScreen extends RepresentationModel<TheaterScreen>{

	private Integer id;
	
	private String name;

	private List<SeatingRow> seatingRows; 
	
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

	public List<SeatingRow> getSeatingRows() {
		return seatingRows;
	}

	public void setSeatingRows(List<SeatingRow> seatingRows) {
		this.seatingRows = seatingRows;
	}

}
