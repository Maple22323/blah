/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Cas <https://github.com/casvandongen>
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
package net.runelite.client.plugins.agility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.List;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.Tile;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.game.AgilityShortcut;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

class AgilityOverlay extends Overlay
{
	private static final int MAX_DISTANCE = 2350;
	private static final Color SHORTCUT_HIGH_LEVEL_COLOR = Color.ORANGE;

	private final Client client;
	private final AgilityPlugin plugin;
	private final AgilityConfig config;

	@Inject
	private AgilityOverlay(Client client, AgilityPlugin plugin, AgilityConfig config)
	{
		super(plugin);
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
		this.client = client;
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		LocalPoint playerLocation = client.getLocalPlayer().getLocalLocation();
		Point mousePosition = client.getMouseCanvasPosition();
		final List<Tile> marksOfGrace = plugin.getMarksOfGrace();
		plugin.getObstacles().forEach((object, obstacle) ->
		{
			if (Obstacles.SHORTCUT_OBSTACLE_IDS.containsKey(object.getId()) && !config.highlightShortcuts() ||
					Obstacles.TRAP_OBSTACLE_IDS.contains(object.getId()) && !config.showTrapOverlay() ||
					Obstacles.COURSE_OBSTACLE_IDS.contains(object.getId()) && !config.showAgilityCourseClickboxes())
			{
				return;
			}

			Tile tile = obstacle.getTile();
			if (tile.getPlane() == client.getPlane()
				&& object.getLocalLocation().distanceTo(playerLocation) < MAX_DISTANCE)
			{
				// This assumes that the obstacle is not clickable.
				if (Obstacles.TRAP_OBSTACLE_IDS.contains(object.getId()))
				{
					Polygon polygon = object.getCanvasTilePoly();
					if (polygon != null)
					{
						OverlayUtil.renderPolygon(graphics, polygon, config.getTrapColor());
					}
					return;
				}
				Shape objectClickbox = object.getClickbox();
				if (objectClickbox != null)
				{
					AgilityShortcut agilityShortcut = obstacle.getShortcut();
					Color configColor = agilityShortcut == null || agilityShortcut.getLevel() <= plugin.getAgilityLevel() ? config.getOverlayColor() : SHORTCUT_HIGH_LEVEL_COLOR;
					if (config.highlightMarks() && !marksOfGrace.isEmpty())
					{
						configColor = config.getMarkColor();
					}

					if (objectClickbox.contains(mousePosition.getX(), mousePosition.getY()))
					{
						graphics.setColor(configColor.darker());
					}
					else
					{
						graphics.setColor(configColor);
					}

					graphics.draw(objectClickbox);
					graphics.setColor(new Color(configColor.getRed(), configColor.getGreen(), configColor.getBlue(), 50));
					graphics.fill(objectClickbox);
				}
			}

		});

		if (config.highlightMarks() && !marksOfGrace.isEmpty())
		{
			for (Tile markOfGraceTile : marksOfGrace)
			{
				if (markOfGraceTile.getPlane() == client.getPlane() && markOfGraceTile.getItemLayer() != null
						&& markOfGraceTile.getLocalLocation().distanceTo(playerLocation) < MAX_DISTANCE)
				{
					final Polygon poly = markOfGraceTile.getItemLayer().getCanvasTilePoly();

					if (poly == null)
					{
						continue;
					}

					OverlayUtil.renderPolygon(graphics, poly, config.getMarkColor());
				}
			}
		}

		return null;
	}
}
