package forestry.book;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;

import forestry.api.book.IBookCategory;
import forestry.api.book.IBookEntry;
import forestry.api.book.IBookEntryBuilder;
import forestry.api.book.IBookPageFactory;
import forestry.book.pages.TextPageParser;

public class BookEntryBuilder implements IBookEntryBuilder {
	private final String name;
	private final IBookCategory category;
	private ItemStack stack = ItemStack.EMPTY;
	private IBookPageFactory loader = TextPageParser.INSTANCE;
	private List<IBookEntryBuilder> subEntries = new LinkedList<>();

	BookEntryBuilder(IBookCategory category, String name) {
		this.category = category;
		this.name = name;
	}

	@Override
	public BookEntryBuilder setStack(ItemStack stack) {
		this.stack = stack;
		return this;
	}

	@Override
	public BookEntryBuilder setLoader(IBookPageFactory loader) {
		this.loader = loader;
		return this;
	}

	@Override
	public BookEntryBuilder addSubEntry(String name, ItemStack stack) {
		subEntries.add(new BookEntryBuilder(category, this.name + "." + name).setStack(stack));
		return this;
	}

	@Override
	public IBookEntry build(@Nullable IBookEntry parent) {
		return new BookEntry(name, stack, loader, entry -> subEntries.stream().map(builder -> builder.build(entry)).toArray(IBookEntry[]::new), parent);
	}

	@Override
	public IBookEntry build() {
		return build(null);
	}

	@Override
	public IBookCategory addToCategory() {
		category.addEntry(build());
		return category;
	}
}
