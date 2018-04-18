/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
package net.runelite.client.plugins.playerindicators;

import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import java.awt.Color;
import java.util.Collection;
import javax.inject.Inject;

import net.runelite.api.*;

import static net.runelite.api.ClanMemberRank.UNRANKED;
import static net.runelite.api.MenuAction.FOLLOW;
import static net.runelite.api.MenuAction.ITEM_USE_ON_PLAYER;
import static net.runelite.api.MenuAction.PLAYER_EIGTH_OPTION;
import static net.runelite.api.MenuAction.PLAYER_FIFTH_OPTION;
import static net.runelite.api.MenuAction.PLAYER_FIRST_OPTION;
import static net.runelite.api.MenuAction.PLAYER_FOURTH_OPTION;
import static net.runelite.api.MenuAction.PLAYER_SECOND_OPTION;
import static net.runelite.api.MenuAction.PLAYER_SEVENTH_OPTION;
import static net.runelite.api.MenuAction.PLAYER_SIXTH_OPTION;
import static net.runelite.api.MenuAction.PLAYER_THIRD_OPTION;
import static net.runelite.api.MenuAction.SPELL_CAST_ON_PLAYER;
import static net.runelite.api.MenuAction.TRADE;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ClanManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.Overlay;

@PluginDescriptor(
	name = "Player Indicators"
)
public class PlayerIndicatorsPlugin extends Plugin
{
	@Inject
	private PlayerIndicatorsConfig config;

	@Inject
	private PlayerIndicatorsOverlay playerIndicatorsOverlay;

	@Inject
	private PlayerIndicatorsMinimapOverlay playerIndicatorsMinimapOverlay;

	@Inject
	private Client client;

	@Inject
	private ClanManager clanManager;

	@Provides
	PlayerIndicatorsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PlayerIndicatorsConfig.class);
	}

	@Override
	public Collection<Overlay> getOverlays()
	{
		return Sets.newHashSet(playerIndicatorsOverlay, playerIndicatorsMinimapOverlay);
	}

	@Subscribe
	public void onMenuEntryAdd(MenuEntryAdded menuEntryAdded)
	{
		int type = menuEntryAdded.getType();

		if (type >= 2000)
		{
			type -= 2000;
		}

		int identifier = menuEntryAdded.getIdentifier();
		if (type == FOLLOW.getId() || type == TRADE.getId()
			|| type == SPELL_CAST_ON_PLAYER.getId() || type == ITEM_USE_ON_PLAYER.getId()
			|| type == PLAYER_FIRST_OPTION.getId()
			|| type == PLAYER_SECOND_OPTION.getId()
			|| type == PLAYER_THIRD_OPTION.getId()
			|| type == PLAYER_FOURTH_OPTION.getId()
			|| type == PLAYER_FIFTH_OPTION.getId()
			|| type == PLAYER_SIXTH_OPTION.getId()
			|| type == PLAYER_SEVENTH_OPTION.getId()
			|| type == PLAYER_EIGTH_OPTION.getId())
		{
			final Player localPlayer = client.getLocalPlayer();
			Player[] players = client.getCachedPlayers();
			Player player = null;

			if (identifier >= 0 && identifier < players.length)
			{
				player = players[identifier];
			}

			if (player == null)
			{
				return;
			}

			int image = -1;
			Color color = null;

			if (config.drawFriendNames() && player.isFriend())
			{
				color = config.getFriendNameColor();
			}
			else if (config.drawClanMemberNames() && player.isClanMember())
			{
				color = config.getClanMemberColor();

				ClanMemberRank rank = clanManager.getRank(player.getName());
				if (rank != UNRANKED)
				{
					image = clanManager.getIconNumber(rank);
				}
			}
			else if (config.drawTeamMemberNames() && player.getTeam() > 0 && localPlayer.getTeam() == player.getTeam())
			{
				color = config.getTeamMemberColor();
			}
			else if (config.drawNonClanMemberNames() && !player.isClanMember())
			{
				color = config.getNonClanMemberColor();
			}
			else if (config.drawAttackableNames() && isWithinLevelRange(player.getCombatLevel()))
			{
				color = config.getAttackableNameColor();
			}

			if (image != -1 || color != null)
			{
				MenuEntry[] menuEntries = client.getMenuEntries();
				MenuEntry lastEntry = menuEntries[menuEntries.length - 1];

				if (color != null && config.colorPlayerMenu())
				{
					// strip out existing <col...
					String target = lastEntry.getTarget();
					int idx = target.indexOf('>');
					if (idx != -1)
					{
						target = target.substring(idx + 1);
					}

					lastEntry.setTarget("<col=" + Integer.toHexString(color.getRGB() & 0xFFFFFF) + ">" + target);
				}

				if (image != -1 && config.clanMenuIcons())
				{
					lastEntry.setTarget("<img=" + image + ">" + lastEntry.getTarget());
				}

				client.setMenuEntries(menuEntries);
			}
		}
	}

	public boolean isWithinLevelRange(int playerCombatLevel)
	{
		Widget levelRangeWidget = client.getWidget(WidgetInfo.COMBAT_AREA_LEVEL_RANGE);
		Widget wildernessLevelWidget = client.getWidget(WidgetInfo.COMBAT_AREA_WILDERNESS_LEVEL);

		int localPlayerLevel = client.getLocalPlayer().getCombatLevel();
		int lowerLevelBound = localPlayerLevel - 15;
		int upperLevelBound = localPlayerLevel + 15;

		if (levelRangeWidget == null && wildernessLevelWidget == null)
		{
			return false;
		}
		if (!levelRangeWidget.isHidden() && !wildernessLevelWidget.isHidden())
		{
			int wildernessLevel = calculateWildernessLevel(client.getLocalPlayer().getWorldLocation());
			lowerLevelBound = localPlayerLevel - wildernessLevel - 15;
			upperLevelBound = localPlayerLevel + wildernessLevel + 15;
			return (playerCombatLevel >= lowerLevelBound && playerCombatLevel <= upperLevelBound);
		}
		else if (levelRangeWidget.isHidden() && !wildernessLevelWidget.isHidden())
		{
			int wildernessLevel = calculateWildernessLevel(client.getLocalPlayer().getWorldLocation());
			lowerLevelBound = localPlayerLevel - wildernessLevel;
			upperLevelBound = localPlayerLevel + wildernessLevel;
			return (playerCombatLevel >= lowerLevelBound && playerCombatLevel <= upperLevelBound);
		}
		else
		{
			return (playerCombatLevel >= lowerLevelBound && playerCombatLevel <= upperLevelBound);
		}
	}

	public static int calculateWildernessLevel(WorldPoint userLocation)
	{
		int wildernessLevel = 0;
		if (WorldPoint.isInZone(new WorldPoint(2944, 3520, 0), new WorldPoint(3391, 4351, 3), userLocation))
		{
			wildernessLevel = ((userLocation.getY() - (55 * 64)) / 8) + 1;
		}
		else if (WorldPoint.isInZone(new WorldPoint(3008, 10112, 0), new WorldPoint(3071, 10175, 3), userLocation))
		{
			wildernessLevel = ((userLocation.getY() - (155 * 64)) / 8) - 1;
		}
		else if (WorldPoint.isInZone(new WorldPoint(2944, 9920, 0), new WorldPoint(3391, 10879, 3), userLocation))
		{
			wildernessLevel = ((userLocation.getY() - (155 * 64)) / 8) + 1;
		}
		return wildernessLevel;
	}
}
