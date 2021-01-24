package us.dontcareabout.homePage.client.vo.makeWine;

public enum Ingredient {
	blackBean("黑豆 1 斤", 45, Type.base),

	vat5L("酒罈 (5L)", 230, Type.vat),

	longan("龍眼 4 兩", 95, Type.other),

	rice("米酒", 25),
	ricePure("純米酒", 90),
	riceHead("米酒頭", 140),
	sorghum38("金門高粱 38 度", 355),
	sorghum58("金門高粱 58 度", 380),
	;

	public final String name;
	public final int price;
	public final Type type;

	private Ingredient(String name, int price) {
		this(name, price, Type.wine);
	}

	private Ingredient(String name, int price, Type type) {
		this.name = name;
		this.price = price;
		this.type = type;
	}

	public enum Type {
		base, wine, vat, other
	}
}
