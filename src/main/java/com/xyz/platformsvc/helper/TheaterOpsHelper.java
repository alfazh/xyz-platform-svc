package com.xyz.platformsvc.helper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.dal.entity.theater.TheaterEntity;
import com.xyz.dal.repository.TheaterRepository;
import com.xyz.platformsvc.mapper.TheaterMapper;
import com.xyz.platformsvc.rest.model.Theater;

@Component
public class TheaterOpsHelper {

	@Autowired
	TheaterMapper theaterMapper;

	@Autowired
	TheaterRepository theaterRepository;

	public Theater createTheater(Theater theater) {
		// TODO validate movie object

		TheaterEntity newEntity = theaterMapper.toEntityObj(theater);

		// FIXME exception handling
		newEntity = theaterRepository.saveAndFlush(newEntity);

		return theaterMapper.toRestObj(newEntity);
	}

	public Optional<Theater> getMovie(Long theaterId) {
		Optional<TheaterEntity> theaterEntity = theaterRepository.findById(theaterId);
		if (theaterEntity.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(theaterMapper.toRestObj(theaterEntity.get()));
	}
}
