package com.xyz.platformsvc.helper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.xyz.dal.entity.show.ScreenSeatLayoutEntity;
import com.xyz.dal.entity.show.ShowPricingEntity;
import com.xyz.dal.entity.show.ShowSeatAllocationEntity;
import com.xyz.dal.entity.show.TheaterMovieCatalogEntity;
import com.xyz.dal.entity.show.TheaterShowEntity;
import com.xyz.dal.entity.show.TheaterShowScheduleEntity;
import com.xyz.dal.entity.theater.TheaterScreenEntity;
import com.xyz.dal.repository.TheaterMovieCatalogRepository;
import com.xyz.dal.repository.TheaterScreenRepository;
import com.xyz.dal.repository.TheaterShowScheduleRepository;
import com.xyz.platformsvc.rest.model.SeatClassPriceGroup;
import com.xyz.platformsvc.rest.model.Show;
import com.xyz.platformsvc.rest.model.TheaterShowSchedule;

@Component
public class ShowOpsHelper {

	@Autowired
	TheaterScreenRepository theaterScreenRepository;

	@Autowired
	TheaterShowScheduleRepository showScheduleRepository;
	
	@Autowired
	TheaterMovieCatalogRepository theaterCatalogRepository;
	
	public TheaterShowSchedule getShow(TheaterShowScheduleEntity theaterShowScheduleEntity, TheaterMovieCatalogEntity catalogEntity) {
		
		TheaterShowScheduleEntity showScheduleEntity = showScheduleRepository.getById(theaterShowScheduleEntity.getShowScheduleId());
		Long theaterId = catalogEntity.getTheater().getTheaterId();
		Long movieId = catalogEntity.getMovie().getMovieId();
		
		TheaterShowSchedule showSchedule = new TheaterShowSchedule();
		
		//id
		showSchedule.setShowScheduleId(showScheduleEntity.getShowScheduleId());
		
		if(!CollectionUtils.isEmpty(showScheduleEntity.getShowList())) {
			List<TheaterShowEntity> showScheduleEntityList = showScheduleEntity.getShowList();
			
			List<Show> showList = new ArrayList<>();
			for(TheaterShowEntity showEntity : showScheduleEntityList) {
				//map to show schedule
				Show show = new Show();
				show.setId(showEntity.getShowId());
				show.setTime(showEntity.getTime());
				show.add(LinkHelper.getShowLink(theaterId, movieId, show.getId(), LinkHelper.SELF));
				//add link to theater
				show.add(LinkHelper.getTheaterScreenLink(theaterId, showEntity.getScreen().getScreenId(), LinkHelper.THEATER_SCREEN));
				showList.add(show);
			}
			showSchedule.setShowScheduleList(showList);
		}
		
		//add links
		showSchedule.add(LinkHelper.getShowScheduleLink(theaterId, movieId, theaterShowScheduleEntity.getDate(), LinkHelper.SELF));
		showSchedule.add(LinkHelper.getTheaterLink(theaterId, LinkHelper.THEATER));
		showSchedule.add(LinkHelper.getMovieLink(movieId, LinkHelper.MOVIE));
		
		return showSchedule;
	}
	
	private Set<? extends ShowSeatAllocationEntity> getSeatAllocationList(TheaterShowEntity scheduleEntity, String row,
			int numSeats) {
		return	IntStream.rangeClosed(1, numSeats).mapToObj(i -> ShowSeatAllocationEntity.of(scheduleEntity, row, i)).collect(Collectors.toSet());
	}

	public TheaterShowSchedule createAndGetShowSchedule(Long theaterId, Long movieId, LocalDate date, TheaterShowSchedule showSchedule) {
		
		// lookup theater movie catalog 
		TheaterMovieCatalogEntity theaterMovieCatalogEntity = theaterCatalogRepository.findByTheaterAndMovie(theaterId, movieId);
		
		// create theater show scehedule
		TheaterShowScheduleEntity showScheduleEntity = createShowScheduleEntity(showSchedule, theaterMovieCatalogEntity, date);
		
		return  getShow(showScheduleEntity, theaterMovieCatalogEntity);
	}
	

	public TheaterShowScheduleEntity createShowScheduleEntity(TheaterShowSchedule showSchedule, TheaterMovieCatalogEntity theaterMovieCatalogEntity, LocalDate date) {

		TheaterShowScheduleEntity showScheduleEntity = new TheaterShowScheduleEntity();
		
		// theater movie catalog
		showScheduleEntity.setTheaterCatalog(theaterMovieCatalogEntity);
		
		//date
		showScheduleEntity.setDate(date);
		
		// pricing 
		List<SeatClassPriceGroup> seatClassPriceList = showSchedule.getShowPricingList();
		List<ShowPricingEntity> defaultPriceEntityList = new ArrayList<>();
		for(SeatClassPriceGroup priceGrp : seatClassPriceList) {
			priceGrp.getRows().stream().forEach(rowName -> {
			ShowPricingEntity showPricingEntity = new ShowPricingEntity();
			showPricingEntity.setPrice(priceGrp.getPrice());
			showPricingEntity.setSeatClass(priceGrp.getSeatClass());
			showPricingEntity.setSeatRow(rowName);
			defaultPriceEntityList.add(showPricingEntity);
			});
		}
		
		List<TheaterShowEntity> showEntityList = new ArrayList<>();
		for(Show show : showSchedule.getShowScheduleList()) {
			TheaterShowEntity showEntity = new TheaterShowEntity();
			//time
			showEntity.setTime(show.getTime());

			//parent schedule
			showEntity.setShowSchedule(showScheduleEntity);
			
			//pricing
			if(!CollectionUtils.isEmpty(show.getShowPricingList())) {
				List<ShowPricingEntity> priceEntityList = new ArrayList<>();
				for(SeatClassPriceGroup priceGrp : seatClassPriceList) {
					priceGrp.getRows().stream().forEach(rowName -> {
					ShowPricingEntity showPricingEntity = new ShowPricingEntity();
					showPricingEntity.setPrice(priceGrp.getPrice());
					showPricingEntity.setSeatClass(priceGrp.getSeatClass());
					showPricingEntity.setSeatRow(rowName);
					showPricingEntity.setShow(showEntity);
					priceEntityList.add(showPricingEntity);
					});
				}
				showEntity.setShowPricing(priceEntityList);
			} else {
				List<ShowPricingEntity> newShowPricingEntityList = new ArrayList<>(defaultPriceEntityList);
				newShowPricingEntityList.stream().forEach(p -> p.setShow(showEntity));
				showEntity.setShowPricing(newShowPricingEntityList);
			}
			
			//screen, seat allocation
			//FIXME - use bulk query and reference from map
			TheaterScreenEntity theaterScreenEntity = theaterScreenRepository.getById(show.getTheaterScreen().getId());
			showEntity.setScreen(theaterScreenEntity);			
			Set<ShowSeatAllocationEntity> showSeatAllocationEntityList = new HashSet<>();
			List<ScreenSeatLayoutEntity> seatLayoutList = theaterScreenEntity.getSeatLayoutList();
			for(ScreenSeatLayoutEntity layout : seatLayoutList) {
				showSeatAllocationEntityList.addAll(getSeatAllocationList(showEntity, layout.getRowName(), layout.getNumSeats()));

			}
			showEntity.setShowSeatAllocation(showSeatAllocationEntityList);

			//add to show list
			showEntityList.add(showEntity);
		}
				
		showScheduleEntity.setShowList(showEntityList);
		
		showScheduleEntity = showScheduleRepository.save(showScheduleEntity);

		
		return showScheduleEntity;
	}
}
