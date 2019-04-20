/*
 * Copyright (c) 2018, Jeremy Plsek <https://github.com/jplsek>
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
package net.runelite.client.plugins.bank;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import static net.runelite.api.ItemID.COINS_995;
import static net.runelite.api.ItemID.MASTER_SCROLL_BOOK;
import static net.runelite.api.ItemID.PLATINUM_TOKEN;
import net.runelite.api.Varbits;
import net.runelite.client.game.ItemManager;

@Slf4j
class BankCalculation
{
	private static final float HIGH_ALCHEMY_CONSTANT = 0.6f;
	private static final ImmutableList<Varbits> TAB_VARBITS = ImmutableList.of(
		Varbits.BANK_TAB_ONE_COUNT,
		Varbits.BANK_TAB_TWO_COUNT,
		Varbits.BANK_TAB_THREE_COUNT,
		Varbits.BANK_TAB_FOUR_COUNT,
		Varbits.BANK_TAB_FIVE_COUNT,
		Varbits.BANK_TAB_SIX_COUNT,
		Varbits.BANK_TAB_SEVEN_COUNT,
		Varbits.BANK_TAB_EIGHT_COUNT,
		Varbits.BANK_TAB_NINE_COUNT
	);
	private static final Map<Varbits, Integer> VARBIT_MASTERBOOK_ITEMIDMAP = new HashMap<Varbits, Integer>()
	{
		{
			put(Varbits.MASTER_SCROLL_BOOK_NARDAH, ItemID.NARDAH_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_DIGSITE, ItemID.DIGSITE_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_FELDIP_HILLS, ItemID.FELDIP_HILLS_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_LUNAR_ISLE, ItemID.LUNAR_ISLE_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_MORTTON, ItemID.MORTTON_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_PEST_CONTROL, ItemID.PEST_CONTROL_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_PISCATORIS, ItemID.PISCATORIS_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_TAI_BWO_WANNAI, ItemID.TAI_BWO_WANNAI_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_ELF_CAMP, ItemID.ELF_CAMP_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_MOS_LE_HARMLESS, ItemID.MOS_LEHARMLESS_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_LUMBERYARD, ItemID.LUMBERYARD_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_ZUL_ANDRA, ItemID.ZULANDRA_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_REVENANT_CAVES, ItemID.REVENANT_CAVE_TELEPORT);
			put(Varbits.MASTER_SCROLL_BOOK_WATSON_TELEPORT, ItemID.WATSON_TELEPORT);
		}
	};
	private final BankConfig config;
	private final ItemManager itemManager;
	private final Client client;

	// Used to avoid extra calculation if the bank has not changed
	private int itemsHash;

	@Getter
	private long gePrice;

	@Getter
	private long haPrice;

	@Inject
	BankCalculation(ItemManager itemManager, BankConfig config, Client client)
	{
		this.itemManager = itemManager;
		this.config = config;
		this.client = client;
	}

	/**
	 * Calculate the bank based on the cache, price can be 0 if bank not active, or cache not set
	 */
	void calculate()
	{
		ItemContainer bankInventory = client.getItemContainer(InventoryID.BANK);

		if (bankInventory == null)
		{
			return;
		}

		Item[] items = bankInventory.getItems();
		int currentTab = client.getVar(Varbits.CURRENT_BANK_TAB);

		if (currentTab > 0)
		{
			int startIndex = 0;

			for (int i = currentTab - 1; i > 0; i--)
			{
				startIndex += client.getVar(TAB_VARBITS.get(i - 1));
			}

			int itemCount = client.getVar(TAB_VARBITS.get(currentTab - 1));
			items = Arrays.copyOfRange(items, startIndex, startIndex + itemCount);
		}

		if (items.length == 0 || !isBankDifferent(items))
		{
			return;
		}

		log.debug("Calculating new bank value...");

		gePrice = haPrice = 0;

		List<Integer> itemIds = new ArrayList<>();

		// Generate our lists (and do some quick price additions)
		for (Item item : items)
		{
			int quantity = item.getQuantity();

			if (item.getId() <= 0 || quantity == 0)
			{
				continue;
			}

			if (item.getId() == COINS_995)
			{
				gePrice += quantity;
				haPrice += quantity;
				continue;
			}

			if (item.getId() == PLATINUM_TOKEN)
			{
				gePrice += quantity * 1000L;
				haPrice += quantity * 1000L;
				continue;
			}

			if (item.getId() == MASTER_SCROLL_BOOK)
			{
				gePrice += itemManager.getItemPrice(ItemID.MASTER_SCROLL_BOOK_EMPTY);

				for (Map.Entry<Varbits, Integer> entry : VARBIT_MASTERBOOK_ITEMIDMAP.entrySet())
				{
					final long price = itemManager.getItemPrice(entry.getValue());
					final long amount = client.getVar(entry.getKey());
					gePrice += price * amount;
				}
			}

			final ItemComposition itemComposition = itemManager.getItemComposition(item.getId());

			if (config.showGE())
			{
				itemIds.add(item.getId());
			}

			if (config.showHA())
			{
				int price = itemComposition.getPrice();

				if (price > 0)
				{
					haPrice += (long) Math.round(price * HIGH_ALCHEMY_CONSTANT) *
						(long) quantity;
				}
			}
		}

		// Now do the calculations
		if (config.showGE() && !itemIds.isEmpty())
		{
			for (Item item : items)
			{
				int itemId = item.getId();
				int quantity = item.getQuantity();

				if (itemId <= 0 || quantity == 0
					|| itemId == ItemID.COINS_995 || itemId == ItemID.PLATINUM_TOKEN)
				{
					continue;
				}

				gePrice += (long) itemManager.getItemPrice(itemId) * quantity;
			}
		}
	}

	private boolean isBankDifferent(Item[] items)
	{
		Map<Integer, Integer> mapCheck = new HashMap<>();

		for (Item item : items)
		{
			mapCheck.put(item.getId(), item.getQuantity());
		}

		int curHash = mapCheck.hashCode();

		if (curHash != itemsHash)
		{
			itemsHash = curHash;
			return true;
		}

		return false;
	}
}
