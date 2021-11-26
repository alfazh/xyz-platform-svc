package com.xyz.platformsvc.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.dal.entity.theater.screen.TheaterScreenEntity;
import com.xyz.platformsvc.rest.model.screen.TheaterScreen;

@Component
public class TheaterScreenMapper implements DomainDataMapper<TheaterScreenEntity, TheaterScreen>{

	@Autowired
	SeatingMapper seatingMapper;
	
	@Override
	public TheaterScreenEntity toEntityObj(TheaterScreen screen) {
		TheaterScreenEntity screenEntity = new TheaterScreenEntity();
		screenEntity.setName(screen.getName());
		screenEntity.setSeatRowList(screen.getSeatingRows().stream().map(seatingMapper::toEntityObj).collect(Collectors.toList()));
		screenEntity.getSeatRowList().forEach(s -> s.setTheaterScreen(screenEntity));
		return screenEntity;
	}

	@Override
	public TheaterScreen toRestObj(TheaterScreenEntity screenEntity) {
		TheaterScreen screen = new TheaterScreen();
		screen.setName(screenEntity.getName());
		screen.setId(screenEntity.getScreenId());
		screen.setSeatingRows(screenEntity.getSeatRowList().stream().map(seatingMapper::toRestObj).collect(Collectors.toList()));
		return screen;
	}
	
}
