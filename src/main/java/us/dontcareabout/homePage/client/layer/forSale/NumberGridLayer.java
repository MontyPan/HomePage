package us.dontcareabout.homePage.client.layer.forSale;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;
import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;
import us.dontcareabout.homePage.client.ui.ForSaleView;

public class NumberGridLayer extends VerticalLayoutLayer {
	private static final int ROW = 3;
	private static final int COL = 10;

	private final ForSaleView parent;

	private HorizontalLayoutLayer[] rows = new HorizontalLayoutLayer[ROW];

	public NumberGridLayer(ForSaleView parent) {
		this.parent = parent;

		setMargins(5);
		setGap(5);

		for (int i = 0; i < ROW; i++) {
			rows[i] = new HorizontalLayoutLayer();
			rows[i].setMargins(2);
			rows[i].setGap(2);
			addChild(rows[i], 1.0 / ROW);
		}

		moneyMode();
	}

	public void houseMode() {
		for (HorizontalLayoutLayer r : rows) { r.clear(); }

		for (int i = 0; i < ROW; i++) {
			for (int i2 = 0; i2 < COL; i2++) {
				rows[i].addChild(new NumberButton(i * COL + i2 + 1), 1.0 / COL);
			}
		}
	}

	public void moneyMode() {
		for (HorizontalLayoutLayer r : rows) { r.clear(); }

		rows[0].addChild(new NumberButton(0), 1.0 / COL);
		rows[0].addChild(new NumberButton(0), 1.0 / COL);

		int counter = 1;

		for (int i = 2; i < 16; i++) {
			rows[counter / 5].addChild(new NumberButton(i), 1.0 / COL);
			rows[counter / 5].addChild(new NumberButton(i), 1.0 / COL);
			counter++;
		}
	}

	private class NumberButton extends TextButton {
		final int value;

		NumberButton(int value) {
			this.value = value;
			setText("" + value);
			setBgRadius(5);
			setBgColor(RGB.PINK);
		}
	}
}
