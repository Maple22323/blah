/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
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
package net.runelite.mixins;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import net.runelite.api.Actor;
import net.runelite.api.FreezeInfo;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.api.SpritePixels;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GraphicChanged;
import net.runelite.api.mixins.FieldHook;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import static net.runelite.client.callback.Hooks.eventBus;
import net.runelite.rs.api.RSActor;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSCombatInfo1;
import net.runelite.rs.api.RSCombatInfo2;
import net.runelite.rs.api.RSCombatInfoList;
import net.runelite.rs.api.RSCombatInfoListHolder;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSNode;

@Mixin(RSActor.class)
public abstract class RSActorMixin implements RSActor
{
	@Shadow("clientInstance")
	private static RSClient client;

	@Inject
	private FreezeInfo freeze;

	@Inject
	@Override
	public Actor getInteracting()
	{
		int i = getRSInteracting();
		if (i == -1)
		{
			return null;
		}

		if (i < 0x8000)
		{
			NPC[] npcs = client.getCachedNPCs();
			return npcs[i];
		}

		i -= 0x8000;
		Player[] players = client.getCachedPlayers();
		return players[i];
	}

	@Inject
	@Override
	public int getHealthRatio()
	{
		RSCombatInfoList combatInfoList = getCombatInfoList();
		if (combatInfoList != null)
		{
			RSNode node = combatInfoList.getNode();
			RSNode next = node.getNext();
			if (next instanceof RSCombatInfoListHolder)
			{
				RSCombatInfoListHolder combatInfoListWrapper = (RSCombatInfoListHolder) next;
				RSCombatInfoList combatInfoList1 = combatInfoListWrapper.getCombatInfo1();

				RSNode node2 = combatInfoList1.getNode();
				RSNode next2 = node2.getNext();
				if (next2 instanceof RSCombatInfo1)
				{
					RSCombatInfo1 combatInfo = (RSCombatInfo1) next2;
					return combatInfo.getHealthRatio();
				}
			}
		}
		return -1;
	}

	@Inject
	@Override
	public int getHealth()
	{
		RSCombatInfoList combatInfoList = getCombatInfoList();
		if (combatInfoList != null)
		{
			RSNode node = combatInfoList.getNode();
			RSNode next = node.getNext();
			if (next instanceof RSCombatInfoListHolder)
			{
				RSCombatInfoListHolder combatInfoListWrapper = (RSCombatInfoListHolder) next;
				RSCombatInfo2 cf = combatInfoListWrapper.getCombatInfo2();
				return cf.getHealthScale();
			}
		}
		return -1;
	}

	@Override
	@Inject
	public WorldPoint getWorldLocation()
	{
		return WorldPoint.fromLocal(client,
			this.getPathX()[0] * Perspective.LOCAL_TILE_SIZE + Perspective.LOCAL_TILE_SIZE / 2,
			this.getPathY()[0] * Perspective.LOCAL_TILE_SIZE + Perspective.LOCAL_TILE_SIZE / 2,
			client.getPlane());
	}

	@Inject
	@Override
	public LocalPoint getLocalLocation()
	{
		return new LocalPoint(getX(), getY());
	}

	@Inject
	@Override
	public Polygon getCanvasTilePoly()
	{
		return Perspective.getCanvasTilePoly(client, getLocalLocation());
	}

	@Inject
	@Override
	public Point getCanvasTextLocation(Graphics2D graphics, String text, int zOffset)
	{
		return Perspective.getCanvasTextLocation(client, graphics, getLocalLocation(), text, zOffset);
	}

	@Inject
	@Override
	public Point getCanvasImageLocation(Graphics2D graphics, BufferedImage image, int zOffset)
	{
		return Perspective.getCanvasImageLocation(client, graphics, getLocalLocation(), image, zOffset);
	}

	@Inject
	@Override
	public Point getCanvasSpriteLocation(Graphics2D graphics, SpritePixels sprite, int zOffset)
	{
		return Perspective.getCanvasSpriteLocation(client, graphics, getLocalLocation(), sprite, zOffset);
	}

	@Inject
	@Override
	public Point getMinimapLocation()
	{
		return Perspective.worldToMiniMap(client, getX(), getY());
	}

	@Inject
	@Override
	public Point getRegionTile()
	{
		return new Point(this.getPathX()[0], this.getPathY()[0]);
	}

	@Inject
	@Override
	public Point getWorldTile()
	{
		Point region = getRegionTile();
		int baseX = client.getBaseX();
		int baseY = client.getBaseY();

		return new Point(baseX + region.getX(), baseY + region.getY());
	}

	@Inject
	@Override
	public FreezeInfo getFreeze()
	{
		if (this.freeze == null)
			this.freeze = new FreezeInfo();
		
		return this.freeze;
	}

	@Inject
	@Override
	public boolean hasComposition()
	{
		return false;
	}

	@FieldHook("animation")
	@Inject
	public void animationChanged(int idx)
	{
		AnimationChanged animationChange = new AnimationChanged();
		animationChange.setActor(this);
		eventBus.post(animationChange);
	}

	@FieldHook("graphic")
	@Inject
	public void graphicChanged(int idx)
	{
		GraphicChanged graphicChanged = new GraphicChanged();
		graphicChanged.setActor(this);
		eventBus.post(graphicChanged);
	}

	@Inject
	@Override
	public Polygon getConvexHull()
	{
		RSModel model = getModel();
		if (model == null)
		{
			return null;
		}
		return model.getConvexHull(getX(), getY(), getOrientation());
	}

	@Inject
	@Override
	public WorldArea getWorldArea()
	{
		int size = 1;
		if (this instanceof NPC)
		{
			NPCComposition composition = ((NPC)this).getComposition();
			if (composition != null && composition.getConfigs() != null)
			{
				composition = composition.transform();
			}
			if (composition != null)
			{
				size = composition.getSize();
			}
		}

		return new WorldArea(this.getWorldLocation(), size, size);
	}
}
