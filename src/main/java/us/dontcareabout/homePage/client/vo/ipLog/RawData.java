package us.dontcareabout.homePage.client.vo.ipLog;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class RawData {
	public static DateTimeFormat format = DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss");

	public final Date date;
	public final String ip;
	public final String name;

	public RawData(String line) {
		date = format.parse(line.substring(0, 10) + " " + line.substring(16, 24));
		ip = line.substring(32, 46).trim();
		name = line.substring(48).trim();
	}
}
