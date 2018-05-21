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

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.game.AsyncBufferedImage;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.PluginPanel;


@Slf4j
class LootRecorderPanel extends PluginPanel
{
	private final ItemManager itemManager;
	private final LootRecorderPlugin lootRecorderPlugin;

	private JTabbedPane tabsPanel = new JTabbedPane();

	private Map<String, JPanel> tabsMap = new HashMap<>();
	private Map<String, LootPanel> lootMap = new HashMap<>();

	@Inject
	LootRecorderPanel(ItemManager itemManager, LootRecorderPlugin lootRecorderPlugin)
	{
		super(false);
		this.itemManager = itemManager;
		this.lootRecorderPlugin = lootRecorderPlugin;

		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);

		createPanel(this);
	}

	private void createPanel(LootRecorderPanel panel)
	{
		// Create each Tab of the Panel
		for (Tab tab : Tab.values())
		{
			// Only show tabs for recorded options
			if (lootRecorderPlugin.isBeingRecorded(tab.getName()))
			{
				createTab(tab);
			}
		}

		// Refresh Panel Button
		JButton refresh = new JButton("Refresh Panel");
		refresh.addActionListener(e ->
				refreshPanel(panel));

		// Add to Panel
		panel.add(tabsPanel);
		panel.add(refresh);
		log.info("Panel Created");
	}

	private LootPanel createLootPanel(Tab tab)
	{
		// Grab Tab Data
		ArrayList<LootEntry> data = lootRecorderPlugin.getData(tab.getName());
		// Unique Items Info
		final ArrayList<UniqueItem> list = UniqueItem.getByActivityName(tab.getName());
		Map<Integer, ArrayList<UniqueItem>> sets = UniqueItem.createSetMap(list);
		// Create & Return Loot Panel
		return new LootPanel(data, sets, itemManager);
	}

	private void refreshPanel(LootRecorderPanel panel)
	{
		// Refresh Log Data
		lootRecorderPlugin.loadAllData();
		// Remove All Panel Components
		panel.removeAll();
		tabsPanel.removeAll();
		// Recreate Panel Components
		createPanel(panel);
		// Ensure panel updates are applied
		panel.revalidate();
		panel.repaint();
		log.info("Refreshed Panel");
	}

	private void refreshLootPanel(LootPanel lootPanel, Tab tab)
	{
		// Refresh data for necessary tab
		lootRecorderPlugin.loadTabData(tab.getName());
		// Recreate the loot panel
		lootPanel.refreshPanel();
		// Ensure changes are applied
		this.revalidate();
		this.repaint();
	}

	private void createTab(Tab tab)
	{
		// Container Panel for this tab
		JPanel tabPanel = new JPanel();
		tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.Y_AXIS));
		tabPanel.setBorder(new EmptyBorder(2, 2, 2, 2));

		// Button Container
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.setBorder(new EmptyBorder(0, 0, 4, 0));

		// Loot Panel
		LootPanel lootPanel = createLootPanel(tab);

		// Scrolling Ability for lootPanel
		JPanel wrapped = new JPanel(new BorderLayout());
		wrapped.add(lootPanel, BorderLayout.NORTH);
		JScrollPane scroller = new JScrollPane(wrapped);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.getVerticalScrollBar().setUnitIncrement(16);


		// Refresh Button
		JButton refresh = new JButton("Refresh Data");
		refresh.addActionListener(e ->
				refreshLootPanel(lootPanel, tab));
		buttons.add(refresh);

		// Add components to Tab Panel
		tabPanel.add(buttons);
		tabPanel.add(scroller);

		// Add new tab to panel
		tabsPanel.addTab(tab.getName(), null, tabPanel, tab.getName());

		// Add Tab Icon
		AsyncBufferedImage icon = itemManager.getImage(tab.getItemID());
		int idx = tabsPanel.getTabCount() - 1;
		Runnable resize = () ->
				tabsPanel.setIconAt(idx, new ImageIcon(icon.getScaledInstance(24, 21, Image.SCALE_SMOOTH)));
		icon.onChanged(resize);
		resize.run();

		tabsMap.put(tab.getName().toUpperCase(), tabPanel);
		lootMap.put(tab.getName().toUpperCase(), lootPanel);
		log.info("Created " + String.valueOf(tab) + " tab");
	}

	private void removeTab(Tab tab)
	{
		JPanel panel = tabsMap.get(tab.getName().toUpperCase());

		panel.getParent().remove(panel);
	}

	void updateLootPanel(LootPanel panel, ArrayList<LootEntry> records)
	{
		panel.updateRecords(records);
		panel.repaint();
		panel.revalidate();
	}

	void updateTab(String tabName)
	{
		// Update tabs loot panel with new data
		// final LootPanel panel = lootMap.get(tabName.toUpperCase());
		// final ArrayList<LootEntry> records = lootRecorderPlugin.getData(tabName);
		// SwingUtilities.invokeLater(() -> updateLootPanel(panel, records));

		// Recreating tab
		JPanel panel = tabsMap.get(tabName.toUpperCase());
		panel.getParent().remove(panel);
		final Tab tab = Tab.getByName(tabName);
		SwingUtilities.invokeLater(() -> createTab(tab));
	}

	void toggleTab(String tabName, Boolean status)
	{
		Tab tab = Tab.getByName(tabName);
		log.info("Toggling: ");
		log.info(tab.getName());
		if (status)
		{
			createTab(tab);
		}
		else
		{
			removeTab(tab);
		}
	}
}