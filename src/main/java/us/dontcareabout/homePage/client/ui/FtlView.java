package us.dontcareabout.homePage.client.ui;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.homePage.client.data.DataCenter;
import us.dontcareabout.homePage.client.data.FTL;
import us.dontcareabout.homePage.client.data.FtlReadyEvent;
import us.dontcareabout.homePage.client.data.FtlReadyEvent.FtlReadyHandler;
import us.dontcareabout.homePage.client.layer.ftl.StatisticsLayer;
import us.dontcareabout.homePage.client.layer.ftl.TimeLayer;

public class FtlView extends LayerContainer {
	private TimeLayer timeLayer = new TimeLayer();
	private StatisticsLayer statisticLayer = new StatisticsLayer();

	public FtlView() {
		addLayer(timeLayer);
		addLayer(statisticLayer);

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
		int leftSize = 350;
		timeLayer.setLX(0);
		timeLayer.setLY(0);
		timeLayer.resize(width - leftSize, height);

		statisticLayer.setLX(width - leftSize);
		statisticLayer.setLY(0);
		statisticLayer.resize(leftSize, height);

		super.onResize(width, height);
	}

	private void refresh(ArrayList<FTL> data) {
		timeLayer.refresh(data);
		statisticLayer.refresh(data);
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
