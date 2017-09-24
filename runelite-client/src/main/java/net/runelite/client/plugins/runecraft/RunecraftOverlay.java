/*
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
package net.runelite.client.plugins.runecraft;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.Point;
import net.runelite.api.Varbits;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.RuneLite;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;

public class RunecraftOverlay extends Overlay
{
	private final Client client = RuneLite.getClient();

	private final RunecraftConfig config;
	private final int MEDIUM_POUCH_DAMAGED = ItemID.MEDIUM_POUCH_5511;
	private final int LARGE_POUCH_DAMAGED = ItemID.LARGE_POUCH_5513;
	private final int GIANT_POUCH_DAMAGED = ItemID.GIANT_POUCH_5515;

	public RunecraftOverlay(Runecraft plugin)
	{
		super(OverlayPosition.DYNAMIC);
		this.config = plugin.getConfig();
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (client.getGameState() != GameState.LOGGED_IN
				|| !config.showPouch()
				|| client.getWidget(WidgetInfo.LOGIN_CLICK_TO_PLAY_SCREEN) != null)
		{
			return null;
		}

		Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);

		if (inventoryWidget == null || inventoryWidget.isHidden())
		{
			return null;
		}

		for (WidgetItem item : inventoryWidget.getWidgetItems())
		{
			Varbits varbits;

			switch (item.getId())
			{
				case ItemID.SMALL_POUCH:
					varbits = Varbits.POUCH_SMALL;
					break;
				case ItemID.MEDIUM_POUCH:
				case MEDIUM_POUCH_DAMAGED:
					varbits = Varbits.POUCH_MEDIUM;
					break;
				case ItemID.LARGE_POUCH:
				case LARGE_POUCH_DAMAGED:
					varbits = Varbits.POUCH_LARGE;
					break;
				case ItemID.GIANT_POUCH:
				case GIANT_POUCH_DAMAGED:
					varbits = Varbits.POUCH_GIANT;
					break;
				default:
					continue;
			}

			Point location = item.getCanvasLocation();
			if (location != null)
			{
				int value = client.getSetting(varbits);
				graphics.setColor(Color.black);
				graphics.drawString("" + value, location.getX() + 1, location.getY() + graphics.getFontMetrics().getHeight() + 1);

				graphics.setColor(Color.white);
				graphics.drawString("" + value, location.getX(), location.getY() + graphics.getFontMetrics().getHeight());
			}
		}
		return null;
	}

}
