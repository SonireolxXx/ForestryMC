package forestry.core.gui.elements.layouts;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import forestry.api.gui.IGuiElement;
import forestry.api.gui.IGuiElementContainer;
import forestry.api.gui.IGuiElementLayout;
import forestry.api.gui.IGuiElementLayoutHelper;

public class ElementLayoutHelper implements IGuiElementLayoutHelper {
	private final List<IGuiElementLayout> layouts = new ArrayList<>();
	private final LayoutFactory layoutFactory;
	private final int width;
	private final int height;
	private final IGuiElementContainer parent;
	private int xOffset;
	private int yOffset;
	@Nullable
	private IGuiElementLayout currentLayout;
	private boolean horizontal;

	public ElementLayoutHelper(LayoutFactory layoutFactory, int width, int height, IGuiElementContainer parent) {
		this.layoutFactory = layoutFactory;
		this.width = width;
		this.height = height;
		this.parent = parent;
	}

	/**
	 * @return Only false if the helper has no space to add this element.
	 */
	@Override
	public boolean add(IGuiElement element) {
		if (currentLayout == null) {
			layouts.add(currentLayout = layoutFactory.createLayout(0, 0));
			this.horizontal = currentLayout instanceof VerticalElementLayout;
		}
		int groupWidth = currentLayout.getWidth();
		int groupHeight = currentLayout.getHeight();
		int eleWidth = element.getX() + element.getWidth();
		int eleHeight = element.getY() + element.getHeight();
		if (horizontal) {
			if (yOffset >= height) {
				if (width != 0 && xOffset > width) {
					return false;
				}
				xOffset += currentLayout.getWidth();
				layouts.add(currentLayout = layoutFactory.createLayout(0, 0));
				groupHeight = currentLayout.getHeight();
			}
			groupHeight += eleHeight;
			yOffset = groupHeight;
		} else {
			if (xOffset >= width) {
				if (height != 0 && yOffset > height) {
					return false;
				}
				yOffset += currentLayout.getHeight();
				layouts.add(currentLayout = layoutFactory.createLayout(0, 0));
				groupWidth = currentLayout.getWidth();
			}
			groupWidth += eleWidth;
			xOffset = groupWidth;
		}
		currentLayout.add(element);
		return true;
	}

	@Override
	public void finish(boolean center) {
		for (IGuiElement element : layouts) {
			parent.add(center ? parent.centerElementX(element) : element);
		}
		clear();
	}

	@Override
	public void clear() {
		layouts.clear();
		currentLayout = null;
		xOffset = 0;
		yOffset = 0;
	}

	@Override
	public Collection<IGuiElementLayout> layouts() {
		return layouts;
	}
}
