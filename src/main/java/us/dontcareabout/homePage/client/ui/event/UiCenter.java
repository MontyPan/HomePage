package us.dontcareabout.homePage.client.ui.event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.homePage.client.ui.event.makeWine.AmountChangeEvent;
import us.dontcareabout.homePage.client.ui.event.makeWine.AmountChangeEvent.AmountChangeHandler;
import us.dontcareabout.homePage.client.vo.makeWine.Ingredient;

public class UiCenter {
	private final static SimpleEventBus eventBus = new SimpleEventBus();

	public static HandlerRegistration addAmountChange(AmountChangeHandler handler) {
		return eventBus.addHandler(AmountChangeEvent.TYPE, handler);
	}

	public static void amountChange(Ingredient ingredient, int amount) {
		eventBus.fireEvent(new AmountChangeEvent(ingredient, amount));
	}
}
