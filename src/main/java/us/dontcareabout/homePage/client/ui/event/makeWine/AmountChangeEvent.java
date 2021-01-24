package us.dontcareabout.homePage.client.ui.event.makeWine;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.homePage.client.ui.event.makeWine.AmountChangeEvent.AmountChangeHandler;
import us.dontcareabout.homePage.client.vo.makeWine.Ingredient;

public class AmountChangeEvent extends GwtEvent<AmountChangeHandler> {
	public static final Type<AmountChangeHandler> TYPE = new Type<AmountChangeHandler>();

	public final Ingredient ingredient;
	public final int amount;

	public AmountChangeEvent(Ingredient ingredient, int amount) {
		this.ingredient = ingredient;
		this.amount = amount;
	}

	@Override
	public Type<AmountChangeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AmountChangeHandler handler) {
		handler.onAmountChange(this);
	}

	public interface AmountChangeHandler extends EventHandler{
		public void onAmountChange(AmountChangeEvent event);
	}
}
