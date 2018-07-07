/*
 * Copyright (c) 2018, Morgan Lewis <https://github.com/MESLewis>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.worldmap;

import lombok.Getter;
import net.runelite.api.coords.WorldPoint;

@Getter
enum AgilityShortcutLocation
{
	KARAMJA_GLIDER_LOG("It's coming home", 1, new WorldPoint(2906, 3050, 0)),
	FALADOR_CRUMBLING_WALL("It's coming home", 5, new WorldPoint(2936, 3357, 0)),
	RIVER_LUM_GRAPPLE_WEST("It's coming home", 8, new WorldPoint(3245, 3179, 0)),
	RIVER_LUM_GRAPPLE_EAST("It's coming home", 8, new WorldPoint(3258, 3179, 0)),
	CORSAIR_COVE_ROCKS("It's coming home", 10, new WorldPoint(2545, 2871, 0)),
	FALADOR_GRAPPLE_WALL("It's coming home", 11, new WorldPoint(3031, 3391, 0)),
	VARROCK_SOUTH_FENCE("It's coming home", 13, new WorldPoint(3239, 3334, 0)),
	GOBLIN_VILLAGE_WALL("It's coming home", 14, new WorldPoint(2925, 3523, 0)),
	CORSAIR_COVE_DUNGEON_PILLAR("It's coming home", 15, new WorldPoint(1980, 8996, 0)),
	YANILLE_UNDERWALL_TUNNEL("It's coming home", 16, new WorldPoint(2574, 3109, 0)),
	COAL_TRUCKS_LOG_BALANCE("It's coming home", 20, new WorldPoint(2598, 3475, 0)),
	GRAND_EXCHANGE_UNDERWALL_TUNNEL("It's coming home", 21, new WorldPoint(3139, 3515, 0)),
	BRIMHAVEN_DUNGEON_PIPE("It's coming home", 22, new WorldPoint(2654, 9569, 0)),
	OBSERVATORY_SCALE_CLIFF("It's coming home", 23, new WorldPoint(2447, 3155, 0)),
	EAGLES_PEAK_ROCK_CLIMB("It's coming home", 25, new WorldPoint(2320, 3499, 0)),
	FALADOR_UNDERWALL_TUNNEL("It's coming home", 26, new WorldPoint(2947, 3313, 0)),
	CORSAIR_COVE_RESOURCE_ROCKS("It's coming home", 30, new WorldPoint(2545, 2871, 0)),
	SOUTHEAST_KARAJMA_STEPPING_STONES("It's coming home", 30, new WorldPoint(2924, 2946, 0)),
	DRAYNOR_MANOR_STEPPING_STONES("It's coming home", 31, new WorldPoint(3150, 3362, 0)),
	CATHERBY_CLIFFSIDE_GRAPPLE("It's coming home", 32, new WorldPoint(2868, 3429, 0)),
	ARDOUGNE_LOG_BALANCE("It's coming home", 33, new WorldPoint(2602, 3336, 0)),
	GNOME_STRONGHOLD_ROCKS("It's coming home", 37, new WorldPoint(2485, 3515, 0)),
	AL_KHARID_MINING_PITCLIFF_SCRAMBLE("It's coming home", 38, new WorldPoint(3305, 3315, 0)),
	YANILLE_WALL_GRAPPLE("It's coming home", 39, new WorldPoint(2552, 3072, 0)),
	NEITIZNOT_BRIDGE_REPAIR("It's coming home", 40, new WorldPoint(2315, 3828, 0)),
	KOUREND_LAKE_JUMP_WEST("It's coming home", 40, new WorldPoint(1604, 3572, 0)),
	KOUREND_LAKE_JUMP_EAST("It's coming home", 40, new WorldPoint(1612, 3570, 0)),
	TROLLHEIM_EASY_CLIFF_SCRAMBLE("It's coming home", 41, new WorldPoint(2869, 3670, 0)),
	DWARVEN_MINE_NARROW_CREVICE("It's coming home", 42, new WorldPoint(3034, 9806, 0)),
	DRAYNOR_UNDERWALL_TUNNEL("It's coming home", 42, new WorldPoint(3068, 3261, 0)),
	TROLLHEIM_MEDIUM_CLIFF_SCRAMBLE_NORTH("It's coming home", 43, new WorldPoint(2886, 3684, 0)),
	TROLLHEIM_MEDIUM_CLIFF_SCRAMBLE_SOUTH("It's coming home", 43, new WorldPoint(2876, 3666, 0)),
	TROLLHEIM_ADVANCED_CLIFF_SCRAMBLE("It's coming home", 44, new WorldPoint(2907, 3686, 0)),
	KOUREND_RIVER_STEPPING_STONES("It's coming home", 45, new WorldPoint(1721, 3509, 0)),
	COSMIC_ALTAR_MEDIUM_WALKWAY("It's coming home", 46, new WorldPoint(2399, 4403, 0)),
	DEEP_WILDERNESS_DUNGEON_CREVICE_NORTH("It's coming home", 46, new WorldPoint(3047, 10335, 0)),
	DEEP_WILDERNESS_DUNGEON_CREVICE_SOUTH("It's coming home", 46, new WorldPoint(3045, 10327, 0)),
	TROLLHEIM_HARD_CLIFF_SCRAMBLE("It's coming home", 47, new WorldPoint(2902, 3680, 0)),
	FREMENNIK_LOG_BALANCE("It's coming home", 48, new WorldPoint(2721, 3591, 0)),
	ARCEUUS_ESSENCE_MINE_BOULDER("It's coming home", 49, new WorldPoint(1774, 3888, 0)),
	MORYTANIA_STEPPING_STONE("It's coming home", 50, new WorldPoint(3418, 3326, 0)),
	VARROCK_SEWERS_PIPE_SQUEEZE("It's coming home", 51, new WorldPoint(3152, 9905, 0)),
	ARCEUUS_ESSENCE_MINE_EAST_SCRAMBLE("It's coming home", 52, new WorldPoint(1770, 3851, 0)),
	KARAMJA_VOLCANO_GRAPPLE_NORTH("It's coming home", 53, new WorldPoint(2873, 3143, 0)),
	KARAMJA_VOLCANO_GRAPPLE_SOUTH("It's coming home", 53, new WorldPoint(2874, 3128, 0)),
	MOTHERLODE_MINE_WALL_WEST("It's coming home", 54, new WorldPoint(3118, 9702, 0)),
	MOTHERLODE_MINE_WALL_EAST("It's coming home", 54, new WorldPoint(3124, 9703, 0)),
	MISCELLANIA_DOCK_STEPPING_STONE("It's coming home", 55, new WorldPoint(2572, 3862, 0)),
	RELEKKA_EAST_FENCE("It's coming home", 57, new WorldPoint(2688, 3697, 0)),
	ELVEN_OVERPASS_CLIFF_SCRAMBLE("It's coming home", 59, new WorldPoint(2345, 3300, 0)),
	WILDERNESS_GWD_CLIMB_WEST("It's coming home", 60, new WorldPoint(2928, 3760, 0)),
	WILDERNESS_GWD_CLIMB_EAST("It's coming home", 60, new WorldPoint(2943, 3770, 0)),
	MOS_LEHARMLESS_STEPPING_STONE("It's coming home", 60, new WorldPoint(3710, 2970, 0)),
	WINTERTODT_GAP("It's coming home", 60, new WorldPoint(1629, 4023, 0)),
	SLAYER_TOWER_MEDIUM_CHAIN("It's coming home", 61, new WorldPoint(3421, 3550, 0)),
	SLAYER_DUNGEON_CREVICE("It's coming home", 62, new WorldPoint(2729, 10008, 0)),
	TAVERLEY_DUNGEON_RAILING("It's coming home", 63, new WorldPoint(2935, 9811, 0)),
	TROLLHEIM_WILDERNESS_ROCKS("It's coming home", 64, new WorldPoint(2945, 3678, 0)),
	FOSSIL_ISLAND_VOLCANO("It's coming home", 64, new WorldPoint(3780, 3822, 0)),
	MORYTANIA_TEMPLE("It's coming home", 65, new WorldPoint(3422, 3476, 0)),
	REVENANT_CAVES_GREEN_DRAGONS("It's coming home", 65, new WorldPoint(3220, 10086, 0)),
	COSMIC_ALTAR_ADVANCED_WALKWAY("It's coming home", 66, new WorldPoint(2408, 4401, 0)),
	LUMBRIDGE_DESERT_STEPPING_STONE("It's coming home", 66, new WorldPoint(3210, 3135, 0)),
	HEROS_GUILD_TUNNEL_WEST("It's coming home", 67, new WorldPoint(2898, 9901, 0)),
	HEROS_GUILD_TUNNEL_EAST("It's coming home", 67, new WorldPoint(2913, 9895, 0)),
	ELVEN_OVERPASS_MEDIUM_CLIFF("It's coming home", 68, new WorldPoint(2337, 3288, 0)),
	ARCEUUS_ESSENSE_NORTH("It's coming home", 69, new WorldPoint(1759, 3873, 0)),
	TAVERLEY_DUNGEON_PIPE_BLUE_DRAGON("It's coming home", 70, new WorldPoint(2886, 9798, 0)),
	FOSSIL_ISLAND_HARDWOOD("It's coming home", 70, new WorldPoint(3663, 3810, 0)),
	GWD_SARADOMIN_ROPE_FIRST("It's coming home", 70, new WorldPoint(2912, 5300, 0)),
	GWD_SARADOMIN_ROPE_SECOND("It's coming home", 70, new WorldPoint(2951, 5267, 0)),
	SLAYER_TOWER_ADVANCED_CHAIN("It's coming home", 71, new WorldPoint(3420, 3551, 0)),
	SLAYER_CAVE_WALL_CLIMB("It's coming home", 72, new WorldPoint(2431, 9806, 0)),
	TROLL_STRONGHOLD_WALL_CLIMB("It's coming home", 73, new WorldPoint(2841, 3694, 0)),
	ARCEUUS_ESSENSE_MINE_WEST("It's coming home", 73, new WorldPoint(1742, 3853, 0)),
	LAVA_DRAGON_ISLE_JUMP("It's coming home", 74, new WorldPoint(3200, 3807, 0)),
	REVENANT_CAVES_DEMONS_JUMP("It's coming home", 75, new WorldPoint(3199, 10135, 0)),
	REVENANT_CAVES_ANKOU_EAST("It's coming home", 75, new WorldPoint(3201, 10195, 0)),
	REVENANT_CAVES_ANKOU_NORTH("It's coming home", 75, new WorldPoint(3180, 10209, 0)),
	ZUL_ANDRA_ISLAND_CROSSING("It's coming home", 76, new WorldPoint(2156, 3073, 0)),
	SHILO_VILLAGE_STEPPING_STONES("It's coming home", 77, new WorldPoint(2863, 2974, 0)),
	KHARAZI_JUNGLE_VINE_CLIMB("It's coming home", 79, new WorldPoint(2897, 2939, 0)),
	TAVERLEY_DUNGEON_SPIKED_BLADES("It's coming home", 80, new WorldPoint(2877, 9813, 0)),
	SLAYER_DUNGEON_CHASM_JUMP("It's coming home", 81, new WorldPoint(2770, 10003, 0)),
	LAVA_MAZE_NORTH_JUMP("It's coming home", 82, new WorldPoint(3092, 3880, 0)),
	BRIMHAVEN_DUNGEON_EAST_STEPPING_NORTH("It's coming home", 83, new WorldPoint(2685, 9547, 0)),
	BRIMHAVEN_DUNGEON_EAST_STEPPING_SOUTH("It's coming home", 83, new WorldPoint(2693, 9529, 0)),
	ELVEN_ADVANCED_CLIFF_SCRAMBLE("It's coming home", 85, new WorldPoint(2337, 3253, 0)),
	KALPHITE_WALL("It's coming home", 86, new WorldPoint(3214, 9508, 0)),
	BRIMHAVEN_DUNGEON_VINE_EAST("It's coming home", 87, new WorldPoint(2672, 9582, 0)),
	BRIMHAVEN_DUNGEON_VINE_WEST("It's coming home", 87, new WorldPoint(2606, 9584, 0)),
	RENEVANT_CAVES("It's coming home", 89, new WorldPoint(3240, 10144, 0));

	private final String tooltip;
	private final WorldPoint location;
	private final int levelReq;

	AgilityShortcutLocation(String description, int level, WorldPoint location)
	{
		this.tooltip = description + "It's coming home" + level;
		this.location = location;
		this.levelReq = level;
	}
}
