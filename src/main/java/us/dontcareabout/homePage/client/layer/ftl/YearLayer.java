package us.dontcareabout.homePage.client.layer.ftl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;

//TODO 年份過多出現左右捲動按鈕
public class YearLayer extends LayerSprite {
	private ArrayList<YearButton> list = new ArrayList<>();
	private YearButton now;

	public void refresh(Set<Integer> set) {
		for (YearButton tb : list) {
			remove(tb);
		}

		ArrayList<Integer> yearList = new ArrayList<>(set);
		Collections.sort(yearList);

		for (int year : set) {
			YearButton tb = new YearButton(year);
			add(tb);
			list.add(tb);
			redeploy();
		}

		now = list.get(list.size() - 1);
		now.setEnable(true);

		adjustMember();
	}

	@Override
	protected void adjustMember() {
		final int space = 3;
		final int width = 70;

		int count = 1;

		for (YearButton tb : list) {
			tb.setLX(count * (width + space) - width);
			tb.setLY(space);
			tb.resize(width, getHeight() - space * 2);
			count++;
		}
	}

	private class YearButton extends TextButton {
		YearButton(int year) {
			super("" + year);
			setBgRadius(5);
			setEnable(false);
		}

		void setEnable(boolean flag) {
			setBgColor(flag ? RGB.RED : RGB.WHITE);
			setTextColor(flag ? RGB.WHITE : RGB.RED);
		}
	}
}
