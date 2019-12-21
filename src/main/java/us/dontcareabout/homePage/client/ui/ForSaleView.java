package us.dontcareabout.homePage.client.ui;

import us.dontcareabout.gxt.client.draw.LayerContainer;

public class ForSaleView extends LayerContainer {
	public static final int[] INIT_MONEY = {18, 18, 14, 14};

	public static final int totalTurn(int total) {
		return 11 - total;
	}
}
