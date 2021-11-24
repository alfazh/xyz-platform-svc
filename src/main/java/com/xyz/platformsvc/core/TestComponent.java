package com.xyz.platformsvc.core;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.xyz.dal.entity.movie.Genre;
import com.xyz.dal.entity.movie.Language;
import com.xyz.dal.entity.movie.MovieEntity;
import com.xyz.dal.entity.movie.MovieFormat;
import com.xyz.dal.repository.MovieRepository;

@Component
public class TestComponent {
	
	@Autowired
	MovieRepository movieRepository;
	
	
	@PostConstruct
	public void testDBOperations() {
		List<MovieEntity> movieList = movieRepository.findAll();
		if(!CollectionUtils.isEmpty(movieList))
			movieList.stream().forEach(MovieEntity::toString);
		else {
			System.out.println("No movies found. Adding one");
			MovieEntity movie = new MovieEntity();
			movie.setName("The Shawshank Redemption");
			movie.setGenre(Genre.DRAMA);
			movie.setDescription("A tale of 2 friends");
			movie.setLanguage(Language.ENGLISH);
			movie.setRunTime(240);
			movie.setMovieFormat(MovieFormat.TWO_D);
			movieRepository.save(movie);
		}
	}	
	
}
