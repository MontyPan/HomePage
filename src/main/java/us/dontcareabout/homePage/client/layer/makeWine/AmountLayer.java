package us.dontcareabout.homePage.client.layer.makeWine;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent.SpriteSelectionHandler;

import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;
import us.dontcareabout.homePage.client.ui.event.UiCenter;
import us.dontcareabout.homePage.client.vo.makeWine.Ingredient;

public class AmountLayer extends HorizontalLayoutLayer {
	private final Ingredient ingredient;

	private int amount = 0;
	private int max = Integer.MAX_VALUE;

	private Button minus = new Button(false);
	private TextButton amountTB = new TextButton("0");
	private Button plus = new Button(true);

	public AmountLayer(Ingredient igdnt) {
		ingredient = igdnt;

		setMargins(5);
		addChild(minus, 0.2);
		addChild(amountTB, 0.6);
		addChild(plus, 0.2);
	}

	public void setMax(int value) {
		max = value + amount;
	}

	public int getAmount() {
		return amount;
	}

	private void setAmount(int value) {
		if (amount == value) { return; }

		amount = value;
		amountTB.setText("" + value);
		UiCenter.amountChange(ingredient, amount);
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
						setAmount(Math.min(max, getAmount() + 1));
					} else {
						setAmount(Math.max(0, getAmount() - 1));
					}
				}
			});
		}
	}
}
