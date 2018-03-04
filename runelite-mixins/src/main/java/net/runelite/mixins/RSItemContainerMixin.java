/*
 * Copyright (c) 2016-2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.mixins;

import net.runelite.api.Item;
import net.runelite.api.events.ContainerChanged;
import net.runelite.api.mixins.FieldHook;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSItem;
import net.runelite.rs.api.RSItemContainer;

import java.util.ArrayList;
import java.util.List;

import static net.runelite.client.callback.Hooks.eventBus;

@Mixin(RSItemContainer.class)
public abstract class RSItemContainerMixin implements RSItemContainer
{
	@Shadow("clientInstance")
	private static RSClient client;

	@Inject
	private List<Item> previousItems;

	@Inject
	@Override
	public Item[] getItems()
	{
		int[] itemIds = getItemIds();
		int[] stackSizes = getStackSizes();
		Item[] items = new Item[itemIds.length];

		for (int i = 0; i < itemIds.length; ++i)
		{
			RSItem item = client.createItem();
			item.setId(itemIds[i]);
			item.setQuantity(stackSizes[i]);
			items[i] = item;
		}

		return items;
	}

	@FieldHook("stackSizes")
	@Inject
	public void itemsChanged(int idx)
	{
		if (previousItems == null)
		{
			previousItems = new ArrayList<Item>(getItemIds().length);
		}

		if (idx < 0 || idx >= getItemIds().length)
		{
			return;
		}

		int[] itemIds = getItemIds();
		int[] stackSizes = getStackSizes();

		// fetch the previous information
		Item previousItem = idx >= previousItems.size() ? null : previousItems.get(idx);

		RSItem item = client.createItem();
		item.setId(itemIds[idx]);
		item.setQuantity(stackSizes[idx]);

		if (idx >= previousItems.size())
		{
			previousItems.add(idx, item);
		}
		else
		{
			previousItems.set(idx, item);
		}

		ContainerChanged containerChanged = new ContainerChanged();
		containerChanged.setContainer(this);
		containerChanged.setIdx(idx);
		containerChanged.setItem(item);
		containerChanged.setPreviousItem(previousItem);
		eventBus.post(containerChanged);
	}

}
