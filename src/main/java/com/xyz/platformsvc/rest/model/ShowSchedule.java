package com.xyz.platformsvc.rest.model;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

public class ShowSchedule {

	@JsonFormat(pattern = "HH:mm")
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	private LocalTime time;

	private TheaterScreen theaterScreen;

	private List<SeatingClassGroup> seatingData;

	public LocalTime getTime() {
		return time;
	}

	public TheaterScreen getTheaterScreen() {
		return theaterScreen;
	}

	public List<SeatingClassGroup> getSeatingData() {
		return seatingData;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public void setTheaterScreen(TheaterScreen theaterScreen) {
		this.theaterScreen = theaterScreen;
	}

	public void setSeatingData(List<SeatingClassGroup> seatingData) {
		this.seatingData = seatingData;
	}

}
