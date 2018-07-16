/*
 * Copyright (c) 2018, Damen <https://github.com/basicDamen>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *	list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *	this list of conditions and the following disclaimer in the documentation
 *	and/or other materials provided with the distribution.
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
package net.runelite.client.plugins.grotesqueguardians;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GraphicsObject;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

public class GrotesqueGuardiansOverlay extends Overlay
{
	private final Client client;
	private final GrotesqueGuardiansConfig config;

	@Inject
	private GrotesqueGuardiansOverlay(Client client, GrotesqueGuardiansConfig config)
	{
		this.client = client;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
		setPriority(OverlayPriority.LOW);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!client.isInInstancedRegion())
		{
			if (client.getMapRegions()[0] != 6727)
			{
				return null;
			}
		}

		// TODO: Awaiting GraphicsObjectDespawn event to be tracked to make this more efficient.
		for (GraphicsObject graphicsObject : client.getGraphicsObjects())
		{
			LocalPoint lp = graphicsObject.getLocation();
			Polygon poly = Perspective.getCanvasTilePoly(client, lp);
			Color color = null;
			String text = "";

			if (poly == null)
			{
				return null;
			}

			if (graphicsObject.getId() >= 1416 && graphicsObject.getId() <= 1431)
			{
				color = Color.ORANGE;
				text = "Lightning";
			}

			if (graphicsObject.getId() == 160)
			{
				color = Color.GRAY;
				text = "Stone Orb";
			}

			if (graphicsObject.getId() == 1436)
			{
				color = Color.YELLOW;
				text = "Falling Rocks";

			}

			if (color != null)
			{
				OverlayUtil.renderPolygon(graphics, poly, color);

				Point textLocation = Perspective.getCanvasTextLocation(client, graphics, lp, text, 0);

				if (textLocation != null)
				{
					OverlayUtil.renderTextLocation(graphics, textLocation, text, color);
				}
			}
		}
		return null;
	}
}
