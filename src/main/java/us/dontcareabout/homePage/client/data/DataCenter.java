package us.dontcareabout.homePage.client.data;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.gwt.client.Console;
import us.dontcareabout.homePage.client.data.AliceWorkReadyEvent.AliceWorkReadyHandler;
import us.dontcareabout.homePage.client.data.FtlReadyEvent.FtlReadyHandler;
import us.dontcareabout.homePage.client.gf.GoogleSheet;
import us.dontcareabout.homePage.client.gf.SheetHappen;
import us.dontcareabout.homePage.client.gf.SheetHappen.Callback;

public class DataCenter {
	private final static SimpleEventBus eventBus = new SimpleEventBus();

	private final static String PS_ID = "1TLbmAtO_3TMCvRTW9NNPBsAd05bpmjNzQrM5xZS0Pjc";

	public static void wantFTL() {
		SheetHappen.<FTL>get(PS_ID, 1,
			new Callback<FTL>() {
				@Override
				public void onSuccess(GoogleSheet<FTL> gs) {
					eventBus.fireEvent(new FtlReadyEvent(gs.getEntry()));
				}

				@Override
				public void onError(Throwable exception) {
					Console.log(exception);
				}
		});
	}

	public static HandlerRegistration addFtlReady(FtlReadyHandler handler) {
		return eventBus.addHandler(FtlReadyEvent.TYPE, handler);
	}

	////////////////

	private final static String ALICE_ID = "1tnZL2c9dO7KO4-rOCw_Xr89MX0ccpziMPSmk54q8cPQ";

	public static void wantAlice() {
		SheetHappen.<AliceWork>get(ALICE_ID, new Callback<AliceWork>() {
			@Override
			public void onSuccess(GoogleSheet<AliceWork> gs) {
				eventBus.fireEvent(new AliceWorkReadyEvent(gs.getEntry()));
			}

			@Override
			public void onError(Throwable exception) {
				Console.log(exception);
			}
		});
	}

	public static HandlerRegistration addAliceReady(AliceWorkReadyHandler handler) {
		return eventBus.addHandler(AliceWorkReadyEvent.TYPE, handler);
	}
}
