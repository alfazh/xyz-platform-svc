package com.xyz.platformsvc.mapper;

import java.util.stream.Collectors;

import com.xyz.dal.entity.theater.TheaterEntity;
import com.xyz.platformsvc.rest.model.Theater;

public class TheaterMapper implements DataMapper<TheaterEntity, Theater> {

	@Override
	public TheaterEntity toEntity(Theater theater) {
		TheaterEntity theaterEntity = new TheaterEntity();
		theaterEntity.setName(theater.getName());
		theaterEntity.setAddress(theater.getAddress());
		theaterEntity.setType(theater.getType());
		theaterEntity.setCity(theater.getCity());
		theaterEntity.setScreens(theater.getScreens().stream().map(MapperFactory.THEATER_SCREEN_MAPPER::toEntity).collect(Collectors.toList()));
		theaterEntity.getScreens().stream().forEach(c -> c.setTheater(theaterEntity));
		return theaterEntity;
	}

	@Override
	public Theater toRestObj(TheaterEntity theaterEntity) {
		Theater theater = new Theater();
		theater.setTheaterId(theaterEntity.getTheaterId());
		theater.setName(theaterEntity.getName());
		theater.setAddress(theaterEntity.getAddress());
		theater.setType(theaterEntity.getType());
		theater.setCity(theaterEntity.getCity());
		theater.setScreens(theaterEntity.getScreens().stream().map(MapperFactory.THEATER_SCREEN_MAPPER::toRestObj).collect(Collectors.toList()));
		return theater;
	}

}
