package us.dontcareabout.homePage.client.layer.forSale;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;
import us.dontcareabout.homePage.client.ui.ForSaleView;

public class PlayerListLayer extends VerticalLayoutLayer {
	private final ForSaleView parent;
	private PlayerLayer[] players;

	public PlayerListLayer(ForSaleView parent) {
		this.parent = parent;
	}

	public void init() {
		players = new PlayerLayer[parent.param.playerAmount];
		for (int i = 0; i < parent.param.playerAmount; i++) {
			players[i] = new PlayerLayer(parent, i);
			addChild(players[i], 1.0 / parent.param.playerAmount);
		}

		redeploy();
		redraw();
		adjustMember();
	}

	public void changeBg() {
		for (int i = 0; i < parent.param.playerAmount; i++) {
			players[i].setBgColor(!parent.param.isBidMode() || i != parent.param.getNowPlayer() ? Color.NONE : RGB.YELLOW);
		}
	}

	public void updateLowestPrice() {
		for (int i = 0; i < parent.param.playerAmount; i++) {
			players[i].lowestPrice(parent.param.getNowPrice());
		}
	}

	public void returnBid(int player) {
		players[player].returnBid();
	}

	public void recieve(int player, int house) {
		players[player].recieve(house);
	}

	public void resetBidPrice() {
		for (PlayerLayer pl : players) {
			pl.resetBidPrice();
		}
	}
}