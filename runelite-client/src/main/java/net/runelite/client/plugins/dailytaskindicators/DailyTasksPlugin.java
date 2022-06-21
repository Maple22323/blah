/*
 * Copyright (c) 2018, Infinitay <https://github.com/Infinitay>
 * Copyright (c) 2018-2019, Shaun Dreclin <https://github.com/ShaunDreclin>
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

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.VarClientInt;
import net.runelite.api.VarPlayer;
import net.runelite.api.Varbits;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.vars.AccountType;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Daily Task Indicator",
	description = "Show chat notifications for daily tasks upon login"
)
public class DailyTasksPlugin extends Plugin
{
	private static final int ONE_DAY = 86400000;

	private static final String HERB_BOX_MESSAGE = "You have herb boxes waiting to be collected at NMZ.";
	private static final int HERB_BOX_MAX = 15;
	private static final int HERB_BOX_COST = 9500;
	private static final String STAVES_MESSAGE = "You have battlestaves waiting to be collected from Zaff.";
	private static final String ESSENCE_MESSAGE = "You have essence waiting to be collected from Wizard Cromperty.";
	private static final String RUNES_MESSAGE = "You have random runes waiting to be collected from Lundail.";
	private static final String SAND_MESSAGE = "You have sand waiting to be collected from Bert.";
	private static final int SAND_QUEST_COMPLETE = 160;
	private static final String FLAX_MESSAGE = "You have bowstrings waiting to be converted from flax from the Flax keeper.";
	private static final String ARROWS_MESSAGE = "You have ogre arrows waiting to be collected from Rantz.";
	private static final String BONEMEAL_MESSAGE = "You have bonemeal and slime waiting to be collected from Robin.";
	private static final int BONEMEAL_PER_DIARY = 13;
	private static final String DYNAMITE_MESSAGE = "You have dynamite waiting to be collected from Thirus.";

	@Inject
	private Client client;

	@Inject
	private DailyTasksConfig config;

	@Inject
	private ChatMessageManager chatMessageManager;

	private long lastReset;
	private boolean loggingIn;

	@Provides
	DailyTasksConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DailyTasksConfig.class);
	}

	@Override
	public void startUp()
	{
		loggingIn = true;
	}

	@Override
	public void shutDown()
	{
		lastReset = 0L;
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGING_IN)
		{
			loggingIn = true;
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		long currentTime = System.currentTimeMillis();
		boolean dailyReset = !loggingIn && currentTime - lastReset > ONE_DAY;

		if (dailyReset) {
			checkConfigDaily(currentTime);
		} else {
			checkConfigLogin(currentTime);
		}
	}

	private void checkConfigDaily(long currentTime)
	{
		if (client.getVar(VarClientInt.MEMBERSHIP_STATUS) == 1)
		{
			// Round down to the nearest day
			lastReset = (long) Math.floor(currentTime / ONE_DAY) * ONE_DAY;
			loggingIn = false;

			if (config.showHerbBoxes())
			{
				checkHerbBoxesDaily();
			}

			if (config.showStaves())
			{
				checkStavesDaily();
			}

			if (config.showEssence())
			{
				checkEssenceDaily();
			}

			if (config.showRunes())
			{
				checkRunesDaily();
			}

			if (config.showSand())
			{
				checkSandDaily();
			}

			if (config.showFlax())
			{
				checkFlaxDaily();
			}

			if (config.showArrows())
			{
				checkArrowsDaily();
			}

			if (config.showBonemeal())
			{
				checkBonemealDaily();
			}

			if (config.showDynamite())
			{
				checkDynamiteDaily();
			}
		}
	}

	private void checkConfigLogin(long currentTime)
	{
		if (loggingIn && client.getVar(VarClientInt.MEMBERSHIP_STATUS) == 1)
		{
			// Round down to the nearest day
			lastReset = (long) Math.floor(currentTime / ONE_DAY) * ONE_DAY;
			loggingIn = false;

			if (config.showHerbBoxes())
			{
				checkHerbBoxesLogin();
			}

			if (config.showStaves())
			{
				checkStavesLogin();
			}

			if (config.showEssence())
			{
				checkEssenceLogin();
			}

			if (config.showRunes())
			{
				checkRunesLogin();
			}

			if (config.showSand())
			{
				checkSandLogin();
			}

			if (config.showFlax())
			{
				checkFlaxLogin();
			}

			if (config.showArrows())
			{
				checkArrowsLogin();
			}

			if (config.showBonemeal())
			{
				checkBonemealLogin();
			}

			if (config.showDynamite())
			{
				checkDynamiteLogin();
			}
		}
	}

	private void checkHerbBoxesDaily()
	{
		if (client.getAccountType() == AccountType.NORMAL
			&& client.getVar(VarPlayer.NMZ_REWARD_POINTS) >= HERB_BOX_COST)
		{
			sendChatMessage(HERB_BOX_MESSAGE);
		}
	}

	private void checkHerbBoxesLogin()
	{
		if (client.getVarbitValue(Varbits.DAILY_HERB_BOXES_COLLECTED) < HERB_BOX_MAX)
		{
			checkHerbBoxesDaily();
		}
	}

	private void checkStavesDaily()
	{
		if (client.getVarbitValue(Varbits.DIARY_VARROCK_EASY) == 1)
		{
			sendChatMessage(STAVES_MESSAGE);
		}
	}

	private void checkStavesLogin()
	{
		if (client.getVarbitValue(Varbits.DAILY_STAVES_COLLECTED) == 0)
		{
			checkStavesDaily();
		}
	}

	private void checkEssenceDaily()
	{
		if (client.getVarbitValue(Varbits.DIARY_ARDOUGNE_MEDIUM) == 1)
		{
			sendChatMessage(ESSENCE_MESSAGE);
		}
	}

	private void checkEssenceLogin()
	{
		if (client.getVarbitValue(Varbits.DAILY_ESSENCE_COLLECTED) == 0)
		{
			checkEssenceDaily();
		}
	}

	private void checkRunesDaily()
	{
		if (client.getVarbitValue(Varbits.DIARY_WILDERNESS_EASY) == 1)
		{
			sendChatMessage(RUNES_MESSAGE);
		}
	}

	private void checkRunesLogin()
	{
		if (client.getVarbitValue(Varbits.DAILY_RUNES_COLLECTED) == 0)
		{
			checkRunesDaily();
		}
	}

	private void checkSandDaily()
	{
		if (client.getAccountType() != AccountType.ULTIMATE_IRONMAN
			&& client.getVarbitValue(Varbits.QUEST_THE_HAND_IN_THE_SAND) >= SAND_QUEST_COMPLETE)
		{
			sendChatMessage(SAND_MESSAGE);
		}
	}

	private void checkSandLogin()
	{
		if (client.getVarbitValue(Varbits.DAILY_SAND_COLLECTED) == 0)
		{
			checkSandDaily();
		}
	}

	private void checkFlaxDaily()
	{
		if (client.getVarbitValue(Varbits.DIARY_KANDARIN_EASY) == 1)
		{
			sendChatMessage(FLAX_MESSAGE);
		}
	}

	private void checkFlaxLogin()
	{
		if (client.getVarbitValue(Varbits.DAILY_FLAX_STATE) == 0)
		{
			checkFlaxDaily();
		}
	}

	private void checkArrowsDaily()
	{
		if (client.getVarbitValue(Varbits.DIARY_WESTERN_EASY) == 1)
		{
			sendChatMessage(ARROWS_MESSAGE);
		}
	}

	private void checkArrowsLogin()
	{
		if (client.getVarbitValue(Varbits.DAILY_ARROWS_STATE) == 0)
		{
			checkArrowsDaily();
		}
	}

	private void checkBonemealDaily()
	{
		if (client.getVarbitValue(Varbits.DIARY_MORYTANIA_MEDIUM) == 1)
		{
			sendChatMessage(BONEMEAL_MESSAGE);
		}
	}

	private void checkBonemealLogin()
	{
		if (client.getVarbitValue(Varbits.DIARY_MORYTANIA_MEDIUM) == 1)
		{
			int collected = client.getVarbitValue(Varbits.DAILY_BONEMEAL_STATE);
			int max = BONEMEAL_PER_DIARY;
			if (client.getVarbitValue(Varbits.DIARY_MORYTANIA_HARD) == 1)
			{
				max += BONEMEAL_PER_DIARY;
				if (client.getVarbitValue(Varbits.DIARY_MORYTANIA_ELITE) == 1)
				{
					max += BONEMEAL_PER_DIARY;
				}
			}
			if (collected < max)
			{
				sendChatMessage(BONEMEAL_MESSAGE);
			}
		}
	}

	private void checkDynamiteDaily()
	{
		if (client.getVarbitValue(Varbits.DIARY_KOUREND_MEDIUM) == 1)
		{
			sendChatMessage(DYNAMITE_MESSAGE);
		}
	}

	private void checkDynamiteLogin()
	{
		if (client.getVarbitValue(Varbits.DAILY_DYNAMITE_COLLECTED) == 0)
		{
			checkDynamiteDaily();
		}
	}

	private void sendChatMessage(String chatMessage)
	{
		final String message = new ChatMessageBuilder()
			.append(ChatColorType.HIGHLIGHT)
			.append(chatMessage)
			.build();

		chatMessageManager.queue(
			QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage(message)
				.build());
	}
}
