package com.xyz.platformsvc.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.dal.entity.theater.TheaterEntity;
import com.xyz.dal.repository.theater.TheaterRepository;
import com.xyz.platformsvc.exception.PlatformServiceException;
import com.xyz.platformsvc.exception.ResourceNotFoundException;
import com.xyz.platformsvc.mapper.TheaterMapper;
import com.xyz.platformsvc.rest.model.Theater;

@Component
public class TheaterService {

	@Autowired
	TheaterMapper theaterMapper;

	@Autowired
	TheaterRepository theaterRepository;

	private static final Logger logger = LoggerFactory.getLogger(TheaterService.class);
	
	public Theater createTheater(Theater theater) throws PlatformServiceException {
		// TODO validate
		TheaterEntity newEntity = theaterMapper.toEntityObj(theater);
		try {
			newEntity = theaterRepository.saveAndFlush(newEntity);

		} catch (Exception e) {
			String errorString = "Fail to create theater. Exception: "+e.getMessage();
			logger.error(errorString);
			throw new PlatformServiceException(errorString, e);
		}

		return theaterMapper.toRestObj(newEntity);
	}

	public Theater getTheater(Long theaterId) throws ResourceNotFoundException {
		Optional<TheaterEntity> theaterEntity = theaterRepository.findById(theaterId);
		if (theaterEntity.isEmpty()) {
			throw new ResourceNotFoundException("Fail to find theater having id: "+theaterId);
		}

		return theaterMapper.toRestObj(theaterEntity.get());
	}
}
