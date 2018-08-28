/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
package net.runelite.client.plugins.farmingprofit;

import lombok.Getter;
import net.runelite.api.ItemID;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Crop
{

	UNKNOWN(-1, -1, "Unknown"),

	// Allotments
	POTATO(ItemID.POTATO_SEED, ItemID.POTATO, "Potatoes"),
	ONION(ItemID.ONION_SEED, ItemID.ONION, "Onions"),
	CABBAGE(ItemID.CABBAGE_SEED, ItemID.CABBAGE, "Cabbages"),
	TOMATO(ItemID.TOMATO_SEED, ItemID.TOMATO, "Tomatoes"),
	SWEETCORN(ItemID.SWEETCORN_SEED, ItemID.SWEETCORN, "Sweetcorn"),
	STRAWBERRY(ItemID.STRAWBERRY_SEED, ItemID.STRAWBERRY, "Strawberries"),
	WATERMELON(ItemID.WATERMELON_SEED, ItemID.WATERMELON, "Watermelons"),

	// Flowers
	MARIGOLD(ItemID.MARIGOLD_SEED, ItemID.MARIGOLDS, "Marigolds"),
	ROSEMARY(ItemID.ROSEMARY_SEED, ItemID.ROSEMARY, "Rosemaries"),
	NASTURTIUM(ItemID.NASTURTIUM_SEED, ItemID.NASTURTIUMS, "Nasturtiums"),
	WOAD(ItemID.WOAD_SEED, ItemID.WOAD_LEAF, "Woad leaves"),
	LIMPWURT(ItemID.LIMPWURT_SEED, ItemID.LIMPWURT_ROOT, "Limpwurt roots"),

	// Herbs
	GUAM		(ItemID.GUAM_SEED, 			ItemID.GUAM_LEAF, 	ItemID.GRIMY_GUAM_LEAF, "Guam"),
	MARRENTIL	(ItemID.MARRENTILL_SEED, 	ItemID.MARRENTILL, 	ItemID.GRIMY_MARRENTILL, "Marrentil"),
	TARROMIN	(ItemID.TARROMIN_SEED, 		ItemID.TARROMIN, 	ItemID.GRIMY_TARROMIN, "Tarromin"),
	HARRALANDER	(ItemID.HARRALANDER_SEED, 	ItemID.HARRALANDER, ItemID.GRIMY_HARRALANDER, "Harralander"),
	GOUTWEED	(ItemID.GOUT_TUBER, 		ItemID.GOUTWEED, 	"Goutweed"),
	RANARR		(ItemID.RANARR_SEED, 		ItemID.RANARR_WEED, ItemID.GRIMY_RANARR_WEED, "Ranarr"),
	TOADFLAX	(ItemID.TOADFLAX_SEED, 		ItemID.TOADFLAX, 	ItemID.GRIMY_TOADFLAX, "Toadflax"),
	IRIT		(ItemID.IRIT_SEED, 			ItemID.IRIT_LEAF, 	ItemID.GRIMY_IRIT_LEAF, "Irit"),
	AVANTOE		(ItemID.AVANTOE_SEED, 		ItemID.AVANTOE, 	ItemID.GRIMY_AVANTOE, "Avantoe"),
	KWUARM		(ItemID.KWUARM_SEED, 		ItemID.KWUARM, 		ItemID.GRIMY_KWUARM, "Kwuarm"),
	SNAPDRAGON	(ItemID.SNAPDRAGON_SEED, 	ItemID.SNAPDRAGON, 	ItemID.GRIMY_SNAPDRAGON, "Snapdragon"),
	CADANTINE	(ItemID.CADANTINE_SEED, 	ItemID.CADANTINE, 	ItemID.GRIMY_CADANTINE, "Cadantine"),
	LANTADYME	(ItemID.LANTADYME_SEED, 	ItemID.LANTADYME, 	ItemID.GRIMY_LANTADYME, "Lantadyme"),
	DWARF_WEED	(ItemID.DWARF_WEED_SEED, 	ItemID.DWARF_WEED, 	ItemID.GRIMY_DWARF_WEED, "Dwarf weed"),
	TORSTOL		(ItemID.TORSTOL_SEED, 		ItemID.TORSTOL, 	ItemID.GRIMY_TORSTOL, "Torstol"),

	// Hops
	BARLEY(ItemID.BARLEY_SEED, ItemID.BARLEY, "Barley"),
	HAMMERSTONE(ItemID.HAMMERSTONE_SEED, ItemID.HAMMERSTONE_HOPS, "Hammerstone hops"),
	ASGARNIAN(ItemID.ASGARNIAN_SEED, ItemID.ASGARNIAN_HOPS, "Asgarnian hops"),
	JUTE(ItemID.JUTE_SEED, ItemID.JUTE_FIBRE, "Jute fibre"),
	YANILLIAN(ItemID.YANILLIAN_SEED, ItemID.YANILLIAN_HOPS, "Yanillian hops"),
	KRANDORIAN(ItemID.KRANDORIAN_SEED, ItemID.KRANDORIAN_HOPS, "Krandorian hops"),
	WILDBLOOD(ItemID.WILDBLOOD_SEED, ItemID.WILDBLOOD_HOPS, "Wildblood hops"),

	// Bushes
	REDBERRY(ItemID.REDBERRY_SEED, ItemID.REDBERRIES, "Redberries"),
	CADAVABERRY(ItemID.CADAVABERRY_SEED, ItemID.CADAVA_BERRIES, "Cadava berries"),
	DWELLBERRY(ItemID.DWELLBERRY_SEED, ItemID.DWELLBERRIES, "Dwellberries"),
	JANGERBERRY(ItemID.JANGERBERRY_SEED, ItemID.JANGERBERRIES, "Jangerberries"),
	WHITEBERRY(ItemID.WHITEBERRY_SEED, ItemID.WHITE_BERRIES, "White berries"),
	POISON_IVY(ItemID.POISON_IVY_SEED, ItemID.POISON_IVY_BERRIES, "Poison ivy berries"),

	// Trees
	// TODO Not sure what to do for trees yet.

	// Fruit trees
	APPLE(ItemID.APPLE_SAPLING, ItemID.COOKING_APPLE, "Cooking apples"),
	BANANA(ItemID.BANANA_SAPLING, ItemID.BANANA, "Bananas"),
	ORANGE(ItemID.ORANGE_SAPLING, ItemID.ORANGE, "Oranges"),
	CURRY(ItemID.CURRY_SAPLING, ItemID.CURRY_LEAF, "Curry leaves"),
	PINEAPPLE(ItemID.PINEAPPLE_SAPLING, ItemID.PINEAPPLE, "Pineapples"),
	PAPAYA(ItemID.PAPAYA_SAPLING, ItemID.PAPAYA_FRUIT, "Papayas"),
	COCONUT(ItemID.PALM_SAPLING, ItemID.COCONUT, "Coconut"),

	// Special
	SEEWEED(ItemID.SEAWEED_SPORE, ItemID.GIANT_SEAWEED, "Seaweed"),
	TEAK(ItemID.TEAK_SAPLING, ItemID.TEAK_LOGS, "Teak Trees"),
	GRAPE(ItemID.GRAPE_SEED, ItemID.GRAPES, "Grapes"),
	MUSHROOM(ItemID.MUSHROOM_SPORE, ItemID.MUSHROOM, "Mushrooms"),
	MAHOGANY(ItemID.MAHOGANY_SAPLING, ItemID.MAHOGANY_LOGS, "Mahogany Trees"),
	CACTUS(ItemID.CACTUS_SEED, ItemID.CACTUS_SPINE, "Cactus"),
	BELLADONNA(ItemID.BELLADONNA_SEED, ItemID.CAVE_NIGHTSHADE, "Belladonna"),
	CALQUAT(ItemID.CALQUAT_SAPLING, ItemID.CALQUAT_FRUIT, "Calquat");


	Crop(int seedId, int cleanProductId, String displayName)
	{
		this.seedId = seedId;
		this.cleanProductId = cleanProductId;
		this.grimyProductId = -1;
		this.displayName = displayName;
	}

	Crop(int seedId, int cleanProductId, int grimyProductId, String displayName)
	{
		this.seedId = seedId;
		this.cleanProductId = cleanProductId;
		this.grimyProductId = grimyProductId;
		this.displayName = displayName;
	}

	@Getter
	private final int seedId;
	@Getter
	private final int cleanProductId;
	@Getter
	private final int grimyProductId;
	@Getter
	private final String displayName;
	private static final Map<Integer, Crop> map = Collections.unmodifiableMap(initializeMapping());

	public static Crop fromProductId(int product)
	{
		if (map.containsKey(product))
		{
			return map.get(product);
		}
		return Crop.UNKNOWN;
	}

	private static Map<Integer, Crop> initializeMapping()
	{
		Map<Integer, Crop> mMap = new HashMap<>();
		for (Crop s : Crop.values())
		{
			mMap.put(s.cleanProductId, s);
			if (s.grimyProductId != -1)
			{
				mMap.put(s.grimyProductId, s);
			}
		}
		return mMap;
	}

}
