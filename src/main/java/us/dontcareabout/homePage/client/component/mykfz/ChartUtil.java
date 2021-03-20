package us.dontcareabout.homePage.client.component.mykfz;

class ChartUtil {
	public static final String[] LEVEL = {"", "K", "M", "B", "T", "aa", "ab", "ac", "ad"};
	public static final int LV_MIN = 4;
	public static final int LV_MAX = LEVEL.length;
	public static final int LV_WEIGHT_UNIT = 5;

	static double outputWeight(int level, double mantissa) {
		if (level < LV_MIN) { return 0; }

		return levelWeight(level) + mantissa / 100.0 * (level - LV_MIN + 1);
	}

	static int levelWeight(int level) {
		if (level < LV_MIN) { return 0; }

		int result = LV_WEIGHT_UNIT;

		for (int i = LV_MIN; i <= level; i++) {
			result += levelUnit(i) * LV_WEIGHT_UNIT;
		}

		return result;
	}

	static int mantissaWeight(int level, double mantissa) {
		return ((int) Math.ceil(mantissa / 1000.0 * levelUnit(level))) * LV_WEIGHT_UNIT;
	}

	static int levelUnit(int level) {
		return (level - LV_MIN ) * 3;
	}

	static String inverseLevel(Number weight) {
		int value = weight.intValue();

		for (int i = LV_MIN; i < LV_MAX; i++) {
			if (value == levelWeight(i)) { return LEVEL[i - 1]; }
		}

		return "";
	}
}
