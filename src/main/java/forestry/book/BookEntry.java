package forestry.book;

import javax.annotation.Nullable;
import java.util.function.Function;

import net.minecraft.item.ItemStack;

import forestry.api.book.IBookEntry;
import forestry.api.book.IBookPageFactory;
import forestry.book.pages.TextPageParser;
import forestry.core.utils.Translator;

public class BookEntry implements IBookEntry {
	private final String name;
	private final ItemStack stack;
	private final IBookPageFactory loader;
	@Nullable
	private final IBookEntry parent;
	private final IBookEntry[] subEntries;

	public BookEntry(String name) {
		this(name, ItemStack.EMPTY, null);
	}

	public BookEntry(String name, ItemStack stack, @Nullable IBookEntry parent) {
		this(name, stack, TextPageParser.INSTANCE, new IBookEntry[0], parent);
	}

	public BookEntry(String name, ItemStack stack, IBookPageFactory loader, IBookEntry[] subEntries, @Nullable IBookEntry parent) {
		this.name = name;
		this.stack = stack;
		this.loader = loader;
		this.subEntries = subEntries;
		this.parent = parent;
	}

	public BookEntry(String name, ItemStack stack, IBookPageFactory loader, Function<IBookEntry, IBookEntry[]> subEntryFactory, @Nullable IBookEntry parent) {
		this.name = name;
		this.stack = stack;
		this.loader = loader;
		this.subEntries = subEntryFactory.apply(this);
		this.parent = parent;
	}

	@Override
	public IBookPageFactory getPageFactory() {
		return loader;
	}

	@Override
	public ItemStack getStack() {
		return stack;
	}

	@Override
	public String getLocalizedName() {
		return Translator.translateToLocal("for.gui.book.entry." + name + ".title");
	}

	@Override
	public String getLocalizedPages() {
		return Translator.translateToLocal("for.gui.book.entry." + name + ".pages");
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IBookEntry[] getSubEntries() {
		return subEntries;
	}

	@Override
	@Nullable
	public IBookEntry getParent() {
		return parent;
	}
}
