package forestry.core.gui.elements;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

import forestry.core.gui.Drawable;
import forestry.core.gui.widgets.IScrollable;
import org.lwjgl.input.Mouse;

public class ScrollBarElement extends GuiElement {
	/* Attributes - Final */
	@Nullable
	private final Drawable background;
	private final GuiElement slider;

	/* Attributes - State */
	private boolean visible;
	private boolean isScrolling;
	private boolean wasClicked;
	private int currentValue;
	private int initialMouseClickY;

	/* Attributes - Parameters */
	@Nullable
	private IScrollable listener;
	private int minValue;
	private int maxValue;
	private int step;

	public ScrollBarElement(int xPos, int yPos, int width, int height, Drawable sliderTexture) {
		super(xPos, yPos, width, height);

		this.background = null;
		this.width = width;
		this.height = height;

		isScrolling = false;
		wasClicked = false;
		visible = true;
		slider = new DrawableElement(xPos, yPos, sliderTexture);
	}

	public ScrollBarElement(int xPos, int yPos, Drawable backgroundTexture, boolean hasBorder, Drawable sliderTexture) {
		super(xPos, yPos, backgroundTexture.uWidth, backgroundTexture.vHeight);

		int offset = hasBorder ? 1 : 0;

		this.background = backgroundTexture;

		isScrolling = false;
		wasClicked = false;
		visible = true;
		slider = new DrawableElement(xPos + offset, yPos + offset, sliderTexture);
	}

	public void setParameters(IScrollable listener, int minValue, int maxValue, int step){
		this.listener = listener;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.step = step;

		setValue(currentValue);
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public int getValue() {
		return MathHelper.clamp(currentValue, minValue, maxValue);
	}

	public int setValue(int value) {
		currentValue = MathHelper.clamp(value, minValue, maxValue);
		if(listener != null){
			listener.onScroll(currentValue);
		}
		int offset;
		if(value >= maxValue){
			offset = height - slider.height;
		}else if(value <= minValue){
			offset = 0;
		}else{
			offset = (int)(((float)(currentValue - minValue) / (maxValue - minValue)) * (float) (height - slider.height));
		}
		slider.setYOffset(offset);
		return currentValue;
	}

	@Override
	public void draw(int startX, int startY, int mouseX, int mouseY) {
		if(!isVisible()) {
			return;
		}
		updateSlider(mouseX, mouseY);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if(background != null){
			background.draw(startX + xPos, startY + yPos);
		}
		slider.draw(startX, startY, mouseX, mouseY);
	}

	protected void updateSlider(int mouseX, int mouseY) {
		if(!isVisible()) {
			return;
		}
		boolean mouseDown = Mouse.isButtonDown(0);

		if(listener == null || listener.isFocused(mouseX, mouseY)) {
			int wheel = Mouse.getDWheel();
			if (wheel > 0) {
				setValue(currentValue - step);
				return;
			} else if (wheel < 0) {
				setValue(currentValue + step);
				return;
			}
		}

		//Zhe position of the mouse relative to the position of the widget
		int y = mouseY - yPos;

		if(!mouseDown && wasClicked){
			wasClicked = false;
		}

		//Not clicked and scrolling -> stop scrolling
		if (!mouseDown && isScrolling) {
			this.isScrolling = false;
		}

		//Clicked on the slider and scrolling
		if (this.isScrolling) {
			float range = maxValue - minValue;
			float value = (float)(y - initialMouseClickY) / (float)(height - slider.height);
			value *= range;
			if(value < (float) step / 2f){
				setValue(minValue);
			}else if(value > maxValue - ((float) step / 2f)){
				setValue(maxValue);
			}else {
				setValue((int) (minValue + (float) step * Math.round(value)));
			}
		} else if(slider.isMouseOver(mouseX, mouseY)){ //clicked on the slider
			if(mouseDown){
				isScrolling = true;
				initialMouseClickY = y - slider.getYOffset();
			}
		} else if(mouseDown && !wasClicked && isMouseOver(mouseX, mouseY)) { //clicked on the bar but not on the slider
			float range = maxValue - minValue;
			float value = (float)(y - slider.height / 2) / (float)(height - slider.height);
			value *= range;
			if(value < (float) step / 2f){
				setValue(minValue);
			}else if(value > maxValue - ((float) step / 2f)){
				setValue(maxValue);
			}else {
				setValue((int) (minValue + (float) step * Math.round(value)));
			}
			wasClicked = true;
		}
	}
}
