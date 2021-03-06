package com.xyz.platformsvc.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.dal.entity.theater.TheaterEntity;
import com.xyz.platformsvc.rest.model.Theater;
import com.xyz.platformsvc.util.ResourceLinkGenerator;

@Component
public class TheaterMapper implements DomainDataMapper<TheaterEntity, Theater> {

	@Autowired
	TheaterScreenMapper theaterScreenMapper;
	
	@Override
	public TheaterEntity toEntityObj(Theater theater) {
		TheaterEntity theaterEntity = new TheaterEntity();
		theaterEntity.setName(theater.getName());
		theaterEntity.setAddress(theater.getAddress());
		theaterEntity.setType(theater.getType());
		theaterEntity.setCity(theater.getCity());
		theaterEntity.setScreens(theater.getScreens().stream().map(theaterScreenMapper::toEntityObj).collect(Collectors.toList()));
		theaterEntity.getScreens().stream().forEach(c -> c.setTheater(theaterEntity));
		theaterEntity.getScreens().forEach(s -> s.getSeatRowList().stream().forEach(l->l.setTheater(theaterEntity)));
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
		theater.setScreens(theaterEntity.getScreens().stream().map(theaterScreenMapper::toRestObj).collect(Collectors.toList()));
		theater.add(ResourceLinkGenerator.getTheaterLink(theater.getTheaterId(), ResourceLinkGenerator.SELF));
		return theater;
	}

}
