/*
 * Copyright (c) 2018, Joshua Filby <joshua@filby.me>
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
package net.runelite.client.plugins.virtuallevels;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(
	keyName = "virtuallevels",
	name = "Virtual Levels",
	description = "Configuration for the virtual levels plugin."
)
public interface VirtualLevelsConfig extends Config
{

	@ConfigItem(
		position = 0,
		keyName = "skillTab",
		name = "Enable virtual levels on skill tab",
		description = "Configures if the skill tab should show virtual levels."
	)
	default boolean showOnSkillTab()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "skillTabTotal",
		name = "Enable virtual total level on skill tab",
		description = "Configures if the skill tab should show your virtual total level."
	)
	default boolean showTotalOnSkillTab()
	{
		return false;
	}

	@ConfigItem(
		position = 2,
		keyName = "orbs",
		name = "Enable virtual levels on Xp Globes",
		description = "Configures if the Xp Globes should go up to 126."
	)
	default boolean showOnSkillOrb()
	{
		return true;
	}

	@ConfigItem(
		position = 3,
		keyName = "hiscoresPanel",
		name = "Enable virtual levels on HiScores",
		description = "Configures if the HiScores panel shows virtual levels."
	)
	default boolean showOnHiScoresPanel()
	{
		return true;
	}

}
