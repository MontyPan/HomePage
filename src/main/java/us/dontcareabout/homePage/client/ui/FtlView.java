package us.dontcareabout.homePage.client.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.homePage.client.data.DataCenter;
import us.dontcareabout.homePage.client.data.FTL;
import us.dontcareabout.homePage.client.data.FtlReadyEvent;
import us.dontcareabout.homePage.client.data.FtlReadyEvent.FtlReadyHandler;
import us.dontcareabout.homePage.client.layer.ftl.CalendarLayer;
import us.dontcareabout.homePage.client.layer.ftl.InfoLayer;
import us.dontcareabout.homePage.client.layer.ftl.StatisticsLayer;
import us.dontcareabout.homePage.client.layer.ftl.YearLayer;

public class FtlView extends LayerContainer {
	private CalendarLayer calendar = new CalendarLayer();
	private StatisticsLayer statisticLayer = new StatisticsLayer();
	private YearLayer years = new YearLayer();
	private InfoLayer info = new InfoLayer();

	private HashMap<Integer, ArrayList<FTL>> yearMap = new HashMap<>();

	public FtlView() {
		addLayer(statisticLayer);
		addLayer(calendar);
		addLayer(years);
		addLayer(info);

		DataCenter.addFtlReady(new FtlReadyHandler() {
			@Override
			public void onFtlReady(FtlReadyEvent event) {
				refresh(event.data);
			}
		});
		DataCenter.wantFTL();

		addChangeYear(new ChangeYearHandler() {
			@Override
			public void onChangeYear(ChangeYearEvent event) {
				calendar.refresh(event.year, yearMap.get(event.year));
			}
		});
	}

	@Override
	protected void onResize(int width, int height) {
		int leftSize = 350;
		statisticLayer.setLX(width - leftSize);
		statisticLayer.setLY(0);
		statisticLayer.resize(leftSize, height);

		years.setLX(0);
		years.setLY(0);
		years.resize(width - leftSize, 40);

		calendar.setLX(0);
		calendar.setLY(40);
		calendar.resize(width - leftSize, height - 76);

		info.setLX(0);
		info.setLY(height - 36);
		info.resize(width - leftSize, 36);
		super.onResize(width, height);
	}

	private void refresh(ArrayList<FTL> data) {
		statisticLayer.refresh(data);

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

	// ==== Event Center ==== //
	private final static SimpleEventBus eventBus = new SimpleEventBus();

	public static void fire(GwtEvent<?> event) {
		eventBus.fireEvent(event);
	}

	////////

	public static class ChangeYearEvent extends GwtEvent<ChangeYearHandler> {
		public static final Type<ChangeYearHandler> TYPE = new Type<ChangeYearHandler>();

		public final int year;

		public ChangeYearEvent(int year) {
			this.year = year;
		}

		@Override
		public Type<ChangeYearHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(ChangeYearHandler handler) {
			handler.onChangeYear(this);
		}
	}

	public interface ChangeYearHandler extends EventHandler{
		public void onChangeYear(ChangeYearEvent event);
	}

	public static HandlerRegistration addChangeYear(ChangeYearHandler handler) {
		return eventBus.addHandler(ChangeYearEvent.TYPE, handler);
	}

	////////

	public static class ChangeRecordEvent extends GwtEvent<ChangeRecordHandler> {
		public static final Type<ChangeRecordHandler> TYPE = new Type<ChangeRecordHandler>();

		public final FTL record;

		public ChangeRecordEvent(FTL record) {
			this.record = record;
		}

		@Override
		public Type<ChangeRecordHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(ChangeRecordHandler handler) {
			handler.onChangeRecord(this);
		}
	}

	public interface ChangeRecordHandler extends EventHandler{
		public void onChangeRecord(ChangeRecordEvent event);
	}

	public static HandlerRegistration addChangeRecord(ChangeRecordHandler handler) {
		return eventBus.addHandler(ChangeRecordEvent.TYPE, handler);
	}
}
