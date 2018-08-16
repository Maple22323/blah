/*
 * Copyright (c) 2018, Morgan Lewis <https://github.com/MESLewis>
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
package net.runelite.client.ui.overlay.worldmap;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SwingUtilities;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.RenderOverview;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.input.MouseListener;

@Singleton
public class WorldMapOverlayMouseListener extends MouseListener
{
	private final Client client;
	private final WorldMapPointManager worldMapPointManager;
	private final WorldMapLineManager worldMapLineManager;
	private WorldMapPoint tooltipPoint = null;
	private WorldMapLine tooltipLine = null;

	@Inject
	private WorldMapOverlayMouseListener(Client client, WorldMapPointManager worldMapPointManager, WorldMapLineManager worldMapLineManager)
	{
		this.client = client;
		this.worldMapPointManager = worldMapPointManager;
		this.worldMapLineManager = worldMapLineManager;
	}

	@Override
	public MouseEvent mousePressed(MouseEvent e)
	{
		final List<WorldMapPoint> worldMapPoints = worldMapPointManager.getWorldMapPoints();

		if (SwingUtilities.isLeftMouseButton(e) && !worldMapPoints.isEmpty())
		{
			Point mousePos = client.getMouseCanvasPosition();

			for (WorldMapPoint worldMapPoint : worldMapPoints)
			{
				Rectangle clickbox = worldMapPoint.getClickbox();
				if (clickbox != null && clickbox.contains(mousePos.getX(), mousePos.getY()))
				{
					if (worldMapPoint.isJumpOnClick())
					{
						// jump map to position of point
						WorldPoint target = worldMapPoint.getWorldPoint();
						RenderOverview renderOverview = client.getRenderOverview();
						renderOverview.setWorldMapPositionTarget(target);
					}
					return worldMapPoint.onClick(e);
				}
			}
		}
		return e;
	}

	@Override
	public MouseEvent mouseMoved(MouseEvent mouseEvent)
	{
		final List<WorldMapPoint> worldMapPoints = worldMapPointManager.getWorldMapPoints();

		if (worldMapPoints.isEmpty())
		{
			return mouseEvent;
		}

		final Point mousePos = client.getMouseCanvasPosition();
		final Widget view = client.getWidget(WidgetInfo.WORLD_MAP_VIEW);

		if (view == null)
		{
			return mouseEvent;
		}

		final Rectangle worldMapDisplay = view.getBounds();

		if (worldMapDisplay == null || !worldMapDisplay.contains(mousePos.getX(), mousePos.getY()))
		{
			if (tooltipPoint != null)
			{
				tooltipPoint.setTooltipVisible(false);
				tooltipPoint = null;
				final Widget rsTooltip = client.getWidget(WidgetInfo.WORLD_MAP_TOOLTIP);
				if (rsTooltip != null)
				{
					rsTooltip.setHidden(false);
				}
			}

			if (tooltipLine != null)
			{
				tooltipLine.setTooltipVisible(false);
				tooltipLine = null;
				final Widget rsTooltip = client.getWidget(WidgetInfo.WORLD_MAP_TOOLTIP);
				if (rsTooltip != null)
				{
					rsTooltip.setHidden(false);
				}
			}
			return mouseEvent;
		}

		if (tooltipPoint != null)
		{
			if (tooltipPoint.getClickbox() != null
				&& tooltipPoint.getClickbox().contains(mousePos.getX(), mousePos.getY()))
			{
				return mouseEvent;
			}
			else
			{
				tooltipPoint.setTooltipVisible(false);
				tooltipPoint = null;
				final Widget rsTooltip = client.getWidget(WidgetInfo.WORLD_MAP_TOOLTIP);
				if (rsTooltip != null)
				{
					rsTooltip.setHidden(false);
				}
			}
		}

		if (tooltipLine != null)
		{
			if (tooltipLine.getClickbox() != null
				&& tooltipLine.getClickbox().contains(mousePos.getX(), mousePos.getY()))
			{
				return mouseEvent;
			}
			else
			{
				tooltipLine.setTooltipVisible(false);
				tooltipLine = null;
				final Widget rsTooltip = client.getWidget(WidgetInfo.WORLD_MAP_TOOLTIP);
				if (rsTooltip != null)
				{
					rsTooltip.setHidden(false);
				}
			}
		}

		for (WorldMapPoint worldMapPoint : worldMapPointManager.getWorldMapPoints())
		{
			if (worldMapPoint.getClickbox() != null
				&& worldMapPoint.getClickbox().contains(mousePos.getX(), mousePos.getY())
				&& worldMapPoint.getTooltip() != null)
			{
				worldMapPoint.setTooltipVisible(true);
				tooltipPoint = worldMapPoint;
				final Widget rsTooltip = client.getWidget(WidgetInfo.WORLD_MAP_TOOLTIP);
				if (rsTooltip != null)
				{
					rsTooltip.setHidden(true);
				}
				return mouseEvent;
			}
		}

		for (WorldMapLine worldMapLine : worldMapLineManager.getWorldMapLines())
		{
			if (worldMapLine.getClickbox() != null
				&& worldMapLine.getClickbox().contains(mousePos.getX(), mousePos.getY())
				&& worldMapLine.getTooltip() != null)
			{
				worldMapLine.setTooltipVisible(true);
				tooltipLine = worldMapLine;
				final Widget rsTooltip = client.getWidget(WidgetInfo.WORLD_MAP_TOOLTIP);
				if (rsTooltip != null)
				{
					rsTooltip.setHidden(true);
				}
				return mouseEvent;
			}
		}
		return mouseEvent;
	}
}
