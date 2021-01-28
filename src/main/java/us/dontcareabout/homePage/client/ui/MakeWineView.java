package us.dontcareabout.homePage.client.ui;

import java.util.HashMap;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gxt.client.draw.LRectangleSprite;
import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;
import us.dontcareabout.homePage.client.layer.makeWine.Base_VatLayer;
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
	private Base_VatLayer baseVatLayer = new Base_VatLayer();
	private SummaryLayer summaryLayer = new SummaryLayer();

	public MakeWineView() {
		double unit = 1.0 / (Ingredient.values().length + 3);

		root.addChild(wineLayer, unit * 6);
		root.addChild(otherLayer, unit * 2);
		root.addChild(new BR(RGB.LIGHTGRAY), 5);
		root.addChild(baseVatLayer, unit * 2);
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

		if(event.ingredient.type == Type.wine) {
			wineLayer.setRemainder(wineRemainder);
			wineLayer.setComment(wineComment(wineRemainder, composition.getWineComposition()));
		}

		summaryLayer.setEnable(wineRemainder == 0);

		if (wineRemainder == 0) {
			summaryLayer.setTotal(composition.getTotal());
		}
	}

	private static String wineComment(int remainder, HashMap<Ingredient, Integer> map) {
		if (remainder != 0) { return "還需要加 " + remainder + " 瓶酒"; }

		return "";//FIXME
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
}
