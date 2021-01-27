package us.dontcareabout.homePage.client.layer.makeWine;

import com.sencha.gxt.core.client.util.Margins;

import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;
import us.dontcareabout.homePage.client.vo.makeWine.Ingredient;

public class Base_VatLayer extends VerticalLayoutLayer {
	public Base_VatLayer() {
		setMargins(new Margins(5, 5, 5, 55));
		setGap(3);

		addChild(new IngredientLayer(Ingredient.blackBean), 0.5);
		addChild(new IngredientLayer(Ingredient.vat5L), 0.5);
	}
}
