package us.dontcareabout.homePage.client.data;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.homePage.client.data.FtlReadyEvent.FtlReadyHandler;

public class FtlReadyEvent extends GwtEvent<FtlReadyHandler> {
	public static final Type<FtlReadyHandler> TYPE = new Type<FtlReadyHandler>();
	public final List<FTL> data;

	public FtlReadyEvent(List<FTL> data) {
		this.data = data;
	}

	@Override
	public Type<FtlReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FtlReadyHandler handler) {
		handler.onFtlReady(this);
	}

	public interface FtlReadyHandler extends EventHandler{
		public void onFtlReady(FtlReadyEvent event);
	}
}
