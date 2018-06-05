/*
 * Copyright (c) 2018, Infinitay <https://github.com/Infinitay>
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
package net.runelite.client.plugins.dailytaskindicators;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(
	keyName = "dailytaskindicators",
	name = "Daily Task Indicators",
	description = "Configuration for Daily Task Indicators plugin"
)
public interface DailyTasksConfig extends Config
{
	@ConfigItem(
		keyName = "showHerbBoxes",
		name = "Show Claimable Herb Boxes",
		description = "Configures whether or not to show a message for daily herb boxes",
		position = 1
	)
	default boolean showHerbBoxes()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showStaves",
		name = "Show Claimable Staves",
		description = "Configures whether or not to show a message for daily staves",
		position = 2
	)
	default boolean showStaves()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showEssence",
		name = "Show Claimable Essence",
		description = "Configures whether or not to show a message for daily pure essence",
		position = 3
	)
	default boolean showEssence()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showSand",
		name = "Show Claimable Sand",
		description = "Configures whether or not to show a message for daily buckets of sand",
		position = 4
	)
	default boolean showSand()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showEcto",
		name = "Show Claimable Bonemeal and Slime",
		description = "Configures whether or not to show a message for daily bonemeal and slime",
		position = 5
	)
	default boolean showEcto()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showBowstring",
		name = "Show Claimable Bowstring",
		description = "Configures whether or not to show a message for daily flax to bowstring",
		position = 6
	)
	default boolean showBowstring()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showRunes",
		name = "Show Claimable Runes",
		description = "Configures whether or not to show a message for daily runes",
		position = 7
	)
	default boolean showRunes()
	{
		return true;
	}
}
