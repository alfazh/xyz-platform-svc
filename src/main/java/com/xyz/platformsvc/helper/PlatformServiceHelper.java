package com.xyz.platformsvc.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.dal.entity.movie.MovieEntity;
import com.xyz.dal.entity.show.TheaterShowEntity;
import com.xyz.dal.entity.theater.TheaterEntity;
import com.xyz.dal.repository.MovieRepository;
import com.xyz.dal.repository.TheaterRepository;
import com.xyz.dal.repository.TheaterShowRepository;
import com.xyz.platformsvc.mapper.MapperFactory;
import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.rest.model.Theater;
import com.xyz.platformsvc.rest.model.TheaterShow;

@Component
public class PlatformServiceHelper {
	
	@Autowired
	MovieRepository movieRepository;

	@Autowired
	TheaterRepository theaterRepository;

	@Autowired
	TheaterShowRepository theaterShowRepository;	
	
	public Movie createMovie(Movie movie) {
		MovieEntity movieEntity = MapperFactory.MOVIE_MAPPER.toEntity(movie);
		movieEntity = movieRepository.save(movieEntity);
		return MapperFactory.MOVIE_MAPPER.toRestObj(movieEntity);
	}

	public Theater createTheater(Theater theater) {
		TheaterEntity theaterEntity = MapperFactory.THEATER_MAPPER.toEntity(theater);
		theaterRepository.save(theaterEntity);
		return MapperFactory.THEATER_MAPPER.toRestObj(theaterEntity);
	}

	public TheaterShow createTheaterShow(TheaterShow theaterShow) {
		TheaterShowEntity showEntity = MapperFactory.THEATER_SHOW_MAPPER.toEntity(theaterShow);
		theaterShowRepository.save(showEntity);
		return MapperFactory.THEATER_SHOW_MAPPER.toRestObj(showEntity);
	}
	
}
