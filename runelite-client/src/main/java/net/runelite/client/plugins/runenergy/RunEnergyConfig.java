/*
 * Copyright (c) 2018, Sean Dewar <https://github.com/seandewar>
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
package net.runelite.client.plugins.runenergy;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("runenergy")
public interface RunEnergyConfig extends Config
{
	@ConfigItem(
		keyName = "ringOfEnduranceCharges",
		name = "Ring of endurance charges",
		description = "Gives the number of stamina charges remaining on the Ring of endurance.",
		hidden = true
	)
	default int ringOfEnduranceCharges()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "ringOfEnduranceCharges",
		name = "",
		description = ""
	)
	void ringOfEnduranceCharges(int charges);

	@ConfigItem(
		keyName = "enableRingMessages",
		name = "Ring of endurance messages",
		description = "Sends a message asking you to check/charge your equipped Ring of endurance when it has less than 500 charges."
	)
	default boolean enableRingMessages()
	{
		return true;
	}

	@ConfigItem(
		keyName = "replaceOrbText",
		name = "Replace orb text with run time left",
		description = "Show the remaining run time (in seconds) next in the energy orb."
	)
	default boolean replaceOrbText()
	{
		return false;
	}
}
