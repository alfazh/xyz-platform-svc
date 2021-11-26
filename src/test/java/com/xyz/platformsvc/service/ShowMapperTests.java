package com.xyz.platformsvc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.xyz.dal.entity.theater.TheaterMovieCatalogEntity;
import com.xyz.dal.entity.theater.show.ShowScheduleEntity;
import com.xyz.dal.repository.show.ShowScheduleRepository;
import com.xyz.dal.repository.theater.TheaterMovieCatalogRepository;
import com.xyz.dal.repository.theater.screen.TheaterScreenSeatLayoutRepository;
import com.xyz.platformsvc.mapper.ShowScheduleMapper;
import com.xyz.platformsvc.rest.model.Movie;
import com.xyz.platformsvc.rest.model.Theater;
import com.xyz.platformsvc.rest.model.TheaterMovieCatalog;
import com.xyz.platformsvc.rest.model.screen.TheaterScreen;
import com.xyz.platformsvc.rest.model.show.Show;
import com.xyz.platformsvc.rest.model.show.ShowSchedule;

public class ShowMapperTests {
	
	@Test
	void test_toEntityObj_invalidcatalog() {
		
		ShowScheduleMapper scheduleMapper = new ShowScheduleMapper();
		TheaterMovieCatalogRepository theaterCatalogRepository = Mockito.mock(TheaterMovieCatalogRepository.class);
		
		TheaterMovieCatalogEntity catalogEntity = new TheaterMovieCatalogEntity();
		catalogEntity.setTheaterMovieCatalogId(5L);
		Mockito.when(theaterCatalogRepository.findByTheaterAndMovie(Mockito.anyLong(), Mockito.anyLong())).thenReturn(catalogEntity);
		scheduleMapper.setTheaterCatalogRepository(theaterCatalogRepository);
		
		TheaterScreenSeatLayoutRepository screenSeatLayoutRepository = Mockito.mock(TheaterScreenSeatLayoutRepository.class);
		Mockito.when(screenSeatLayoutRepository.findByScreenId(Mockito.anyInt())).thenReturn(Collections.emptyList());
		
		scheduleMapper.setScreenSeatLayoutRepository(screenSeatLayoutRepository);
		
		ShowSchedule showSchedule = new ShowSchedule();
		showSchedule.setShowPricingList(Collections.emptyList());
		showSchedule.setDate(LocalDate.now());
		
		Movie movie = new Movie();
		movie.setMovieId(1L);
		
		Theater theater = new Theater();
		theater.setTheaterId(2L);
		
		showSchedule.setMovie(movie);
		showSchedule.setTheater(theater);

		List<Show> showList = new ArrayList<>();
		Show show = new Show();
		LocalTime showTime = LocalTime.of(10, 15, 0);
		show.setTime(showTime);
		TheaterScreen screen = new TheaterScreen();
		screen.setId(3);
		
		show.setTheaterScreen(screen);
		showList.add(show);
		showSchedule.setShowScheduleList(showList);
		
		ShowScheduleEntity showScheduleEntity = scheduleMapper.toEntityObj(showSchedule);		
		
		assertNotNull(showScheduleEntity);
		assertTrue(showScheduleEntity.getTheaterCatalog().getTheaterMovieCatalogId().equals(5L));
		assertTrue(showScheduleEntity.getShowList().size()==1);
		assertTrue(showScheduleEntity.getShowList().get(0).getTime().equals(showTime));
		assertTrue(showScheduleEntity.getShowList().get(0).getScreen().getScreenId().equals(3));
	}
}
