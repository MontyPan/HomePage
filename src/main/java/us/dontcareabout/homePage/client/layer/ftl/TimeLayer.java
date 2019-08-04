package us.dontcareabout.homePage.client.layer.ftl;

import java.util.ArrayList;
import java.util.HashMap;

import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.homePage.client.data.FTL;
import us.dontcareabout.homePage.client.ui.FtlView;
import us.dontcareabout.homePage.client.ui.FtlView.ChangeYearEvent;
import us.dontcareabout.homePage.client.ui.FtlView.ChangeYearHandler;

public class TimeLayer extends LayerSprite {
	private CalendarLayer calendar = new CalendarLayer();
	private YearLayer years = new YearLayer();
	private InfoLayer info = new InfoLayer();

	private HashMap<Integer, ArrayList<FTL>> yearMap = new HashMap<>();

	public TimeLayer() {
		add(calendar);
		add(years);
		add(info);

		FtlView.addChangeYear(new ChangeYearHandler() {
			@Override
			public void onChangeYear(ChangeYearEvent event) {
				calendar.refresh(event.year, yearMap.get(event.year));
			}
		});
	}

	public void refresh(ArrayList<FTL> data) {
		yearMap.clear();

		int maxYear = 0;
		for (FTL ftl : data) {
			ArrayList<FTL> year = yearMap.get(ftl.getStart().getFullYear());

			if (year == null) {
				year = new ArrayList<>();
				yearMap.put(ftl.getStart().getFullYear(), year);
			}

			if (ftl.getStart().getFullYear() > maxYear) { maxYear = ftl.getStart().getFullYear(); }

			year.add(ftl);
		}

		calendar.refresh(maxYear, yearMap.get(maxYear));
		years.refresh(yearMap.keySet());
	}

	@Override
	protected void adjustMember() {
		years.setLX(0);
		years.setLY(0);
		years.resize(getWidth(), 40);

		calendar.setLX(0);
		calendar.setLY(40);
		calendar.resize(getWidth(), getHeight() - 76);

		info.setLX(0);
		info.setLY(getHeight() - 36);
		info.resize(getWidth(), 36);
	}
}
