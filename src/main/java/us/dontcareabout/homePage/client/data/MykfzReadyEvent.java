package us.dontcareabout.homePage.client.data;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.homePage.client.data.MykfzReadyEvent.MykfzReadyHandler;

public class MykfzReadyEvent extends GwtEvent<MykfzReadyHandler> {
	public static final Type<MykfzReadyHandler> TYPE = new Type<MykfzReadyHandler>();
	public final ArrayList<Mykfz> data;

	public MykfzReadyEvent(ArrayList<Mykfz> data) {
		this.data = data;
	}

	@Override
	public Type<MykfzReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MykfzReadyHandler handler) {
		handler.onMykfzReady(this);
	}

	public interface MykfzReadyHandler extends EventHandler{
		public void onMykfzReady(MykfzReadyEvent event);
	}
}
