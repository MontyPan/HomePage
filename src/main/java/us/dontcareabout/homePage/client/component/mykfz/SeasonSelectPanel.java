package us.dontcareabout.homePage.client.component.mykfz;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

import us.dontcareabout.homePage.client.Util;
import us.dontcareabout.homePage.client.common.mykfz.DateUtil;
import us.dontcareabout.homePage.client.ui.MykfzView;
import us.dontcareabout.homePage.client.ui.event.mykfz.SelectSeasonChangeEvent;
import us.dontcareabout.homePage.client.ui.event.mykfz.SelectSeasonChangeEvent.SelectSeasonChangeHandler;

public class SeasonSelectPanel extends FramedPanel {
	private TextButton submitBtn = new TextButton("確定");
	private SeasonComboBox seasonCB = new SeasonComboBox();

	public SeasonSelectPanel() {
		setHeaderVisible(false);

		FieldLabel fl = new FieldLabel();
		fl.setText("選擇賽季");
		fl.setLabelWidth(65);
		fl.setWidget(seasonCB);

		submitBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Util.closeDialog();
				MykfzView.selectSeasonChange(seasonCB.getValue());
			}
		});
		MykfzView.addSelectSeasonChange(new SelectSeasonChangeHandler() {
			@Override
			public void onSelectSeasonChange(SelectSeasonChangeEvent event) {
				seasonCB.setValue(event.data);
			}
		});

		add(fl);
		addButton(submitBtn);
	}

	class SeasonComboBox extends ComboBox<Integer> {
		SeasonComboBox() {
			super(
				new ListStore<>(new ModelKeyProvider<Integer>() {
					@Override
					public String getKey(Integer item) {
						return item.toString();
					}
				}),
				new LabelProvider<Integer>() {
					@Override
					public String getLabel(Integer item) {
						return "第 N + " + item + " 季";
					}
				}
			);

			for (int i = DateUtil.nowSession(); i >= 1; i--) {
				getStore().add(i);
			}

			setTriggerAction(TriggerAction.ALL);
		}
	}
}
