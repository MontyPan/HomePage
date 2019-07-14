package us.dontcareabout.homePage.client.data;

import java.util.Date;

import us.dontcareabout.homePage.client.gf.GoogleSheetEntry;

public final class FTL extends GoogleSheetEntry {
	protected FTL() {}

	public String getShip() {
		return stringField("船艦");
	}

	public Date getStart() {
		return dateField("開始時間");
	}

	public Date getEnd() {
		return dateField("結束時間");
	}

	public int getAmount() {
		return intField("次數");
	}
}
