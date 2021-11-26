package com.xyz.platformsvc.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.xyz.dal.entity.theater.TheaterMovieCatalogEntity;
import com.xyz.dal.entity.theater.screen.ScreenSeatRowEntity;
import com.xyz.dal.entity.theater.screen.TheaterScreenEntity;
import com.xyz.dal.entity.theater.show.ShowEntity;
import com.xyz.dal.entity.theater.show.ShowPricingEntity;
import com.xyz.dal.entity.theater.show.ShowScheduleEntity;
import com.xyz.dal.entity.theater.show.ShowSeatAllocationEntity;
import com.xyz.dal.repository.theater.TheaterMovieCatalogRepository;
import com.xyz.dal.repository.theater.screen.TheaterScreenSeatLayoutRepository;
import com.xyz.platformsvc.rest.model.show.SeatClassPriceGroup;
import com.xyz.platformsvc.rest.model.show.Show;
import com.xyz.platformsvc.rest.model.show.ShowSchedule;
import com.xyz.platformsvc.util.ResourceLinkGenerator;

@Component
public class ShowScheduleMapper implements DomainDataMapper<ShowScheduleEntity, ShowSchedule> {

	@Autowired
	TheaterMovieCatalogRepository theaterCatalogRepository;

	@Autowired
	TheaterScreenSeatLayoutRepository screenSeatLayoutRepository;
	
	@Override
	public ShowScheduleEntity toEntityObj(ShowSchedule showSchedule) {
		ShowScheduleEntity showScheduleEntity = new ShowScheduleEntity();
		
		// lookup theater movie catalog 
		TheaterMovieCatalogEntity theaterMovieCatalogEntity = theaterCatalogRepository.findByTheaterAndMovie(showSchedule.getTheater().getTheaterId(), showSchedule.getMovie().getMovieId());
		
		// theater movie catalog
		showScheduleEntity.setTheaterCatalog(theaterMovieCatalogEntity);

		// date
		showScheduleEntity.setDate(showSchedule.getDate());

		// pricing
		List<SeatClassPriceGroup> seatClassPriceList = showSchedule.getShowPricingList();
		List<ShowPricingEntity> defaultPriceEntityList = new ArrayList<>();
		for (SeatClassPriceGroup priceGrp : seatClassPriceList) {
			ShowPricingEntity showPricingEntity = new ShowPricingEntity();
			showPricingEntity.setPrice(priceGrp.getPrice());
			showPricingEntity.setSeatClass(priceGrp.getSeatClass());
			defaultPriceEntityList.add(showPricingEntity);
		}

		List<ShowEntity> showEntityList = new ArrayList<>();
		for (Show show : showSchedule.getShowScheduleList()) {
			ShowEntity showEntity = new ShowEntity();
			// time
			showEntity.setTime(show.getTime());

			// parent schedule
			showEntity.setShowSchedule(showScheduleEntity);

			// pricing
			if (!CollectionUtils.isEmpty(show.getShowPricingList())) {
				List<ShowPricingEntity> priceEntityList = new ArrayList<>();
				for (SeatClassPriceGroup priceGrp : seatClassPriceList) {
					ShowPricingEntity showPricingEntity = new ShowPricingEntity();
					showPricingEntity.setPrice(priceGrp.getPrice());
					showPricingEntity.setSeatClass(priceGrp.getSeatClass());
					showPricingEntity.setShow(showEntity);
					priceEntityList.add(showPricingEntity);
				}
				showEntity.setShowPricing(priceEntityList);
			} else {
				List<ShowPricingEntity> newShowPricingEntityList = getPricingListForShow(defaultPriceEntityList, showEntity);
				newShowPricingEntityList.stream().forEach(p -> p.setShow(showEntity));
				showEntity.setShowPricing(newShowPricingEntityList);
			}

			// screen, seat allocation
			TheaterScreenEntity theaterScreenEntity = new TheaterScreenEntity();
			theaterScreenEntity.setScreenId(show.getTheaterScreen().getId());
			showEntity.setScreen(theaterScreenEntity);
			
			Set<ShowSeatAllocationEntity> showSeatAllocationEntityList = new HashSet<>();
			List<ScreenSeatRowEntity> seatLayoutList = screenSeatLayoutRepository.findByScreenId(theaterScreenEntity.getScreenId());
			for (ScreenSeatRowEntity layout : seatLayoutList) {
				showSeatAllocationEntityList.addAll(getSeatAllocationList(showEntity, layout.getRowName(), layout.getNumSeats()));

			}
			showEntity.setShowSeatAllocation(showSeatAllocationEntityList);

			// add to show list
			showEntityList.add(showEntity);
		}

		showScheduleEntity.setShowList(showEntityList);

		return showScheduleEntity;
	}

