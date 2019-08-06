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
			ArrayList<FTL> year = ensure(ftl.getStart().getFullYear());
			year.add(ftl);

			if (ftl.getStart().getFullYear() > maxYear) { maxYear = ftl.getStart().getFullYear(); }

			//不考慮橫跨兩年以上的狀況
			if (ftl.getStart().getFullYear() != ftl.getEnd().getFullYear()) {
				year = ensure(ftl.getEnd().getFullYear());
				year.add(ftl);

				//再度檢查 maxYear 是否要調整
				if (ftl.getEnd().getFullYear() > maxYear) { maxYear = ftl.getEnd().getFullYear(); }
			}
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

	private ArrayList<FTL> ensure(int year) {
		ArrayList<FTL> result = yearMap.get(year);

		if (result == null) {
			result = new ArrayList<>();
			yearMap.put(year, result);
		}

		return result;
	}
}
