package us.dontcareabout.homePage.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import us.dontcareabout.homePage.client.common.mykfz.DateUtil;
import us.dontcareabout.homePage.client.component.mykfz.LeaderBoardChart;
import us.dontcareabout.homePage.client.component.mykfz.LeaderBoardGrid;
import us.dontcareabout.homePage.client.data.DataCenter;
import us.dontcareabout.homePage.client.data.MykfzReadyEvent;
import us.dontcareabout.homePage.client.data.MykfzReadyEvent.MykfzReadyHandler;

public class MykfzView extends Composite {
	private static MykfzViewUiBinder uiBinder = GWT.create(MykfzViewUiBinder.class);
	private final static SimpleEventBus eventBus = new SimpleEventBus();

	@UiField LeaderBoardChart leaderBoardChart;
	@UiField LeaderBoardGrid leaderBoardGrid;

	public MykfzView() {
		initWidget(uiBinder.createAndBindUi(this));
		DataCenter.addMykfzReady(new MykfzReadyHandler() {
			@Override
			public void onMykfzReady(MykfzReadyEvent event) {
				leaderBoardChart.refresh(event.data);
				leaderBoardGrid.refresh(event.data);
			}
		});
		DataCenter.wantMykfz(DateUtil.nowSession());
	}

	interface MykfzViewUiBinder extends UiBinder<Widget, MykfzView> {}


}
