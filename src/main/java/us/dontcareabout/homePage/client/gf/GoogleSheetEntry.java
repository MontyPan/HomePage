package us.dontcareabout.homePage.client.gf;

import java.util.Date;

import com.google.common.base.Strings;
import com.google.gwt.core.client.JavaScriptObject;

//Refactory GF
public class GoogleSheetEntry extends JavaScriptObject {
	protected GoogleSheetEntry() {}

	protected final native String stringField(String name) /*-{
		return this["gsx$" + name].$t;
	}-*/;

	protected final Integer intField(String name) {
		String value = stringField(name);
		return Strings.isNullOrEmpty(value) ? 0 : Integer.valueOf(value);
	}

	protected final Long longField(String name) {
		String value = stringField(name);
		return Strings.isNullOrEmpty(value) ? 0 : Long.valueOf(value);
	}

	protected final Double doubleField(String name) {
		String value = stringField(name);
		return Strings.isNullOrEmpty(value) ? 0 : Double.valueOf(value);
	}

	@SuppressWarnings("deprecation")
	protected final Date dateField(String name) {
		String value = stringField(name);
		return Strings.isNullOrEmpty(value) ? null : new Date(value);
	}
}
