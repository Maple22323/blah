/*
 * Copyright (c) 2019 NNNN4
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
package net.runelite.client.plugins.kittennotifier;
import com.google.inject.Provides;
import net.runelite.api.ChatMessageType;
import net.runelite.api.NPC;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.Client;
import javax.inject.Inject;

@PluginDescriptor
(
	name = "Kitten Notifier",
	description = "Sends a notification when your kitten needs food, attention, or is grown.",
	tags = {"kitten", "cat", "notifications"}
)
public class KittenNotifierPlugin extends Plugin
{
	@Inject
	private Notifier notifier;

	@Inject
	private KittenNotifierConfig config;

	@Inject
	private Client client;

	@Provides
	KittenNotifierConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(KittenNotifierConfig.class);
	}

	private NPC catName;
	private String catNameWas;

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (config.kittenNeeds() && event.getType() == ChatMessageType.SERVER)
		{
			if (event.getMessage().matches(".+Your kitten is.+hungry.+"))
			{
				notifier.notify("Your kitten is hungry.");
			}
			if (event.getMessage().matches(".+Your kitten.+wants attention.+"))
			{
				notifier.notify("Your kitten wants attention.");
			}
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (catName == null || catName.getName() == null)
		{
			catName = null;
		}
		else
		{
			System.out.println(catName.getName());
			if (catNameWas.equals("Kitten") && catName.getName().equals("Cat"))
			{
				notifier.notify("Your kitten has grown into a cat.");
			}
			catNameWas = catName.getName();

		}
	}

	@Subscribe
	public void onNpcSpawned(NpcSpawned event)
	{
		if (event.getNpc().getInteracting() != null && event.getNpc().getInteracting().getName().equals(client.getLocalPlayer().getName()))
		{
			if (event.getNpc().getName().equals("Kitten") || event.getNpc().getName().equals("Cat"))
			{
				catName = event.getNpc();
				catNameWas = event.getNpc().getName();
			}
		}
	}
}
