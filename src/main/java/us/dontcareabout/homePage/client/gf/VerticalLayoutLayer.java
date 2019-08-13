package us.dontcareabout.homePage.client.gf;

import java.util.ArrayList;

import com.google.common.base.Preconditions;
import com.sencha.gxt.core.client.util.Margins;

import us.dontcareabout.gxt.client.draw.LayerSprite;

public class VerticalLayoutLayer extends LayerSprite {
	private ArrayList<LayerSprite> children = new ArrayList<>();
	private ArrayList<Double> weightList = new ArrayList<>();
	private Margins margins = new Margins();

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

	@Override
	protected final void adjustMember() {
		double fixed = 0;

		for (double w : weightList) {
			if (w > 1) { fixed += w; }
		}

		double remain = getHeight() - fixed - margins.getTop() - margins.getBottom();
		double y = margins.getTop();
		double x = margins.getLeft();
		double width = getWidth() - margins.getLeft() - margins.getRight();

		for (int i = 0; i < children.size(); i++) {
			LayerSprite child = children.get(i);
			double weight = weightList.get(i);
			double height = weight > 1 ? weight : remain * weight;
			child.setLX(x);
			child.setLY(y);
			child.resize(width, height);
			y += height;
		}
	}
}
