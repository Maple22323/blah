/*
 * Copyright (c) 2018, TheStonedTurtle <www.github.com/TheStonedTurtle>
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
package net.runelite.client.plugins.lootrecorder;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.swing.SwingUtilities;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.WidgetID;
import net.runelite.client.Notifier;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.PluginToolbar;
import net.runelite.client.util.Text;
import net.runelite.http.api.RuneLiteAPI;

import static net.runelite.client.RuneLite.LOOTS_DIR;

@PluginDescriptor(
	name = "Loot Recorder"
)

@Slf4j
public class LootRecorderPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ItemManager itemManager;

	@Inject
	private LootRecorderConfig lootRecorderConfig;

	@Inject
	private Notifier notifier;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Inject
	private PluginToolbar pluginToolbar;

	@Getter
	private ArrayList<LootEntry> barrows = new ArrayList<LootEntry>();
	@Getter
	private ArrayList<LootEntry> raids = new ArrayList<LootEntry>();
	@Getter
	private ArrayList<LootEntry> zulrah = new ArrayList<LootEntry>();

	private Integer barrowsNumber;
	private Integer raidsNumber;
	private Integer zulrahNumber;

	private String barrowsFilename = "barrows.log";
	private String raidsFilename = "raids.log";
	private String zulrahFilename = "zulrah.log";

	private File playerFolder;
	private static final Pattern NUMBER_PATTERN = Pattern.compile("([0-9]+)");

	private LootRecorderPanel panel;
	private NavigationButton navButton;

	@Provides
	LootRecorderConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(LootRecorderConfig.class);
	}

	@Override
	protected void startUp()
	{
		if (lootRecorderConfig.showLootTotals())
		{
			// Waits 2 seconds, helps ensure itemManager is loaded
			// Client cache loading is async, plugins can be loaded before it is finished
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
			scheduler.schedule(() -> SwingUtilities.invokeLater(this::createPanel), 2, TimeUnit.SECONDS);
		}
	}

	// Separated from startUp for toggling panel from settings
	void createPanel()
	{
		panel = new LootRecorderPanel(itemManager, this);
		// Panel Icon (Looting Bag)
		BufferedImage icon = null;
		synchronized (ImageIO.class)
		{
			try
			{
				icon = ImageIO.read(getClass().getResourceAsStream("panel_icon.png"));
			}
			catch (IOException e)
			{
				log.warn("Error getting panel icon:", e);
			}
		}

		navButton = NavigationButton.builder()
			.tooltip("Loot Recorder")
			.icon(icon)
			.panel(panel)
			.priority(10)
			.build();

		pluginToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
		removePanel();
	}

	private void removePanel()
	{
		pluginToolbar.removeNavigation(navButton);
	}

	// Checks for loot that is rewarded via interfaces
	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event)
	{
		// Barrows Chests
		if (event.getGroupId() == WidgetID.BARROWS_REWARD_GROUP_ID && lootRecorderConfig.recordBarrowsChest())
		{
			ItemContainer rewardContainer = client.getItemContainer(InventoryID.BARROWS_REWARD);
			LootEntry entry = createLootEntry(barrowsNumber, rewardContainer);
			barrows.add(entry);
			addLootEntry(barrowsFilename, entry);
			lootRecordedAlert("Barrows Chest added to log.");
			panel.updateTab("Barrows");
		}

		// Raids Chest
		if (event.getGroupId() == WidgetID.RAIDS_REWARD_GROUP_ID && lootRecorderConfig.recordRaidsChest())
		{
			ItemContainer rewardContainer = client.getItemContainer(InventoryID.valueOf("RAIDS_REWARD_GROUP_ID")); // TODO: Update to RAIDS REWARD ONCE implemented
			LootEntry entry = createLootEntry(raidsNumber, rewardContainer);
			raids.add(entry);
			addLootEntry(raidsFilename, entry);
			lootRecordedAlert("Raid Loot added to log.");
			panel.updateTab("Raids");
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		// Only update if our plugin config was changed
		if (!event.getGroup().equals("lootrecorder"))
		{
			return;
		}

		switch (event.getKey())
		{
			case "recordBarrowsChest":
				ToggleTab("Barrows", lootRecorderConfig.recordBarrowsChest());
				return;
			case "recordRaidsChest":
				ToggleTab("Raids", lootRecorderConfig.recordRaidsChest());
				return;
			case "recordZulrahKills":
				ToggleTab("Zulrah", lootRecorderConfig.recordZulrahKills());
				return;
			case "showLootTotals":
				loadAllData();
				if (lootRecorderConfig.showLootTotals())
				{
					createPanel();
				}
				else
				{
					removePanel();
				}
				return;
			default:
				break;
		}
	}

	private void lootRecordedAlert(String message)
	{
		message = "Loot Recorder: " + message;
		if (lootRecorderConfig.showChatMessages())
		{
			final ChatMessageBuilder chatMessage = new ChatMessageBuilder()
					.append(ChatColorType.HIGHLIGHT)
					.append(message)
					.append(ChatColorType.NORMAL);

			chatMessageManager.queue(QueuedMessage.builder()
					.type(ChatMessageType.EXAMINE_ITEM)
					.runeLiteFormattedMessage(chatMessage.build())
					.build());
		}

		if (lootRecorderConfig.showTrayAlerts())
		{
			notifier.notify(message);
		}
	}

	private void ToggleTab(String tabName, Boolean status)
	{
		if (lootRecorderConfig.showLootTotals())
		{
			panel.toggleTab(tabName, status);
		}
	}

	void loadTabData(String tabName)
	{
		switch (tabName.toUpperCase())
		{
			case "BARROWS":
				loadLootEntries(barrowsFilename, barrows);
				break;
			case "RAIDS":
				loadLootEntries(raidsFilename, raids);
			case "ZULRAH":
				loadLootEntries(zulrahFilename, zulrah);
				break;
			default:
				break;
		}
	}

	void loadAllData()
	{
		loadLootEntries(barrowsFilename, barrows);
		loadLootEntries(raidsFilename, raids);
		loadLootEntries(zulrahFilename, zulrah);
	}

	// Update KC variable on chat message event
	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (event.getType() != ChatMessageType.SERVER && event.getType() != ChatMessageType.FILTERED)
		{
			return;
		}

		String chatMessage = event.getMessage();

		// Barrows KC
		if (chatMessage.startsWith("Your Barrows chest count is"))
		{
			Matcher m = NUMBER_PATTERN.matcher(Text.removeTags(chatMessage));
			if (m.find())
			{
				barrowsNumber = Integer.valueOf(m.group());
				return;
			}
		}

		// Raids KC
		if (chatMessage.startsWith("Your completed Chambers of Xeric count is:"))
		{
			Matcher m = NUMBER_PATTERN.matcher(Text.removeTags(chatMessage));
			if (m.find())
			{
				raidsNumber = Integer.valueOf(m.group());
			}
		}
	}

	// Create Loot Entry for ItemContainer
	private LootEntry createLootEntry(Integer kill_count, ItemContainer container)
	{
		ArrayList<DropEntry> drops = new ArrayList<>();
		for (Item item : container.getItems())
		{
			int id = item.getId();
			int amount = item.getQuantity();
			drops.add(new DropEntry(id, amount));
		}
		return new LootEntry(kill_count, drops);
	}

	// Add Loot Entry to the necessary file
	private void addLootEntry(String fileName, LootEntry entry)
	{
		// Convert entry to JSON
		String dataAsString = RuneLiteAPI.GSON.toJson(entry);
		// Grab file by username or loots directory if not logged in
		if (client.getLocalPlayer() != null && client.getLocalPlayer().getName() != null)
		{
			playerFolder = new File(LOOTS_DIR, client.getLocalPlayer().getName());
		}
		else
		{
			playerFolder = LOOTS_DIR;
		}
		// Open File and append data
		File lootFile = new File(playerFolder, fileName);
		try
		{
			BufferedWriter file = new BufferedWriter(new FileWriter(String.valueOf(lootFile), true));
			file.append(dataAsString);
			file.newLine();
			file.close();
		}
		catch (IOException ioe)
		{
			log.warn("Error witting loot data in file.", ioe);
		}
	}

	// Receive Loot from the necessary file
	private synchronized void loadLootEntries(String fileName, ArrayList<LootEntry> data)
	{
		// Empty Array
		data.clear();

		// Grab file by username or loots directory if not logged in
		if (client.getLocalPlayer() != null && client.getLocalPlayer().getName() != null)
		{
			playerFolder = new File(LOOTS_DIR, client.getLocalPlayer().getName());
		}
		else
		{
			playerFolder = LOOTS_DIR;
		}

		// Open File and read line by line
		File file = new File(playerFolder, fileName);
		try (BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				// Convert JSON to LootEntry and add to data ArrayList
				if (line.length() > 0)
				{
					LootEntry entry = RuneLiteAPI.GSON.fromJson(line, LootEntry.class);
					data.add(entry);
				}
			}
		}
		catch (FileNotFoundException e)
		{
			log.warn("File not found: " + fileName);
		}
		catch (IOException e)
		{
			log.warn("Unexpected error", e);
		}
	}

	// Returns stored data by tab name
	ArrayList<LootEntry> getData(String type)
	{
		switch (type.toUpperCase())
		{
			case "BARROWS":
				return barrows;
			case "RAIDS":
				return raids;
			case "ZULRAH":
				return zulrah;
			default:
				return null;
		}
	}

	// Handles if panel should be shown by Tab Name
	Boolean isBeingRecorded(String tabName)
	{
		switch (tabName.toUpperCase())
		{
			case "BARROWS":
				return lootRecorderConfig.recordBarrowsChest();
			case "RAIDS":
				return lootRecorderConfig.recordRaidsChest();
			case "ZULRAH":
				return lootRecorderConfig.recordZulrahKills();
			default:
				return false;
		}
	}
}