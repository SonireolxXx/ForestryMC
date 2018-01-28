package forestry.core.gui.elements.layouts;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import forestry.api.gui.GuiElementAlignment;
import forestry.api.gui.IGuiElement;
import forestry.api.gui.IGuiElementContainer;
import forestry.api.gui.IGuiElementLayoutHelper;
import forestry.core.gui.Drawable;
import forestry.core.gui.elements.DrawableElement;
import forestry.core.gui.elements.GuiElement;
import forestry.core.gui.elements.ItemElement;
import forestry.core.gui.elements.TextElement;

public abstract class ElementContainer extends GuiElement implements IGuiElementContainer {
	protected final List<IGuiElement> elements = new ArrayList<>();

	protected ElementContainer(int xPos, int yPos, int width, int height) {
		super(xPos, yPos, width, height);
	}

	public <E extends IGuiElement> E add(E element) {
		elements.add(element);
		element.setParent(this);
		return element;
	}

	public <E extends IGuiElement> E remove(E element) {
		elements.remove(element);
		return element;
	}

	public void clear() {
		elements.clear();
	}

	@Override
	public List<IGuiElement> getElements() {
		return elements;
	}

	@Override
	public void draw(int startX, int startY, int mouseX, int mouseY) {
		startX += getX();
		startY += getY();
		mouseX -= getX();
		mouseY -= getY();
		for (IGuiElement element : elements) {
			element.draw(startX, startY, mouseX, mouseY);
		}
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
		mouseX -= getX();
		mouseY -= getY();
		for (IGuiElement element : elements) {
			if (element.isMouseOver(mouseX, mouseY) && element.mouseClicked(mouseX, mouseY, mouseEvent)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean mouseReleased(int mouseX, int mouseY, int mouseEvent) {
		mouseX -= getX();
		mouseY -= getY();
		for (IGuiElement element : elements) {
			if (element.mouseReleased(mouseX, mouseY, mouseEvent)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isMouseOver(int mouseX, int mouseY) {
		mouseX -= getX();
		mouseY -= getY();
		for (IGuiElement element : elements) {
			if (element.isMouseOver(mouseX, mouseY)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean keyTyped(char typedChar, int keyCode) {
		for (IGuiElement element : elements) {
			if (element.keyTyped(typedChar, keyCode)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> getTooltip(int mouseX, int mouseY) {
		mouseX -= getX();
		mouseY -= getY();
		for (IGuiElement element : elements) {
			if (element.isMouseOver(mouseX, mouseY)) {
				List<String> tooltip = element.getTooltip(mouseX, mouseY);
				if (!tooltip.isEmpty()) {
					return tooltip;
				}
			}
		}
		return tooltip;
	}

	@Nullable
	@Override
	public IGuiElement getElementAtPosition(int mouseX, int mouseY) {
		mouseX -= getX();
		mouseY -= getY();
		for (IGuiElement element : elements) {
			if (element.isMouseOver(mouseX, mouseY)) {
				return element;
			}
		}
		return null;
	}

	public DrawableElement drawable(Drawable drawable) {
		return add(new DrawableElement(drawable));
	}

	public DrawableElement drawable(int x, int y, Drawable drawable) {
		return add(new DrawableElement(x, y, drawable));
	}

	@Override
	public IGuiElement item(int xPos, int yPos, ItemStack itemStack) {
		IGuiElement element = new ItemElement(xPos, yPos, itemStack);
		add(element);
		return element;
	}

	@Override
	public IGuiElement text(String text) {
		return text(0, text, 0xFFFFFF);
	}

	@Override
	public IGuiElement text(String text, int color) {
		return text(0, text, color);
	}

	@Override
	public IGuiElement text(String text, GuiElementAlignment align) {
		return text(text, align, 0xFFFFFF);
	}

	@Override
	public IGuiElement text(String text, GuiElementAlignment align, int color) {
		return text(0, text, align, color);
	}

	public IGuiElement text(int x, String text, int color) {
		return text(x, text, GuiElementAlignment.LEFT, color);
	}

	public IGuiElement text(int x, String text, GuiElementAlignment align, int color) {
		return text(x, 12, text, align, color);
	}

	@Override
	public IGuiElement text(int x, int height, String text, GuiElementAlignment align, int color) {
		return text(x, height, text, align, color, false);
	}

	@Override
	public IGuiElement text(int x, int y, int width, int height, String text) {
		return add(new TextElement(x, y, width, height, text, GuiElementAlignment.LEFT, 0xFFFFFF, false));
	}

	@Override
	public IGuiElement text(int x, int height, String text, GuiElementAlignment align, int color, boolean unicode) {
		return add(new TextElement(x, 0, align.isCentered() ? getWidth() : -1, height, text, align, color, unicode));
	}

	@Override
	public AbstractElementLayout vertical(int xPos, int yPos, int width) {
		return add(new VerticalElementLayout(xPos, yPos, width));
	}

	@Override
	public AbstractElementLayout vertical(int width) {
		return add(new VerticalElementLayout(0, 0, width));
	}

	@Override
	public AbstractElementLayout horizontal(int xPos, int yPos, int height) {
		return add(new HorizontalElementLayout(xPos, yPos, height));
	}

	@Override
	public AbstractElementLayout horizontal(int height) {
		return add(new HorizontalElementLayout(0, 0, height));
	}

	@Override
	public ElementContainer panel(int xPos, int yPos, int width, int height) {
		return add(new PanelElementLayout(xPos, yPos, width, height));
	}

	@Override
	public ElementContainer panel(int width, int height) {
		return add(new PanelElementLayout(0, 0, width, height));
	}

	@Override
	public ElementLayoutHelper layoutHelper(IGuiElementLayoutHelper.LayoutFactory layoutFactory, int width, int height) {
		return new ElementLayoutHelper(layoutFactory, width, height, this);
	}
}
