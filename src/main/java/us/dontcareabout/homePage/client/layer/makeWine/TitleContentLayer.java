package us.dontcareabout.homePage.client.layer.makeWine;

import java.util.ArrayList;
import java.util.List;

import com.sencha.gxt.core.client.util.Margins;

import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;
import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;
import us.dontcareabout.homePage.client.vo.makeWine.Ingredient;
import us.dontcareabout.homePage.client.vo.makeWine.Ingredient.Type;

class TitleContentLayer extends VerticalLayoutLayer {
	private TextButton titleTB = new TextButton();
	private TextButton commentTB = new TextButton();

	private ArrayList<IngredientLayer> itemList = new ArrayList<>();

	private VerticalLayoutLayer itemL = new VerticalLayoutLayer();
	private HorizontalLayoutLayer titleL = new HorizontalLayoutLayer();

	TitleContentLayer(Type type, String title) {
		setMargins(5);
		setGap(5);

		titleTB.setText(title);
		commentTB.setMargin(5);

		titleL.setGap(5);
		titleL.addChild(titleTB, 0.2);
		titleL.addChild(commentTB, 0.8);

		for (Ingredient igdnt : Ingredient.values()) {
			if (igdnt.type != type) { continue; }

			itemList.add(new IngredientLayer(igdnt));
		}

		for (IngredientLayer il : itemList) {
			itemL.addChild(il, 1.0 / itemList.size());
		}

		itemL.setMargins(new Margins(0, 0, 0, 50));
		itemL.setGap(3);

		double weight = 1.0 / (itemList.size() + 1);
		addChild(titleL, weight);
		addChild(itemL, 1 - weight);
	}

	public void setComment(String comment) {
		commentTB.setText(comment);
	}

	List<IngredientLayer> getItemList() {
		return itemList;
	}
}
