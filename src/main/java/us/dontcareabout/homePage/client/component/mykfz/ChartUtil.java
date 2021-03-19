package us.dontcareabout.homePage.client.component.mykfz;

class ChartUtil {
	public static final String[] LEVEL = {"K", "M", "B", "T", "aa", "ab", "ac", "ad"};

	private static final int LV_MIN = 4;
	private static final int LV_MAX = LEVEL.length;
	private static final int LV_WEIGHT_UNIT = 5;

	static double outputWeight(int level, double mantissa) {
		if (level < LV_MIN) { return 0; }

		return levelWeight(level) + mantissa / 100.0 * (level - LV_MIN + 1);
	}

	static int levelWeight(int level) {
		if (level < LV_MIN) { return 0; }

		int result = LV_WEIGHT_UNIT;

		for (int i = 1; i < level - LV_MIN + 1; i++) {
			result += i * LV_WEIGHT_UNIT * 2;
		}

		return result;
	}

	static String inverseLevel(Number weight) {
		int value = weight.intValue();

		for (int i = LV_MIN; i < LV_MAX; i++) {
			if (value == levelWeight(i)) { return LEVEL[i - 1]; }
		}

		return "";
	}
}
