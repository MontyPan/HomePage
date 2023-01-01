package us.dontcareabout.homePage.client.ui;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextArea;

import us.dontcareabout.homePage.client.layer.ipLog.IpTimeline;
import us.dontcareabout.homePage.client.vo.ipLog.IpData;
import us.dontcareabout.homePage.client.vo.ipLog.RawData;

public class IpLogView extends VerticalLayoutContainer {
	private static final VerticalLayoutData V1x1 = new VerticalLayoutData(1, 1);

	private TextArea log = new TextArea();
	private TextButton submit = new TextButton("Go!");

	private List<RawData> rawList;
	private HashMap<String, IpData> ipMap = new HashMap<>();

	public IpLogView() {
		HorizontalLayoutContainer inputHL = new HorizontalLayoutContainer();
		inputHL.add(log, new HorizontalLayoutData(1, 1));
		inputHL.add(submit, new HorizontalLayoutData(100, 1));

		add(inputHL, new VerticalLayoutData(1, 200));
		add(new FlowLayoutContainer(), V1x1);

		submit.addSelectHandler(e->analysis());
	}

	private void analysis() {
		rawList = Stream.of(log.getText().split("\n"))
			.map(line -> {
				try {
					return new RawData(line);
				} catch (Exception e) {
					return null;
				}
			})
			.filter(r -> r != null)
			.collect(Collectors.toList());

		ipMap.clear();
		rawList.forEach(raw -> {
			IpData ip = ipMap.get(raw.ip);

			if (ip == null) {
				ip = new IpData(raw.ip);
				ipMap.put(raw.ip, ip);
			}

			ip.add(raw);
		});

		remove(1);
		add(new IpTimeline(ipMap), V1x1);
		forceLayout();
	}
}

/*
L1~L3：ip 長度變化
L3/L5：同人同 ip，8hr 內
L3/L4：不同人同 ip，8hr 內
L5~L6：同人同 ip，8hr 外

///////////////////////////////////////

2022/01/01      18:50:34        80.100.20.50    假假
2022/01/01      23:37:09        33.222.11.111   假假
2022/01/01      23:01:00        180.200.110.140 假假
2022/01/02      05:00:00        180.200.110.140 真真
2022/01/02      06:01:00        180.200.110.140 假假
2022/01/02      14:01:01        180.200.110.140 假假
*/