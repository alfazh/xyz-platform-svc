package com.xyz.platformsvc.core;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.xyz.dal.entity.movie.Genre;
import com.xyz.dal.entity.movie.Language;
import com.xyz.dal.entity.movie.MovieEntity;
import com.xyz.dal.entity.movie.MovieFormat;
import com.xyz.dal.entity.theater.show.ShowEntity;
import com.xyz.dal.entity.theater.show.ShowScheduleEntity;
import com.xyz.dal.repository.MovieRepository;
import com.xyz.dal.repository.ShowScheduleRepository;

@Component
public class TestComponent {
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	ShowScheduleRepository showScheduleRepository;
	
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
		
		List<ShowScheduleEntity> shows = showScheduleRepository.getShowSchedule(1L, LocalDate.of(2021, 11, 28), "Pune");
		System.out.println("Found shows playing at below theaters"); 
		shows.stream().forEach(s -> {
			System.out.println("Theater: "+s.getTheaterCatalog().getTheater().getName()+". Date: "+s.getDate());
			System.out.println("Show times: "+s.getShowList().stream().map(ShowEntity::getTime).collect(Collectors.toList()));
			
		});
	}	
	
}
