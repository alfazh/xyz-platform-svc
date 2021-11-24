package com.xyz.platformsvc.mapper;

import com.xyz.dal.entity.theater.TheaterScreenEntity;
import com.xyz.platformsvc.rest.model.TheaterScreen;

public class TheaterScreenMapper implements DataMapper<TheaterScreenEntity, TheaterScreen>{

	@Override
	public TheaterScreenEntity toEntity(TheaterScreen screen) {
		TheaterScreenEntity screenEntity = new TheaterScreenEntity();
		screenEntity.setName(screen.getName());
		return screenEntity;
	}

	@Override
	public TheaterScreen toRestObj(TheaterScreenEntity screenEntity) {
		TheaterScreen screen = new TheaterScreen();
		screen.setName(screenEntity.getName());
		screen.setId(screenEntity.getScreenId());
		return screen;
	}
	
}
