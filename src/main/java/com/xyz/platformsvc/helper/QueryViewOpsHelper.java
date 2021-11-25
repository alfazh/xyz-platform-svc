package com.xyz.platformsvc.helper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.dal.entity.movie.MovieEntity;
import com.xyz.dal.entity.theater.TheaterMovieCatalogEntity;
import com.xyz.dal.entity.theater.show.ShowEntity;
import com.xyz.dal.entity.theater.show.ShowScheduleEntity;
import com.xyz.dal.repository.ShowScheduleRepository;
import com.xyz.dal.repository.TheaterMovieCatalogRepository;
import com.xyz.platformsvc.mapper.ShowScheduleMapper;
import com.xyz.platformsvc.mapper.TheaterCatalogMapper;
import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.rest.model.TheaterMovieCatalog;
import com.xyz.platformsvc.rest.model.show.ShowSchedule;
import com.xyz.platformsvc.rest.model.view.ScheduleSearch;
import com.xyz.platformsvc.rest.model.view.ScheduleSearchResult;
import com.xyz.platformsvc.rest.model.view.TheaterShowSearchResult;
import com.xyz.platformsvc.rest.model.view.TheaterShowView;

@Component
public class QueryViewOpsHelper {

	@Autowired
	ShowScheduleRepository showScheduleRepository;

	@Autowired
	TheaterMovieCatalogRepository theaterCatalogRepository;

	@Autowired
	TheaterCatalogMapper theaterCatalogMapper;

	@Autowired
	ShowScheduleMapper showScheduleMapper;
	
	public Optional<TheaterShowSearchResult> findTheaters(Long movieId, LocalDate date, String city) {
		List<ShowScheduleEntity> showScheduleList = showScheduleRepository.getShowSchedule(movieId, date, city);

		if (showScheduleList.isEmpty()) {
			return Optional.empty();
		}

		List<TheaterShowView> theaterShowList = new ArrayList<>();

		MovieEntity movieEntity = showScheduleList.get(0).getTheaterCatalog().getMovie();
		Movie movie = new Movie();
		movie.setMovieId(movieEntity.getMovieId());
		movie.setName(movieEntity.getName());

		for (ShowScheduleEntity showSchedule : showScheduleList) {
			TheaterShowView theaterShowView = new TheaterShowView();
			theaterShowView.setId(showSchedule.getTheaterCatalog().getTheater().getTheaterId());
			theaterShowView.setName(showSchedule.getTheaterCatalog().getTheater().getName());
			List<LocalTime> showTimeList = showSchedule.getShowList().stream().map(ShowEntity::getTime)
					.collect(Collectors.toList());
			theaterShowView.setShowTimes(showTimeList);
			theaterShowList.add(theaterShowView);
		}

		return Optional.of(new TheaterShowSearchResult(movie, theaterShowList));
	}

	public Optional<TheaterMovieCatalog> findTheaterMovieCatalog(Long theaterId, Long movieId) {
		TheaterMovieCatalogEntity catalogEntity = theaterCatalogRepository.findByTheaterAndMovie(theaterId, movieId);

		return Optional.of(theaterCatalogMapper.toRestObj(catalogEntity));
	}

	public ScheduleSearchResult findSchedules(ScheduleSearch scheduleSearch) {
		ScheduleSearchResult scheduleSearchResult = new ScheduleSearchResult();
		Long theaterMovieCatalogId = scheduleSearch.getTheaterMovieCatalogId();
		Long movieId = scheduleSearch.getMovieId();
		Long theaterId = scheduleSearch.getTheaterId();

		if(theaterMovieCatalogId==null && movieId!=null && theaterId!=null) {
			TheaterMovieCatalogEntity catalogEntity =theaterCatalogRepository.findByTheaterAndMovie(theaterId, movieId);
			theaterMovieCatalogId=catalogEntity.getTheaterMovieCatalogId();
		} 
		
		if(theaterMovieCatalogId==null) {
			return scheduleSearchResult;
		}

		List<ShowScheduleEntity> showScheduleEntityList=showScheduleRepository.lookupShowSchedule(theaterMovieCatalogId, scheduleSearch.getDate());
		List<ShowSchedule> showScheduleList = showScheduleEntityList.stream().map(showScheduleMapper::toRestObj).collect(Collectors.toList());
		
		scheduleSearchResult.setShowSchedules(showScheduleList);
		
		return scheduleSearchResult;
	}

}
