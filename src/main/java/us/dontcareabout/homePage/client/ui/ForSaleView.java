package us.dontcareabout.homePage.client.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;

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
		playerList.setMargins(5);
		playerList.setGap(10);

		param = new Parameter(5, 3);//TODO magic number

		players = new PlayerLayer[param.playerAmount];
		for (int i = 0; i < param.playerAmount; i++) {
			PlayerLayer pl = new PlayerLayer(this, i);
			playerList.addChild(pl, 1.0 / param.playerAmount);
			players[i] = pl;
		}

		switchNowPlayer();

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
		if (!param.isMyTurn(player) || !param.bidMode) { return; }

		int house = param.pool.remove(0);
		players[player].recieve(house);
		poolLayer.hide(house);

		//懶得作最後一位自動 pass 的功能... [逃]

		if (param.playerEnd(player)) {
			poolLayer.clear();
			param.nowPrice = 0;
			updateLowestPrice();

			if (!param.bidMode) {
				numberGrid.moneyMode();
			}
		} else {
			players[player].returnBid();
		}

		switchNowPlayer();
	}

	//因為可能會改變 PlayerLayer 的金額，所以透過 return 值來判斷要不要改變
	//很爛的作法...... (艸
	public boolean bid(int player, int value) {
		if (!param.isMyTurn(player) || !param.bidMode) { return false; }

		param.nowPrice = value;
		param.playerBid(player);
		switchNowPlayer();
		return true;
	}

	public boolean sell(int index, int house) {
		if (param.bidMode || !param.turnReady) { return false; }
		if (param.playerSell[index] != 0) { return false; }

		param.playerSell[index] = house;

		if (param.allSell()) {
			poolLayer.clear();
			param.sellEnd();
			//懶得把金額加回去了 XD
		}
		return true;
	}

	@Override
	protected void onResize(int width, int height) {
		root.resize(width, height);
		super.onResize(width, height);
	}

	private void switchNowPlayer() {
		for (int i = 0; i < param.playerAmount; i++) {
			players[i].setBgColor(!param.bidMode || i != param.nowPlayer ? Color.NONE : RGB.YELLOW);
		}

		//懶得作細部調整了，全部都改 XD
		updateLowestPrice();
	}

	private void updateLowestPrice() {
		for (int i = 0; i < param.playerAmount; i++) {
			players[i].lowestPrice(param.nowPrice);
		}
	}


	public static class Parameter {
		private static final int[] INIT_MONEY = {18, 18, 14, 14};

		public final int playerAmount;

		/** pass 時是否返回向下取整的金額 */
		public final boolean floorMode;

		private boolean bidMode = true;
		private boolean turnReady;
		private int nowTurn = 1;
		private int nowPrice;
		private int nowPlayer;
		private boolean[] playerPass;
		private int[] playerSell;
		private ArrayList<Integer> pool = new ArrayList<>();

		public Parameter(int playerAmount, int startPlayer) {
			this(playerAmount, startPlayer, true);
		}

		public Parameter(int playerAmount, int startPlayer, boolean floorMode) {
			this.playerAmount = playerAmount;
			this.floorMode = floorMode;
			playerPass = new boolean[playerAmount];
			playerSell = new int[playerAmount];
			nowPlayer = startPlayer;
		}

		public int getInitMoney() {
			return INIT_MONEY[playerAmount - 3];
		}

		public int getTurnAmount() {
			return 11 - playerAmount;
		}

		private boolean isMyTurn(int player) {
			return turnReady && nowPlayer == player;
		}

		private void newTurn() {
			if (nowTurn == getTurnAmount()) {
				if (bidMode) {
					bidMode = !bidMode;
					nowTurn = 0;	//反正等一下會加回來
				}
			}
			nowTurn++;
			nowPrice = 0;
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
			}

			nowPlayer = nowPlayer % playerAmount;

			return counter == playerAmount;
		}

		private void playerBid(int player) {
			//bid 不會導致結束回合，所以只要找下一個沒 pass 的玩家就好
			do {
				nowPlayer = (nowPlayer + 1) % playerAmount;
			} while(playerPass[nowPlayer]);
		}

		private boolean allSell() {
			for (int i : playerSell) {
				if (i == 0) { return false; }
			}

			return true;
		}

		private void sellEnd() {
			nowTurn++;
			turnReady = false;
			pool.clear();
			Arrays.fill(playerSell, 0);
		}
	}
}
