/*
 * Copyright (c) 2018 Charlie Waters
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
package net.runelite.client.plugins.xptracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(
	keyName = "xptracker",
	name = "XP Tracker",
	description = "Configuration for the XP tracker plugin"
)
public interface XpTrackerConfig extends Config
{
	@ConfigItem(
		keyName = "resetOnGoal",
		name = "Clear goal when reached",
		description = "Automatically clear skill goals when goal reached"
	)
	default boolean clearOnGoal()
	{
		return false;
	}

	@ConfigItem(
		keyName = "showLevelProgress",
		name = "Show level progress with goal",
		description = "Second progress bar in tracker shows level progress"
	)
	default boolean showLevelProgress()
	{
		return true;
	}

	@ConfigItem(
		keyName = "goalData",
		name = "",
		description = "",
		hidden = true
	)
	default String goalData()
	{
		return "{}";
	}

	@ConfigItem(
		keyName = "goalData",
		name = "",
		description = ""
	)
	void goalData(String str);
}
