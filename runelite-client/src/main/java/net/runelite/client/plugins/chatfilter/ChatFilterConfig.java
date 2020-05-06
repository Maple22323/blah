/*
 * Copyright (c) 2018, Magic fTail
 * Copyright (c) 2019, osrs-music-map <osrs-music-map@users.noreply.github.com>
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
package net.runelite.client.plugins.chatfilter;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

import java.awt.Color;

@ConfigGroup("chatfilter")
public interface ChatFilterConfig extends Config
{
	@ConfigItem(
		keyName = "filterType",
		name = "Filter type",
		description = "Configures how the messages are filtered",
		position = 1
	)
	default ChatFilterType filterType()
	{
		return ChatFilterType.CENSOR_WORDS;
	}

	@ConfigItem(
		keyName = "filteredWords",
		name = "Filtered Words",
		description = "List of filtered words, separated by commas",
		position = 2
	)
	default String filteredWords()
	{
		return "";
	}

	@ConfigItem(
		keyName = "filteredRegex",
		name = "Filtered Regex",
		description = "List of regular expressions to filter, one per line",
		position = 3
	)
	default String filteredRegex()
	{
		return "";
	}

	@ConfigItem(
		keyName = "filteredNames",
		name = "Filtered Names",
		description = "List of filtered names, one per line. Accepts regular expressions",
		position = 4
	)
	default String filteredNames()
	{
		return "";
	}

	@ConfigItem(
		keyName = "filterFriends",
		name = "Filter Friends",
		description = "Filter your friends' messages",
		position = 5
	)
	default boolean filterFriends()
	{
		return false;
	}

	@ConfigItem(
		keyName = "filterClan",
		name = "Filter Clan Chat Members",
		description = "Filter your clan chat members' messages",
		position = 6
	)
	default boolean filterClan()
	{
		return false;
	}

	@ConfigItem(
		keyName = "filterLogin",
		name = "Filter Logged In/Out Messages",
		description = "Filter your private chat to remove logged in/out messages",
		position = 7
	)
	default boolean filterLogin()
	{
		return false;
	}

	@ConfigItem(
			keyName = "chatCountColor",
			name = "Default count color",
			description = "Configures the color for the count of game messages",
			position = 8
	)
	default Color chatCountColor()
	{
		return new Color(0xA00080);
	}

	@ConfigItem(
			keyName = "consolidatePlayerChat",
			name = "Consolidate Player Chat",
			description = "Configures consolidation of player chat",
			position = 9
	)
	default boolean consolidatePlayerChat()
	{
		return false;
	}

	@ConfigItem(
			keyName = "maxRepeatedPublicChats",
			name = "Max repeated public chats",
			description = "Blocks messages if repeated this many times. 0 is off",
			position = 10
	)
	default int maxRepeatedPublicChats()
	{
		return 5;
	}

	@ConfigItem(
			keyName = "maxTrackedChats",
			name = "Max Tracked Chats",
			description = "Configures the maximum number of chats that are tracked. Suggested 500",
			position = 11
	)
	@Range(min = 100)
	default int maxTrackedChats()
	{
		return 500;
	}
}
