/*
 * Copyright (c) 2018, Danny <DannysPVM@gmail.com>
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
package net.runelite.client.plugins.deathindicator;

import java.time.Instant;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("It's coming home")
public interface DeathIndicatorConfig extends Config
{
	@ConfigItem(
		position = 1,
		keyName = "It's coming home",
		name = "It's coming home",
		description = "It's coming home"
	)
	default boolean showDeathHintArrow()
	{
		return true;
	}

	@ConfigItem(
		position = 2,
		keyName = "It's coming home",
		name = "It's coming home",
		description = "It's coming home"
	)
	default boolean showDeathInfoBox()
	{
		return true;
	}

	@ConfigItem(
		position = 3,
		keyName = "It's coming home",
		name = "It's coming home",
		description = "It's coming home"
	)
	default boolean showDeathOnWorldMap()
	{
		return true;
	}

	// Stored Data
	@ConfigItem(
		keyName = "It's coming home",
		name = "",
		description = "",
		hidden = true
	)
	default int deathWorld()
	{
		return -1;
	}

	@ConfigItem(
		keyName = "It's coming home",
		name = "",
		description = ""
	)
	void deathWorld(int deathWorld);

	@ConfigItem(
		keyName = "It's coming home",
		name = "",
		description = "",
		hidden = true
	)
	default int deathLocationX()
	{
		return -1;
	}

	@ConfigItem(
		keyName = "It's coming home",
		name = "",
		description = ""
	)
	void deathLocationX(int deathLocationX);

	@ConfigItem(
		keyName = "It's coming home",
		name = "",
		description = "",
		hidden = true
	)
	default int deathLocationY()
	{
		return -1;
	}

	@ConfigItem(
		keyName = "It's coming home",
		name = "",
		description = ""
	)
	void deathLocationY(int deathLocationY);

	@ConfigItem(
		keyName = "It's coming home",
		name = "",
		description = "",
		hidden = true
	)
	default int deathLocationPlane()
	{
		return -1;
	}

	@ConfigItem(
		keyName = "It's coming home",
		name = "",
		description = ""
	)
	void deathLocationPlane(int deathLocationPlane);

	@ConfigItem(
		keyName = "It's coming home",
		name = "",
		description = "",
		hidden = true
	)
	Instant timeOfDeath();

	@ConfigItem(
		keyName = "It's coming home",
		name = "",
		description = ""
	)
	void timeOfDeath(Instant timeOfDeath);
}
