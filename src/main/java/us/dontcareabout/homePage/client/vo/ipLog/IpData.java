package us.dontcareabout.homePage.client.vo.ipLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sencha.gxt.core.client.util.DateWrapper;

public class IpData {
	/**
	 * 一筆 {@link RawData} 的有效期限，單位小時。
	 * <p>
	 * 主要用來判斷多筆 {@link RawData} 是否能整合成為一筆 {@link NamePeriod}
	 *
	 * @see #add(RawData)
	 */
	public static int VALIDITY_HOUR = 8;

	public final String ip;
	private List<NamePeriod> periodList = new ArrayList<>();
	private Set<String> nameSet = new HashSet<>();

	public IpData(String ip) {
		this.ip = ip;
	}

	public void add(RawData raw) {
		if (!ip.equals(raw.ip)) { return; }	//純粹預防萬一 XD

		for (NamePeriod np : periodList) {
			if (!np.name.equals(raw.name)) { continue; }

			//兩筆資料的時間在有效期限內，所以合併 / 延展
			if (new DateWrapper(raw.date).before(np.getEnd().addHours(VALIDITY_HOUR))) {
				np.expand(raw.date);
				return;
			}
		}

		//找不到同樣 name、或是有同樣 name 但是時間間隔過長
		//就要增加一筆新的 NamePeriod
		NamePeriod np = new NamePeriod(raw.name, raw.date);
		periodList.add(np);
		nameSet.add(raw.name);
	}

	public List<NamePeriod> getPeriodList() {
		return periodList;
	}

	public List<NamePeriod> getPeriodList(String name) {
		return periodList.stream().filter(p -> name.equals(p.name))
			.collect(Collectors.toList());
	}

	public Set<String> getNameSet() {
		return nameSet;
	}

	/**
	 * @return 最早一筆 {@link RawData#date}
	 */
	public Date getStart() {
		Date result = new Date();

		for (NamePeriod p : periodList) {
			if (p.start.after(result)) { continue; }

			result = p.start;
		}

		return result;
	}

	/**
	 * @return 最後一筆 {@link RawData#date}
	 */
	public Date getEnd() {
		Date result = new Date(0);

		for (NamePeriod p : periodList) {
			if (p.getEnd().asDate().before(result)) { continue; }

			result = p.getEnd().asDate();
		}

		return result;
	}
}
