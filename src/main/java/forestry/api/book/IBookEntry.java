package forestry.api.book;

import net.minecraft.item.ItemStack;

public interface IBookEntry {

	/**
	 * @return The stack that is displayed before the entry name.
	 */
	ItemStack getStack();

	void setStack(ItemStack stack);

	/**
	 * @return A unique string identifier for this entry.
	 */
	String getName();

	/**
	 * @return Localized short, human-readable identifier.
	 */
	String getLocalizedName();

	String getLocalizedPages();

	IBookPageFactory getPageFactory();

	void setLoader(IBookPageFactory loader);

	/**
	 * All sub entries of this entry are reachable over the buttons on the right side of the book.
	 */
	IBookEntry[] getSubEntries();

}
