package us.dontcareabout.homePage.client.data;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.homePage.client.data.AliceWorkReadyEvent.AliceWorkReadyHandler;

public class AliceWorkReadyEvent extends GwtEvent<AliceWorkReadyHandler> {
	public static final Type<AliceWorkReadyHandler> TYPE = new Type<AliceWorkReadyHandler>();
	public final List<AliceWork> data;

	public AliceWorkReadyEvent(List<AliceWork> list) {
		this.data = list;
	}

	@Override
	public Type<AliceWorkReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AliceWorkReadyHandler handler) {
		handler.onAliceWorkReady(this);
	}

	public interface AliceWorkReadyHandler extends EventHandler{
		public void onAliceWorkReady(AliceWorkReadyEvent event);
	}
}
