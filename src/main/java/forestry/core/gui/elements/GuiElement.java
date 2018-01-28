package forestry.core.gui.elements;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.client.gui.Gui;

import forestry.api.gui.IGuiElement;

public class GuiElement extends Gui implements IGuiElement {
	/* Attributes - Final */
	//Element Position
	protected final int xPos;
	protected final int yPos;
	//Tooltip of the element
	protected final List<String> tooltip = new ArrayList<>();
	/* Attributes - State*/
	//Position Offset
	protected int xOffset = 0;
	protected int yOffset = 0;
	//Size of this element
	protected int width;
	protected int height;
	//The element container that contains this element
	@Nullable
	protected IGuiElement parent;

	public GuiElement(int width, int height) {
		this(0, 0, width, height);
	}

	public GuiElement(int xPos, int yPos, int width, int height) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
	}

	@Override
	public int getX() {
		return xPos + xOffset;
	}

	@Override
	public int getY() {
		return yPos + yOffset;
	}

	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	@Override
	public int getOriginalX() {
		return xPos;
	}

	@Override
	public int getOriginalY() {
		return yPos;
	}

	public final int getAbsoluteX() {
		return parent == null ? getX() : getX() + parent.getX();
	}

	public final int getAbsoluteY() {
		return parent == null ? getY() : getY() + parent.getY();
	}

	@Override
	public void draw(int startX, int startY, int mouseX, int mouseY) {
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
		return false;
	}

	@Override
	public boolean mouseReleased(int x, int y, int mouseEvent) {
		return false;
	}

	@Override
	public boolean keyTyped(char typedChar, int keyCode){
		return false;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setXOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	@Override
	public void setYOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	@Override
	public<E extends IGuiElement> E centerElementX(E element) {
		element.setXOffset((getWidth() - element.getWidth()) / 2);
		return element;
	}

	@Override
	public <E extends IGuiElement> E centerElementY(E element) {
		element.setYOffset((getHeight() - element.getHeight()) / 2);
		return element;
	}

	@Override
	public <E extends IGuiElement> E centerElement(E element) {
		return centerElementX(centerElementY(element));
	}

	@Override
	public boolean isMouseOver(int mouseX, int mouseY) {
		return isMouseOverElement(mouseX, mouseY);
	}

	protected final boolean isMouseOverElement(int mouseX, int mouseY) {
		int xPos = getX();
		int yPos = getY();
		return mouseX >= xPos && mouseX <= xPos + getWidth() && mouseY >= yPos && mouseY <= yPos + getHeight();
	}

	@Nullable
	@Override
	public IGuiElement getParent() {
		return parent;
	}

	@Override
	public void setParent(@Nullable IGuiElement parent) {
		this.parent = parent;
	}

	@Override
	public List<String> getTooltip(int mouseX, int mouseY) {
		return tooltip;
	}

	@Override
	public IGuiElement addTooltip(String line) {
		tooltip.add(line);
		return this;
	}

	@Override
	public IGuiElement addTooltip(Collection<String> lines) {
		tooltip.addAll(lines);
		return this;
	}

	@Override
	public void clearTooltip() {
		tooltip.clear();
	}

	@Override
	public List<String> getTooltip() {
		return tooltip;
	}
}
