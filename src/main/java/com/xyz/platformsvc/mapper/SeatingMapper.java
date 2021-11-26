package com.xyz.platformsvc.mapper;

import org.springframework.stereotype.Component;

import com.xyz.dal.entity.theater.screen.ScreenSeatRowEntity;
import com.xyz.platformsvc.rest.model.screen.SeatingRow;

@Component
public class SeatingMapper implements DomainDataMapper<ScreenSeatRowEntity, SeatingRow>{

	@Override
	public ScreenSeatRowEntity toEntityObj(SeatingRow seatingRow) {
		ScreenSeatRowEntity rowEntity= new ScreenSeatRowEntity();
		rowEntity.setRowName(seatingRow.getRow());
		rowEntity.setSeatClass(seatingRow.getSeatClass());
		rowEntity.setNumSeats(seatingRow.getNumSeats());
		return rowEntity;
	}

	@Override
	public SeatingRow toRestObj(ScreenSeatRowEntity rowEntity) {
		SeatingRow seatingRow = new SeatingRow();
		seatingRow.setRow(rowEntity.getRowName());
		seatingRow.setNumSeats(rowEntity.getNumSeats());
		seatingRow.setSeatClass(rowEntity.getSeatClass());
		return seatingRow;
	}

}
