package us.dontcareabout.homePage.client.component.mykfz;

class ChartUtil {
	public static final String[] LEVEL = {"", "K", "M", "B", "T", "aa", "ab", "ac", "ad"};
	public static final int LV_MIN = 4;
	public static final int LV_MAX = LEVEL.length;
	public static final int LV_WEIGHT_UNIT = 5;
	public static final int LV_UNIT_D = 3;

	static double outputWeight(int level, double mantissa) {
		if (level < LV_MIN) { return 0; }

		return levelWeight(level) + mantissa / 1000.0 * levelUnit(level) * LV_WEIGHT_UNIT;
	}

	/**
	 * @return 指定 level 的起點 y 座標
	 * @see #levelUnit(int)
	 */
	static int levelWeight(int level) {
		if (level < LV_MIN) { return 0; }

		int n = level - LV_MIN + 1;
		//a0 = 0 的等差級數求和公式簡化，外加一個 0～LV_MIN 的基本高度
		return (1 + (n * (n - 1)) * LV_UNIT_D / 2) * LV_WEIGHT_UNIT;
	}

	/**
	 * levelUnit() 為每個 level（到下個 level）的高度。
	 * levelUnit(n) 為等差數列，方便起見設 a0 為 level 3
	 */
	static int levelUnit(int level) {
		return (level - LV_MIN + 1) * LV_UNIT_D;
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
