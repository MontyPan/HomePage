package us.dontcareabout.homePage.client.ui;

import java.util.HashMap;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.core.client.util.Margins;

import us.dontcareabout.gxt.client.draw.LRectangleSprite;
import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;
import us.dontcareabout.homePage.client.layer.makeWine.IngredientLayer;
import us.dontcareabout.homePage.client.layer.makeWine.OtherLayer;
import us.dontcareabout.homePage.client.layer.makeWine.SummaryLayer;
import us.dontcareabout.homePage.client.layer.makeWine.WineLayer;
import us.dontcareabout.homePage.client.ui.event.UiCenter;
import us.dontcareabout.homePage.client.ui.event.makeWine.AmountChangeEvent;
import us.dontcareabout.homePage.client.ui.event.makeWine.AmountChangeEvent.AmountChangeHandler;
import us.dontcareabout.homePage.client.vo.makeWine.Composition;
import us.dontcareabout.homePage.client.vo.makeWine.Ingredient;
import us.dontcareabout.homePage.client.vo.makeWine.Ingredient.Type;

public class MakeWineView extends LayerContainer {
	private Composition composition = new Composition();

	private VerticalLayoutLayer root = new VerticalLayoutLayer();
	private WineLayer wineLayer = new WineLayer();
	private OtherLayer otherLayer = new OtherLayer();
	private SummaryLayer summaryLayer = new SummaryLayer();

	public MakeWineView() {
		wineLayer.setComment(wineComment(Composition.WINE_MAX, null));

		double unit = 1.0 / (Ingredient.values().length + 3);

		root.addChild(wineLayer, unit * 6);
		root.addChild(new BR(RGB.LIGHTGRAY), 5);
		root.addChild(otherLayer, unit * 2);
		root.addChild(new BR(RGB.LIGHTGRAY), 5);
		root.addChild(new SingleIngredientLayer(Ingredient.vat5L), unit);
		root.addChild(new BR(RGB.LIGHTGRAY), 5);
		root.addChild(new SingleIngredientLayer(Ingredient.blackBean), unit);
		root.addChild(new BR(RGB.DARKGRAY), 8);
		root.addChild(summaryLayer, unit);

		root.setMargins(5);
		root.setGap(5);

		addLayer(root);

		UiCenter.addAmountChange(new AmountChangeHandler() {
			@Override
			public void onAmountChange(AmountChangeEvent event) {
				update(event);
			}
		});
	}

	@Override
	protected void adjustMember(int width, int height) {
		root.resize(width, height);
	}

	private void update(AmountChangeEvent event) {
		composition.change(event.ingredient, event.amount);

		int wineRemainder = composition.getWineRemainder();

		if (event.ingredient.type == Type.other) {
			otherLayer.setComment(otherComment(event.amount));
		}

		if(event.ingredient.type == Type.wine) {
			wineLayer.setRemainder(wineRemainder);
			wineLayer.setComment(wineComment(wineRemainder, composition.getWineComposition()));
		}

		summaryLayer.setEnable(wineRemainder == 0 && composition.getBlackBean() != 0);

		if (wineRemainder == 0) {
			summaryLayer.setTotal(composition.getTotal());
		}
	}

	private String otherComment(int amount) {
		switch(amount) {
		case 0: return "";
		case 1: return "有點甜～ 有點補～";
		case 2: return "這加得好像有點多...";
		case 3: return "你一定是台南人 [指]";
		default: return "這是黑豆酒不是桂圓酒啊，大佬？";
		}
	}

	private static String wineComment(int remainder, HashMap<Ingredient, Integer> map) {
		if (remainder != 0) { return "還需要加 " + remainder + " 瓶酒"; }

		if (map.size() == 1) {
			if (map.containsKey(Ingredient.rice)) {
				return "我阿罵的麻油雞都比這個濃...";
			}
			if (map.containsKey(Ingredient.ricePure)) {
				return "這批很純！";
			}
			if (map.containsKey(Ingredient.riceHead)) {
				return "內行的都加米酒頭 (y)";
			}
			if (map.containsKey(Ingredient.sorghum38)) {
				return "有種三八的味道 [捏鼻]";
			}
			if (map.containsKey(Ingredient.sorghum58)) {
				return "黑豆酒界的生命之水 [抖]";
			}
		}

		if (map.size() == 2) {
			Integer pure = map.get(Ingredient.ricePure);
			Integer head = map.get(Ingredient.riceHead);

			if (pure != null && head != null && pure == 5 && head == 1) {
				return "\\囧/ 太座指定組合 \\囧/";
			}

			return "你濃我濃（？）";
		}

		if (map.size() == 3) { return "三分天下"; }
		if (map.size() == 4) { return "湊在一起打麻將"; }
		if (map.size() == 5) { return "混搭大師"; }

		return "糟糕，有駭客！？";	//不可能發生... 除非有 bug...
	}

	private class BR extends LayerSprite {
		LRectangleSprite line = new LRectangleSprite();

		BR(RGB color) {
			this.add(line);
			line.setFill(color);
		}

		@Override
		protected void adjustMember() {
			line.setLX(getWidth() * 0.1);;
			line.setWidth(getWidth() * 0.8);
			line.setHeight(getHeight());
		}
	}

	//純粹是為了要向右內縮而搞出來的無聊東西
	private class SingleIngredientLayer extends VerticalLayoutLayer {
		SingleIngredientLayer(Ingredient igdnt) {
			setMargins(new Margins(5, 5, 5, 55));
			addChild(new IngredientLayer(igdnt), 1);
		}
	}
}
