/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.gui;

/**
 * The alignment of the text in a text gui element.
 */
public enum GuiElementAlignment {
	LEFT(false),
	CENTER_LEFT,
	CENTER,
	CENTER_RIGHT,
	RIGHT(false);

	private final boolean centered;

	GuiElementAlignment() {
		this(true);
	}

	GuiElementAlignment(boolean centered) {
		this.centered = centered;
	}

	public final boolean isCentered(){
		return centered;
	}
}
