package us.dontcareabout.homePage.client.vo.ipLog;

import java.util.Date;

import com.sencha.gxt.core.client.util.DateWrapper;

public class NamePeriod {
	public final String name;
	public final Date start;
	private DateWrapper end;
	private int count;

	public NamePeriod(String name, Date start) {
		this.name = name;
		this.start = start;
		expand(start);
	}

	public DateWrapper getEnd() {
		return end;
	}

	/** @return 時間區間內有幾筆記錄 */
	public int getCount() {
		return count;
	}

	public void expand(Date end) {
		this.end = new DateWrapper(end);
		count++;
	}
}
