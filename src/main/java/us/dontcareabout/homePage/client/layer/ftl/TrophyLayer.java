package us.dontcareabout.homePage.client.layer.ftl;

import java.util.ArrayList;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gxt.client.draw.LTextSprite;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.homePage.client.Util;
import us.dontcareabout.homePage.client.data.FTL;
import us.dontcareabout.homePage.client.gf.VerticalLayoutLayer;

public class TrophyLayer extends VerticalLayoutLayer {
	private UnitLayer longest = new UnitLayer(new UnitBuilder() {
		@Override
		public String title() { return "慢慢長路獎"; }

		@Override
		public String info(FTL ftl) {
			return "打了 " + ftl.getLength() + " 天";
		}
	});
	private UnitLayer dieHard = new UnitLayer(new UnitBuilder() {
		@Override
		public String title() { return "屢敗屢戰獎"; }

		@Override
		public String info(FTL ftl) {
			return "平均一天死 " + Util.numberFormat.format(ftl.getAmount() / ftl.getLength()) + " 次";
		}
	});
	private UnitLayer damnTitle = new UnitLayer(new UnitBuilder() {
		@Override
		public String title() { return "倒楣透頂獎"; }

		@Override
		public String info(FTL ftl) {
			return "死了 " + ftl.getAmount() + " 次";
		}
	});

	public TrophyLayer() {
		addChild(longest, 130);
		addChild(dieHard, 130);
		addChild(damnTitle, 130);
	}

	public void refresh(ArrayList<FTL> data) {
		longest.ftl = data.get(0);
		dieHard.ftl = data.get(0);
		damnTitle.ftl = data.get(0);

		for (FTL ftl : data) {
			if (ftl.getLength() >= longest.ftl.getLength()) {
				longest.ftl = ftl;
			}
			if (ftl.getAmount() / ftl.getLength() >= dieHard.ftl.getAmount() / dieHard.ftl.getLength()) {
				dieHard.ftl = ftl;
			}
			if (ftl.getAmount() > damnTitle.ftl.getAmount()) {
				damnTitle.ftl = ftl;
			}
		}

		adjustMember();
	}

	private class UnitLayer extends LayerSprite {
		FTL ftl;
		LTextSprite titleTS = new LTextSprite();
		TextButton ship = new TextButton();
		LTextSprite sectionTS = new LTextSprite();
		LTextSprite infoTS = new LTextSprite();
		final UnitBuilder infoBuilder;

		UnitLayer(UnitBuilder ib) {
			infoBuilder = ib;
			titleTS.setText(infoBuilder.title());
			titleTS.setFontSize(18);
			ship.setBgColor(RGB.GREEN);
			ship.setBgOpacity(0.6);
			ship.setBgRadius(5);
			add(titleTS);
			add(ship);
			add(sectionTS);
			add(infoTS);
		}

		@Override
		protected void adjustMember() {
			if (ftl == null) { return; }

			final int space = 5;
			titleTS.setLX(space);
			titleTS.setLY(space);
			ship.setLX(space * 4);
			ship.setLY(35);
			ship.setText(ftl.getShip());
			ship.resize(getWidth() - space * 6, 50);
			sectionTS.setLX(space * 4);
			sectionTS.setLY(85);
			sectionTS.setText(
				Util.dateFormat.format(ftl.getStart().asDate()) + " ~ " + Util.dateFormat.format(ftl.getEnd().asDate())
			);
			infoTS.setLX(space * 4);
			infoTS.setLY(100);
			infoTS.setText(infoBuilder.info(ftl));
		}
	}

	interface UnitBuilder {
		String title();
		String info(FTL ftl);
	}
}
