package us.dontcareabout.homePage.client.component.mykfz;

import java.util.Date;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;

import us.dontcareabout.homePage.client.Util;
import us.dontcareabout.homePage.client.common.mykfz.DateUtil;

public class LeaderBoardGallery extends VerticalLayoutContainer {
	private static final DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyyMMdd");

	private ComboBox<Date> dateCB = new ComboBox<>(
		new ListStore<>(new ModelKeyProvider<Date>() {
			@Override
			public String getKey(Date item) {
				return item.toString();
			}
		}),
		new LabelProvider<Date>() {
			@Override
			public String getLabel(Date item) {
				return Util.dateFormat.format(item);
			}
		}
	);
	private VerticalLayoutContainer imageVC = new VerticalLayoutContainer();

	public LeaderBoardGallery() {
		dateCB.setEmptyText("請選擇日期");
		dateCB.setTriggerAction(TriggerAction.ALL);
		dateCB.addSelectionHandler(new SelectionHandler<Date>() {
			@Override
			public void onSelection(SelectionEvent<Date> event) {
				showImage(event.getSelectedItem());
			}
		});
		imageVC.setScrollMode(ScrollMode.AUTOY);
		imageVC.setAdjustForScroll(true);

		add(dateCB, new VerticalLayoutData(1, -1, new Margins(5, 100, 10, 100)));
		add(imageVC, new VerticalLayoutData(1, 1));
	}

	public void refresh(int session) {
		imageVC.clear();
		dateCB.getStore().clear();
		dateCB.setValue(null);

		DateWrapper start = new DateWrapper(DateUtil.sessionStart(session));
		//為了迴圈方便，所以 end 多一天
		DateWrapper end = new DateWrapper(DateUtil.sessionEnd(session)).addDays(1);
		DateWrapper now = new DateWrapper();

		while(start.before(end) && start.before(now)) {
			dateCB.getStore().add(start.asDate());
			start = start.addDays(1);
		}
	}

	protected void showImage(Date selectedItem) {
		imageVC.clear();

		for (int i = 1; i < 10; i++) {
			imageVC.add(
				new Image("https://psmonkey.github.io/MYKFZ/" + dateFormat.format(selectedItem) + "/LeaderBoard0" + i + ".png")
			);
		}

		imageVC.add(
			new Image("https://psmonkey.github.io/MYKFZ/" + dateFormat.format(selectedItem) + "/LeaderBoard10.png")
		);
	}
}
