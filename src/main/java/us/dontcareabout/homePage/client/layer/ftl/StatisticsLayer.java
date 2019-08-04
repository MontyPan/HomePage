package us.dontcareabout.homePage.client.layer.ftl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.google.gwt.i18n.client.NumberFormat;
import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gxt.client.draw.LRectangleSprite;
import us.dontcareabout.gxt.client.draw.LTextSprite;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.homePage.client.data.FTL;

public class StatisticsLayer extends LayerSprite {
	private static NumberFormat numberFormat = NumberFormat.getFormat("##.#");
	private static RGB shipBG = new RGB("#70EEFF");

	private ArrayList<ShipLayer> ships = new ArrayList<>();

	public void refresh(ArrayList<FTL> data) {
		HashMap<String, ShipLayer> shipMap = new HashMap<>();

		for (FTL ftl : data) {
			ShipLayer sl = shipMap.get(ftl.getShip());

			if (sl == null) {
				sl = new ShipLayer(ftl.getShip());
				shipMap.put(ftl.getShip(), sl);
				ships.add(sl);
				add(sl);
			}

			sl.amount += ftl.getAmount();
			sl.success++;
		}

		Collections.sort(ships);
		redeploy();
		adjustMember();
	}

	@Override
	protected void adjustMember() {
		final int space = 3;
		final int hUnit = 25;

		int index = 0;

		for (ShipLayer sl : ships) {
			sl.resize(getWidth() - space * 3, hUnit);
			sl.setLX(3);
			sl.setLY((index + 1) * space + index * hUnit);
			index++;
		}
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
			amountRS.setHeight(getHeight());

			nameTS.setText(ship + " (" + numberFormat.format(getRatio()) + ")");
			nameTS.setLX(6);
			nameTS.setLY(3);
		}
	}
}
