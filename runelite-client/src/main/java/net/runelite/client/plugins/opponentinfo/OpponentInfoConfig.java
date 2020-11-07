/*
 * Copyright (c) 2018, Jordan Atwood <jordan.atwood423@gmail.com>
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
package net.runelite.client.plugins.opponentinfo;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("opponentinfo")
public interface OpponentInfoConfig extends Config
{
	@ConfigItem(
		keyName = "lookupOnInteraction",
		name = "Lookup players on interaction",
		description = "Display a combat stat comparison panel on player interaction. (follow, trade, challenge, attack, etc.)",
		position = 0
	)
	default boolean lookupOnInteraction()
	{
		return false;
	}

	@ConfigItem(
		keyName = "hitpointsDisplayStyle",
		name = "Hitpoints display style",
		description = "Show opponent's hitpoints as a value (if known), percentage, or both",
		position = 1
	)
	default HitpointsDisplayStyle hitpointsDisplayStyle()
	{
		return HitpointsDisplayStyle.HITPOINTS;
	}

	@ConfigItem(
		keyName = "showOpponentsInMenu",
		name = "Show opponents in menu",
		description = "Marks opponents names in the menu which you are attacking or are attacking you (NPC only)",
		position = 3
	)
	default boolean showOpponentsInMenu()
	{
		return false;
	}

	@ConfigItem(
		keyName = "showVanillaPercentages",
		name = "Show HP % on the Vanilla HP Bar",
		description = "Adds a percentage value next to the health value of the vanilla HP Bar",
		position = 4
	)

	default boolean showVanillaPercentages()
	{
		return false;
	}

	@ConfigItem(
		keyName = "mergeVanillaHealthBar",
		name = "Merge Vanilla Health Bar Overlay",
		description = "Hides the vanilla health bar overlay, but adds the info to the existing opponent info",
		position = 5
	)

	default boolean mergeVanillaHealthBar()
	{
		return false;
	}

	@ConfigItem(
		keyName = "vanillaHealthBarOverride",
		name = "Use vanilla Health Bar at these bosses",
		description = "If merge vanilla health bar overlay is checked, this can allow you to still use the vanilla HP bar on certain bosses, e.g. for vanguards the vanilla HP bar is quite useful. Use '*' if you want to use the vanilla HP bar wherever possible.",
		position = 6
	)
	default String vanillaHealthBarOverride()
	{
		return "vanguard*";
	}
}


