package com.xyz.platformsvc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.dal.entity.theater.TheaterMovieCatalogEntity;
import com.xyz.dal.repository.theater.TheaterMovieCatalogRepository;
import com.xyz.platformsvc.exception.PlatformServiceException;
import com.xyz.platformsvc.mapper.TheaterCatalogMapper;
import com.xyz.platformsvc.rest.model.TheaterMovieCatalog;

@Component
public class TheaterCatalogService {

	private static final Logger logger = LoggerFactory.getLogger(TheaterCatalogService.class);
	
	@Autowired
	private TheaterCatalogMapper theaterCatalogMapper;
	
	@Autowired
	private TheaterMovieCatalogRepository theaterCatalogRepository;
	
	public TheaterMovieCatalog createTheaterCatalog(TheaterMovieCatalog theaterMovieCatalog) throws PlatformServiceException {
		TheaterMovieCatalogEntity catalogEntity = theaterCatalogMapper.toEntityObj(theaterMovieCatalog);

		try {
			catalogEntity = theaterCatalogRepository.saveAndFlush(catalogEntity);
		} catch(Exception e) {
			String errorString = "Fail to create theatercatalog. Exception: "+e.getMessage();
			logger.error(errorString);
			throw new PlatformServiceException(errorString, e);
		}
			
		return theaterCatalogMapper.toRestObj(catalogEntity);
	}

}
