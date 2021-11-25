package com.xyz.platformsvc.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.dal.entity.theater.TheaterMovieCatalogEntity;
import com.xyz.dal.repository.TheaterMovieCatalogRepository;
import com.xyz.platformsvc.mapper.TheaterCatalogMapper;
import com.xyz.platformsvc.rest.model.TheaterMovieCatalog;

@Component
public class TheaterCatalogOpsHelper {

	@Autowired
	private TheaterCatalogMapper theaterCatalogMapper;
	
	private TheaterMovieCatalogRepository theaterCatalogRepository;
	
	public TheaterMovieCatalog createTheaterCatalog(TheaterMovieCatalog theaterMovieCatalog) {
		TheaterMovieCatalogEntity catalogEntity = theaterCatalogMapper.toEntityObj(theaterMovieCatalog);
		
		catalogEntity = theaterCatalogRepository.saveAndFlush(catalogEntity);
			
		return theaterCatalogMapper.toRestObj(catalogEntity);
	}

}
