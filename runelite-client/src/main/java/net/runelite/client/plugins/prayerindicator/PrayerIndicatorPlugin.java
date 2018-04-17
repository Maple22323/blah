/*
 * Copyright (c) 2018 Nicholas I
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

package net.runelite.client.plugins.prayerindicator;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.infobox.Counter;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

@PluginDescriptor (
	name = "Prayer Indicator"
)
public class PrayerIndicatorPlugin extends Plugin
{
	
	@Inject
	private PrayerIndicatorOverlay overlay;
	
	@Inject
	private PrayerIndicatorConfig config;
	
	@Inject
	private ItemManager itemManager;
	
	@Inject
	private InfoBoxManager infoBoxManager;
	
	@Getter (AccessLevel.PACKAGE)
	private Counter indicator;
	
	@Override
	public Overlay getOverlay ()
	{
		return overlay;
	}
	
	@Provides
	PrayerIndicatorConfig getConfig (ConfigManager configManager)
	{
		return configManager.getConfig(PrayerIndicatorConfig.class);
	}
	
	@Subscribe
	public void onGameStateChanged (GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN && indicator == null)
			indicator = new Counter(itemManager.getImage(ItemID.PRAYER_POTION4), this, "");
	}
	
	@Subscribe
	public void onTick (GameTick tick)
	{
		overlay.onTick();
	}
	
	@Subscribe
	public void onConfigChanged (ConfigChanged event)
	{
		if (!config.inInfoBox())
			if (infoBoxManager.getInfoBoxes().contains (indicator))
				infoBoxManager.removeInfoBox(indicator);
	}
	
}
