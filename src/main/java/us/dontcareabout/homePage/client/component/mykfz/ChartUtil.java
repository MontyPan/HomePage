package us.dontcareabout.homePage.client.component.mykfz;

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
}
