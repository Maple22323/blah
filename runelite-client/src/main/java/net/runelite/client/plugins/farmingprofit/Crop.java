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
	UNKNOWN("Unknown", -1, -1),

	// Allotments
	POTATO		("Potatoes", 			ItemID.POTATO_SEED, 		ItemID.POTATO),
	ONION		("Onions", 				ItemID.ONION_SEED, 			ItemID.ONION),
	CABBAGE		("Cabbages", 			ItemID.CABBAGE_SEED, 		ItemID.CABBAGE),
	TOMATO		("Tomatoes", 			ItemID.TOMATO_SEED, 		ItemID.TOMATO),
	SWEETCORN	("Sweetcorn", 			ItemID.SWEETCORN_SEED, 		ItemID.SWEETCORN),
	STRAWBERRY	("Strawberries", 		ItemID.STRAWBERRY_SEED, 	ItemID.STRAWBERRY),
	WATERMELON	("Watermelons", 		ItemID.WATERMELON_SEED, 	ItemID.WATERMELON),

	// Flowers
	MARIGOLD	("Marigolds", 			ItemID.MARIGOLD_SEED, 		ItemID.MARIGOLDS),
	ROSEMARY	("Rosemaries", 			ItemID.ROSEMARY_SEED, 		ItemID.ROSEMARY),
	NASTURTIUM	("Nasturtiums", 		ItemID.NASTURTIUM_SEED, 	ItemID.NASTURTIUMS),
	WOAD		("Woad leaves", 		ItemID.WOAD_SEED, 			ItemID.WOAD_LEAF),
	LIMPWURT	("Limpwurt roots", 		ItemID.LIMPWURT_SEED, 		ItemID.LIMPWURT_ROOT),

	// Herbs
	GUAM		("Guam", 				ItemID.GUAM_SEED, 			ItemID.GUAM_LEAF, 	ItemID.GRIMY_GUAM_LEAF),
	MARRENTIL	("Marrentil", 			ItemID.MARRENTILL_SEED, 	ItemID.MARRENTILL, 	ItemID.GRIMY_MARRENTILL),
	TARROMIN	("Tarromin", 			ItemID.TARROMIN_SEED, 		ItemID.TARROMIN, 	ItemID.GRIMY_TARROMIN),
	HARRALANDER	("Harralander", 		ItemID.HARRALANDER_SEED,	ItemID.HARRALANDER, ItemID.GRIMY_HARRALANDER),
	GOUTWEED	("Goutweed", 			ItemID.GOUT_TUBER, 			ItemID.GOUTWEED),
	RANARR		("Ranarr", 				ItemID.RANARR_SEED, 		ItemID.RANARR_WEED, ItemID.GRIMY_RANARR_WEED),
	TOADFLAX	("Toadflax", 			ItemID.TOADFLAX_SEED, 		ItemID.TOADFLAX, 	ItemID.GRIMY_TOADFLAX),
	IRIT		("Irit", 				ItemID.IRIT_SEED, 			ItemID.IRIT_LEAF, 	ItemID.GRIMY_IRIT_LEAF),
	AVANTOE		("Avantoe", 			ItemID.AVANTOE_SEED, 		ItemID.AVANTOE, 	ItemID.GRIMY_AVANTOE),
	KWUARM		("Kwuarm", 				ItemID.KWUARM_SEED, 		ItemID.KWUARM, 		ItemID.GRIMY_KWUARM),
	SNAPDRAGON	("Snapdragon", 			ItemID.SNAPDRAGON_SEED, 	ItemID.SNAPDRAGON, 	ItemID.GRIMY_SNAPDRAGON),
	CADANTINE	("Cadantine", 			ItemID.CADANTINE_SEED, 		ItemID.CADANTINE, 	ItemID.GRIMY_CADANTINE),
	LANTADYME	("Lantadyme", 			ItemID.LANTADYME_SEED, 		ItemID.LANTADYME, 	ItemID.GRIMY_LANTADYME),
	DWARF_WEED	("Dwarf weed", 			ItemID.DWARF_WEED_SEED, 	ItemID.DWARF_WEED, 	ItemID.GRIMY_DWARF_WEED),
	TORSTOL		("Torstol", 			ItemID.TORSTOL_SEED, 		ItemID.TORSTOL, 	ItemID.GRIMY_TORSTOL),

	// Hops
	BARLEY		("Barley", 				ItemID.BARLEY_SEED, 		ItemID.BARLEY),
	HAMMERSTONE	("Hammerstone hops", 	ItemID.HAMMERSTONE_SEED, 	ItemID.HAMMERSTONE_HOPS),
	ASGARNIAN	("Asgarnian hops", 		ItemID.ASGARNIAN_SEED, 		ItemID.ASGARNIAN_HOPS),
	JUTE		("Jute fibre", 			ItemID.JUTE_SEED, 			ItemID.JUTE_FIBRE),
	YANILLIAN	("Yanillian hops", 		ItemID.YANILLIAN_SEED, 		ItemID.YANILLIAN_HOPS),
	KRANDORIAN	("Krandorian hops", 	ItemID.KRANDORIAN_SEED, 	ItemID.KRANDORIAN_HOPS),
	WILDBLOOD	("Wildblood hops", 		ItemID.WILDBLOOD_SEED, 		ItemID.WILDBLOOD_HOPS),

	// Bushes
	REDBERRY	("Redberries", 			ItemID.REDBERRY_SEED, 		ItemID.REDBERRIES),
	CADAVABERRY	("Cadava berries", 		ItemID.CADAVABERRY_SEED, 	ItemID.CADAVA_BERRIES),
	DWELLBERRY	("Dwellberries", 		ItemID.DWELLBERRY_SEED, 	ItemID.DWELLBERRIES),
	JANGERBERRY	("Jangerberries", 		ItemID.JANGERBERRY_SEED, 	ItemID.JANGERBERRIES),
	WHITEBERRY	("White berries", 		ItemID.WHITEBERRY_SEED, 	ItemID.WHITE_BERRIES),
	POISON_IVY	("Poison ivy berries", 	ItemID.POISON_IVY_SEED, 	ItemID.POISON_IVY_BERRIES),

	// Fruit trees
