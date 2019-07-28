package us.dontcareabout.homePage.client.layer.ftl;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.gxt.client.draw.LTextSprite;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.homePage.client.data.FTL;
import us.dontcareabout.homePage.client.ui.FtlView;
import us.dontcareabout.homePage.client.ui.FtlView.ChangeRecordEvent;
import us.dontcareabout.homePage.client.ui.FtlView.ChangeRecordHandler;

public class InfoLayer extends LayerSprite {
	private static final DateTimeFormat format = DateTimeFormat.getFormat("yyyy/MM/dd");

	private LTextSprite text = new LTextSprite();

	public InfoLayer() {
		setBgColor(new RGB(231, 231, 231));
		text.setFontSize(20);
		text.setLX(16);
		text.setLY(4);
		add(text);

		FtlView.addChangeRecord(new ChangeRecordHandler() {
			@Override
			public void onChangeRecord(ChangeRecordEvent event) {
				refresh(event.record);
			}
		});
	}

	public void refresh(FTL record) {
		text.setText(
			record == null ? "" :
			record.getShip() + " : " + record.getAmount() + " 次 / " + record.getLength() + " 天 ("
					+ format.format(record.getStart().asDate()) + " ~ " + format.format(record.getEnd().asDate()) + ")"
		);
	}
}
