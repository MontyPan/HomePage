package us.dontcareabout.homePage.client.layer.makeWine;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent.SpriteSelectionHandler;

import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;

public class AmountLayer extends HorizontalLayoutLayer {
	private int amount = 0;

	private Button minus = new Button(false);
	private TextButton amountTB = new TextButton();
	private Button plus = new Button(true);

	public AmountLayer() {
		setMargins(5);
		addChild(minus, 0.2);
		addChild(amountTB, 0.6);
		addChild(plus, 0.2);

		setAmount(0);
	}

	public int getAmount() {
		return amount;
	}

	private void setAmount(int value) {
		amount = value;
		amountTB.setText("" + value);
	}

	private class Button extends TextButton {
		Button(boolean isPlus) {
			super(isPlus ? "＋" : "－");
			setBgRadius(10);
			setBgColor(RGB.BLACK);
			setTextColor(RGB.WHITE);

			addSpriteSelectionHandler(new SpriteSelectionHandler() {
				@Override
				public void onSpriteSelect(SpriteSelectionEvent event) {
					if (isPlus) {
						setAmount(getAmount() + 1);
					} else {
						setAmount(Math.max(0, getAmount() - 1));
					}
				}
			});
		}
	}
}
