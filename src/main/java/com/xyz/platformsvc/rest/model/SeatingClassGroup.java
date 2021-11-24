package com.xyz.platformsvc.rest.model;

import java.math.BigDecimal;
import java.util.List;

public class SeatingClassGroup {

	private String seatClass;
	private BigDecimal price;
	private List<SeatingRowData> seatingRowData;

	public String getSeatClass() {
		return seatClass;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<SeatingRowData> getSeatingRowData() {
		return seatingRowData;
	}

	public void setSeatingRowData(List<SeatingRowData> seatingRowData) {
		this.seatingRowData = seatingRowData;
	}

}
