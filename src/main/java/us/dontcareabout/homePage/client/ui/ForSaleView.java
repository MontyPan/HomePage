package us.dontcareabout.homePage.client.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;
import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;
import us.dontcareabout.homePage.client.layer.forSale.NumberGridLayer;
import us.dontcareabout.homePage.client.layer.forSale.PlayerLayer;
import us.dontcareabout.homePage.client.layer.forSale.PoolLayer;

public class ForSaleView extends LayerContainer {
	private HorizontalLayoutLayer root = new HorizontalLayoutLayer();
	private VerticalLayoutLayer playerList = new VerticalLayoutLayer();
	private NumberGridLayer numberGrid = new NumberGridLayer(this);
	private PoolLayer poolLayer = new PoolLayer(this);

	private PlayerLayer[] players;

	public Parameter param;

	public ForSaleView() {
		playerList.setMargins(2);
		playerList.setGap(2);

		param = new Parameter(5, 3);//TODO magic number

		players = new PlayerLayer[param.playerAmount];
		for (int i = 0; i < param.playerAmount; i++) {
			PlayerLayer pl = new PlayerLayer(this, i);
			playerList.addChild(pl, 1.0 / param.playerAmount);
			players[i] = pl;
		}

		VerticalLayoutLayer boardVL = new VerticalLayoutLayer();
		boardVL.addChild(poolLayer, 300);
		boardVL.addChild(numberGrid, 1);

		root.addChild(boardVL, 1);
		root.addChild(playerList, 300);
		addLayer(root);
	}

	public boolean deal(int number) {
		if (param.turnReady) { return false; }

		poolLayer.add(number);
		param.poolAdd(number);
		return true;
	}

	public void pass(int player) {
		if (!param.turnReady) { return; }
		if (player != param.nowPlayer) { return; }

		int house = param.pool.remove(0);
		players[player].recieve(house);
		poolLayer.hide(house);

		if (param.playerEnd(player)) {
			poolLayer.clear();
		}
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
		private boolean turnReady;
		private int nowPlayer;
		private boolean[] playerPass;
		private ArrayList<Integer> pool = new ArrayList<>();

		public Parameter(int playerAmount, int startPlayer) {
			this.playerAmount = playerAmount;
			playerPass = new boolean[playerAmount];
			nowPlayer = startPlayer;
		}

		public int getInitMoney() {
			return INIT_MONEY[playerAmount - 3];
		}

		public int getTurnAmount() {
			return 11 - playerAmount;
		}

		public void newTurn() {
			//TODO 切換 mode
			nowTurn++;
			turnReady = false;
			pool.clear();
			Arrays.fill(playerPass, false);
		}

		private void poolAdd(int number) {
			pool.add(number);
			turnReady = pool.size() == playerAmount;

			if (turnReady) { Collections.sort(pool); }
		}

		/**
		 * @return 是不是結束該回合
		 */
		private boolean playerEnd(int player) {
			playerPass[player] = true;
			nowPlayer++;

			int counter = 1;

			while (playerPass[nowPlayer % playerAmount] && counter != playerAmount) {
				nowPlayer++;
				counter++;
			}

			if (counter == playerAmount) {
				newTurn();
				//觸發結束回合的玩家就是下一回合的起始玩家
				//還要再多作一次才會回到自己
				nowPlayer++;
			}

			nowPlayer = nowPlayer % playerAmount;

			return counter == playerAmount;
		}
	}
}
