package us.dontcareabout.homePage.client.data;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.gwt.client.Console;
import us.dontcareabout.gwt.client.google.sheet.Sheet;
import us.dontcareabout.gwt.client.google.sheet.SheetDto;
import us.dontcareabout.gwt.client.google.sheet.SheetDto.Callback;
import us.dontcareabout.homePage.client.data.AliceWorkReadyEvent.AliceWorkReadyHandler;
import us.dontcareabout.homePage.client.data.FtlReadyEvent.FtlReadyHandler;
import us.dontcareabout.homePage.client.data.MykfzReadyEvent.MykfzReadyHandler;

public class DataCenter {
	private final static String KEY = "AIzaSyC6ZKEGWCfDoMhjIqPT0SRLu73WWFTGPdY";
	private final static SimpleEventBus eventBus = new SimpleEventBus();

	private final static SheetDto<FTL> ftlSheet = new SheetDto<FTL>()
		.key(KEY).sheetId("1TLbmAtO_3TMCvRTW9NNPBsAd05bpmjNzQrM5xZS0Pjc").tabName("FTL");

	public static void wantFTL() {
		ftlSheet.fetch(
			new Callback<FTL>() {
				@Override
				public void onSuccess(Sheet<FTL> gs) {
					eventBus.fireEvent(new FtlReadyEvent(gs.getRows()));
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

	private final static SheetDto<AliceWork> aliceSheet = new SheetDto<AliceWork>()
		.key(KEY).sheetId("1tnZL2c9dO7KO4-rOCw_Xr89MX0ccpziMPSmk54q8cPQ").tabName("publication");

	public static void wantAlice() {
		aliceSheet.fetch(new Callback<AliceWork>() {
			@Override
			public void onSuccess(Sheet<AliceWork> gs) {
				eventBus.fireEvent(new AliceWorkReadyEvent(gs.getRows()));
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

	//Delete 等到 QtdClan 正式完工之後再一起砍砍掉
	public static void wantMykfz(int session) {}

	public static HandlerRegistration addMykfzReady(MykfzReadyHandler handler) {
		return eventBus.addHandler(MykfzReadyEvent.TYPE, handler);
	}
}
