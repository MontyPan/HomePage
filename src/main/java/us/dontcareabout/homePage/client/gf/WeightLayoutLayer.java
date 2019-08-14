package us.dontcareabout.homePage.client.gf;

import java.util.ArrayList;

import com.google.common.base.Preconditions;
import com.sencha.gxt.core.client.util.Margins;

import us.dontcareabout.gxt.client.draw.LayerSprite;

class WeightLayoutLayer extends LayerSprite {
	ArrayList<LayerSprite> children = new ArrayList<>();
	ArrayList<Double> weightList = new ArrayList<>();
	Margins margins = new Margins();

	public void addChild(LayerSprite child, double weight) {
		Preconditions.checkArgument(weight > 0);
		children.add(child);
		weightList.add(weight);
		add(child);
	}

	public void setMargin(int value) {
		margins = new Margins(value);
	}

	public void setMargin(Margins margins) {
		Preconditions.checkArgument(margins != null);
		this.margins = margins;
	}
}
