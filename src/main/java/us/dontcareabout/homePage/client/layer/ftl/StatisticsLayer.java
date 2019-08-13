package us.dontcareabout.homePage.client.layer.ftl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gxt.client.draw.LRectangleSprite;
import us.dontcareabout.gxt.client.draw.LTextSprite;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.homePage.client.Util;
import us.dontcareabout.homePage.client.data.FTL;
import us.dontcareabout.homePage.client.gf.VerticalLayoutLayer;

public class StatisticsLayer extends VerticalLayoutLayer {
	private static RGB shipBG = new RGB("#70EEFF");

	private ArrayList<ShipLayer> ships = new ArrayList<>();

	public StatisticsLayer() {
		setMargin(3);
	}

	public void refresh(ArrayList<FTL> data) {
		HashMap<String, ShipLayer> shipMap = new HashMap<>();

		for (FTL ftl : data) {
			ShipLayer sl = shipMap.get(ftl.getShip());

			if (sl == null) {
				sl = new ShipLayer(ftl.getShip());
				shipMap.put(ftl.getShip(), sl);
				ships.add(sl);
			}

			sl.amount += ftl.getAmount();
			sl.success++;
		}

		Collections.sort(ships);

		for (ShipLayer sl : ships) {
			addChild(sl, 28);
		}

		redeploy();
		adjustMember();
	}

	private class ShipLayer extends LayerSprite implements Comparable<ShipLayer> {
		String ship;
		int amount;
		int success;

		LRectangleSprite amountRS = new LRectangleSprite();
		LTextSprite nameTS = new LTextSprite();

		ShipLayer(String ship) {
			this.ship = ship;
			amountRS.setFill(shipBG);
			amountRS.setRadius(5);
			nameTS.setFontSize(14);
			add(amountRS);
			add(nameTS);
		}

		double getRatio() {
			return amount * 1.0 / success;
		}

		@Override
		public int compareTo(ShipLayer o) {
			return Double.compare(o.getRatio(), getRatio());
		}

		@Override
		protected void adjustMember() {
			amountRS.setLX(0);
			amountRS.setLY(0);
			amountRS.setWidth(getWidth() * getRatio() / ships.get(0).getRatio());
			amountRS.setHeight(getHeight() - 3);

			nameTS.setText(ship + " (" + Util.numberFormat.format(getRatio()) + ")");
			nameTS.setLX(6);
			nameTS.setLY(3);
		}
	}
}
