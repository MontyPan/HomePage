package us.dontcareabout.homePage.client.ui.event.mykfz;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.homePage.client.data.Mykfz;
import us.dontcareabout.homePage.client.ui.event.mykfz.SelectPlayerChangeEvent.SelectPlayerChangeHandler;

public class SelectPlayerChangeEvent extends GwtEvent<SelectPlayerChangeHandler> {
	public static final Type<SelectPlayerChangeHandler> TYPE = new Type<SelectPlayerChangeHandler>();

	public final List<Mykfz> data;

	public SelectPlayerChangeEvent(List<Mykfz> selectPlayer) {
		this.data = selectPlayer;
	}

	@Override
	public Type<SelectPlayerChangeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectPlayerChangeHandler handler) {
		handler.onSelectPlayerChange(this);
	}

	public interface SelectPlayerChangeHandler extends EventHandler{
		public void onSelectPlayerChange(SelectPlayerChangeEvent event);
	}
}