//	APPLE		("Cooking apples", 		ItemID.APPLE_SAPLING, 		ItemID.COOKING_APPLE),
//	BANANA		("Bananas", 			ItemID.BANANA_SAPLING, 		ItemID.BANANA),
//	ORANGE		("Oranges", 			ItemID.ORANGE_SAPLING, 		ItemID.ORANGE),
//	CURRY		("Curry leaves", 		ItemID.CURRY_SAPLING, 		ItemID.CURRY_LEAF),
//	PINEAPPLE	("Pineapples", 			ItemID.PINEAPPLE_SAPLING, 	ItemID.PINEAPPLE),
//	PAPAYA		("Papayas", 			ItemID.PAPAYA_SAPLING, 		ItemID.PAPAYA_FRUIT),
//	COCONUT		("Coconut", 			ItemID.PALM_SAPLING, 		ItemID.COCONUT),

	// Special
	SEEWEED		("Seaweed", 			ItemID.SEAWEED_SPORE, 		ItemID.GIANT_SEAWEED),
//	TEAK		("Teak Trees", 			ItemID.TEAK_SAPLING, 		ItemID.TEAK_LOGS),
//	GRAPE		("Grapes", 				ItemID.GRAPE_SEED, 			ItemID.GRAPES),
//	MUSHROOM	("Mushrooms", 			ItemID.MUSHROOM_SPORE, 		ItemID.MUSHROOM),
//	MAHOGANY	("Mahogany Trees", 		ItemID.MAHOGANY_SAPLING, 	ItemID.MAHOGANY_LOGS),
	CACTUS		("Cactus", 				ItemID.CACTUS_SEED, 		ItemID.CACTUS_SPINE);
//	BELLADONNA	("Belladonna", 			ItemID.BELLADONNA_SEED, 	ItemID.CAVE_NIGHTSHADE),
//	CALQUAT		("Calquat", 			ItemID.CALQUAT_SAPLING, 	ItemID.CALQUAT_FRUIT);


	Crop(String displayName, int seedId, int... products)
	{
		this.seedId = seedId;
		this.products = products;
		this.displayName = displayName;
	}

	@Getter
	private final int seedId;
	@Getter
	private final int[] products;
	@Getter
	private final String displayName;

	private static final Map<Integer, Crop> map = Collections.unmodifiableMap(initializeMapping());

	public int getProductId()
	{
		if (products.length > 0)
		{
			return products[0];
		}
		return -1;
	}

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
			for (Integer productId : s.products)
			{
				mMap.put(productId, s);
			}
		}
		return mMap;
	}

}
