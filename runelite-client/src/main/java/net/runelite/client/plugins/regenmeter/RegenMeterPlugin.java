/*
 * Copyright (c) 2018 Abex
 * Copyright (c) 2018, Zimaya <https://github.com/Zimaya>
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
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
package net.runelite.client.plugins.regenmeter;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Prayer;
import net.runelite.api.Setting;
import net.runelite.api.Skill;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.Overlay;

@PluginDescriptor(name = "Regeneration Meter")
public class RegenMeterPlugin extends Plugin
{
	private static final int SPEC_REGEN_TICKS = 51;
	private static final int NORMAL_HP_REGEN_TICKS = 100;

	@Inject
	private RegenMeterOverlay overlay;

	@Inject
	private Client client;

	@Inject
	RegenMeterConfig config;

	@Inject
	Notifier notifier;


	private int ticksSinceHPRegen;

	private boolean wasRapidHeal;

	private boolean alreadyNotified = false;

	@Getter
	private double hitpointsPercentage;


	private int ticksSinceSpecRegen;

	@Getter
	private double specialPercentage;

	@Override
	public Overlay getOverlay()
	{
		return overlay;
	}

	@Provides
	RegenMeterConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RegenMeterConfig.class);
	}

	@Subscribe
	private void onGameStateChanged(GameStateChanged ev)
	{
		if (ev.getGameState() == GameState.HOPPING || ev.getGameState() == GameState.LOGIN_SCREEN)
		{
			ticksSinceHPRegen = -2; // For some reason this makes this accurate
			ticksSinceSpecRegen = 0;
			resetNotification();
		}
	}

	@Subscribe
	private void onVarbitChanged(VarbitChanged ev)
	{
		boolean isRapidHeal = client.isPrayerActive(Prayer.RAPID_HEAL);
		if (wasRapidHeal != isRapidHeal)
		{
			ticksSinceHPRegen = 0;
			resetNotification();
		}
		wasRapidHeal = isRapidHeal;
	}

	@Subscribe
	private void onTick(GameTick event)
	{
		if (client.getSetting(Setting.SPECIAL_ATTACK_PERCENT) == 1000)
		{
			// The recharge doesn't tick when at 100%
			ticksSinceSpecRegen = 0;
		}
		else
		{
			ticksSinceSpecRegen = (ticksSinceSpecRegen + 1) % SPEC_REGEN_TICKS;
		}
		specialPercentage = ticksSinceSpecRegen / (double) SPEC_REGEN_TICKS;


		int ticksPerHPRegen = NORMAL_HP_REGEN_TICKS;
		if (client.isPrayerActive(Prayer.RAPID_HEAL))
		{
			ticksPerHPRegen /= 2;
		}

		ticksSinceHPRegen = (ticksSinceHPRegen + 1) % ticksPerHPRegen;
		hitpointsPercentage = ticksSinceHPRegen / (double) ticksPerHPRegen;

		int currentHP = client.getBoostedSkillLevel(Skill.HITPOINTS);
		int maxHP = client.getRealSkillLevel(Skill.HITPOINTS);
		if (currentHP == maxHP && !config.showWhenNoChange())
		{
			hitpointsPercentage = 0;
		}
		else if (currentHP > maxHP)
		{
			// Show it going down
			hitpointsPercentage = 1 - hitpointsPercentage;
		}

		if (config.showNotification())
		{
			if (ticksSinceHPRegen == 0)
			{
				resetNotification();
			}

			double timeUntilRegen = (1 - hitpointsPercentage) * ticksPerHPRegen * 0.6d;
			if (timeUntilRegen <= config.notificationEarliness())
			{
				showNotification();
			}
		}
	}

	private void showNotification()
	{
		if (!config.showNotification() || alreadyNotified)
		{
			return;
		}

		notifier.notify("Your health is about to regenerate!");

		alreadyNotified = true;
	}

	private void resetNotification()
	{
		alreadyNotified = false;
	}
}
