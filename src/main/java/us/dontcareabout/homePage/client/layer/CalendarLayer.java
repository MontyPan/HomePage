package us.dontcareabout.homePage.client.layer;

import java.util.ArrayList;
import java.util.HashMap;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gxt.client.draw.LRectangleSprite;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.homePage.client.data.FTL;

public class CalendarLayer extends LayerSprite {
	private HashMap<Integer, MonthLayer> layerMap = new HashMap<>();

	public CalendarLayer() {
		for (int i = 0; i < 12; i++) {
			MonthLayer ml = new MonthLayer(i);
			add(ml);
			layerMap.put(i, ml);
		}
	}

	public void refresh(ArrayList<FTL> data) {
		for (MonthLayer ml : layerMap.values()) {
			ml.clean();
		}

		for (FTL ftl : data) {
			layerMap.get(ftl.getStart().getMonth()).addRecord(ftl);
		}

		redeploy();
		adjustMember();
	}

	@Override
	protected void adjustMember() {
		double unit = (getHeight() - 13 * 3) / 12;

		for (MonthLayer ml : layerMap.values()) {
			ml.setLX(3);
			ml.setLY(ml.month * (unit + 3) + 3);
			ml.resize(getWidth() - 6, unit);
		}
	}

	private class MonthLayer extends LayerSprite {
		int month;
		boolean overlapUp = true;
		ArrayList<SectionLayer> sections = new ArrayList<>();

		public MonthLayer(int month) {
			this.month = month;
		}

		public void addRecord(FTL ftl) {
			SectionLayer sl = new SectionLayer(ftl);
			sections.add(sl);
			add(sl);

			//處理開始日期與上一個結束日期重疊的
			if (sections.size() == 1) { return; }

			SectionLayer last = sections.get(sections.size() - 2);

			if (last.record.getEnd().getDate() == ftl.getStart().getDate()) {
				last.up = overlapUp;
				overlapUp = !overlapUp;
				sl.up = overlapUp;
			}
			////
		}

		public void clean() {
			for (LRectangleSprite lrs : sections) {
				remove(lrs);
			}
		}

		@Override
		protected void adjustMember() {
			final int space = 2;
			double widthUnit = getWidth() / 31;

			for (SectionLayer sl : sections) {
				sl.setLX((sl.record.getStart().getDate() - 1) * widthUnit + space);
				sl.setWidth(sl.record.getLength() * widthUnit - space * 2);

				if (sl.up == null) {
					sl.setLY(space);
					sl.setHeight(getHeight() - space * 2);
				} else if (sl.up) {
					sl.setLY(space);
					sl.setHeight(getHeight() / 2 - space);
				} else {
					sl.setLY(getHeight() / 2 + space);
					sl.setHeight(getHeight() / 2 - space);
				}
			}
		}
	}

	private class SectionLayer extends LRectangleSprite {
		FTL record;

		/** null 表示全幅顯示，true 表示只佔上半格、false 表示只佔下半格 */
		Boolean up;

		SectionLayer(FTL ftl) {
			record = ftl;
			setFill(RGB.ORANGE);
			setRadius(4);
		}
	}
}
