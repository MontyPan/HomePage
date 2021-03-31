package us.dontcareabout.homePage.client.ui.event.mykfz;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.homePage.client.ui.event.mykfz.SelectSeasonChangeEvent.SelectSeasonChangeHandler;

public class SelectSeasonChangeEvent extends GwtEvent<SelectSeasonChangeHandler> {
	public static final Type<SelectSeasonChangeHandler> TYPE = new Type<SelectSeasonChangeHandler>();

	public final int data;

	public SelectSeasonChangeEvent(int selectSeason) {
		this.data = selectSeason;
	}

	@Override
	public Type<SelectSeasonChangeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectSeasonChangeHandler handler) {
		handler.onSelectSeasonChange(this);
	}

	public interface SelectSeasonChangeHandler extends EventHandler{
		public void onSelectSeasonChange(SelectSeasonChangeEvent event);
	}
}
