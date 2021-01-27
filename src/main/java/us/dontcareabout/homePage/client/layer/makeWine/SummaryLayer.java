package us.dontcareabout.homePage.client.layer.makeWine;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent.SpriteSelectionHandler;
import com.sencha.gxt.core.client.util.Margins;

import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.HorizontalLayoutLayer;

public class SummaryLayer extends HorizontalLayoutLayer {
	private static final RGB COLOR = new RGB("#9c27b0");

	private TextButton output = new TextButton("下載文字清單");
	private TextButton summary = new TextButton("總金額：");
	private TextButton totalTB = new TextButton();

	public SummaryLayer() {
		setMargins(new Margins(5, 5, 5, 55));

		output.setBgRadius(10);
		output.setBgColor(COLOR);
		output.setTextColor(RGB.WHITE);
		output.setHidden(true);

		summary.setTextColor(COLOR);

		addChild(output, 0.22);
		addChild(new LayerSprite(), 0.33);
		addChild(summary, 0.2);
		addChild(totalTB, 0.25);
	}

	public void setTotal(int total) {
		totalTB.setText("" + total);
	}

	public void setEnable(boolean value) {
		output.setHidden(!value);
		totalTB.setHidden(!value);
	}

	public void addOutputHandler(SpriteSelectionHandler h) {
		output.addSpriteSelectionHandler(h);
	}
}
