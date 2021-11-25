package com.xyz.platformsvc.helper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.xyz.dal.entity.theater.show.ShowEntity;
import com.xyz.dal.entity.theater.show.ShowScheduleEntity;
import com.xyz.dal.repository.ShowRepository;
import com.xyz.dal.repository.ShowScheduleRepository;
import com.xyz.dal.repository.TheaterMovieCatalogRepository;
import com.xyz.dal.repository.TheaterScreenRepository;
import com.xyz.platformsvc.mapper.ShowScheduleMapper;
import com.xyz.platformsvc.rest.model.show.ShowSchedule;

@Component
public class ShowScheduleOpsHelper {

	@Autowired
	ShowScheduleMapper showScheduleMapper;
	
	@Autowired
	TheaterScreenRepository theaterScreenRepository;

	@Autowired
	ShowScheduleRepository showScheduleRepository;
	
	@Autowired
	ShowRepository showRepository;
	
	@Autowired
	TheaterMovieCatalogRepository theaterCatalogRepository;
	
	public ShowSchedule getShow(ShowScheduleEntity showScheduleEntity) {
		return showScheduleMapper.toRestObj(showScheduleEntity);
	}
	
	public ShowSchedule createShowSchedule(ShowSchedule showSchedule) {
		ShowScheduleEntity showScheduleEntity = showScheduleMapper.toEntityObj(showSchedule);
		showScheduleEntity = showScheduleRepository.save(showScheduleEntity);
		return  getShow(showScheduleEntity);
	}

	public List<ShowSchedule> getShowSchedule(Long theaterId, Long movieId, LocalDate date) {
		List<ShowScheduleEntity> showScheduleList = showScheduleRepository.lookupShowSchedule(theaterId, movieId, date);
		if(CollectionUtils.isEmpty(showScheduleList)) {
			//exception
		}
		
		return showScheduleList.stream().map(showScheduleMapper::toRestObj).collect(Collectors.toList());
	}
	
	public ShowSchedule getShowSchedule(Long showScheduleId) {
		ShowScheduleEntity showScheduleEntity = showScheduleRepository.lookupShowSchedule(showScheduleId);
		
		return showScheduleMapper.toRestObj(showScheduleEntity);
	}

	public ShowSchedule updateShowSchedule(Long showScheduleId, ShowSchedule showSchedule) {
		//lookup existing schedule 
		final ShowScheduleEntity existingEntity = showScheduleRepository.lookupShowSchedule(showScheduleId);
		
		//new 
		ShowScheduleEntity newEntity = showScheduleMapper.toEntityObj(showSchedule);
		
		if(!existingEntity.getDate().equals(newEntity.getDate())) {
			// different dates - unsupported
		} else if(existingEntity.getTheaterCatalog().getTheaterMovieCatalogId().equals(newEntity.getTheaterCatalog().getTheaterMovieCatalogId())) {
			// different theater and movie - unsupported
		} 

		Map<ShowIdentifier, ShowEntity> existingShowMap = existingEntity.getShowList().parallelStream().collect(Collectors.toMap(ShowScheduleOpsHelper::getShowIdentifier, m->m));
		Map<ShowIdentifier, ShowEntity> newShowMap = newEntity.getShowList().parallelStream().collect(Collectors.toMap(ShowScheduleOpsHelper::getShowIdentifier,m->m));
		
		List<ShowEntity> showDeleteList = existingShowMap.entrySet().stream().filter(e -> !newShowMap.containsKey(e.getKey())).map(Entry::getValue).collect(Collectors.toList());
		
		List<ShowEntity> newShowList = new ArrayList<>();
		for(Entry<ShowIdentifier, ShowEntity> entry : newShowMap.entrySet()) {
			ShowEntity incomingShowEntity = entry.getValue();
			if(existingShowMap.containsKey(entry.getKey()) ) {
				ShowEntity existingShowEntity = existingShowMap.get(entry.getKey());
				incomingShowEntity.getShowPricing().stream().forEach( p -> p.setShow(existingShowEntity));
				// update pricing
				existingShowEntity.getShowPricing().clear();
				existingShowEntity.getShowPricing().addAll(incomingShowEntity.getShowPricing());
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
		ShowScheduleEntity updatedEntity = showScheduleRepository.saveAndFlush(existingEntity);
		
		return showScheduleMapper.toRestObj(updatedEntity);
	}

	public static ShowIdentifier getShowIdentifier(ShowEntity showEntity) {
		return new ShowIdentifier(showEntity.getTime(), showEntity.getScreen().getScreenId());
	}
	
	public void deleteShowSchedule(Long showScheduleId) {
		ShowScheduleEntity showScheduleEntity = showScheduleRepository.lookupShowSchedule(showScheduleId);
		showScheduleRepository.delete(showScheduleEntity);
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
