package com.xyz.platformsvc.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xyz.dal.entity.theater.show.ShowEntity;
import com.xyz.dal.entity.theater.show.ShowPricingEntity;
import com.xyz.dal.entity.theater.show.ShowScheduleEntity;
import com.xyz.dal.repository.show.ShowScheduleRepository;
import com.xyz.dal.repository.theater.screen.TheaterScreenRepository;
import com.xyz.platformsvc.exception.InvalidRequestException;
import com.xyz.platformsvc.exception.PlatformServiceException;
import com.xyz.platformsvc.exception.ResourceNotFoundException;
import com.xyz.platformsvc.mapper.ShowScheduleMapper;
import com.xyz.platformsvc.rest.model.show.ShowSchedule;

@Component
public class ShowScheduleService {
	
	private static final Logger logger = LoggerFactory.getLogger(ShowScheduleService.class);
	
	@Autowired
	private ShowScheduleMapper showScheduleMapper;
	
	@Autowired
	private TheaterScreenRepository theaterScreenRepository;

	@Autowired
	private ShowScheduleRepository showScheduleRepository;
	
	public ShowSchedule getShow(ShowScheduleEntity showScheduleEntity) {
		return showScheduleMapper.toRestObj(showScheduleEntity);
	}
	
	public ShowSchedule createShowSchedule(ShowSchedule showSchedule) throws PlatformServiceException {
		ShowScheduleEntity showScheduleEntity = showScheduleMapper.toEntityObj(showSchedule);
		try {
			showScheduleEntity = showScheduleRepository.saveAndFlush(showScheduleEntity);
		} catch(Exception e) {
			String errorString = "Fail to create show schedule. Exception: "+e.getMessage();
			logger.error(errorString);
			throw new PlatformServiceException(errorString, e);
		}
		return  getShow(showScheduleEntity);
	}

	public List<ShowSchedule> getShowSchedule(Long theaterId, Long movieId, LocalDate date) {
		List<ShowScheduleEntity> showScheduleList = showScheduleRepository.lookupShowSchedule(theaterId, movieId, date);
		return showScheduleList.stream().map(showScheduleMapper::toRestObj).collect(Collectors.toList());
	}
	
	public ShowSchedule getShowSchedule(Long showScheduleId) throws ResourceNotFoundException {
		ShowScheduleEntity showScheduleEntity = showScheduleRepository.lookupShowSchedule(showScheduleId);
		if(showScheduleEntity==null) {
			String errorString ="Fail to find schedule having id: "+showScheduleId;
			throw new ResourceNotFoundException(errorString);
		}
		return showScheduleMapper.toRestObj(showScheduleEntity);
	}

