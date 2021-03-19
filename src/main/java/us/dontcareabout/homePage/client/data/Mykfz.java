package us.dontcareabout.homePage.client.data;

import java.util.Date;

import us.dontcareabout.gwt.client.google.SheetEntry;

public final class Mykfz extends SheetEntry {
	protected Mykfz() {}

	public Date getDate() {
		return dateField("date");
	}

	public String getPlayer() {
		return stringField("player");
	}

	public int getLevel() {
		return intField("level");
	}

	public double getMantissa() {
		return doubleField("mantissa");
	}
}
