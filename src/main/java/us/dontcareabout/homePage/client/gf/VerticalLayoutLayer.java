package us.dontcareabout.homePage.client.gf;

import java.util.ArrayList;

import com.google.common.base.Preconditions;

import us.dontcareabout.gwt.client.Console;
import us.dontcareabout.gxt.client.draw.LayerSprite;

public class VerticalLayoutLayer extends LayerSprite {
	private ArrayList<LayerSprite> children = new ArrayList<>();
	private ArrayList<Double> weightList = new ArrayList<>();

	public void addChild(LayerSprite child, double weight) {
		Preconditions.checkArgument(weight > 0);
		children.add(child);
		weightList.add(weight);
		add(child);
	}

	@Override
	protected final void adjustMember() {
		double fixed = 0;

		for (double w : weightList) {
			if (w > 1) { fixed += w; }
		}

		double remain = getHeight() - fixed;
		double y = 0;

		for (int i = 0; i < children.size(); i++) {
			LayerSprite child = children.get(i);
			double weight = weightList.get(i);
			double height = weight > 1 ? weight : remain * weight;
			Console.log(weight + " => " + height);
			child.setLX(0);
			child.setLY(y);
			child.resize(getWidth(), height);
			y += height;
		}
	}
}
