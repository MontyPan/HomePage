package us.dontcareabout.homePage.client.layer.makeWine;

import us.dontcareabout.homePage.client.vo.makeWine.Ingredient.Type;

public class WineLayer extends TitleContentLayer {
	public WineLayer() {
		super(Type.wine, "酒：");
		setRemainder(6);
	}

	public void setRemainder(int remainder) {
		for (IngredientLayer il : getItemList()) {
			il.setMaxAmount(remainder);
		}

		if (remainder == 0) { return; }

		setComment("還需要加 " + remainder + " 瓶酒");
	}
}
