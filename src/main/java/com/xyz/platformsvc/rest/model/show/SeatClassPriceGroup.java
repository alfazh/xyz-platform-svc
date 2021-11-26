package com.xyz.platformsvc.rest.model.show;

import java.math.BigDecimal;

public class SeatClassPriceGroup {

	private String seatClass;
	private BigDecimal price;

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

}
