package com.xyz.platformsvc.helper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.dal.entity.movie.MovieEntity;
import com.xyz.dal.repository.MovieRepository;
import com.xyz.platformsvc.rest.model.Movie;

@Component
public class MovieOpsHelper {
	
	@Autowired
	MovieRepository movieRepository;
	
	public Movie createMovie(Movie movie) {
		// TODO validate movie object
		
		MovieEntity newEntity = toEntityObj(movie);
		
		//FIXME exception handling
		newEntity = movieRepository.saveAndFlush(newEntity);
		
		return toRestObj(newEntity);
	}
	
	public Optional<Movie> getMovie(Long movieId) {
		Optional<MovieEntity> movieEntity = movieRepository.findById(movieId);
		if(movieEntity.isEmpty()) {
			return Optional.empty();
		}
		
		return Optional.of(toRestObj(movieEntity.get()));
	}
	

	private MovieEntity toEntityObj(Movie movie) {
		MovieEntity movieEntity = new MovieEntity();
		movieEntity.setName(movie.getName());
		movieEntity.setDescription(movie.getDescription());
		movieEntity.setGenre(movie.getGenre());
		movieEntity.setLanguage(movie.getLanguage());
		movieEntity.setRunTime(movie.getRunTime());
		movieEntity.setMovieFormat(movie.getMovieFormat());
		return movieEntity;
	}

	private Movie toRestObj(MovieEntity movieEntity) {
		Movie movie = new Movie();
		movie.setMovieId(movieEntity.getMovieId());
		movie.setName(movieEntity.getName());
		movie.setDescription(movieEntity.getDescription());
		movie.setGenre(movieEntity.getGenre());
		movie.setLanguage(movieEntity.getLanguage());
		movie.setRunTime(movieEntity.getRunTime());
		movie.setMovieFormat(movieEntity.getMovieFormat());
		return movie;
	}
}
