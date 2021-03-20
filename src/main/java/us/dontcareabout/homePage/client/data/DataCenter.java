package us.dontcareabout.homePage.client.data;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.gwt.client.Console;
import us.dontcareabout.gwt.client.google.Sheet;
import us.dontcareabout.gwt.client.google.SheetHappen;
import us.dontcareabout.gwt.client.google.SheetHappen.Callback;
import us.dontcareabout.homePage.client.common.mykfz.DateUtil;
import us.dontcareabout.homePage.client.data.AliceWorkReadyEvent.AliceWorkReadyHandler;
import us.dontcareabout.homePage.client.data.FtlReadyEvent.FtlReadyHandler;
import us.dontcareabout.homePage.client.data.MykfzReadyEvent.MykfzReadyHandler;

public class DataCenter {
	private final static SimpleEventBus eventBus = new SimpleEventBus();

	private final static String PS_ID = "1TLbmAtO_3TMCvRTW9NNPBsAd05bpmjNzQrM5xZS0Pjc";

	public static void wantFTL() {
		SheetHappen.<FTL>get(PS_ID, 1,
			new Callback<FTL>() {
				@Override
				public void onSuccess(Sheet<FTL> gs) {
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
			public void onSuccess(Sheet<AliceWork> gs) {
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

	////////////////

	private final static String MYKFZ_ID = "1OqUlyBD5bTbZjKyR7hYO1As44mPqDad-dZ90TH-zZAw";

	public static void wantMykfz(int session) {
		//第一個 tab 是 boss 進度，第二個是目前賽季、第 2+N 個是往前 N 個賽季...
		int sessionIndex = 2 + DateUtil.nowSession() - session;
		SheetHappen.<Mykfz>get(MYKFZ_ID, sessionIndex, new Callback<Mykfz>() {
			@Override
			public void onSuccess(Sheet<Mykfz> gs) {
				eventBus.fireEvent(new MykfzReadyEvent(gs.getEntry()));
			}

			@Override
			public void onError(Throwable exception) {
				Console.log(exception);
			}
		});
	}

	public static HandlerRegistration addMykfzReady(MykfzReadyHandler handler) {
		return eventBus.addHandler(MykfzReadyEvent.TYPE, handler);
	}
}
