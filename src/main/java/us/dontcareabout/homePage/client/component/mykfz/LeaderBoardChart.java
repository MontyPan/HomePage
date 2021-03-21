package us.dontcareabout.homePage.client.component.mykfz;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.axis.TimeAxis;
import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.chart.series.Series;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;

import us.dontcareabout.gxt.client.model.GetValueProvider;
import us.dontcareabout.homePage.client.component.mykfz.LeaderBoardChart.DateData;
import us.dontcareabout.homePage.client.data.Mykfz;

public class LeaderBoardChart extends Chart<DateData> {
	private ListStore<DateData> store = new ListStore<>(new ModelKeyProvider<DateData>() {
		@Override
		public String getKey(DateData item) {
			return item.getDate().toString();
		}
	});
	private NumericAxis<DateData> outputAxis;
	private TimeAxis<DateData> timeAxis;

	public LeaderBoardChart() {
		outputAxis = genYAxis();
		timeAxis = genXAxis();

		setStore(store);
		addAxis(outputAxis);
		addAxis(timeAxis);
		setDefaultInsets(30);
	}

	public void refresh(List<Mykfz> data) {
		//TODO 沒處理重複呼叫的狀況
		HashSet<String> nameSet = new HashSet<>();
		Date startDate = new Date();
		int maxLevel = 0;
		double maxMantissa = 0;

		for (Mykfz mykfz : data) {
			DateData item = store.findModelWithKey(mykfz.getDate().toString());

			if (item == null) {
				item = new DateData(mykfz.getDate());
				store.add(item);

				if (mykfz.getDate().before(startDate)) { startDate = mykfz.getDate(); }
			}

			item.put(mykfz.getPlayer(), ChartUtil.outputWeight(mykfz.getLevel(), mykfz.getMantissa()));
			nameSet.add(mykfz.getPlayer());

			if (maxLevel < mykfz.getLevel()) {
				maxLevel = mykfz.getLevel();
				maxMantissa = mykfz.getMantissa();
			} else if (maxLevel == mykfz.getLevel() && maxMantissa < mykfz.getMantissa()) {
				maxMantissa = mykfz.getMantissa();
			}
		}

		for (String name : nameSet) {
			createLine(name);
		}

		timeAxis.setStartDate(startDate);
		timeAxis.setEndDate(new Date());

		int lvWeightMax = ChartUtil.levelWeight(maxLevel) + ChartUtil.mantissaWeight(maxLevel, maxMantissa);
		outputAxis.setSteps(lvWeightMax / ChartUtil.LV_WEIGHT_UNIT);
		outputAxis.setMaximum(lvWeightMax);

		redrawChart();
	}

	public void selectPlayer(List<Mykfz> data) {
		for (Series<DateData> s : getSeries()) {
			boolean hit = false;
			for (Mykfz m : data) {
				if (s.getLegendTitles().get(0).equals(m.getPlayer())) {
					s.show(0);
					hit = true;
					break;
				}
			}
			if (!hit) { s.hide(0); }
		}
	}

	private NumericAxis<DateData> genYAxis() {
		TextSprite title = new TextSprite("傷害輸出");
		title.setFontSize(18);

		NumericAxis<DateData> result = new NumericAxis<>();
		result.setPosition(Position.LEFT);
		result.setTitleConfig(title);
		result.setDisplayGrid(true);
		result.setMinimum(0);
		result.setLabelProvider(new LabelProvider<Number>() {
			@Override
			public String getLabel(Number item) {
				return ChartUtil.inverseLevel(item);
			}
		});
		return result;
	}

	private TimeAxis<DateData> genXAxis() {
		TimeAxis<DateData> result = new TimeAxis<>();
		result.setField(new GetValueProvider<DateData, Date>() {
			@Override
			public Date getValue(DateData object) {
				return object.getDate();
			}
		});
		result.setLabelProvider(new LabelProvider<Date>() {
			DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd");

			@Override
			public String getLabel(Date item) {
				return dateFormat.format(item);
			}
		});
		return result;
	}

	private void createLine(String player) {
		ValueProviderByName vp = new ValueProviderByName(player);

		//TODO 強化顏色選擇
		RGB color = new RGB((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));

		Sprite marker = Primitives.circle(0, 0, 3);
		marker.setFill(color);

		LineSeries<DateData> series = new LineSeries<>();
		series.setYAxisPosition(Position.LEFT);
		series.setStroke(color);
		series.setStrokeWidth(2);
		series.setShowMarkers(true);
		series.setMarkerConfig(marker);
		series.setYField(vp);

		outputAxis.addField(vp);
		addSeries(series);
	}

	class DateData extends HashMap<String, Double> {
		private static final long serialVersionUID = 1L;
		private Date date;
		public DateData(Date date) { this.date = date; }
		public Date getDate() { return date; }
	}

	private class ValueProviderByName implements ValueProvider<DateData, Double> {
		private String name;

		public ValueProviderByName(String name) { this.name = name; }

		@Override
		public String getPath() { return name; }

		@Override
		public Double getValue(DateData object) { return object.get(name); }

		@Override
		public void setValue(DateData object, Double value) { object.put(name, value); }
	}
}
