package us.dontcareabout.homePage.client.layer.forSale;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;
import us.dontcareabout.homePage.client.ui.ForSaleView;

public 	class PoolLayer extends HorizontalLayoutLayer {
	private final ForSaleView parent;

	public PoolLayer(ForSaleView parent) {
		this.parent = parent;

		setMargins(4);
		setGap(4);
	}

	public void add(int value) {
		addChild(new NumberButton(value), 1.0 / parent.param.playerAmount);
		redeploy();
		adjustMember();
	}

	private class NumberButton extends TextButton {
		final int number;

		NumberButton(int value) {
			number = value;
			setText("" + value);
			setBgRadius(5);
			setBgColor(RGB.RED);
			setTextColor(RGB.WHITE);
		}
	}
}