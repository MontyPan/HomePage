package us.dontcareabout.homePage.client.layer.ftl;

import java.util.ArrayList;
import java.util.HashMap;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteOutEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteOutEvent.SpriteOutHandler;
import com.sencha.gxt.chart.client.draw.sprite.SpriteOverEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteOverEvent.SpriteOverHandler;
import com.sencha.gxt.core.client.util.DateWrapper;

import us.dontcareabout.gxt.client.draw.LRectangleSprite;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.homePage.client.data.FTL;
import us.dontcareabout.homePage.client.ui.FtlView;
import us.dontcareabout.homePage.client.ui.FtlView.ChangeRecordEvent;

class CalendarLayer extends LayerSprite {
	private HashMap<Integer, MonthLayer> layerMap = new HashMap<>();
	private int year;

	public CalendarLayer() {
		for (int i = 0; i < 12; i++) {
			MonthLayer ml = new MonthLayer(i);
			add(ml);
			layerMap.put(i, ml);
		}
	}

	/**
	 * 這裡只考慮 {@link FTL} 起訖年份相差一年以內的狀況。
	 */
	public void refresh(int year, ArrayList<FTL> data) {
		this.year = year;

		for (MonthLayer ml : layerMap.values()) {
			ml.clean();
		}

		for (FTL ftl : data) {
			DateWrapper start = ftl.getStart();
			DateWrapper end = ftl.getEnd();
			//起始日期年份小於指定年份，就等同於從年初開始（1 月）
			int startMonth = start.getFullYear() < year ? 0 : start.getMonth();
			//終止日期年份大於指定年份，就等同於到年底（12 月）
			int endMonth = end.getFullYear() > year ? 11 : end.getMonth();

			for (int i = startMonth; i <= endMonth; i++) {
				layerMap.get(i).addRecord(ftl);
			}
		}

		redeploy();
		adjustMember();
	}

	@Override
	protected void adjustMember() {
		final int space = 4;
		double unit = (getHeight() - 13 * space) / 12;

		for (MonthLayer ml : layerMap.values()) {
			ml.setLX(space / 2);
			ml.setLY(ml.month * (unit + space) + space);
			ml.resize(getWidth() - space, unit);
		}
	}

	private class MonthLayer extends LayerSprite {
		int month;
		boolean overlapUp = true;
		ArrayList<SectionLayer> sections = new ArrayList<>();

		public MonthLayer(int month) {
			this.month = month;

			addSpriteOverHandler(new SpriteOverHandler() {
				@Override
				public void onSpriteOver(SpriteOverEvent event) {
					for (SectionLayer section : sections) {
						if (event.getSprite() == section) {
							FtlView.fire(new ChangeRecordEvent(section.record));
						}
					}
				}
			});

			addSpriteOutHandler(new SpriteOutHandler() {
				@Override
				public void onSpriteLeave(SpriteOutEvent event) {
					FtlView.fire(new ChangeRecordEvent(null));
				}
			});
		}

		public void addRecord(FTL ftl) {
			SectionLayer sl = new SectionLayer(ftl);
			sections.add(sl);
			add(sl);

			//校正起始 or 結束月份非本月的對應日期
			if (sl.record.getStart().getFullYear() < year || sl.record.getStart().getMonth() < month) {
				sl.startDay = 1;
			}

			if (sl.record.getEnd().getFullYear() > year || sl.record.getEnd().getMonth() > month) {
				sl.endDay = new DateWrapper(year, month, 1).getLastDateOfMonth().getDate();
			}
			////

			//處理開始日期與上一個結束日期重疊的
			if (sections.size() == 1) { return; }

			SectionLayer last = sections.get(sections.size() - 2);

			if (last.endDay == sl.startDay) {
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
				sl.setLX((sl.startDay - 1) * widthUnit + space);
				sl.setWidth((sl.endDay - sl.startDay + 1) * widthUnit - space * 2);

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
		/** 考慮年份與月份的修正起始日 */
		int startDay;
		/** 考慮年份與月份的修正結束日 */
		int endDay;

		/** null 表示全幅顯示，true 表示只佔上半格、false 表示只佔下半格 */
		Boolean up;

		SectionLayer(FTL ftl) {
			record = ftl;
			startDay = ftl.getStart().getDate();
			endDay = ftl.getEnd().getDate();

			double avg = ftl.getAmount() * 1.0 / ftl.getLength();

			//XXX 整體來說還是套個漸層公式比較好，不過那等到有講究配色的時候再說 XD
			if (avg > 1) {
				setOpacity(avg * 0.15 + 0.25);
				//平均一天死五次也太悲情，所以假設不會超過
				//因為不希望顏色太淡、所以把 (1, 5] 對應值域 (0.4, 1]
				setFill(RGB.RED);
			} else if (avg < 0.2){
				//太淡會看不到，所以把 (0, 0.2) 對應值域 (0.1, 0.7)
				setOpacity(avg * 3 + 0.1);
				setFill(RGB.YELLOW);
			} else {
				setOpacity(avg);
				setFill(RGB.ORANGE);
			}

			setRadius(4);
		}
	}
}
