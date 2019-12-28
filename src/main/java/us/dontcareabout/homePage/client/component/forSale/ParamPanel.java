package us.dontcareabout.homePage.client.component.forSale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.IntegerField;

import us.dontcareabout.homePage.client.Util;
import us.dontcareabout.homePage.client.ui.ForSaleView;

public class ParamPanel extends Composite {
	private static ParamPanelUiBinder uiBinder = GWT.create(ParamPanelUiBinder.class);
	interface ParamPanelUiBinder extends UiBinder<Widget, ParamPanel> {}

	@UiField IntegerField player;
	@UiField IntegerField start;
	@UiField CheckBox floor;

	private final ForSaleView parent;

	public ParamPanel(ForSaleView parent) {
		this.parent = parent;
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("submit")
	void clickSubmit(SelectEvent se) {
		if (player.getValue() > 6 || player.getValue() < 3) { return; }
		if (start.getValue() < 1 || start.getValue() > player.getValue()) { return; }
		Util.closeDialog();
		parent.start(player.getValue(), start.getValue() - 1, floor.getValue());
	}
}
