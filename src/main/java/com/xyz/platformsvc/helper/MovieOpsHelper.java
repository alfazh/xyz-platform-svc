package com.xyz.platformsvc.helper;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.dal.entity.movie.MovieEntity;
import com.xyz.dal.repository.MovieRepository;
import com.xyz.platformsvc.exception.PlatformServiceException;
import com.xyz.platformsvc.exception.ResourceNotFoundException;
import com.xyz.platformsvc.mapper.MovieMapper;
import com.xyz.platformsvc.rest.model.Movie;

@Component
public class MovieOpsHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(MovieOpsHelper.class);
	
	@Autowired
	MovieMapper movieMapper;
	
	@Autowired
	MovieRepository movieRepository;
	
	public Movie createMovie(Movie movie) throws PlatformServiceException {
		// TODO validate movie object
		MovieEntity newEntity = movieMapper.toEntityObj(movie);
		try {
			newEntity = movieRepository.saveAndFlush(newEntity);
			logger.info("Created movie. Id: "+newEntity.getMovieId()+", name: "+newEntity.getName());
		} catch(Exception e) {
			String errorString = "Fail to create movie. Exception: "+e.getMessage();
			logger.error(errorString, e);
			throw new PlatformServiceException(errorString, e);
		}
		
		return movieMapper.toRestObj(newEntity);
	}
	
	public Movie getMovie(Long movieId) throws ResourceNotFoundException {
		Optional<MovieEntity> movieEntity = movieRepository.findById(movieId);
		if(movieEntity.isEmpty()) {
			String errorString = "Fail to find movie having id: "+movieId;
			logger.error(errorString);
			throw new ResourceNotFoundException(errorString);
		}
		
		return movieMapper.toRestObj(movieEntity.get());
	}
}
