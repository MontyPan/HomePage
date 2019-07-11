package us.dontcareabout.homePage.client.gf;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;

//Refactory GF
public class GoogleSheetEntry extends JavaScriptObject {
	protected GoogleSheetEntry() {}

	//用泛型大法會炸 casting 錯誤，只好提供各種對應 method... ＝＝"
	protected final native String stringField(String name) /*-{
		return this["gsx$" + name].$t;
	}-*/;

	protected final native int intField(String name) /*-{
		return this["gsx$" + name].$t;
	}-*/;

	protected final native Long longField(String name) /*-{
		return this["gsx$" + name].$t;
	}-*/;

	protected final native double doubleField(String name) /*-{
		return this["gsx$" + name].$t;
	}-*/;

	@SuppressWarnings("deprecation")
	protected final Date dateField(String name) {
		return new Date(stringField(name));
	}
}
