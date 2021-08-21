package us.dontcareabout.homePage.client.layer.ftl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;
import us.dontcareabout.homePage.client.data.FTL;
import us.dontcareabout.homePage.client.ui.FtlView;
import us.dontcareabout.homePage.client.ui.FtlView.ChangeYearEvent;
import us.dontcareabout.homePage.client.ui.FtlView.ChangeYearHandler;

public class TimeLayer extends VerticalLayoutLayer {
	private CalendarLayer calendar = new CalendarLayer();
	private YearLayer years = new YearLayer();
	private InfoLayer info = new InfoLayer();

	private HashMap<Integer, ArrayList<FTL>> yearMap = new HashMap<>();

	public TimeLayer() {
		addChild(years, 40);
		addChild(calendar, 1);
		addChild(info, 36);

		FtlView.addChangeYear(new ChangeYearHandler() {
			@Override
			public void onChangeYear(ChangeYearEvent event) {
				calendar.refresh(event.year, yearMap.get(event.year));
			}
		});
	}

	public void refresh(List<FTL> data) {
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

	private ArrayList<FTL> ensure(int year) {
		ArrayList<FTL> result = yearMap.get(year);

		if (result == null) {
			result = new ArrayList<>();
			yearMap.put(year, result);
		}

		return result;
	}
}
