package us.dontcareabout.homePage.client.data;

import java.util.Date;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.util.DateWrapper;

import us.dontcareabout.gwt.client.google.sheet.Row;

public final class FTL extends Row {
	protected FTL() {}

	public String getShip() {
		return stringField("船艦");
	}

	public DateWrapper getStart() {
		return new DateWrapper(dateField("開始時間"));
	}

	public DateWrapper getEnd() {
		Date date = dateField("結束時間");
		return date == null ? new DateWrapper() : new DateWrapper(date);
	}

	public int getLength() {
		return CalendarUtil.getDaysBetween(getStart().asDate(), getEnd().asDate()) + 1;
	}

	public int getAmount() {
		return intField("次數") + 1;
	}
}
