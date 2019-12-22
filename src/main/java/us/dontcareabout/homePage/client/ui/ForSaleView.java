package us.dontcareabout.homePage.client.ui;

import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;
import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;
import us.dontcareabout.homePage.client.layer.forSale.NumberGridLayer;
import us.dontcareabout.homePage.client.layer.forSale.PlayerLayer;

public class ForSaleView extends LayerContainer {
	private HorizontalLayoutLayer root = new HorizontalLayoutLayer();
	private VerticalLayoutLayer playerList = new VerticalLayoutLayer();
	private NumberGridLayer numberGrid = new NumberGridLayer(this);

	public Parameter param;

	public ForSaleView() {
		playerList.setMargins(2);
		playerList.setGap(2);

		param = new Parameter(4);

		for (int i = 0; i < param.playerAmount; i++) {
			playerList.addChild(new PlayerLayer(this, i), 1.0 / param.playerAmount);
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

	public static class Parameter {
		private static final int[] INIT_MONEY = {18, 18, 14, 14};

		public final int playerAmount;

		private int nowTurn = 1;

		public Parameter(int playerAmount) {
			this.playerAmount = playerAmount;
		}

		public int getInitMoney() {
			return INIT_MONEY[playerAmount - 3];
		}

		public int getTurnAmount() {
			return 11 - playerAmount;
		}
	}
}