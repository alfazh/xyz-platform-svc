package com.xyz.platformsvc.mapper;
import org.springframework.stereotype.Component;

import com.xyz.dal.entity.movie.MovieEntity;
import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.util.ResourceLinkGenerator;

@Component
public class MovieMapper implements DataMapper<MovieEntity, Movie>{

	@Override
	public MovieEntity toEntityObj(Movie movie) {
		MovieEntity movieEntity = new MovieEntity();
		movieEntity.setName(movie.getName());
		movieEntity.setDescription(movie.getDescription());
		movieEntity.setGenre(movie.getGenre());
		movieEntity.setLanguage(movie.getLanguage());
		movieEntity.setRunTime(movie.getRunTime());
		movieEntity.setMovieFormat(movie.getMovieFormat());
		return movieEntity;
	}

	@Override
	public Movie toRestObj(MovieEntity movieEntity) {
		Movie movie = new Movie();
		movie.setMovieId(movieEntity.getMovieId());
		movie.setName(movieEntity.getName());
		movie.setDescription(movieEntity.getDescription());
		movie.setGenre(movieEntity.getGenre());
		movie.setLanguage(movieEntity.getLanguage());
		movie.setRunTime(movieEntity.getRunTime());
		movie.setMovieFormat(movieEntity.getMovieFormat());
		movie.add(ResourceLinkGenerator.getMovieLink(movie.getMovieId(), ResourceLinkGenerator.SELF));
		return movie;
	}
	
}
