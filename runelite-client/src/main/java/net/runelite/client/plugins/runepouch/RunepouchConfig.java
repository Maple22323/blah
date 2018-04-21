/*
 * Copyright (c) 2017, Tyler <https://github.com/tylerthardy>
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
package net.runelite.client.plugins.runepouch;

import java.awt.Color;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(
	keyName = "runepouch",
	name = "Rune Pouch",
	description = "Configuration for the Runepouch plugin"
)
public interface RunepouchConfig extends Config
{

	@ConfigItem(
		keyName = "showinbank",
		name = "Show in bank",
		description = "Show the contents of the Rune Pouch in the bank",
		position = 0
	)
	default boolean showInBank()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showininventory",
		name = "Show in inventory",
		description = "Show the contents of the Rune Pouch in the inventory",
		position = 1
	)
	default boolean showInInventory()
	{
		return true;
	}

	@ConfigItem(
		keyName = "runeicons",
		name = "Show Rune Icons",
		description = "Show the rune icons next to the number of runes in pouch",
		position = 2
	)
	default boolean showIcons()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showOnlyOnHover",
		name = "Show only on hover",
		description = "Show the runes only when hovered",
		position = 3
	)
	default boolean showOnlyOnHover()
	{
		return false;
	}

	@ConfigItem(
		keyName = "fontcolor",
		name = "Font Color",
		description = "Color of the font for the number of runes in pouch",
		position = 4
	)
	default Color fontColor()
	{
		return Color.yellow;
	}


}
