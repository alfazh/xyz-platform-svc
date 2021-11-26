package com.xyz.platformsvc.mapper;

import org.springframework.stereotype.Component;

import com.xyz.dal.entity.movie.MovieEntity;
import com.xyz.dal.entity.theater.TheaterEntity;
import com.xyz.dal.entity.theater.TheaterMovieCatalogEntity;
import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.rest.model.Theater;
import com.xyz.platformsvc.rest.model.TheaterMovieCatalog;

@Component
public class TheaterCatalogMapper implements DomainDataMapper<TheaterMovieCatalogEntity, TheaterMovieCatalog>{

	@Override
	public TheaterMovieCatalogEntity toEntityObj(TheaterMovieCatalog theaterMovieCatalog) {
		TheaterMovieCatalogEntity catalogEntity = new TheaterMovieCatalogEntity();
		
		MovieEntity movieEntity = new MovieEntity();
		movieEntity.setMovieId(theaterMovieCatalog.getMovie().getMovieId());
		catalogEntity.setMovie(movieEntity);
		
		TheaterEntity theaterEntity = new TheaterEntity();
		theaterEntity.setTheaterId(theaterMovieCatalog.getTheater().getTheaterId());
		catalogEntity.setTheater(theaterEntity);
		
		return catalogEntity;
	}

	@Override
	public TheaterMovieCatalog toRestObj(TheaterMovieCatalogEntity theaterMovieCatalogEntity) {
		TheaterMovieCatalog theaterMovieCatalog = new TheaterMovieCatalog();
		theaterMovieCatalog.setId(theaterMovieCatalogEntity.getTheaterMovieCatalogId());
		
		//movie 
		Movie movie = new Movie();
		movie.setMovieId(theaterMovieCatalogEntity.getMovie().getMovieId());
		movie.setName(theaterMovieCatalogEntity.getMovie().getName());
		theaterMovieCatalog.setMovie(movie);
		
		//theater
		Theater theater = new Theater();
		theater.setTheaterId(theaterMovieCatalogEntity.getTheater().getTheaterId());
		theater.setName(theaterMovieCatalogEntity.getTheater().getName());
		theaterMovieCatalog.setTheater(theater);
		
		return theaterMovieCatalog;
	} 

}
