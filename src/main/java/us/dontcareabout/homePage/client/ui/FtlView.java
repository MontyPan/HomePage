package us.dontcareabout.homePage.client.ui;

import java.util.ArrayList;
import java.util.HashMap;

import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.homePage.client.data.DataCenter;
import us.dontcareabout.homePage.client.data.FTL;
import us.dontcareabout.homePage.client.data.FtlReadyEvent;
import us.dontcareabout.homePage.client.data.FtlReadyEvent.FtlReadyHandler;
import us.dontcareabout.homePage.client.layer.ftl.CalendarLayer;
import us.dontcareabout.homePage.client.layer.ftl.YearLayer;

public class FtlView extends LayerContainer {
	private CalendarLayer calendar = new CalendarLayer();
	private YearLayer years = new YearLayer();

	private HashMap<Integer, ArrayList<FTL>> yearMap = new HashMap<>();

	public FtlView() {
		addLayer(calendar);
		addLayer(years);

		DataCenter.addFtlReady(new FtlReadyHandler() {
			@Override
			public void onFtlReady(FtlReadyEvent event) {
				refresh(event.data);
			}
		});
		DataCenter.wantFTL();
	}

	@Override
	protected void onResize(int width, int height) {
		int leftSize = 300;
		years.resize(width - leftSize, 40);
		calendar.resize(width - leftSize, height - 40);
		super.onResize(width, height);
	}

	private void refresh(ArrayList<FTL> data) {
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
		redrawSurface();
	}
}
