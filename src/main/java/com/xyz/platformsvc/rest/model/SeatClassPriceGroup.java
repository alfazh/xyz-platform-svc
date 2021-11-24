package com.xyz.platformsvc.rest.model;

import java.math.BigDecimal;
import java.util.Set;

public class SeatClassPriceGroup {
	
	private String seatClass;
	private Set<String> rows;
	private BigDecimal price;

	public String getSeatClass() {
		return seatClass;
	}

	public Set<String> getRows() {
		return rows;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public void setRows(Set<String> rows) {
		this.rows = rows;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
