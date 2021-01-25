package us.dontcareabout.homePage.client.layer.makeWine;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent.SpriteSelectionHandler;

import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;
import us.dontcareabout.homePage.client.ui.event.UiCenter;
import us.dontcareabout.homePage.client.ui.event.makeWine.AmountChangeEvent;
import us.dontcareabout.homePage.client.ui.event.makeWine.AmountChangeEvent.AmountChangeHandler;
import us.dontcareabout.homePage.client.vo.makeWine.Ingredient;
import us.dontcareabout.homePage.client.vo.makeWine.Ingredient.Type;

public class IngredientLayer extends HorizontalLayoutLayer {
	private static final RGB[] COLOR = {
		new RGB("#263238"),	//base / vat
		new RGB("#1565c0"),	//wine
		new RGB("#bf360c"),	//other
	};

	private final Ingredient ingredient;

	private TextButton nameTB;
	private TextButton priceTB;
	private AmountLayer amountL;
	private TextButton totalTB;

	public IngredientLayer(Ingredient igdnt) {
		ingredient = igdnt;
		RGB color = pickColor();

		nameTB = new TextButton(ingredient.name);
		nameTB.setTextColor(color);
		priceTB = new TextButton("" + ingredient.price);
		priceTB.setBgColor(color);
		priceTB.setTextColor(RGB.WHITE);
		priceTB.setBgRadius(10);
		amountL = new AmountLayer(ingredient);
		totalTB = new TextButton("0");
		totalTB.setTextColor(color);

		addChild(nameTB, 0.4);
		addChild(priceTB, 0.16);
		addChild(igdnt.type == Type.vat ? new CheckboxLayer() : amountL, 0.2);
		addChild(totalTB, 0.24);

		UiCenter.addAmountChange(new AmountChangeHandler() {
			@Override
			public void onAmountChange(AmountChangeEvent event) {
				if (event.ingredient != ingredient) { return; }

				totalTB.setText(event.amount * ingredient.price + "");
			}
		});
	}

	public void setMaxAmount(int max) {
		amountL.setMax(max);
	}

	private RGB pickColor() {
		switch(ingredient.type) {
		case base:
		case vat:
			return COLOR[0];
		case wine:
			return COLOR[1];
		case other:
			return COLOR[2];
		default:
			return COLOR[1];
		}
	}

	private class CheckboxLayer extends TextButton {
		boolean isChecked;

		CheckboxLayer() {
			refresh();
			addSpriteSelectionHandler(new SpriteSelectionHandler() {
				@Override
				public void onSpriteSelect(SpriteSelectionEvent event) {
					isChecked = !isChecked;
					refresh();
					UiCenter.amountChange(ingredient, isChecked ? 1 : 0);
				}
			});
		}

		private void refresh() {
			setText(isChecked ? "V" : "無");
		}
	}
}
