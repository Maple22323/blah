/*
 * Copyright (c) 2017, Kronos <https://github.com/KronosDesign>
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
package net.runelite.client.plugins.devtools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.runelite.client.ui.PluginPanel;

public class DevToolsPanel extends PluginPanel
{
	private JButton renderPlayersBtn = new JButton();
	private JButton renderNpcsBtn = new JButton();
	private JButton renderGroundItemsBtn = new JButton();
	private JButton renderGroundObjectsBtn = new JButton();
	private JButton renderGameObjectsBtn = new JButton();
	private JButton renderWallsBtn = new JButton();
	private JButton renderDecorBtn = new JButton();
	private JButton renderInventoryBtn = new JButton();

	public DevToolsPanel(DevTools tools)
	{
		setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		setVisible(true);

		JPanel container = new JPanel();
		container.setLayout(new GridLayout(4, 2, 3, 3));
		add(container);

		renderPlayersBtn = new JButton("Players");
		renderPlayersBtn.addActionListener(e -> {
			highlightButton(renderPlayersBtn);
			tools.togglePlayers();
		});
		container.add(renderPlayersBtn);

		renderNpcsBtn = new JButton("NPCs");
		renderNpcsBtn.addActionListener(e -> {
			highlightButton(renderNpcsBtn);
			tools.toggleNpcs();
		});
		container.add(renderNpcsBtn);

		renderGroundItemsBtn = new JButton("Ground Items");
		renderGroundItemsBtn.addActionListener(e -> {
			highlightButton(renderGroundItemsBtn);
			tools.toggleGroundItems();
		});
		container.add(renderGroundItemsBtn);

		renderGroundObjectsBtn = new JButton("Ground Objects");
		renderGroundObjectsBtn.addActionListener(e -> {
			highlightButton(renderGroundObjectsBtn);
			tools.toggleGroundObjects();
		});
		container.add(renderGroundObjectsBtn);

		renderGameObjectsBtn = new JButton("Game Objects");
		renderGameObjectsBtn.addActionListener(e -> {
			highlightButton(renderGameObjectsBtn);
			tools.toggleGameObjects();
		});
		container.add(renderGameObjectsBtn);

		renderWallsBtn = new JButton("Walls");
		renderWallsBtn.addActionListener(e -> {
			highlightButton(renderWallsBtn);
			tools.toggleWalls();
		});
		container.add(renderWallsBtn);

		renderDecorBtn = new JButton("Decorations");
		renderDecorBtn.addActionListener(e -> {
			highlightButton(renderDecorBtn);
			tools.toggleDecor();
		});
		container.add(renderDecorBtn);

		renderInventoryBtn = new JButton("Inventory");
		renderInventoryBtn.addActionListener(e -> {
			highlightButton(renderInventoryBtn);
			tools.toggleInventory();
		});
		container.add(renderInventoryBtn);
	}

	private void highlightButton(JButton button)
	{
		if (button.getBackground().equals(Color.GREEN))
		{
			button.setBackground(null);
		}
		else
		{
			button.setBackground(Color.GREEN);
		}
	}

}
