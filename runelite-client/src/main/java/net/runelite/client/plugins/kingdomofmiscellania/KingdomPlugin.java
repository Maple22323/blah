/*
 * Copyright (c) 2018, Infinitay <https://github.com/Infinitay>
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
package net.runelite.client.plugins.kingdomofmiscellania;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import java.time.Duration;
import java.time.Instant;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import static net.runelite.api.ItemID.TEAK_CHEST;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.runelite.api.VarPlayer;
import net.runelite.api.Varbits;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.QuantityFormatter;

@PluginDescriptor(
	name = "Kingdom of Miscellania",
	description = "Show amount of approval when inside Miscellania",
	tags = {"favor", "favour", "managing", "overlay", "approval", "coffer"},
	enabledByDefault = false
)
@Slf4j
public class KingdomPlugin extends Plugin
{
	private static final ImmutableSet<Integer> KINGDOM_REGION = ImmutableSet.of(10044, 10300);
	private static final String CONFIG_LAST_CHANGED_KEY = "lastChanged";
	private static final String CONFIG_COFFER_KEY = "coffer";
	private static final String CONFIG_APPROVAL_KEY = "approval";
	private static final String CHAT_MESSAGE_FORMAT = "Your Kingdom of Miscellania approval is %d%%, and your coffer has %s coins.";
	private static final String CHAT_MESSAGE_FORMAT_COLLECTION = "You last collected from Miscellania %s days ago.";
	private static final int MAX_WITHDRAWAL_BASE = 50_000;
	private static final int MAX_WITHDRAWAL_ROYAL_TROUBLE = 75_000;
	private static final float APPROVAL_DECREMENT_BASE = 0.025f;
	private static final float APPROVAL_DECREMENT_ROYAL_TROUBLE = 0.010f;
	static final int MAX_APPROVAL = 127;

	private boolean loggingIn;

	@Inject
	private Client client;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Inject
	private ItemManager itemManager;

	@Inject
	private KingdomConfig config;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ChatMessageManager chatMessageManager;

	private KingdomCounter counter;

	@Override
	protected void shutDown() throws Exception
	{
		removeKingdomInfobox();
	}

	@Provides
	KingdomConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(KingdomConfig.class);
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged event)
	{
		final int coffer = client.getVar(Varbits.KINGDOM_COFFER);
		final int approval = client.getVar(Varbits.KINGDOM_APPROVAL);

		if (client.getGameState() == GameState.LOGGED_IN
			&& isThroneOfMiscellaniaCompleted()
			&& (isInKingdom() || coffer > 0 && approval > 0)
			&& (getCoffer() != coffer || getApproval() != approval))
		{
			setLastChanged(Instant.now());
			setCoffer(coffer);
			setApproval(approval);
		}

		processInfobox();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			processInfobox();
		}
		else if (event.getGameState() == GameState.LOGGING_IN)
		{
			loggingIn = true;
		}
	}

	@Subscribe
	public void onGameTick(GameTick gameTick)
	{
		if (loggingIn)
		{
			loggingIn = false;
			createNotification();
		}
	}

	private void processInfobox()
	{
		if (client.getGameState() == GameState.LOGGED_IN && isThroneOfMiscellaniaCompleted() && isInKingdom())
		{
			addKingdomInfobox();
		}
		else
		{
			removeKingdomInfobox();
		}
	}

	private void createNotification()
	{
		if (!config.shouldSendNotifications() || !isThroneOfMiscellaniaCompleted())
		{
			return;
		}

		if (getLastChanged() == null)
		{
			log.debug("Kingdom Of Miscellania values not yet set. Visit Miscellania to automatically set values.");
			return;
		}

		Instant lastChanged = getLastChanged();
		int lastCoffer = getCoffer();
		int lastApproval = getApproval();
		int estimatedCoffer = estimateCoffer(lastChanged, lastCoffer);
		int estimatedApproval = estimateApproval(lastChanged, lastApproval);
		if (estimatedCoffer < config.getCofferThreshold() || getApprovalPercent(estimatedApproval) < config.getApprovalThreshold())
		{
			sendChatMessage(String.format(
				CHAT_MESSAGE_FORMAT,
				getApprovalPercent(estimatedApproval),
				QuantityFormatter.quantityToStackSize(estimatedCoffer)));
		}

		int lastCollected = getCollected(lastChanged);
		if (config.shouldSendCollectedNotifications())
		{
			sendChatMessage(String.format(
				CHAT_MESSAGE_FORMAT_COLLECTION,
				lastCollected));
		}
	}

	private void addKingdomInfobox()
	{
		if (counter == null)
		{
			counter = new KingdomCounter(itemManager.getImage(TEAK_CHEST), this);
			infoBoxManager.addInfoBox(counter);
			log.debug("Added Kingdom Infobox");
		}
	}

	private void removeKingdomInfobox()
	{
		if (counter != null)
		{
			infoBoxManager.removeInfoBox(counter);
			counter = null;
			log.debug("Removed Kingdom Infobox");
		}
	}

	private int estimateCoffer(Instant lastChanged, int lastCoffer)
	{
		int daysSince = (int) Duration.between(lastChanged, Instant.now()).toDays();
		int maxDailyWithdrawal = isRoyalTroubleCompleted() ? MAX_WITHDRAWAL_ROYAL_TROUBLE : MAX_WITHDRAWAL_BASE;
		int maxDailyThreshold = maxDailyWithdrawal * 10;

		for (int i = 0; i < daysSince; i++)
		{
			lastCoffer -= (lastCoffer > maxDailyThreshold) ? maxDailyWithdrawal : lastCoffer / 10;
		}
		return lastCoffer;
	}

	private int estimateApproval(Instant lastChanged, int lastApproval)
	{
		int daysSince = (int) Duration.between(lastChanged, Instant.now()).toDays();
		float dailyPercentage = isRoyalTroubleCompleted() ? APPROVAL_DECREMENT_ROYAL_TROUBLE : APPROVAL_DECREMENT_BASE;

		lastApproval -= (int) (daysSince * dailyPercentage * MAX_APPROVAL);
		return Math.max(lastApproval, 0);
	}

	private boolean isInKingdom()
	{
		return client.getLocalPlayer() != null
			&& KINGDOM_REGION.contains(client.getLocalPlayer().getWorldLocation().getRegionID());
	}

	private boolean isThroneOfMiscellaniaCompleted()
	{
		return client.getVar(VarPlayer.THRONE_OF_MISCELLANIA) > 0;
	}

	private boolean isRoyalTroubleCompleted()
	{
		return Quest.ROYAL_TROUBLE.getState(client) == QuestState.FINISHED;
	}

	static int getApprovalPercent(int approval)
	{
		return (approval * 100) / MAX_APPROVAL;
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

	private Instant getLastChanged()
	{
		return configManager.getRSProfileConfiguration(KingdomConfig.CONFIG_GROUP_NAME, CONFIG_LAST_CHANGED_KEY, Instant.class);
	}

	private void setLastChanged(Instant lastChanged)
	{
		configManager.setRSProfileConfiguration(KingdomConfig.CONFIG_GROUP_NAME, CONFIG_LAST_CHANGED_KEY, lastChanged);
	}

	int getCoffer()
	{
		Integer coffer = configManager.getRSProfileConfiguration(KingdomConfig.CONFIG_GROUP_NAME, CONFIG_COFFER_KEY, int.class);
		return coffer == null ? 0 : coffer;
	}

	private void setCoffer(int coffer)
	{
		configManager.setRSProfileConfiguration(KingdomConfig.CONFIG_GROUP_NAME, CONFIG_COFFER_KEY, coffer);
	}

	int getApproval()
	{
		Integer approval = configManager.getRSProfileConfiguration(KingdomConfig.CONFIG_GROUP_NAME, CONFIG_APPROVAL_KEY, int.class);
		return approval == null ? 0 : approval;
	}

	private void setApproval(int approval)
	{
		configManager.setRSProfileConfiguration(KingdomConfig.CONFIG_GROUP_NAME, CONFIG_APPROVAL_KEY, approval);
	}

	private int getCollected(Instant lastChanged) {
		return (int) Duration.between(lastChanged, Instant.now()).toDays();
	}
}
