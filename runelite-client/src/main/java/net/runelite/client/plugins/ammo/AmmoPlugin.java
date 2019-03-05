/*
 * Copyright (c) 2019 Hydrox6 <ikada@protonmail.ch>
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
package net.runelite.client.plugins.ammo;

import java.awt.image.BufferedImage;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemContainer;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

@PluginDescriptor(
	name = "Ammo"
)
public class AmmoPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Inject
	private ItemManager itemManager;

	private AmmoCounter counterBox;

	@Override
	public void startUp() throws Exception
	{
		clientThread.invokeLater(() ->
		{
			ItemContainer container = client.getItemContainer(InventoryID.EQUIPMENT);
			if (container != null)
			{
				parseInventory(container.getItems());
			}
		});
	}

	@Override
	public void shutDown() throws Exception
	{
		infoBoxManager.removeInfoBox(counterBox);
		counterBox = null;
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		if (event.getItemContainer() != client.getItemContainer(InventoryID.EQUIPMENT))
		{
			return;
		}

		parseInventory(event.getItemContainer().getItems());
	}

	private void parseInventory(Item[] items)
	{
		//Check for weapon slot items. This overrides the ammo slot
		if (items.length >= EquipmentInventorySlot.WEAPON.getSlotIdx() - 1)
		{
			final Item weapon = items[EquipmentInventorySlot.WEAPON.getSlotIdx()];
			final ItemComposition weaponComp = itemManager.getItemComposition(weapon.getId());
			if (weaponComp.isStackable())
			{
				updateInfobox(weapon, weaponComp);
				return;
			}
		}

		if (items.length <= EquipmentInventorySlot.AMMO.getSlotIdx())
		{
			return;
		}

		final Item ammo = items[EquipmentInventorySlot.AMMO.getSlotIdx()];
		final ItemComposition comp = itemManager.getItemComposition(ammo.getId());

		if (!comp.isStackable())
		{
			infoBoxManager.removeInfoBox(counterBox);
			counterBox = null;
			return;
		}

		updateInfobox(ammo, comp);
	}

	private void updateInfobox(Item item, ItemComposition comp)
	{

		if (counterBox != null && counterBox.getItemID() == item.getId())
		{
			counterBox.setCount(item.getQuantity());
			return;
		}

		infoBoxManager.removeInfoBox(counterBox);
		final BufferedImage image = itemManager.getImage(item.getId(), 5, false);
		counterBox = new AmmoCounter(this, item.getId(), item.getQuantity(), comp.getName(), image);
		infoBoxManager.addInfoBox(counterBox);
	}
}
