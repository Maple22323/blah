/*
 * Copyright (c) 2018, Unmoon <https://github.com/Unmoon>
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
package net.runelite.client.plugins.blastmine;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.ItemID;
import net.runelite.api.Perspective;
import net.runelite.api.Tile;
import net.runelite.api.widgets.Widget;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;

public class BlastMineRockOverlay extends Overlay
{
	private static final int TIMER_SIZE = 25;
	private static final int TIMER_BORDER_WIDTH = 1;
	private static final int MAX_DISTANCE = 16;
	private static final int WARNING_DISTANCE = 2;

	private final Client client;
	private final BlastMinePlugin plugin;
	private final BlastMinePluginConfig config;

	private Color timerColor;
	private Color warningColor;

	@Inject
	private ItemManager itemManager;

	@Inject
	BlastMineRockOverlay(@Nullable Client client, BlastMinePlugin plugin, BlastMinePluginConfig config)
	{
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.UNDER_WIDGETS);
		this.plugin = plugin;
		this.config = config;
		this.client = client;
	}

	@Override
	public Dimension render(Graphics2D graphics, Point parent)
	{
		if (config.enabled())
		{
			plugin.getRocks().removeIf(rock -> (rock.getRemainingTimeRelative() == 1 && rock.getType() != BlastMineRockType.NORMAL) || (rock.getRemainingFuseTimeRelative() == 1 && rock.getType() == BlastMineRockType.LIT));
			drawRocks(graphics);
		}
		return null;
	}

	public void updateColors()
	{
		timerColor = config.getTimerColor();
		warningColor = config.getWarningColor();
	}

	private void drawRocks(Graphics2D graphics)
	{
		Tile[][][] tiles = client.getRegion().getTiles();
		BufferedImage chiselIcon = itemManager.getImage(ItemID.CHISEL);
		BufferedImage dynamiteIcon = itemManager.getImage(ItemID.DYNAMITE);
		BufferedImage tinderboxIcon = itemManager.getImage(ItemID.TINDERBOX);
		Widget viewport = client.getViewportWidget();
		for (BlastMineRock rock : plugin.getRocks())
		{
			if (viewport != null && rock.getGameObject().getCanvasLocation() != null
				&& rock.getGameObject().getWorldLocation().distanceTo(client.getLocalPlayer().getWorldLocation()) <= MAX_DISTANCE)
			{
				switch (rock.getType())
				{
					case NORMAL:
						drawIconOnRock(graphics, rock, chiselIcon);
						break;
					case CHISELED:
						drawIconOnRock(graphics, rock, dynamiteIcon);
						break;
					case LOADED:
						drawIconOnRock(graphics, rock, tinderboxIcon);
						break;
					case LIT:
						drawTimerOnRock(graphics, rock, timerColor);
						drawAreaWarning(graphics, rock, warningColor, tiles);
						break;
				}
			}
		}
	}

	private void drawIconOnRock(Graphics2D graphics, BlastMineRock rock, BufferedImage icon)
	{
		net.runelite.api.Point loc = Perspective.getCanvasImageLocation(client, graphics, rock.getGameObject().getLocalLocation(), icon, 150);
		if (loc != null && config.showRockIconOverlay())
		{
			graphics.drawImage(icon, loc.getX(), loc.getY(), null);
		}
	}

	private void drawTimerOnRock(Graphics2D graphics, BlastMineRock rock, Color color)
	{
		net.runelite.api.Point loc = Perspective.worldToCanvas(client, rock.getGameObject().getX(), rock.getGameObject().getY(), client.getPlane(), 150);

		if (loc != null && config.showTimerOverlay())
		{
			//Construct the arc
			Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
			arc.setAngleStart(90);
			double timeLeft = 1 - rock.getRemainingFuseTimeRelative();
			arc.setAngleExtent(timeLeft * 360);
			arc.setFrame(loc.getX() - TIMER_SIZE / 2, loc.getY() - TIMER_SIZE / 2, TIMER_SIZE, TIMER_SIZE);

			//Draw the inside of the arc
			graphics.setColor(color);
			graphics.fill(arc);

			//Draw the outlines of the arc
			graphics.setStroke(new BasicStroke(TIMER_BORDER_WIDTH));
			graphics.drawOval(loc.getX() - TIMER_SIZE / 2, loc.getY() - TIMER_SIZE / 2, TIMER_SIZE, TIMER_SIZE);
		}
	}

	private void drawAreaWarning(Graphics2D graphics, BlastMineRock rock, Color color, Tile[][][] tiles)
	{
		if (!config.showWarningOverlay())
		{
			return;
		}
		int z = client.getPlane();
		int x = rock.getGameObject().getRegionLocation().getX();
		int y = rock.getGameObject().getRegionLocation().getY();
		int orientation = tiles[z][x][y].getWallObject().getOrientationA();

		switch (orientation) //calculate explosion around the tile in front of the wall
		{
			case 1:
				x--;
				break;
			case 4:
				x++;
				break;
			case 8:
				y--;
				break;
			default:
				y++;
		}

		for (int i = -WARNING_DISTANCE; i <= WARNING_DISTANCE; i++)
		{
			for (int j = -WARNING_DISTANCE; j <= WARNING_DISTANCE; j++)
			{
				GameObject gameObject = tiles[z][x + i][y + j].getGameObjects()[0];
				//check if tile is empty, or is a wall... waiting for null names being added to ObjectID
				if (gameObject == null  || !(28570 <= gameObject.getId() && 28588 >= gameObject.getId()))
				{
					net.runelite.api.Point localTile = new net.runelite.api.Point(
							(x + i) * Perspective.LOCAL_TILE_SIZE + Perspective.LOCAL_TILE_SIZE / 2,
							(y + j) * Perspective.LOCAL_TILE_SIZE + Perspective.LOCAL_TILE_SIZE / 2);
					Polygon poly = Perspective.getCanvasTilePoly(client, localTile);
					if (poly != null)
					{
						graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
						graphics.fillPolygon(poly);
					}
				}
			}
		}
	}
}
