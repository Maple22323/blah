/*
 * Copyright (c) 2018, Seth <Sethtroll3@gmail.com>
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
package net.runelite.client.plugins.poh;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("poh")
public interface PohConfig extends Config
{
	@ConfigItem(
		position = 0,
		keyName = "showPortals",
		name = "Show Portals",
		description = "Configures whether to display teleport portals"
	)
	default boolean showPortals()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "showAltar",
		name = "Show Altar",
		description = "Configures whether or not the altar is displayed"
	)
	default boolean showAltar()
	{
		return true;
	}

	@ConfigItem(
		position = 2,
		keyName = "showGlory",
		name = "Show Glory mount",
		description = "Configures whether or not the mounted glory is displayed"
	)
	default boolean showGlory()
	{
		return true;
	}

	@ConfigItem(
		position = 3,
		keyName = "showPools",
		name = "Show Pools",
		description = "Configures whether or not the pools are displayed"
	)
	default boolean showPools()
	{
		return true;
	}

	@ConfigItem(
		position = 4,
		keyName = "showRepairStand",
		name = "Show Repair stand",
		description = "Configures whether or not the repair stand is displayed"
	)
	default boolean showRepairStand()
	{
		return true;
	}

	@ConfigItem(
		position = 5,
		keyName = "showExitPortal",
		name = "Show Exit portal",
		description = "Configures whether or not the exit portal is displayed"
	)
	default boolean showExitPortal()
	{
		return true;
	}

	@ConfigItem(
		position = 6,
		keyName = "showBurner",
		name = "Show Unlit/Lit burner",
		description = "Configures whether or not unlit/lit burners are displayed"
	)
	default boolean showBurner()
	{
		return false;
	}

	@ConfigItem(
		position = 7,
		keyName = "showSpellbook",
		name = "Show Spellbook altar",
		description = "Configures whether or not the Spellbook altar is displayed"
	)
	default boolean showSpellbook()
	{
		return true;
	}

	@ConfigItem(
		position = 8,
		keyName = "showJewelleryBox",
		name = "Show Jewellery Box",
		description = "Configures whether or not the Jewllery box is displayed"
	)
	default boolean showJewelleryBox()
	{
		return true;
	}

	@ConfigItem(
		position = 9,
		keyName = "showMagicTravel",
		name = "Show Fairy/ Spirit Tree/ Obelisk",
		description = "Configures whether or not the Fairy ring, Spirit tree or Obelisk is displayed"
	)
	default boolean showMagicTravel()
	{
		return true;
	}

	@ConfigItem(
		position = 10,
		keyName = "showBurnerTime",
		name = "Show Burner Time",
		description = "Configures whether or not the burner time should be displayed"
	)
	default boolean showBurnerTime()
	{
		return false;
	}
}
