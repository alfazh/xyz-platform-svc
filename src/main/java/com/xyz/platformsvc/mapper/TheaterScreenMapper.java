package com.xyz.platformsvc.mapper;

import org.springframework.stereotype.Component;

import com.xyz.dal.entity.theater.screen.TheaterScreenEntity;
import com.xyz.platformsvc.rest.model.TheaterScreen;

@Component
public class TheaterScreenMapper implements DataMapper<TheaterScreenEntity, TheaterScreen>{

	@Override
	public TheaterScreenEntity toEntityObj(TheaterScreen screen) {
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
