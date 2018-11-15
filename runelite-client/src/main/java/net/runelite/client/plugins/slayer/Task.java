/*
 * Copyright (c) 2017, Tyler <https://github.com/tylerthardy>
 * Copyright (c) 2018, Shaun Dreclin <shaundreclin@gmail.com>
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
package net.runelite.client.plugins.slayer;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import net.runelite.api.ItemID;
import net.runelite.api.NpcID;

@Getter
enum Task
{
	//<editor-fold desc="Enums">
	ABERRANT_SPECTRES("Aberrant spectres", ItemID.ABERRANT_SPECTRE, NpcID.ABERRANT_SPECTRE, NpcID.ABERRANT_SPECTRE_3,
			NpcID.ABERRANT_SPECTRE_4, NpcID.ABERRANT_SPECTRE_5, NpcID.ABERRANT_SPECTRE_6, NpcID.ABERRANT_SPECTRE_7,
			NpcID.ABHORRENT_SPECTRE, NpcID.DEVIANT_SPECTRE, NpcID.REPUGNANT_SPECTRE),
	ABYSSAL_DEMONS("Abyssal demons", ItemID.ABYSSAL_DEMON, NpcID.ABYSSAL_DEMON, NpcID.ABYSSAL_DEMON_415,
			NpcID.ABYSSAL_DEMON_416, NpcID.ABYSSAL_DEMON_7241, NpcID.GREATER_ABYSSAL_DEMON),
	ABYSSAL_SIRE("Abyssal Sire", ItemID.ABYSSAL_ORPHAN),
	ADAMANT_DRAGONS("Adamant dragons", ItemID.ADAMANTITE_BAR, NpcID.ADAMANT_DRAGON, NpcID.ADAMANT_DRAGON_8090),
	ANKOU("Ankou", ItemID.ANKOU_MASK, NpcID.ANKOU, NpcID.ANKOU_2515, NpcID.ANKOU_2516, NpcID.ANKOU_2517,
			NpcID.ANKOU_2518, NpcID.ANKOU_2519, NpcID.ANKOU_6608, NpcID.ANKOU_7257, NpcID.ANKOU_7864),
	AVIANSIES("Aviansies", ItemID.ENSOULED_AVIANSIE_HEAD, NpcID.AVIANSIE, NpcID.AVIANSIE_3170, NpcID.AVIANSIE_3171,
			NpcID.AVIANSIE_3172, NpcID.AVIANSIE_3173, NpcID.AVIANSIE_3174, NpcID.AVIANSIE_3175, NpcID.AVIANSIE_3176,
			NpcID.AVIANSIE_3177, NpcID.AVIANSIE_3178, NpcID.AVIANSIE_3179, NpcID.AVIANSIE_3180, NpcID.AVIANSIE_3181,
			NpcID.AVIANSIE_3182, NpcID.AVIANSIE_3183),
	BANSHEES("Banshees", ItemID.BANSHEE, NpcID.BANSHEE, NpcID.SCREAMING_BANSHEE, NpcID.SCREAMING_TWISTED_BANSHEE,
			NpcID.TWISTED_BANSHEE),
	BARROWS_BROTHERS("Barrows Brothers", ItemID.KARILS_COIF),
	BASILISKS("Basilisks", ItemID.BASILISK, NpcID.BASILISK, NpcID.BASILISK_417, NpcID.BASILISK_418,
			NpcID.MONSTROUS_BASILISK),
	BATS("Bats", ItemID.GIRAL_BAT_2, NpcID.BAT, NpcID.GIANT_BAT, NpcID.GIANT_BAT_4504, NpcID.GIANT_BAT_4562,
			NpcID.GIANT_BAT_5791, NpcID.GIANT_BAT_6824, NpcID.ALBINO_BAT, NpcID.GIRAL_BAT, NpcID.GUANIC_BAT,
			NpcID.KRYKET_BAT, NpcID.MURNG_BAT, NpcID.PHLUXIA_BAT, NpcID.PRAEL_BAT, NpcID.PSYKK_BAT),
	BEARS("Bears", ItemID.ENSOULED_BEAR_HEAD, NpcID.BEAR, NpcID.ANGRY_BEAR, NpcID.BLACK_BEAR, NpcID.BEAR_CUB,
			NpcID.BEAR_CUB_3909, NpcID.GRIZZLY_BEAR, NpcID.ANGRY_BEAR_4692, NpcID.REANIMATED_BEAR,
			NpcID.GRIZZLY_BEAR_3423, NpcID.GRIZZLY_BEAR_CUB, NpcID.GRIZZLY_BEAR_CUB_3425),
	ENTS("Ents", ItemID.ENTS_ROOTS, NpcID.ENT, NpcID.ENT_7234),
	LAVA_DRAGONS("Lava Dragons", ItemID.LAVA_SCALE, NpcID.LAVA_DRAGON),
	BIRDS("Birds", ItemID.FEATHER, NpcID.CHICKEN, NpcID.CHICKEN_2693, NpcID.CHICKEN_2819, NpcID.CHICKEN_2820,
			NpcID.CHICKEN_2821, NpcID.CHICKEN_3316, NpcID.CHICKEN_3564, NpcID.CHICKEN_3661, NpcID.CHICKEN_3662,
			NpcID.ROOSTER, NpcID.ROOSTER_2818, NpcID.ROOSTER_3663, NpcID.TERRORBIRD, NpcID.TERRORBIRD_2065,
			NpcID.TERRORBIRD_2066, NpcID.SEAGULL, NpcID.SEAGULL_1339, NpcID.SEAGULL_1554, NpcID.SEAGULL_1555,
			NpcID.BIRD, NpcID.BIRD_5241, NpcID.CHOMPY_BIRD, NpcID.JUBBLY_BIRD, NpcID.OOMLIE_BIRD,
			NpcID.CHOMPY_BIRD_1476, NpcID.JUBBLY_BIRD_4864),
	BLACK_DEMONS("Black demons", ItemID.BLACK_DEMON_MASK, NpcID.BLACK_DEMON, NpcID.BLACK_DEMON_1432,
			NpcID.BLACK_DEMON_2048, NpcID.BLACK_DEMON_2049, NpcID.BLACK_DEMON_2050, NpcID.BLACK_DEMON_2051,
			NpcID.BLACK_DEMON_2052, NpcID.BLACK_DEMON_5874, NpcID.BLACK_DEMON_5875, NpcID.BLACK_DEMON_5876,
			NpcID.BLACK_DEMON_5877, NpcID.BLACK_DEMON_6357, NpcID.BLACK_DEMON_7242, NpcID.BLACK_DEMON_7243,
			NpcID.BLACK_DEMON_7874, NpcID.BLACK_DEMON_7875, NpcID.BLACK_DEMON_7876, NpcID.BLACK_DEMON_HARD),
	BLACK_DRAGONS("Black dragons", ItemID.BLACK_DRAGON_MASK, NpcID.BLACK_DRAGON, NpcID.BLACK_DRAGON_253,
			NpcID.BLACK_DRAGON_254, NpcID.BLACK_DRAGON_255, NpcID.BLACK_DRAGON_256, NpcID.BLACK_DRAGON_257,
			NpcID.BLACK_DRAGON_258, NpcID.BLACK_DRAGON_259, NpcID.BLACK_DRAGON_7861, NpcID.BLACK_DRAGON_7862,
			NpcID.BLACK_DRAGON_7863, NpcID.BLACK_DRAGON_8084, NpcID.BLACK_DRAGON_8085, NpcID.BRUTAL_BLACK_DRAGON_8092,
			NpcID.BRUTAL_BLACK_DRAGON_8093, NpcID.BABY_DRAGON_1871, NpcID.BABY_DRAGON_1872, NpcID.BABY_DRAGON_7955),
	BLOODVELD("Bloodveld", ItemID.BLOODVELD, NpcID.BLOODVELD, NpcID.BLOODVELD_485, NpcID.BLOODVELD_486,
			NpcID.BLOODVELD_487, NpcID.BLOODVELD_3138, NpcID.INSATIABLE_BLOODVELD, NpcID.INSATIABLE_MUTATED_BLOODVELD,
			NpcID.MUTATED_BLOODVELD, NpcID.REANIMATED_BLOODVELD),
	BLUE_DRAGONS("Blue dragons", ItemID.BLUE_DRAGON_MASK, NpcID.BLUE_DRAGON, NpcID.BLUE_DRAGON_267,
			NpcID.BLUE_DRAGON_266, NpcID.BLUE_DRAGON_268, NpcID.BLUE_DRAGON_269, NpcID.BLUE_DRAGON_4385,
			NpcID.BLUE_DRAGON_5878, NpcID.BLUE_DRAGON_5879, NpcID.BLUE_DRAGON_5880, NpcID.BLUE_DRAGON_5881,
			NpcID.BLUE_DRAGON_5882, NpcID.BLUE_DRAGON_8074, NpcID.BLUE_DRAGON_8077, NpcID.BLUE_DRAGON_8083,
			NpcID.BRUTAL_BLUE_DRAGON, NpcID.BABY_DRAGON, NpcID.BABY_DRAGON_242, NpcID.BABY_DRAGON_243),
	BRINE_RATS("Brine rats", ItemID.BRINE_RAT, NpcID.BRINE_RAT),
	BRONZE_DRAGONS("Bronze dragons", ItemID.BRONZE_DRAGON_MASK, NpcID.BRONZE_DRAGON, NpcID.BRONZE_DRAGON_271,
			NpcID.BRONZE_DRAGON_7253),
	CALLISTO("Callisto", ItemID.CALLISTO_CUB),
	CATABLEPON("Catablepon", ItemID.LEFT_SKULL_HALF, NpcID.CATABLEPON, NpcID.CATABLEPON_2475, NpcID.CATABLEPON_2476),
	CAVE_BUGS("Cave bugs", ItemID.SWAMP_CAVE_BUG, NpcID.CAVE_BUG, NpcID.CAVE_BUG_483, NpcID.CAVE_BUG_LARVA),
	CAVE_CRAWLERS("Cave crawlers", ItemID.CAVE_CRAWLER, NpcID.CAVE_CRAWLER, NpcID.CAVE_CRAWLER_407,
			NpcID.CAVE_CRAWLER_408, NpcID.CAVE_CRAWLER_409, NpcID.CHASM_CRAWLER),
	CAVE_HORRORS("Cave horrors", ItemID.CAVE_HORROR, NpcID.CAVE_HORROR, NpcID.CAVE_HORROR_3210, NpcID.CAVE_HORROR_3211,
			NpcID.CAVE_HORROR_3212, NpcID.CAVE_HORROR_3213, NpcID.CAVE_ABOMINATION),
	CAVE_KRAKEN("Cave kraken", ItemID.CAVE_KRAKEN, NpcID.CAVE_KRAKEN),
	CAVE_SLIMES("Cave slimes", ItemID.SWAMP_CAVE_SLIME, NpcID.CAVE_SLIME),
	CERBERUS("Cerberus", ItemID.HELLPUPPY),
	CHAOS_ELEMENTAL("Chaos Elemental", ItemID.PET_CHAOS_ELEMENTAL),
	CHAOS_FANATIC("Chaos Fanatic", ItemID.PET_CHAOS_ELEMENTAL),
	COCKATRICE("Cockatrice", ItemID.COCKATRICE, NpcID.COCKATHRICE, NpcID.COCKATRICE, NpcID.COCKATRICE_419,
			NpcID.COCKATRICE_420),
	COWS("Cows", ItemID.COW_MASK, NpcID.COW, NpcID.COW_2806, NpcID.COW_2808, NpcID.COW_2810, NpcID.COW_5842,
			NpcID.COW_6401, NpcID.COW_CALF, NpcID.COW_CALF_2809, NpcID.COW_CALF_2816, NpcID.COW_HARD,
			NpcID.PLAGUE_COW_4190, NpcID.PLAGUE_COW_4190, NpcID.PLAGUE_COW_4191, NpcID.UNDEAD_COW_4421,
			NpcID.PLAGUE_COW, NpcID.UNDEAD_COW),
	CRAWLING_HANDS("Crawling hands", ItemID.CRAWLING_HAND, NpcID.CRAWLING_HAND, NpcID.CRAWLING_HAND_448,
			NpcID.CRAWLING_HAND_449, NpcID.CRAWLING_HAND_450, NpcID.CRAWLING_HAND_451, NpcID.CRAWLING_HAND_452,
			NpcID.CRAWLING_HAND_453, NpcID.CRAWLING_HAND_454, NpcID.CRAWLING_HAND_455, NpcID.CRAWLING_HAND_456,
			NpcID.CRAWLING_HAND_457, NpcID.CRUSHING_HAND),
	CRAZY_ARCHAEOLOGIST("Crazy Archaeologist", ItemID.FEDORA),
	CROCODILES("Crocodiles", ItemID.SWAMP_LIZARD, NpcID.CROCODILE),
	DAGANNOTH("Dagannoth", ItemID.DAGANNOTH, NpcID.DAGANNOTH, NpcID.DAGANNOTH_970, NpcID.DAGANNOTH_971,
			NpcID.DAGANNOTH_972, NpcID.DAGANNOTH_973, NpcID.DAGANNOTH_974, NpcID.DAGANNOTH_975, NpcID.DAGANNOTH_976,
			NpcID.DAGANNOTH_977, NpcID.DAGANNOTH_978, NpcID.DAGANNOTH_979, NpcID.DAGANNOTH_2259, NpcID.DAGANNOTH_3185,
			NpcID.DAGANNOTH_5942, NpcID.DAGANNOTH_5943, NpcID.DAGANNOTH_7259, NpcID.DAGANNOTH_7260),
	DAGANNOTH_KINGS("Dagannoth Kings", ItemID.PET_DAGANNOTH_PRIME),
	DARK_BEASTS("Dark beasts", ItemID.DARK_BEAST, NpcID.DARK_BEAST, NpcID.DARK_BEAST_7250, NpcID.NIGHT_BEAST),
	DARK_WARRIORS("Dark warriors", ItemID.BLACK_MED_HELM, NpcID.DARK_WARRIOR, NpcID.DARK_WARRIOR_6606),
	DERANGED_ARCHAEOLOGIST("Deranged Archaeologist", ItemID.ARCHAEOLOGISTS_DIARY),
	DESERT_LIZARDS("Desert lizards", ItemID.DESERT_LIZARD, NpcID.DESERT_LIZARD, NpcID.DESERT_LIZARD_460,
			NpcID.DESERT_LIZARD_461, NpcID.SMALL_LIZARD, NpcID.SMALL_LIZARD_463, NpcID.LIZARD, NpcID.LIZARD_7597),
	DOGS("Dogs", ItemID.GUARD_DOG, NpcID.DOG, NpcID.DOG_7771, NpcID.DOG_8041, NpcID.GUARD_DOG, NpcID.GUARD_DOG_131,
			NpcID.GUARD_DOG_7209, NpcID.SINCLAIR_GUARD_DOG, NpcID.REANIMATED_DOG, NpcID.TERROR_DOG,
			NpcID.TERROR_DOG_6474, NpcID.WILD_DOG, NpcID.WILD_DOG_113, NpcID.WILD_DOG_7322, NpcID.JACKAL),
	DUST_DEVILS("Dust devils", ItemID.DUST_DEVIL, NpcID.DUST_DEVIL, NpcID.DUST_DEVIL_7249, NpcID.CHOKE_DEVIL),
	DWARVES("Dwarves", ItemID.DWARVEN_HELMET, NpcID.DWARF, NpcID.DWARF_292, NpcID.DWARF_293, NpcID.DWARF_294,
			NpcID.DWARF_295, NpcID.DWARF_296, NpcID.DWARF_1051, NpcID.DWARF_1401, NpcID.DWARF_1402, NpcID.DWARF_1403,
			NpcID.DWARF_1404, NpcID.DWARF_1405, NpcID.DWARF_1406, NpcID.DWARF_1407, NpcID.DWARF_1408, NpcID.DWARF_4512,
			NpcID.DWARF_5170, NpcID.DWARF_5171, NpcID.DWARF_5172, NpcID.DWARF_5173, NpcID.DWARF_5174, NpcID.DWARF_5175,
			NpcID.DWARF_7712, NpcID.DWARF_7713, NpcID.DWARF_7714, NpcID.DWARF_7715, NpcID.DWARF_7721,
			NpcID.DWARF_GANG_MEMBER, NpcID.DWARF_GANG_MEMBER_1355, NpcID.DWARF_GANG_MEMBER_1356, NpcID.CHAOS_DWARF_2423,
			NpcID.CHAOS_DWARF, NpcID.GUARD_5185, NpcID.BLACK_GUARD_1411, NpcID.BLACK_GUARD, NpcID.BLACK_GUARD_1410,
			NpcID.BLACK_GUARD_1412, NpcID.BLACK_GUARD_6046, NpcID.BLACK_GUARD_6047, NpcID.BLACK_GUARD_6048,
			NpcID.BLACK_GUARD_6049, NpcID.BLACK_GUARD_8474, NpcID.BLACK_GUARD_8475, NpcID.BLACK_GUARD_8476,
			NpcID.BLACK_GUARD_BERSERKER, NpcID.BLACK_GUARD_BERSERKER_6050, NpcID.BLACK_GUARD_BERSERKER_6051,
			NpcID.BLACK_GUARD_BERSERKER_6052),
	EARTH_WARRIORS("Earth warriors", ItemID.BRONZE_FULL_HELM_T, NpcID.EARTH_WARRIOR),
	ELVES("Elves", ItemID.ELF, NpcID.ELF_WARRIOR, NpcID.ELF_WARRIOR_3429, NpcID.ELF_WARRIOR_3431,
			NpcID.ELF_WARRIOR_5293, NpcID.ELF_WARRIOR_5294, NpcID.ELF_WARRIOR_5295, NpcID.ELF_WARRIOR_5296,
			NpcID.REANIMATED_ELF, NpcID.MOURNER, NpcID.MOURNER_1115, NpcID.MOURNER_1136, NpcID.MOURNER_1148,
			NpcID.MOURNER_1149, NpcID.MOURNER_1150, NpcID.MOURNER_1151, NpcID.MOURNER_4006, NpcID.MOURNER_4259,
			NpcID.MOURNER_4260, NpcID.MOURNER_4261, NpcID.MOURNER_4273, NpcID.MOURNER_5285, NpcID.MOURNER_5286,
			NpcID.MOURNER_5287, NpcID.MOURNER_5311, NpcID.MOURNER_5312),
	FEVER_SPIDERS("Fever spiders", ItemID.FEVER_SPIDER, NpcID.FEVER_SPIDER),
	FIRE_GIANTS("Fire giants", ItemID.FIRE_BATTLESTAFF, NpcID.FIRE_GIANT, NpcID.FIRE_GIANT_2076, NpcID.FIRE_GIANT_2077,
			NpcID.FIRE_GIANT_2078, NpcID.FIRE_GIANT_2079, NpcID.FIRE_GIANT_2080, NpcID.FIRE_GIANT_2081,
			NpcID.FIRE_GIANT_2082, NpcID.FIRE_GIANT_2083, NpcID.FIRE_GIANT_2084, NpcID.FIRE_GIANT_7251,
			NpcID.FIRE_GIANT_7252),
	REVENANTS("Revenants", ItemID.REVENANT_ETHER, NpcID.REVENANT_IMP, NpcID.REVENANT_GOBLIN, NpcID.REVENANT_PYREFIEND,
			NpcID.REVENANT_HOBGOBLIN, NpcID.REVENANT_CYCLOPS, NpcID.REVENANT_HELLHOUND, NpcID.REVENANT_DEMON,
			NpcID.REVENANT_ORK, NpcID.REVENANT_DARK_BEAST, NpcID.REVENANT_KNIGHT, NpcID.REVENANT_DRAGON),
	FLESH_CRAWLERS("Flesh crawlers", ItemID.ENSOULED_SCORPION_HEAD, NpcID.FLESH_CRAWLER, NpcID.FLESH_CRAWLER_2499,
			NpcID.FLESH_CRAWLER_2500),
	FOSSIL_ISLAND_WYVERNS("Fossil island wyverns", ItemID.FOSSIL_ISLAND_WYVERN, NpcID.ANCIENT_WYVERN,
			NpcID.LONGTAILED_WYVERN, NpcID.SPITTING_WYVERN, NpcID.TALONED_WYVERN),
	GARGOYLES("Gargoyles", ItemID.GARGOYLE, NpcID.GARGOYLE, NpcID.GARGOYLE_413, NpcID.GARGOYLE_1543,
			NpcID.MARBLE_GARGOYLE, NpcID.MARBLE_GARGOYLE_7408),
	GENERAL_GRAARDOR("General Graardor", ItemID.PET_GENERAL_GRAARDOR),
	GHOSTS("Ghosts", ItemID.GHOSTSPEAK_AMULET, NpcID.GHOST, NpcID.GHOST_, NpcID.GHOST_86, NpcID.GHOST_87,
			NpcID.GHOST_88, NpcID.GHOST_89, NpcID.GHOST_90, NpcID.GHOST_91, NpcID.GHOST_92, NpcID.GHOST_93,
			NpcID.GHOST_94, NpcID.GHOST_95, NpcID.GHOST_96, NpcID.GHOST_97, NpcID.GHOST_920, NpcID.GHOST_1786,
			NpcID.GHOST_2527, NpcID.GHOST_2528, NpcID.GHOST_2529, NpcID.GHOST_2530, NpcID.GHOST_2531, NpcID.GHOST_2532,
			NpcID.GHOST_2533, NpcID.GHOST_2534, NpcID.GHOST_3516, NpcID.GHOST_3617, NpcID.GHOST_3625, NpcID.GHOST_3975,
			NpcID.GHOST_3976, NpcID.GHOST_3977, NpcID.GHOST_3978, NpcID.GHOST_3979, NpcID.GHOST_5370, NpcID.GHOST_6815,
			NpcID.GHOST_6816, NpcID.GHOST_6817, NpcID.GHOST_6818, NpcID.GHOST_6819, NpcID.GHOST_6820, NpcID.GHOST_6821,
			NpcID.GHOST_6822, NpcID.GHOST_7263, NpcID.GHOST_7264, NpcID.GHOST__3009, NpcID.TORTURED_SOUL),
	GIANT_MOLE("Giant Mole", ItemID.BABY_MOLE),
	GHOULS("Ghouls", ItemID.ZOMBIE_HEAD, NpcID.GHOUL),
	GOBLINS("Goblins", ItemID.ENSOULED_GOBLIN_HEAD, NpcID.GOBLIN, NpcID.GOBLIN_656, NpcID.GOBLIN_657, NpcID.GOBLIN_658,
			NpcID.GOBLIN_659, NpcID.GOBLIN_660, NpcID.GOBLIN_661, NpcID.GOBLIN_662, NpcID.GOBLIN_663, NpcID.GOBLIN_664,
			NpcID.GOBLIN_665, NpcID.GOBLIN_666, NpcID.GOBLIN_667, NpcID.GOBLIN_668, NpcID.GOBLIN_674, NpcID.GOBLIN_677,
			NpcID.GOBLIN_678, NpcID.GOBLIN_2245, NpcID.GOBLIN_2246, NpcID.GOBLIN_2247, NpcID.GOBLIN_2248,
			NpcID.GOBLIN_2249, NpcID.GOBLIN_2484, NpcID.GOBLIN_2485, NpcID.GOBLIN_2486, NpcID.GOBLIN_2487,
			NpcID.GOBLIN_2488, NpcID.GOBLIN_2489, NpcID.GOBLIN_3028, NpcID.GOBLIN_3029, NpcID.GOBLIN_3030,
			NpcID.GOBLIN_3031, NpcID.GOBLIN_3032, NpcID.GOBLIN_3033, NpcID.GOBLIN_3034, NpcID.GOBLIN_3035,
			NpcID.GOBLIN_3036, NpcID.GOBLIN_3037, NpcID.GOBLIN_3038, NpcID.GOBLIN_3039, NpcID.GOBLIN_3040,
			NpcID.GOBLIN_3041, NpcID.GOBLIN_3042, NpcID.GOBLIN_3043, NpcID.GOBLIN_3044, NpcID.GOBLIN_3045,
			NpcID.GOBLIN_3046, NpcID.GOBLIN_3047, NpcID.GOBLIN_3048, NpcID.GOBLIN_3051, NpcID.GOBLIN_3052,
			NpcID.GOBLIN_3053, NpcID.GOBLIN_3054, NpcID.GOBLIN_3073, NpcID.GOBLIN_3074, NpcID.GOBLIN_3075,
			NpcID.GOBLIN_3076, NpcID.GOBLIN_4902, NpcID.GOBLIN_4903, NpcID.GOBLIN_4904, NpcID.GOBLIN_4905,
			NpcID.GOBLIN_4906, NpcID.GOBLIN_5152, NpcID.GOBLIN_5153, NpcID.GOBLIN_5154, NpcID.GOBLIN_5192,
			NpcID.GOBLIN_5193, NpcID.GOBLIN_5195, NpcID.GOBLIN_5196, NpcID.GOBLIN_5197, NpcID.GOBLIN_5198,
			NpcID.GOBLIN_5199, NpcID.GOBLIN_5200, NpcID.GOBLIN_5201, NpcID.GOBLIN_5202, NpcID.GOBLIN_5203,
			NpcID.GOBLIN_5204, NpcID.GOBLIN_5205, NpcID.GOBLIN_5206, NpcID.GOBLIN_5207, NpcID.GOBLIN_5208,
			NpcID.GOBLIN_5376, NpcID.GOBLIN_5377, NpcID.GOBLIN_5508, NpcID.GOBLIN_5509),
	GREATER_DEMONS("Greater demons", ItemID.GREATER_DEMON_MASK, NpcID.GREATER_DEMON, NpcID.GREATER_DEMON_2026,
			NpcID.GREATER_DEMON_2027, NpcID.GREATER_DEMON_2027, NpcID.GREATER_DEMON_2028, NpcID.GREATER_DEMON_2029,
			NpcID.GREATER_DEMON_2030, NpcID.GREATER_DEMON_2031, NpcID.GREATER_DEMON_2032, NpcID.GREATER_DEMON_7244,
			NpcID.GREATER_DEMON_7245, NpcID.GREATER_DEMON_7246, NpcID.GREATER_DEMON_7871, NpcID.GREATER_DEMON_7872,
			NpcID.GREATER_DEMON_7873),
	GREEN_DRAGONS("Green dragons", ItemID.GREEN_DRAGON_MASK, NpcID.GREEN_DRAGON, NpcID.GREEN_DRAGON_261,
			NpcID.GREEN_DRAGON_262, NpcID.GREEN_DRAGON_263, NpcID.GREEN_DRAGON_264, NpcID.GREEN_DRAGON_7868,
			NpcID.GREEN_DRAGON_7869, NpcID.GREEN_DRAGON_7870, NpcID.GREEN_DRAGON_8073, NpcID.GREEN_DRAGON_8076,
			NpcID.GREEN_DRAGON_8082, NpcID.BRUTAL_GREEN_DRAGON_8081, NpcID.BABY_DRAGON_5194, NpcID.BABY_DRAGON_5872,
			NpcID.BABY_DRAGON_5873),
	GROTESQUE_GUARDIANS("Grotesque Guardians", ItemID.MIDNIGHT),
	HARPIE_BUG_SWARMS("Harpie bug swarms", ItemID.SWARM, NpcID.HARPIE_BUG_SWARM),
	HELLHOUNDS("Hellhounds", ItemID.HELLHOUND, NpcID.HELLHOUND, NpcID.HELLHOUND_105, NpcID.HELLHOUND_135,
			NpcID.HELLHOUND_3133, NpcID.HELLHOUND_7256, NpcID.HELLHOUND_7877),
	HILL_GIANTS("Hill giants", ItemID.ENSOULED_GIANT_HEAD, NpcID.HILL_GIANT, NpcID.HILL_GIANT_2099,
			NpcID.HILL_GIANT_2100, NpcID.HILL_GIANT_2101, NpcID.HILL_GIANT_2102, NpcID.HILL_GIANT_2103,
			NpcID.HILL_GIANT_7261),
	HOBGOBLINS("Hobgoblins", ItemID.HOBGOBLIN_GUARD, NpcID.HOBGOBLIN, NpcID.HOBGOBLIN_2241, NpcID.HOBGOBLIN_3049,
			NpcID.HOBGOBLIN_3050, NpcID.HOBGOBLIN_3286, NpcID.HOBGOBLIN_3287, NpcID.HOBGOBLIN_3288,
			NpcID.HOBGOBLIN_3289, NpcID.HOBGOBLIN_4805),
	ICE_GIANTS("Ice giants", ItemID.ICE_DIAMOND, NpcID.ICE_GIANT, NpcID.ICE_GIANT_2086, NpcID.ICE_GIANT_2087,
			NpcID.ICE_GIANT_2088, NpcID.ICE_GIANT_2089, NpcID.ICE_GIANT_7878, NpcID.ICE_GIANT_7879,
			NpcID.ICE_GIANT_7880),
	ICE_WARRIORS("Ice warriors", ItemID.MITHRIL_FULL_HELM_T, NpcID.ICE_WARRIOR, NpcID.ICE_WARRIOR_2842,
			NpcID.ICE_WARRIOR_2851),
	ICEFIENDS("Icefiends", ItemID.ICE_DIAMOND, NpcID.ICEFIEND, NpcID.ICEFIEND_4813, NpcID.ICEFIEND_7586),
	INFERNAL_MAGES("Infernal mages", ItemID.INFERNAL_MAGE, NpcID.INFERNAL_MAGE, NpcID.INFERNAL_MAGE_444,
			NpcID.INFERNAL_MAGE_445, NpcID.INFERNAL_MAGE_446, NpcID.INFERNAL_MAGE_447, NpcID.MALEVOLENT_MAGE),
	IRON_DRAGONS("Iron dragons", ItemID.IRON_DRAGON_MASK, NpcID.IRON_DRAGON, NpcID.IRON_DRAGON_273,
			NpcID.IRON_DRAGON_7254, NpcID.IRON_DRAGON_8080),
	JAD("TzTok-Jad", ItemID.TZREKJAD),
	JELLIES("Jellies", ItemID.JELLY, NpcID.JELLY, NpcID.JELLY_438, NpcID.JELLY_439, NpcID.JELLY_440,
			NpcID.JELLY_441, NpcID.JELLY_442, NpcID.JELLY_7518, NpcID.VITREOUS_JELLY, NpcID.VITREOUS_WARPED_JELLY,
			NpcID.WARPED_JELLY),
	JUNGLE_HORROR("Jungle horrors", ItemID.ENSOULED_HORROR_HEAD, NpcID.JUNGLE_HORROR, NpcID.JUNGLE_HORROR_3205,
			NpcID.JUNGLE_HORROR_3206, NpcID.JUNGLE_HORROR_3207, NpcID.JUNGLE_HORROR_3208),
	KALPHITE("Kalphite", ItemID.KALPHITE_SOLDIER, NpcID.KALPHITE_WORKER, NpcID.KALPHITE_WORKER_956,
			NpcID.KALPHITE_WORKER_961, NpcID.KALPHITE_SOLDIER, NpcID.KALPHITE_SOLDIER_957, NpcID.KALPHITE_SOLDIER_958,
			NpcID.KALPHITE_GUARDIAN, NpcID.KALPHITE_GUARDIAN_960, NpcID.KALPHITE_GUARDIAN_962),
	MAMMOTHS("Mammoths", ItemID.ATTACKER_HORN, NpcID.MAMMOTH),
	KALPHITE_QUEEN("Kalphite Queen", ItemID.KALPHITE_PRINCESS),
	KILLERWATTS("Killerwatts", ItemID.KILLERWATT, NpcID.KILLERWATT, NpcID.KILLERWATT_470),
	KING_BLACK_DRAGON("King Black Dragon", ItemID.PRINCE_BLACK_DRAGON),
	KRAKEN("Cave Kraken Boss", ItemID.PET_KRAKEN),
	KREEARRA("Kree'arra", ItemID.PET_KREEARRA),
	KRIL_TSUTSAROTH("K'ril Tsutsaroth", ItemID.PET_KRIL_TSUTSAROTH),
	KURASK("Kurask", ItemID.KURASK, NpcID.KURASK, NpcID.KURASK_410, NpcID.KURASK_411, NpcID.KING_KURASK),
	ROGUES("Rogues", ItemID.ROGUE_MASK, NpcID.ROGUE, NpcID.ROGUE_6603, NpcID.ROGUE_GUARD, NpcID.ROGUE_GUARD_3191,
			NpcID.ROGUE_GUARD_3192),
	LESSER_DEMONS("Lesser demons", ItemID.LESSER_DEMON_MASK, NpcID.LESSER_DEMON, NpcID.LESSER_DEMON_2006,
			NpcID.LESSER_DEMON_2007, NpcID.LESSER_DEMON_2008, NpcID.LESSER_DEMON_2018, NpcID.LESSER_DEMON_3982,
			NpcID.LESSER_DEMON_7247, NpcID.LESSER_DEMON_7248, NpcID.LESSER_DEMON_7656, NpcID.LESSER_DEMON_7657,
			NpcID.LESSER_DEMON_7664, NpcID.LESSER_DEMON_7865, NpcID.LESSER_DEMON_7866, NpcID.LESSER_DEMON_7867),
	LIZARDMEN("Lizardmen", ItemID.LIZARDMAN_FANG, NpcID.LIZARDMAN, NpcID.LIZARDMAN_6915, NpcID.LIZARDMAN_6916,
			NpcID.LIZARDMAN_6917, NpcID.LIZARDMAN_BRUTE, NpcID.LIZARDMAN_BRUTE_6919, NpcID.LIZARDMAN_SHAMAN,
			NpcID.LIZARDMAN_SHAMAN_6767, NpcID.LIZARDMAN_SHAMAN_7573, NpcID.LIZARDMAN_SHAMAN_7574,
			NpcID.LIZARDMAN_SHAMAN_7744, NpcID.LIZARDMAN_SHAMAN_7745, NpcID.CORRUPT_LIZARDMAN_8000,
			NpcID.CORRUPT_LIZARDMAN),
	MINIONS_OF_SCABARAS("Minions of scabaras", ItemID.GOLDEN_SCARAB, NpcID.SCARAB_SWARM, NpcID.SCARAB_SWARM_4192,
			NpcID.LOCUST_RIDER, NpcID.LOCUST_RIDER_796, NpcID.LOCUST_RIDER_801, NpcID.LOCUST_RIDER_800,
			NpcID.SCARAB_MAGE, NpcID.SCARAB_MAGE_799),
	MINOTAURS("Minotaurs", ItemID.ENSOULED_MINOTAUR_HEAD, NpcID.MINOTAUR, NpcID.MINOTAUR_2482, NpcID.MINOTAUR_2483),
	MITHRIL_DRAGONS("Mithril dragons", ItemID.MITHRIL_DRAGON_MASK, NpcID.MITHRIL_DRAGON, NpcID.MITHRIL_DRAGON_8088,
			NpcID.MITHRIL_DRAGON_8089),
	MOGRES("Mogres", ItemID.MOGRE, NpcID.MOGRE),
	MOLANISKS("Molanisks", ItemID.MOLANISK, NpcID.MOLANISK),
	MONKEYS("Monkeys", ItemID.ENSOULED_MONKEY_HEAD, NpcID.MONKEY, NpcID.MONKEY_1469, NpcID.MONKEY_1817,
			NpcID.MONKEY_2848, NpcID.MONKEY_3200, NpcID.MONKEY_4177, NpcID.MONKEY_5279, NpcID.MONKEY_5280,
			NpcID.MONKEY_6483, NpcID.MONKEY_ARCHER, NpcID.MONKEY_ARCHER_5273, NpcID.MONKEY_ARCHER_5274,
			NpcID.MONKEY_ARCHER_6794, NpcID.MONKEY_ARCHER_6813, NpcID.MONKEY_GUARD, NpcID.MONKEY_GUARD_5275,
			NpcID.MONKEY_GUARD_5276, NpcID.MONKEY_GUARD_6811, NpcID.MONKEY_GUARD_7122, NpcID.MONKEY_GUARD_7123,
			NpcID.MONKEY_GUARD_7123, NpcID.MONKEY_GUARD_7124, NpcID.MONKEY_GUARD_7125, NpcID.MONKEY_GUARD_7126,
			NpcID.MONKEY_GUARD_7127, NpcID.MONKEY_GUARD_7128, NpcID.MONKEY_GUARD_7129, NpcID.MONKEY_GUARD_7130,
			NpcID.MONKEY_GUARD_7131, NpcID.MONKEY_GUARD_7132, NpcID.MONKEY_GUARD_7132, NpcID.MONKEY_GUARD_7133,
			NpcID.MONKEY_GUARD_7134, NpcID.MONKEY_GUARD_7135, NpcID.MONKEY_GUARD_7136, NpcID.MONKEY_GUARD_7137,
			NpcID.MONKEY_GUARD_7138, NpcID.MONKEY_GUARD_7139, NpcID.MONKEY_GUARD_7140, NpcID.MONKEY_GUARD_7141,
			NpcID.MONKEY_GUARD_7142, NpcID.MONKEY_GUARD_7143, NpcID.MANIACAL_MONKEY, NpcID.MANIACAL_MONKEY_7118,
			NpcID.MANIACAL_MONKEY_7212, NpcID.MANIACAL_MONKEY_7213, NpcID.MANIACAL_MONKEY_7214,
			NpcID.MANIACAL_MONKEY_7215, NpcID.MANIACAL_MONKEY_7216, NpcID.MANIACAL_MONKEY_ARCHER, NpcID.MONKEY_ZOMBIE,
			NpcID.MONKEY_ZOMBIE_5282, NpcID.MONKEY_ZOMBIE_5283, NpcID.MONKEY),
	MOSS_GIANTS("Moss giants", ItemID.HILL_GIANT_CLUB, NpcID.MOSS_GIANT, NpcID.MOSS_GIANT_2090, NpcID.MOSS_GIANT_2091,
			NpcID.MOSS_GIANT_2092, NpcID.MOSS_GIANT_2093, NpcID.MOSS_GIANT_3851, NpcID.MOSS_GIANT_3852,
			NpcID.MOSS_GIANT_6386, NpcID.MOSS_GIANT_7262, NpcID.MOSS_GIANT_HARD),
	MUTATED_ZYGOMITES("Mutated zygomites", ItemID.MUTATED_ZYGOMITE, NpcID.ZYGOMITE, NpcID.ZYGOMITE_474,
			NpcID.ANCIENT_ZYGOMITE),
	NECHRYAEL("Nechryael", ItemID.NECHRYAEL, NpcID.NECHRYAEL, NpcID.NECHRYAEL_11, NpcID.NECHRYARCH,
			NpcID.GREATER_NECHRYAEL),
	OGRES("Ogres", ItemID.ENSOULED_OGRE_HEAD, NpcID.OGRE, NpcID.OGRE_1153, NpcID.OGRE_2095, NpcID.OGRE_2096,
			NpcID.OGRE_2233, NpcID.OGRE_GUARD, NpcID.OGRE_GUARD_865, NpcID.OGRE_GUARD_4369, NpcID.OGRE_GUARD_4370,
			NpcID.OGRE_GUARD_4371, NpcID.OGRE_GUARD_4372, NpcID.OGRE_SHAMAN, NpcID.OGRE_SHAMAN_2418,
			NpcID.OGRE_SHAMAN_4382, NpcID.OGRE_SHAMAN_4383, NpcID.OGRE_SHAMAN_4384, NpcID.OGRE_SHAMAN_4386,
			NpcID.OGRE_SHAMAN_4387, NpcID.OGRE_SHAMAN_4388, NpcID.OGRE_SHAMAN_4389, NpcID.OGRE_SHAMAN_4390,
			NpcID.OGRE_SHAMAN_4391, NpcID.OGRE_SHAMAN_4392, NpcID.OGRE_SHAMAN_4393, NpcID.OGRE_SHAMAN_4393,
			NpcID.OGRE_SHAMAN_4394, NpcID.OGRE_SHAMAN_4395, NpcID.OGRE_SHAMAN_4396, NpcID.OGRE_SHAMAN_4571,
			NpcID.OGRESS_SHAMAN, NpcID.OGRESS_SHAMAN_7992, NpcID.OGRESS_WARRIOR, NpcID.OGRESS_WARRIOR_7990),
	OTHERWORLDLY_BEING("Otherworldly beings", ItemID.GHOSTLY_HOOD, NpcID.OTHERWORLDLY_BEING),
	PYREFIENDS("Pyrefiends", ItemID.PYREFIEND, NpcID.PYREFIEND, NpcID.PYREFIEND_434, NpcID.PYREFIEND_435,
			NpcID.PYREFIEND_436, NpcID.PYREFIEND_3139, NpcID.FLAMING_PYRELORD),
	RATS("Rats", ItemID.RATS_TAIL, NpcID.RAT, NpcID.RAT_1021, NpcID.RAT_1022, NpcID.RAT_2492, NpcID.RAT_2513,
			NpcID.RAT_2854, NpcID.RAT_2855, NpcID.RAT_4593, NpcID.RAT_4594, NpcID.RAT_4610, NpcID.RAT_4611,
			NpcID.RAT_4612, NpcID.RAT_4613, NpcID.RAT_4614, NpcID.RAT_4615, NpcID.RAT_4616, NpcID.RAT_4616,
			NpcID.RAT_4618, NpcID.RAT_4617, NpcID.ANGRY_GIANT_RAT_1063, NpcID.ANGRY_GIANT_RAT,
			NpcID.ANGRY_GIANT_RAT_1064, NpcID.ANGRY_GIANT_RAT_4689, NpcID.ANGRY_GIANT_RAT_4690, NpcID.BLESSED_GIANT_RAT,
			NpcID.BLESSED_GIANT_RAT_4535, NpcID.BRINE_RAT, NpcID.CRYPT_RAT, NpcID.GIANT_CRYPT_RAT,
			NpcID.GIANT_CRYPT_RAT_1681, NpcID.GIANT_CRYPT_RAT_1682, NpcID.DUNGEON_RAT, NpcID.DUNGEON_RAT_2866,
			NpcID.DUNGEON_RAT_2867, NpcID.DUNGEON_RAT_3607, NpcID.DUNGEON_RAT_3608, NpcID.DUNGEON_RAT_3609,
			NpcID.GIANT_RAT, NpcID.GIANT_RAT_2511, NpcID.GIANT_RAT_2512, NpcID.GIANT_RAT_2856, NpcID.GIANT_RAT_2857,
			NpcID.GIANT_RAT_2858, NpcID.GIANT_RAT_2859, NpcID.GIANT_RAT_2860, NpcID.GIANT_RAT_2861,
			NpcID.GIANT_RAT_2862, NpcID.GIANT_RAT_2863, NpcID.GIANT_RAT_2864, NpcID.GIANT_RAT_3313,
			NpcID.GIANT_RAT_3314, NpcID.GIANT_RAT_3315, NpcID.KING_RAT, NpcID.ZOMBIE_RAT, NpcID.ZOMBIE_RAT_3970,
			NpcID.ZOMBIE_RAT_3971),
	RED_DRAGONS("Red dragons", ItemID.BABY_RED_DRAGON, NpcID.RED_DRAGON, NpcID.RED_DRAGON_248, NpcID.RED_DRAGON_249,
			NpcID.RED_DRAGON_250, NpcID.RED_DRAGON_251, NpcID.RED_DRAGON_8075, NpcID.RED_DRAGON_8078,
			NpcID.RED_DRAGON_8079, NpcID.BRUTAL_RED_DRAGON_8087, NpcID.BABY_RED_DRAGON, NpcID.BRUTAL_RED_DRAGON,
			NpcID.BABY_DRAGON_244, NpcID.BABY_DRAGON_245, NpcID.BABY_DRAGON_246),
	ROCKSLUGS("Rockslugs", ItemID.ROCKSLUG, NpcID.ROCKSLUG, NpcID.ROCKSLUG_422, NpcID.GIANT_ROCKSLUG),
	RUNE_DRAGONS("Rune dragons", ItemID.RUNITE_BAR, NpcID.RUNE_DRAGON, NpcID.RUNE_DRAGON_8031, NpcID.RUNE_DRAGON_8091),
	SCORPIA("Scorpia", ItemID.SCORPIAS_OFFSPRING),
	CHAOS_DRUIDS("Chaos druids", ItemID.ELDER_CHAOS_HOOD, NpcID.CHAOS_DRUID, NpcID.CHAOS_DRUID_WARRIOR,
			NpcID.ELDER_CHAOS_DRUID, NpcID.ELDER_CHAOS_DRUID_7995, NpcID.REANIMATED_CHAOS_DRUID),
	BANDITS("Bandits", ItemID.BANDIT, NpcID.BANDIT, NpcID.BANDIT_691, NpcID.BANDIT_692, NpcID.BANDIT_693,
			NpcID.BANDIT_694, NpcID.BANDIT_695, NpcID.BANDIT_734, NpcID.BANDIT_735, NpcID.BANDIT_736, NpcID.BANDIT_737,
			NpcID.BANDIT_2892, NpcID.BANDIT_6605, NpcID.BANDIT_LEADER),
	MAGIC_AXES("Magic axes", ItemID.IRON_BATTLEAXE, NpcID.MAGIC_AXE, NpcID.MAGIC_AXE_7269),
	SCORPIONS("Scorpions", ItemID.ENSOULED_SCORPION_HEAD, NpcID.SCORPION, NpcID.SCORPION_2480, NpcID.SCORPION_3024,
			NpcID.SCORPION_5242),
	SEA_SNAKES("Sea snakes", ItemID.SNAKE_CORPSE, NpcID.SEA_SNAKE_HATCHLING, NpcID.SEA_SNAKE_YOUNG,
			NpcID.GIANT_SEA_SNAKE),
	SHADES("Shades", ItemID.SHADE_ROBE_TOP, NpcID.LOAR_SHADE, NpcID.LOAR_SHADOW, NpcID.PHRIN_SHADE, NpcID.PHRIN_SHADOW,
			NpcID.RIYL_SHADE, NpcID.RIYL_SHADOW, NpcID.RIYL_SHADOW_5631, NpcID.ASYN_SHADE, NpcID.ASYN_SHADOW,
			NpcID.ASYN_SHADOW_5632, NpcID.FIYR_SHADE, NpcID.FIYR_SHADOW),
	SHADOW_WARRIORS("Shadow warriors", ItemID.BLACK_FULL_HELM, NpcID.SHADOW_WARRIOR),
	SKELETAL_WYVERNS("Skeletal wyverns", ItemID.SKELETAL_WYVERN, NpcID.SKELETAL_WYVERN, NpcID.SKELETAL_WYVERN_466,
			NpcID.SKELETAL_WYVERN_467, NpcID.SKELETAL_WYVERN_468),
	SKELETONS("Skeletons", ItemID.SKELETON_GUARD, NpcID.SKELETON, NpcID.SKELETON_71, NpcID.SKELETON_72,
			NpcID.SKELETON_73, NpcID.SKELETON_74, NpcID.SKELETON_75, NpcID.SKELETON_76, NpcID.SKELETON_77,
			NpcID.SKELETON_78, NpcID.SKELETON_79, NpcID.SKELETON_80, NpcID.SKELETON_81, NpcID.SKELETON_82,
			NpcID.SKELETON_83, NpcID.SKELETON_130, NpcID.SKELETON_924, NpcID.SKELETON_1685, NpcID.SKELETON_1686,
			NpcID.SKELETON_1687, NpcID.SKELETON_1688, NpcID.SKELETON_1785, NpcID.SKELETON_2520, NpcID.SKELETON_2521,
			NpcID.SKELETON_2522, NpcID.SKELETON_2523, NpcID.SKELETON_2524, NpcID.SKELETON_2525, NpcID.SKELETON_2526,
			NpcID.SKELETON_3565, NpcID.SKELETON_3584, NpcID.SKELETON_3972, NpcID.SKELETON_3973, NpcID.SKELETON_3974,
			NpcID.SKELETON_5237, NpcID.SKELETON_6441, NpcID.SKELETON_6442, NpcID.SKELETON_6443, NpcID.SKELETON_6444,
			NpcID.SKELETON_6445, NpcID.SKELETON_6446, NpcID.SKELETON_6447, NpcID.SKELETON_6448, NpcID.SKELETON_6467,
			NpcID.SKELETON_6468, NpcID.SKELETON_7265, NpcID.SKELETON_8070, NpcID.SKELETON_8071, NpcID.SKELETON_8072,
			NpcID.SKELETON_8139, NpcID.SKELETON_8140, NpcID.SKELETON_BRUTE),
	SMOKE_DEVILS("Smoke devils", ItemID.SMOKE_DEVIL, NpcID.SMOKE_DEVIL, NpcID.SMOKE_DEVIL_6639, NpcID.SMOKE_DEVIL_6655,
			NpcID.SMOKE_DEVIL_8482, NpcID.SMOKE_DEVIL_8483, NpcID.NUCLEAR_SMOKE_DEVIL),
	SPIDERS("Spiders", ItemID.HUGE_SPIDER, NpcID.SPIDER, NpcID.SPIDER_3019, NpcID.SPIDER_3235, NpcID.SPIDER_4561,
			NpcID.SPIDER_5238, NpcID.SPIDER_5239, NpcID.SPIDER_8137, NpcID.SPIDER_8138, NpcID.GIANT_SPIDER_3017,
			NpcID.GIANT_SPIDER, NpcID.GIANT_SPIDER_3018, NpcID.JUNGLE_SPIDER, NpcID.JUNGLE_SPIDER_5243,
			NpcID.JUNGLE_SPIDER_6267, NpcID.JUNGLE_SPIDER_6271, NpcID.FEVER_SPIDER, NpcID.CRYPT_SPIDER),
	SPIRITUAL_CREATURES("Spiritual creatures", ItemID.DRAGON_BOOTS, NpcID.SPIRITUAL_MAGE, NpcID.SPIRITUAL_MAGE_2244,
			NpcID.SPIRITUAL_MAGE_3168, NpcID.SPIRITUAL_MAGE_3161, NpcID.SPIRITUAL_RANGER, NpcID.SPIRITUAL_RANGER_2242,
			NpcID.SPIRITUAL_RANGER_3160, NpcID.SPIRITUAL_RANGER_3167, NpcID.SPIRITUAL_WARRIOR,
			NpcID.SPIRITUAL_WARRIOR_2243, NpcID.SPIRITUAL_WARRIOR_3159, NpcID.SPIRITUAL_WARRIOR_3166),
	STEEL_DRAGONS("Steel dragons", ItemID.STEEL_DRAGON, NpcID.STEEL_DRAGON, NpcID.STEEL_DRAGON_274,
			NpcID.STEEL_DRAGON_275, NpcID.STEEL_DRAGON_7255, NpcID.STEEL_DRAGON_8086),
	SUQAHS("Suqahs", ItemID.SUQAH_TOOTH, NpcID.SUQAH, NpcID.SUQAH_788, NpcID.SUQAH_789, NpcID.SUQAH_790,
			NpcID.SUQAH_791, NpcID.SUQAH_792, NpcID.SUQAH_793),
	TERROR_DOGS("Terror dogs", ItemID.TERROR_DOG, NpcID.TERROR_DOG, NpcID.TERROR_DOG_6474),
	THERMONUCLEAR_SMOKE_DEVIL("Thermonuclear Smoke Devil", ItemID.PET_SMOKE_DEVIL),
	TROLLS("Trolls", ItemID.TROLL_GUARD, NpcID.TROLL, NpcID.TROLL_2833, NpcID.TROLL_8470, NpcID.TROLL_8471,
			NpcID.TROLL_8472, NpcID.TROLL_8473, NpcID.TROLL_GENERAL, NpcID.TROLL_GENERAL_4121, NpcID.TROLL_GENERAL_4122,
			NpcID.THROWER_TROLL, NpcID.THROWER_TROLL_932, NpcID.THROWER_TROLL_933, NpcID.THROWER_TROLL_934,
			NpcID.THROWER_TROLL_935, NpcID.THROWER_TROLL_4135, NpcID.THROWER_TROLL_4136, NpcID.THROWER_TROLL_4137,
			NpcID.THROWER_TROLL_4138, NpcID.THROWER_TROLL_4139, NpcID.ICE_TROLL, NpcID.ICE_TROLL_649,
			NpcID.ICE_TROLL_650, NpcID.ICE_TROLL_651, NpcID.ICE_TROLL_652, NpcID.ICE_TROLL_653, NpcID.ICE_TROLL_654,
			NpcID.ICE_TROLL_698, NpcID.ICE_TROLL_699, NpcID.ICE_TROLL_700, NpcID.ICE_TROLL_701, NpcID.ICE_TROLL_702,
			NpcID.ICE_TROLL_703, NpcID.ICE_TROLL_704, NpcID.ICE_TROLL_705, NpcID.ICE_TROLL_FEMALE,
			NpcID.ICE_TROLL_FEMALE_5825, NpcID.ICE_TROLL_FEMALE_5830, NpcID.ICE_TROLL_GRUNT, NpcID.ICE_TROLL_GRUNT_5826,
			NpcID.ICE_TROLL_GRUNT_5831, NpcID.ICE_TROLL_KING, NpcID.ICE_TROLL_KING_6356, NpcID.ICE_TROLL_KING_HARD,
			NpcID.ICE_TROLL_MALE, NpcID.ICE_TROLL_MALE_5829, NpcID.ICE_TROLL_MALE_5824, NpcID.ICE_TROLL_RUNT,
			NpcID.ICE_TROLL_RUNT_5823, NpcID.ICE_TROLL_RUNT_5828, NpcID.MOUNTAIN_TROLL, NpcID.MOUNTAIN_TROLL_937,
			NpcID.MOUNTAIN_TROLL_938, NpcID.MOUNTAIN_TROLL_939, NpcID.MOUNTAIN_TROLL_940, NpcID.MOUNTAIN_TROLL_941,
			NpcID.MOUNTAIN_TROLL_942, NpcID.MOUNTAIN_TROLL_4143, NpcID.RIVER_TROLL, NpcID.RIVER_TROLL_6733,
			NpcID.RIVER_TROLL_6734, NpcID.RIVER_TROLL_6735, NpcID.RIVER_TROLL_6736, NpcID.RIVER_TROLL_6737, NpcID.STICK,
			NpcID.ROCK),
	TUROTH("Turoth", ItemID.TUROTH, NpcID.TUROTH, NpcID.TUROTH_427, NpcID.TUROTH_428, NpcID.TUROTH_429,
			NpcID.TUROTH_430, NpcID.TUROTH_431, NpcID.TUROTH_432),
	TZHAAR("Tzhaar", ItemID.ENSOULED_TZHAAR_HEAD, NpcID.TZHAARHUR, NpcID.TZHAARHUR_2162, NpcID.TZHAARHUR_2163,
			NpcID.TZHAARHUR_2164, NpcID.TZHAARHUR_2165, NpcID.TZHAARHUR_2166, NpcID.TZHAARHUR_7682,
			NpcID.TZHAARHUR_7683, NpcID.TZHAARHUR_7684, NpcID.TZHAARHUR_7685, NpcID.TZHAARHUR_7686,
			NpcID.TZHAARHUR_7687, NpcID.TZHAARKET, NpcID.TZHAARKET_2174, NpcID.TZHAARKET_2175, NpcID.TZHAARKET_2176,
			NpcID.TZHAARKET_2177, NpcID.TZHAARKET_2178, NpcID.TZHAARKET_2179, NpcID.TZHAARKET_2186,
			NpcID.TZHAARKET_2187, NpcID.TZHAARKET_7679, NpcID.TZHAARMEJ, NpcID.TZHAARMEJ_2155, NpcID.TZHAARMEJ_2156,
			NpcID.TZHAARMEJ_2157, NpcID.TZHAARMEJ_2158, NpcID.TZHAARMEJ_2159, NpcID.TZHAARMEJ_2160,
			NpcID.TZHAARMEJ_7652, NpcID.TZHAARXIL, NpcID.TZHAARXIL_2168, NpcID.TZHAARXIL_2169, NpcID.TZHAARXIL_2170,
			NpcID.TZHAARXIL_2171, NpcID.TZHAARXIL_2172),
	VAMPIRES("Vampires", ItemID.STAKE, NpcID.VAMPYRE_JUVENILE, NpcID.VAMPYRE_JUVENILE_3693, NpcID.VAMPYRE_JUVENILE_3696,
			NpcID.VAMPYRE_JUVENILE_3697, NpcID.VAMPYRE_JUVENILE_4436, NpcID.VAMPYRE_JUVENILE_4437,
			NpcID.VAMPYRE_JUVENILE_4438, NpcID.VAMPYRE_JUVENILE_4439, NpcID.VAMPYRE_JUVENILE_8326,
			NpcID.VAMPYRE_JUVENILE_8327, NpcID.VAMPYRE_JUVINATE, NpcID.VAMPYRE_JUVINATE_3691,
			NpcID.VAMPYRE_JUVINATE_3694, NpcID.VAMPYRE_JUVINATE_3695, NpcID.VAMPYRE_JUVINATE_3698,
			NpcID.VAMPYRE_JUVINATE_3699, NpcID.VAMPYRE_JUVINATE_3700, NpcID.VAMPYRE_JUVINATE_4427,
			NpcID.VAMPYRE_JUVINATE_4428, NpcID.VAMPYRE_JUVINATE_4429, NpcID.VAMPYRE_JUVINATE_4430,
			NpcID.VAMPYRE_JUVINATE_4432, NpcID.VAMPYRE_JUVINATE_4433, NpcID.VAMPYRE_JUVINATE_4434,
			NpcID.VAMPYRE_JUVINATE_4442, NpcID.VAMPYRE_JUVINATE_4481, NpcID.VAMPYRE_JUVINATE_4443,
			NpcID.VAMPYRE_JUVINATE_4482, NpcID.VAMPYRE_JUVINATE_4486, NpcID.VAMPYRE_JUVINATE_4487,
			NpcID.VAMPYRE_JUVINATE_5634, NpcID.VAMPYRE_JUVINATE_5635, NpcID.VAMPYRE_JUVINATE_5636,
			NpcID.VAMPYRE_JUVINATE_5637, NpcID.VAMPYRE_JUVINATE_5638, NpcID.VAMPYRE_JUVINATE_5639, NpcID.VAMPYRIC_HOUND,
			NpcID.FERAL_VAMPYRE, NpcID.FERAL_VAMPYRE_3234, NpcID.FERAL_VAMPYRE_3237, NpcID.FERAL_VAMPYRE_3239,
			NpcID.FERAL_VAMPYRE_3707, NpcID.FERAL_VAMPYRE_3708, NpcID.FERAL_VAMPYRE_4431, NpcID.FERAL_VAMPYRE_5640,
			NpcID.FERAL_VAMPYRE_5641, NpcID.FERAL_VAMPYRE_5642, NpcID.FERAL_VAMPYRE_6529, NpcID.FLYING_FEMALE_VAMPIRE,
			NpcID.FLYING_FEMALE_VAMPIRE_3765, NpcID.FLYING_FEMALE_VAMPIRE_3766, NpcID.FLYING_FEMALE_VAMPIRE_3767,
			NpcID.FORMER_VAMPYRE, NpcID.FORMER_VAMPYRE_3702, NpcID.FORMER_VAMPYRE_3703, NpcID.FORMER_VAMPYRE_3704,
			NpcID.FORMER_VAMPYRE_3705, NpcID.FORMER_VAMPYRE_3706),
	VENENATIS("Venenatis", ItemID.VENENATIS_SPIDERLING),
	VETION("Vet'ion", ItemID.VETION_JR),
	VORKATH("Vorkath", ItemID.VORKI),
	WALL_BEASTS("Wall beasts", ItemID.SWAMP_WALLBEAST, NpcID.WALL_BEAST),
	WATERFIENDS("Waterfiends", ItemID.WATER_ORB, NpcID.WATERFIEND, NpcID.WATERFIEND),
	WEREWOLVES("Werewolves", ItemID.WOLFBANE, NpcID.WEREWOLF, NpcID.WEREWOLF_2594, NpcID.WEREWOLF_2595,
			NpcID.WEREWOLF_2596, NpcID.WEREWOLF_2597, NpcID.WEREWOLF_2598, NpcID.WEREWOLF_2599, NpcID.WEREWOLF_2600,
			NpcID.WEREWOLF_2601, NpcID.WEREWOLF_2602, NpcID.WEREWOLF_2603, NpcID.WEREWOLF_2604, NpcID.WEREWOLF_2605,
			NpcID.WEREWOLF_2606, NpcID.WEREWOLF_2607, NpcID.WEREWOLF_2608, NpcID.WEREWOLF_2609, NpcID.WEREWOLF_2610,
			NpcID.WEREWOLF_2611, NpcID.WEREWOLF_2612, NpcID.WEREWOLF_3135, NpcID.WEREWOLF_3136, NpcID.WEREWOLF_5928),
	WOLVES("Wolves", ItemID.GREY_WOLF_FUR, NpcID.WOLF, NpcID.WOLF_110, NpcID.WOLF_116, NpcID.WOLF_117, NpcID.WOLF_231,
			NpcID.WOLF_2490, NpcID.WOLF_2491, NpcID.WOLF_3912, NpcID.BIG_WOLF, NpcID.DESERT_WOLF, NpcID.DIRE_WOLF,
			NpcID.ICE_WOLF, NpcID.JUNGLE_WOLF, NpcID.WHITE_WOLF, NpcID.BIG_WOLF_115, NpcID.DESERT_WOLF_4650,
			NpcID.DESERT_WOLF_4651, NpcID.WHITE_WOLF_108, NpcID.ICE_WOLF_646, NpcID.ICE_WOLF_647, NpcID.ICE_WOLF_710,
			NpcID.ICE_WOLF_711, NpcID.ICE_WOLF_712, NpcID.ICE_WOLF_713, NpcID.ICE_WOLF_714, NpcID.ICE_WOLF_715),
	ZILYANA("Zilyana", ItemID.PET_ZILYANA),
	ZOMBIES("Zombies", ItemID.ZOMBIE_HEAD, NpcID.ZOMBIE, NpcID.ZOMBIE_27, NpcID.ZOMBIE_28, NpcID.ZOMBIE_29,
			NpcID.ZOMBIE_30, NpcID.ZOMBIE_31, NpcID.ZOMBIE_32, NpcID.ZOMBIE_33, NpcID.ZOMBIE_34, NpcID.ZOMBIE_35,
			NpcID.ZOMBIE_36, NpcID.ZOMBIE_37, NpcID.ZOMBIE_38, NpcID.ZOMBIE_39, NpcID.ZOMBIE_40, NpcID.ZOMBIE_41,
			NpcID.ZOMBIE_42, NpcID.ZOMBIE_43, NpcID.ZOMBIE_44, NpcID.ZOMBIE_45, NpcID.ZOMBIE_46, NpcID.ZOMBIE_47,
			NpcID.ZOMBIE_48, NpcID.ZOMBIE_49, NpcID.ZOMBIE_50, NpcID.ZOMBIE_51, NpcID.ZOMBIE_52, NpcID.ZOMBIE_53,
			NpcID.ZOMBIE_54, NpcID.ZOMBIE_55, NpcID.ZOMBIE_56, NpcID.ZOMBIE_57, NpcID.ZOMBIE_58, NpcID.ZOMBIE_59,
			NpcID.ZOMBIE_60, NpcID.ZOMBIE_61, NpcID.ZOMBIE_62, NpcID.ZOMBIE_63, NpcID.ZOMBIE_64, NpcID.ZOMBIE_65,
			NpcID.ZOMBIE_66, NpcID.ZOMBIE_67, NpcID.ZOMBIE_68, NpcID.ZOMBIE_880, NpcID.ZOMBIE_1784, NpcID.ZOMBIE_2501,
			NpcID.ZOMBIE_2502, NpcID.ZOMBIE_2503, NpcID.ZOMBIE_2504, NpcID.ZOMBIE_2505, NpcID.ZOMBIE_2506,
			NpcID.ZOMBIE_2507, NpcID.ZOMBIE_2508, NpcID.ZOMBIE_2509, NpcID.ZOMBIE_3980, NpcID.ZOMBIE_3981,
			NpcID.ZOMBIE_5507, NpcID.ZOMBIE_5568, NpcID.ZOMBIE_5571, NpcID.ZOMBIE_5574, NpcID.ZOMBIE_5583,
			NpcID.ZOMBIE_5647, NpcID.ZOMBIE_6449, NpcID.ZOMBIE_6450, NpcID.ZOMBIE_6451, NpcID.ZOMBIE_6452,
			NpcID.ZOMBIE_6453, NpcID.ZOMBIE_6454, NpcID.ZOMBIE_6455, NpcID.ZOMBIE_6456, NpcID.ZOMBIE_6457,
			NpcID.ZOMBIE_6458, NpcID.ZOMBIE_6459, NpcID.ZOMBIE_6460, NpcID.ZOMBIE_6461, NpcID.ZOMBIE_6462,
			NpcID.ZOMBIE_6463, NpcID.ZOMBIE_6464, NpcID.ZOMBIE_6465, NpcID.ZOMBIE_6466, NpcID.ZOMBIE_6596,
			NpcID.ZOMBIE_6597, NpcID.ZOMBIE_6598, NpcID.ZOMBIE_6741, NpcID.ZOMBIE_7485, NpcID.ZOMBIE_7486,
			NpcID.ZOMBIE_7487, NpcID.ZOMBIE_7488, NpcID.ZOMBIE_8067, NpcID.ZOMBIE_8068, NpcID.ZOMBIE_8069,
			NpcID.UNDEAD_COW, NpcID.UNDEAD_COW_4421, NpcID.UNDEAD_CHICKEN),
	ZULRAH("Zulrah", ItemID.PET_SNAKELING),
	ZUK("TzKal-Zuk", ItemID.TZREKZUK);
	//</editor-fold>

	private static final Map<String, Task> tasks = new HashMap<>();

	private final String name;
	private final int itemSpriteId;
	private final int[] targetIds;

	static
	{
		for (Task task : values())
		{
			tasks.put(task.getName().toLowerCase(), task);
		}
	}

	Task(String name, int itemSpriteId, int... targetIds)
	{
		Preconditions.checkArgument(itemSpriteId >= 0);
		this.name = name;
		this.itemSpriteId = itemSpriteId;
		this.targetIds = targetIds;
	}

	static Task getTask(String taskName)
	{
		return tasks.get(taskName.toLowerCase());
	}
}
