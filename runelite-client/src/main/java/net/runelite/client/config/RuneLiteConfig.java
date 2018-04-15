/*
 * Copyright (c) 2017, Tyler <https://github.com/tylerthardy>
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
package net.runelite.client.config;

import java.awt.Dimension;
import net.runelite.api.Constants;

@ConfigGroup(
	keyName = "runelite",
	name = "RuneLite",
	description = "Configuration for RuneLite client options"
)
public interface RuneLiteConfig extends Config
{
	@ConfigItem(
		keyName = "rememberResize",
		name = "Remember game size on resize",
		description = "When resizing the window, save the game size for the next start",
		position = 0
	)
	default boolean rememberResize()
	{
		return true;
	}

	@ConfigItem(
		keyName = "gameSize",
		name = "Game size",
		description = "The game will resize to this resolution upon starting the client",
		position = 1
	)
	default Dimension gameSize()
	{
		return Constants.GAME_FIXED_SIZE;
	}

	@ConfigItem(
		keyName = "gameSize",
		name = "",
		description = ""
	)
	void gameSize(Dimension dim);

	// Hide this config because the user should be able to maximize from their desktop
	@ConfigItem(
		keyName = "maximized",
		name = "",
		description = "",
		hidden = true
	)
	default boolean maximized()
	{
		return false;
	}

	@ConfigItem(
		keyName = "maximized",
		name = "",
		description = ""
	)
	void maximized(boolean b);

	@ConfigItem(
		keyName = "lockWindowSize",
		name = "Lock window size",
		description = "Determines if the window resizing is allowed or not",
		position = 2
	)
	default boolean lockWindowSize()
	{
		return false;
	}

	@ConfigItem(
		keyName = "uiEnableCustomChrome",
		name = "Enable custom window chrome",
		description = "Use Runelite's custom window title and borders.",
		confirmationWarining = "Please restart your client after changing this setting",
		warnOnEnable = true,
		warnOnDisable = true,
		position = 3
	)
	default boolean enableCustomChrome()
	{
		return true;
	}

	@ConfigItem(
		keyName = "gameAlwaysOnTop",
		name = "Enable client always on top",
		description = "The game will always be on the top of the screen",
		position = 4
	)
	default boolean gameAlwaysOnTop()
	{
		return false;
	}

	@ConfigItem(
		keyName = "notificationTray",
		name = "Enable tray notifications",
		description = "Enables tray notifications",
		position = 5
	)
	default boolean enableTrayNotifications()
	{
		return true;
	}

	@ConfigItem(
		keyName = "notificationSound",
		name = "Enable sound on notifications",
		description = "Enables the playing of a beep sound when notifications are displayed",
		position = 6
	)
	default boolean enableNotificationSound()
	{
		return true;
	}

	@ConfigItem(
		keyName = "notificationFocused",
		name = "Send notifications when focused",
		description = "Toggles idle notifications for when the client is focused",
		position = 7
	)
	default boolean sendNotificationsWhenFocused()
	{
		return false;
	}

	@ConfigItem(
		keyName = "notificationRequestFocus",
		name = "Request focus on notification",
		description = "Toggles window focus request",
		position = 8
	)
	default boolean requestFocusOnNotification()
	{
		return true;
	}

	@ConfigItem(
		keyName = "useSmallFont",
		name = "Small font in dynamic overlays",
		description = "Toggles between small and regular RuneScape font for in-game overlays"
	)
	default boolean useSmallFont()
	{
		return true;
	}
}