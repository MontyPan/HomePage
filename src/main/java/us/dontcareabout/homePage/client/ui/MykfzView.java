package us.dontcareabout.homePage.client.ui;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Composite;

import us.dontcareabout.homePage.client.common.mykfz.DateUtil;
import us.dontcareabout.homePage.client.component.mykfz.LeaderBoardChart;
import us.dontcareabout.homePage.client.component.mykfz.LeaderBoardGrid;
import us.dontcareabout.homePage.client.data.DataCenter;
import us.dontcareabout.homePage.client.data.Mykfz;
import us.dontcareabout.homePage.client.data.MykfzReadyEvent;
import us.dontcareabout.homePage.client.data.MykfzReadyEvent.MykfzReadyHandler;
import us.dontcareabout.homePage.client.ui.event.mykfz.SelectPlayerChangeEvent;
import us.dontcareabout.homePage.client.ui.event.mykfz.SelectPlayerChangeEvent.SelectPlayerChangeHandler;

public class MykfzView extends Composite {
	private static MykfzViewUiBinder uiBinder = GWT.create(MykfzViewUiBinder.class);
	private final static SimpleEventBus eventBus = new SimpleEventBus();

	@UiField LeaderBoardChart leaderBoardChart;
	@UiField LeaderBoardGrid leaderBoardGrid;

	public MykfzView() {
		initWidget(uiBinder.createAndBindUi(this));
		addSelectPlayerChange(new SelectPlayerChangeHandler() {
			@Override
			public void onSelectPlayerChange(SelectPlayerChangeEvent event) {
				leaderBoardChart.selectPlayer(event.data);
			}
		});
		DataCenter.addMykfzReady(new MykfzReadyHandler() {
			@Override
			public void onMykfzReady(MykfzReadyEvent event) {
				leaderBoardChart.refresh(event.data);
				leaderBoardGrid.refresh(event.data);
			}
		});
		DataCenter.wantMykfz(DateUtil.nowSession());
	}

	public static HandlerRegistration addSelectPlayerChange(SelectPlayerChangeHandler handler) {
		return eventBus.addHandler(SelectPlayerChangeEvent.TYPE, handler);
	}

	public static void selectPlayerChange(List<Mykfz> selectPlayer) {
		eventBus.fireEvent(new SelectPlayerChangeEvent(selectPlayer));
	}

	interface MykfzViewUiBinder extends UiBinder<Widget, MykfzView> {}
}
