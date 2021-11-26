package com.xyz.platformsvc.rest.model.screen;

public class SeatingRow {

	private String seatClass;
	
	private String row;
	
	private int numSeats;

	public String getSeatClass() {
		return seatClass;
	}

	public String getRow() {
		return row;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}
	
}
