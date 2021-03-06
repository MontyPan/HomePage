package us.dontcareabout.homePage.client.vo.makeWine;

import java.util.HashMap;

import us.dontcareabout.homePage.client.vo.makeWine.Ingredient.Type;

public class Composition {
	public static final int WINE_MAX = 6;

	private HashMap<Ingredient, Integer> map = new HashMap<>();

	public Composition() {
		reset();
	}

	public void reset() {
		for (Ingredient igdnt : Ingredient.values()) {
			map.put(igdnt, 0);
		}
	}

	public void change(Ingredient ingredient, int amount) {
		map.put(ingredient, amount);
	}

	/**
	 * @return 還有幾份酒沒有指定品項
	 */
	public int getWineRemainder() {
		int result = WINE_MAX;

		for (Ingredient igdnt : map.keySet()) {
			if (igdnt.type != Type.wine) { continue; }

			result -= map.get(igdnt);
		}

		return result;
	}

	/**
	 * @return 酒類組成，只有數量大於 0 的才會列入
	 */
	public HashMap<Ingredient, Integer> getWineComposition() {
		HashMap<Ingredient, Integer> result = new HashMap<>();

		for (Ingredient igdnt : map.keySet()) {
			if (igdnt.type != Type.wine) { continue; }
			if (map.get(igdnt) == 0) { continue; }

			result.put(igdnt, map.get(igdnt));
		}

		return result;
	}

	public int getBlackBean() {
		return map.get(Ingredient.blackBean);
	}

	public int getVat() {
		return map.get(Ingredient.vat5L);
	}

	public int getLongan() {
		return map.get(Ingredient.longan);
	}

	/**
	 * @return 總金額
	 */
	public int getTotal() {
		int result = 0;

		for (Ingredient igdnt : map.keySet()) {
			if (igdnt.type == Type.base) { continue; }

			result += igdnt.price * map.get(igdnt);
		}

		return (result + Ingredient.blackBean.price) * map.get(Ingredient.blackBean);
	}
}
