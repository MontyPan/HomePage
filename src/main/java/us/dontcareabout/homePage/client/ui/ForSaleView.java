package us.dontcareabout.homePage.client.ui;

import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;
import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;
import us.dontcareabout.homePage.client.layer.forSale.NumberGridLayer;
import us.dontcareabout.homePage.client.layer.forSale.PlayerLayer;

public class ForSaleView extends LayerContainer {
	public static final int[] INIT_MONEY = {18, 18, 14, 14};

	public static final int totalTurn(int total) {
		return 11 - total;
	}

	private HorizontalLayoutLayer root = new HorizontalLayoutLayer();
	private VerticalLayoutLayer playerList = new VerticalLayoutLayer();
	private NumberGridLayer numberGrid = new NumberGridLayer(this);

	private int playerAmount = 4;

	public ForSaleView() {
		playerList.setMargins(2);
		playerList.setGap(2);

		for (int i = 0; i < playerAmount; i++) {
			playerList.addChild(new PlayerLayer(this, i, playerAmount), 1.0 / playerAmount);
		}

		VerticalLayoutLayer boardVL = new VerticalLayoutLayer();
		boardVL.addChild(numberGrid, 1);

		root.addChild(boardVL, 1);
		root.addChild(playerList, 300);
		addLayer(root);
	}

	@Override
	protected void onResize(int width, int height) {
		root.resize(width, height);
		super.onResize(width, height);
	}
}
