package us.dontcareabout.homePage.client.layer.mykfz;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gxt.client.draw.LTextSprite;
import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.homePage.client.Util;
import us.dontcareabout.homePage.client.common.mykfz.DateUtil;

public class SessionLayer extends LayerContainer {
	private static final int HEIGHT = 100;
	private NameLayer nameL = new NameLayer();
	private TextButton leaderBoardTB = new TextButton("排行榜擷圖");

	public SessionLayer() {
		super(1, HEIGHT);

		leaderBoardTB.setBgColor(RGB.DARKGRAY);
		leaderBoardTB.setTextColor(RGB.WHITE);
		leaderBoardTB.setBgRadius(5);

		addLayer(nameL);
		addLayer(leaderBoardTB);
	}

	@Override
	protected void adjustMember(int width, int height) {
		nameL.setLX(15);
		nameL.setLY(5);
		nameL.resize(200, HEIGHT - 10);
		leaderBoardTB.setLX(width - 15 - 100);
		leaderBoardTB.setLY(20);
		leaderBoardTB.resize(100, HEIGHT - 40);
	}

	class NameLayer extends LayerSprite {
		LTextSprite nameTS = new LTextSprite();
		LTextSprite dateTS = new LTextSprite();

		NameLayer() {
			setBgColor(RGB.DARKGRAY);
			setBgRadius(5);

			nameTS.setFontSize(30);
			nameTS.setFill(RGB.WHITE);
			dateTS.setFontSize(16);
			dateTS.setFill(RGB.WHITE);
			add(nameTS);
			add(dateTS);

			setSession(DateUtil.nowSession());
		}

		@Override
		protected void adjustMember() {
			nameTS.setLX(17);
			nameTS.setLY(10);
			dateTS.setLX(17);
			dateTS.setLY(55);
		}

		void setSession(int i) {
			nameTS.setText("第 N + " + i + " 季");
			dateTS.setText(
				Util.dateFormat.format(DateUtil.sessionStart(i)) + "～" +
				Util.dateFormat.format(DateUtil.sessionEnd(i))
			);
		}
	}
}