	public ShowSchedule updateShowSchedule(Long showScheduleId, ShowSchedule showSchedule) throws ResourceNotFoundException, InvalidRequestException, PlatformServiceException {
		//lookup existing schedule 
		final ShowScheduleEntity existingEntity = showScheduleRepository.lookupShowSchedule(showScheduleId);
		if(existingEntity==null) {
			String errorString ="Fail to find schedule having id: "+showScheduleId;
			throw new ResourceNotFoundException(errorString);
		}

		//new 
		ShowScheduleEntity newEntity = showScheduleMapper.toEntityObj(showSchedule);
		
		if(!existingEntity.getDate().equals(newEntity.getDate())) {
			// different dates - unsupported
			throw new InvalidRequestException("Cannot update schedule date.");
		} else if(!existingEntity.getTheaterCatalog().getTheaterMovieCatalogId().equals(newEntity.getTheaterCatalog().getTheaterMovieCatalogId())) {
			// different theater and movie - unsupported
			throw new InvalidRequestException("Cannot update theater or movie associated with this schedule");
		} 

		Map<ShowIdentifier, ShowEntity> existingShowMap = existingEntity.getShowList().parallelStream().collect(Collectors.toMap(ShowScheduleService::getShowIdentifier, m->m));
		Map<ShowIdentifier, ShowEntity> newShowMap = newEntity.getShowList().parallelStream().collect(Collectors.toMap(ShowScheduleService::getShowIdentifier,m->m));
		
		List<ShowEntity> showDeleteList = existingShowMap.entrySet().stream().filter(e -> !newShowMap.containsKey(e.getKey())).map(Entry::getValue).collect(Collectors.toList());
		
		List<ShowEntity> newShowList = new ArrayList<>();
		for(Entry<ShowIdentifier, ShowEntity> entry : newShowMap.entrySet()) {
			ShowEntity incomingShowEntity = entry.getValue();
			if(existingShowMap.containsKey(entry.getKey()) ) {
				ShowEntity existingShowEntity = existingShowMap.get(entry.getKey());
				
				// compare and update pricing
				Map<String, ShowPricingEntity> existingSeatPriceMap = existingShowEntity.getShowPricing().parallelStream().collect(Collectors.toMap(ShowPricingEntity::getSeatClass, m->m));
				Map<String, ShowPricingEntity> newSeatPriceMap = incomingShowEntity.getShowPricing().parallelStream().collect(Collectors.toMap(ShowPricingEntity::getSeatClass, m->m));
				List<ShowPricingEntity> toRemove = existingSeatPriceMap.entrySet().stream().filter(e->!newSeatPriceMap.containsKey(e.getKey())).map(Entry::getValue).collect(Collectors.toList());
				List<ShowPricingEntity> toAdd = new ArrayList<>();
				for(Entry<String, ShowPricingEntity> priceEntry : newSeatPriceMap.entrySet()) {
					ShowPricingEntity existingPrice = existingSeatPriceMap.get(priceEntry.getKey());
					if(existingPrice!=null) {
						existingPrice.setPrice(priceEntry.getValue().getPrice());
					} else {
						ShowPricingEntity newPrice = priceEntry.getValue();
						newPrice.setShow(existingShowEntity);
						toAdd.add(priceEntry.getValue());
					}
				}
				// update pricing
				existingShowEntity.getShowPricing().removeAll(toRemove);
				existingShowEntity.getShowPricing().addAll(toAdd);
				
				// seat allocation for existing shows cannot be updated
			} else {
				newShowList.add(incomingShowEntity);
			}
		}
		// add new shows 
		newShowList.stream().forEach(s -> s.setShowSchedule(existingEntity));
		existingEntity.getShowList().addAll(newShowList);
		// remove shows that are no longer present
		existingEntity.getShowList().removeAll(showDeleteList);
		
		//save changes
		ShowScheduleEntity updatedEntity = null;
		try {
			updatedEntity = showScheduleRepository.saveAndFlush(existingEntity);
		} catch(Exception e) {
			String errorString = "Fail to update schedule. Exception: "+e.getMessage();
			logger.error(errorString);
			throw new PlatformServiceException(errorString, e);
		}
		
		return showScheduleMapper.toRestObj(updatedEntity);
	}

	public static ShowIdentifier getShowIdentifier(ShowEntity showEntity) {
		return new ShowIdentifier(showEntity.getTime(), showEntity.getScreen().getScreenId());
	}
	
	public void deleteShowSchedule(Long showScheduleId) throws ResourceNotFoundException, PlatformServiceException {
		ShowScheduleEntity showScheduleEntity = showScheduleRepository.lookupShowSchedule(showScheduleId);
		if(showScheduleEntity==null) {
			throw new ResourceNotFoundException("Fail to find schedule having id: "+showScheduleId);
		}
		
		try {
			showScheduleRepository.delete(showScheduleEntity);
		} catch(Exception e) {
			String errorString = "Fail to delete schedule. Exception: "+e.getMessage();
			logger.error(errorString);
			throw new PlatformServiceException(errorString, e);
		}
	}
	
	public ShowScheduleMapper getShowScheduleMapper() {
		return showScheduleMapper;
	}

	public TheaterScreenRepository getTheaterScreenRepository() {
		return theaterScreenRepository;
	}

	public ShowScheduleRepository getShowScheduleRepository() {
		return showScheduleRepository;
	}

	public void setShowScheduleMapper(ShowScheduleMapper showScheduleMapper) {
		this.showScheduleMapper = showScheduleMapper;
	}

	public void setTheaterScreenRepository(TheaterScreenRepository theaterScreenRepository) {
		this.theaterScreenRepository = theaterScreenRepository;
	}

	public void setShowScheduleRepository(ShowScheduleRepository showScheduleRepository) {
		this.showScheduleRepository = showScheduleRepository;
	}
	
	public static class ShowIdentifier {
		
		public final LocalTime time;
		
		public final Integer screenId;

		public ShowIdentifier(LocalTime time, Integer screenId) {
			super();
			this.time = time;
			this.screenId = screenId;
		}

		public LocalTime getTime() {
			return time;
		}

		public Integer getScreenId() {
			return screenId;
		}

		@Override
		public int hashCode() {
			return Objects.hash(screenId, time);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ShowIdentifier other = (ShowIdentifier) obj;
			return Objects.equals(screenId, other.screenId) && Objects.equals(time, other.time);
		}

		
	}
}