	private List<ShowPricingEntity> getPricingListForShow(List<ShowPricingEntity> priceEntityList,
			ShowEntity showEntity) {
		List<ShowPricingEntity> showPriceEntityList = new ArrayList<>();
		for (ShowPricingEntity priceGrp : priceEntityList) {
			ShowPricingEntity newEntity = new ShowPricingEntity();
			newEntity.setPrice(priceGrp.getPrice());
			newEntity.setSeatClass(priceGrp.getSeatClass());
			newEntity.setShow(showEntity);
			showPriceEntityList.add(newEntity);
		}
		
		return showPriceEntityList;
	}

	private Set<? extends ShowSeatAllocationEntity> getSeatAllocationList(ShowEntity scheduleEntity, String row,
			int numSeats) {
		return IntStream.rangeClosed(1, numSeats).mapToObj(i -> ShowSeatAllocationEntity.of(scheduleEntity, row, i))
				.collect(Collectors.toSet());
	}

	@Override
	public ShowSchedule toRestObj(ShowScheduleEntity showScheduleEntity) {
		Optional<TheaterMovieCatalogEntity> theaterMovieCatalogEntity = theaterCatalogRepository.findById(showScheduleEntity.getTheaterCatalog().getTheaterMovieCatalogId());
		
		Long theaterId = theaterMovieCatalogEntity.get().getTheater().getTheaterId();
		Long movieId = theaterMovieCatalogEntity.get().getMovie().getMovieId();
		
		ShowSchedule showSchedule = new ShowSchedule();
		
		//id
		showSchedule.setShowScheduleId(showScheduleEntity.getShowScheduleId());
		
		if(!CollectionUtils.isEmpty(showScheduleEntity.getShowList())) {
			List<ShowEntity> showScheduleEntityList = showScheduleEntity.getShowList();
			
			List<Show> showList = new ArrayList<>();
			for(ShowEntity showEntity : showScheduleEntityList) {
				//map to show schedule
				Show show = new Show();
				show.setId(showEntity.getShowId());
				show.setTime(showEntity.getTime());
				//add link to theater
				show.add(ResourceLinkGenerator.getTheaterScreenLink(theaterId, showEntity.getScreen().getScreenId(), ResourceLinkGenerator.THEATER_SCREEN));
				showList.add(show);
			}
			showSchedule.setShowScheduleList(showList);
		}
		
		//add links
		showSchedule.add(ResourceLinkGenerator.getShowScheduleLink(showScheduleEntity.getShowScheduleId(), ResourceLinkGenerator.SELF));
		showSchedule.add(ResourceLinkGenerator.getTheaterLink(theaterId, ResourceLinkGenerator.THEATER));
		showSchedule.add(ResourceLinkGenerator.getMovieLink(movieId, ResourceLinkGenerator.MOVIE));
		
		return showSchedule;
	}

	public TheaterMovieCatalogRepository getTheaterCatalogRepository() {
		return theaterCatalogRepository;
	}

	public TheaterScreenSeatLayoutRepository getScreenSeatLayoutRepository() {
		return screenSeatLayoutRepository;
	}

	public void setTheaterCatalogRepository(TheaterMovieCatalogRepository theaterCatalogRepository) {
		this.theaterCatalogRepository = theaterCatalogRepository;
	}

	public void setScreenSeatLayoutRepository(TheaterScreenSeatLayoutRepository screenSeatLayoutRepository) {
		this.screenSeatLayoutRepository = screenSeatLayoutRepository;
	}

}
