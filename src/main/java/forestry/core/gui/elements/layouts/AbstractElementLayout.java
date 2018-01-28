package forestry.core.gui.elements.layouts;

import forestry.api.gui.IGuiElementLayout;

public abstract class AbstractElementLayout extends ElementContainer implements IGuiElementLayout {
	/* The distance between the different elements of this group. */
	public int distance;

	public AbstractElementLayout(int xPos, int yPos, int width, int height) {
		super(xPos, yPos, width, height);
	}

	public AbstractElementLayout setDistance(int distance) {
		this.distance = distance;
		return this;
	}

	@Override
	public int getDistance() {
		return distance;
	}

	@Override
	public int getSize() {
		return elements.size();
	}
}
