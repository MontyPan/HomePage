package us.dontcareabout.homePage.client.layer.forSale;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent.SpriteSelectionHandler;
import com.sencha.gxt.core.client.util.Margins;

import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;
import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;
import us.dontcareabout.homePage.client.ui.ForSaleView;

class PlayerLayer extends VerticalLayoutLayer {
	private static final Color[] COLORS = {
		new RGB("#43CC00"), new RGB("#000DB3"), new RGB("#FF8C52"),
		new RGB("#00C2A1"), new RGB("#EC77FF"), RGB.BLACK
	};

	private TextButton nameMoney = new TextButton();
	private BidLayer bidLayer = new BidLayer();
	private HouseLayer houseLayer;

	private final ForSaleView parent;
	private final int index;
	private int money;
	private int bidPrice;
	private final String name;

	public PlayerLayer(ForSaleView parent, int index) {
		this.parent = parent;
		this.index = index;
		this.name = index == 0 ? "Me" : "Player " + (char)(64 + index);

		houseLayer = new HouseLayer(parent.param.playerAmount);

		nameMoney.setBgColor(COLORS[index]);
		nameMoney.setTextColor(RGB.WHITE);
		setMoney(parent.param.getInitMoney());

		addChild(nameMoney, 36);
		addChild(bidLayer, 0.5);
		addChild(houseLayer, 0.5);
	}

	public void recieve(int house) {
		houseLayer.addHouse(house);
	}

	public void lowestPrice(int price) {
		bidLayer.setPrice(price + 1);
	}

	public void resetBidPrice() {
		bidPrice = 0;
	}

	public void returnBid() {
		setMoney(
			money +
			(parent.param.floorMode ? (int)Math.floor(bidPrice / 2.0) : (int)Math.ceil(bidPrice / 2.0))
		);
	}

	private void setMoney(int value) {
		money = value;
		nameMoney.setText(name + " : " + money);
	}

	class BidLayer extends HorizontalLayoutLayer {
		TextButton bidMoney = new TextButton("00");

		BidLayer() {
			GrayButton pass = new GrayButton("pass");
			GrayButton plus = new GrayButton("+");
			GrayButton bid = new GrayButton("BID");

			setMargins(3);
			setGap(2);

			addChild(pass, 60);
			addChild(bidMoney, 1);
			addChild(plus, 50);
			addChild(bid, 60);

			plus.addSpriteSelectionHandler(new SpriteSelectionHandler() {
				@Override
				public void onSpriteSelect(SpriteSelectionEvent event) {
					setPrice(getPrice() + 1);
				}
			});
			pass.addSpriteSelectionHandler(new SpriteSelectionHandler() {
				@Override
				public void onSpriteSelect(SpriteSelectionEvent event) {
					parent.pass(index);
				}
			});
			bid.addSpriteSelectionHandler(new SpriteSelectionHandler() {
				@Override
				public void onSpriteSelect(SpriteSelectionEvent event) {
					bidPrice = getPrice();

					if (parent.bid(index, bidPrice)) {
						setMoney(money - bidPrice);
					}
				}
			});
		}

		void setPrice(int price) {
			bidMoney.setText("" + price);
		}

		int getPrice() {
			return Integer.parseInt(bidMoney.getText());
		}
	}

	class HouseLayer extends HorizontalLayoutLayer {
		final double unitWeight;

		HouseLayer(int total) {
			unitWeight = 1.0 / (parent.param.getTurnAmount());
			setMargins(new Margins(2, 2, 6, 2));
			setGap(2);
		}

		void addHouse(final int value) {
			final RedButton house = new RedButton(value);
			house.addSpriteSelectionHandler(new SpriteSelectionHandler() {
				@Override
				public void onSpriteSelect(SpriteSelectionEvent event) {
					if (parent.sell(index, value)) {
						house.setHidden(true);
					}
				}
			});
			addChild(house, unitWeight);
			redeploy();
			redraw();
			adjustMember();
		}
	}

	class GrayButton extends TextButton {
		GrayButton(String text) {
			super(text);
			setBgRadius(5);
			setBgColor(RGB.LIGHTGRAY);
		}
	}

	class RedButton extends TextButton {
		RedButton(int number) {
			super("" + number);
			setBgRadius(2);
			setBgColor(RGB.RED);
			setTextColor(RGB.WHITE);
		}
	}
}