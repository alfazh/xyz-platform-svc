package com.xyz.platformsvc.rest.model.search;

import java.util.List;

import com.xyz.platformsvc.rest.model.show.ShowSchedule;

public class ScheduleSearchResult {

	private List<ShowSchedule> showSchedules;

	public List<ShowSchedule> getShowSchedules() {
		return showSchedules;
	}

	public void setShowSchedules(List<ShowSchedule> showSchedules) {
		this.showSchedules = showSchedules;
	}
	
}
