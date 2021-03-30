package us.dontcareabout.homePage.client.component.mykfz;

import java.util.ArrayList;
import java.util.List;

import com.sencha.gxt.chart.client.draw.HSL;

class ChartUtil {
	public static final String[] LEVEL = {"", "K", "M", "B", "T", "aa", "ab", "ac", "ad"};
	public static final int LV_MIN = 3;
	public static final int LV_MAX = LEVEL.length;
	public static final int LV_WEIGHT_UNIT = 5;
	public static final int LV_UNIT_R = 3;

	static double outputWeight(int level, double mantissa) {
		if (level < LV_MIN) { return 0; }

		return levelWeight(level) + mantissa / 1000.0 * levelUnit(level) * LV_WEIGHT_UNIT;
	}

	/**
	 * @return 指定 level 的起點 y 座標
	 * @see #levelUnit(int)
	 */
	static int levelWeight(int level) {
		if (level <= LV_MIN) { return 0; }

		return (int)((Math.pow(LV_UNIT_R, level - LV_MIN) - 1) / (LV_UNIT_R - 1) * LV_WEIGHT_UNIT);
	}

	/**
	 * levelUnit() 為每個 level（到下個 level）的高度。
	 * levelUnit(n) 為等比數列，a1 為 {@link #LV_MIN}
	 */
	static int levelUnit(int level) {
		if (level < LV_MIN) { return 0; }

		return (int)(Math.pow(LV_UNIT_R, level - LV_MIN));
	}

	static int mantissaWeight(int level, double mantissa) {
		return ((int) Math.ceil(mantissa / 1000.0 * levelUnit(level))) * LV_WEIGHT_UNIT;
	}

	static String inverseLevel(Number weight) {
		int value = weight.intValue();

		for (int i = LV_MIN; i < LV_MAX; i++) {
			if (value == levelWeight(i)) { return LEVEL[i - 1]; }
		}

		return "";
	}

	////////////////

	public static List<HSL> diffrentColor(int amount) {
		ArrayList<HSL> result = new ArrayList<>();

		for (int i = 0; i < amount; i++) {
			if (i < 18) {
				result.add(new HSL(i * 20, 1, 0.5));
			} else if (i < 36) {
				result.add(new HSL(i * 20 + 10, 1, 0.7));
			} else if (i < 46) {
				//飽和變低、亮度變暗會讓顏色差異感變小，所以 H 的差別就大一點
				result.add(new HSL(i * 36, 0.8, 0.4));
			} else {
				//已經多到這種程度就無所謂了，都亂數就算了...... [眼神死]
				result.add(new HSL(Math.random() * 360, Math.random() * 0.5 + 0.5, Math.random() * 0.6 + 0.2));
			}
		}

		return result;
	}
}
