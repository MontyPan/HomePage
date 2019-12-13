package us.dontcareabout.homePage.client.layer.ftl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent.SpriteSelectionHandler;

import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;
import us.dontcareabout.homePage.client.ui.FtlView;
import us.dontcareabout.homePage.client.ui.FtlView.ChangeYearEvent;
import us.dontcareabout.homePage.client.ui.FtlView.ChangeYearHandler;

//TODO 年份過多出現左右捲動按鈕
class YearLayer extends HorizontalLayoutLayer {
	private ArrayList<YearButton> list = new ArrayList<>();
	private YearButton now;

	public YearLayer() {
		setMargins(3);
		FtlView.addChangeYear(new ChangeYearHandler() {
			@Override
			public void onChangeYear(ChangeYearEvent event) {
				for (YearButton yb : list) {
					yb.setEnable(yb.year == event.year);
				}
			}
		});
	}

	public void refresh(Set<Integer> set) {
		for (YearButton tb : list) {
			remove(tb);
		}

		ArrayList<Integer> yearList = new ArrayList<>(set);
		Collections.sort(yearList);

		for (int year : set) {
			YearButton tb = new YearButton(year);
			addChild(tb, 70);
			list.add(tb);
		}

		now = list.get(list.size() - 1);
		now.setEnable(true);

		redeploy();
		adjustMember();
	}

	private class YearButton extends TextButton {
		int year;

		YearButton(int value) {
			super("" + value);
			this.year = value;
			setBgRadius(5);
			setEnable(false);
			addSpriteSelectionHandler(new SpriteSelectionHandler() {
				@Override
				public void onSpriteSelect(SpriteSelectionEvent event) {
					FtlView.fire(new ChangeYearEvent(year));
				}
			});
		}

		void setEnable(boolean flag) {
			setBgColor(flag ? RGB.RED : RGB.WHITE);
			setTextColor(flag ? RGB.WHITE : RGB.RED);
		}
	}
}
