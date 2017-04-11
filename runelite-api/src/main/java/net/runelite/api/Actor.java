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
package net.runelite.api;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import net.runelite.rs.api.CombatInfo1;
import net.runelite.rs.api.CombatInfo2;
import net.runelite.rs.api.CombatInfoList;
import net.runelite.rs.api.CombatInfoListHolder;
import net.runelite.rs.api.Node;

public abstract class Actor extends Renderable
{

	private Client client;
	private net.runelite.rs.api.Actor actor;

	public Actor(Client client, net.runelite.rs.api.Actor actor)
	{
		super(actor);

		this.client = client;
		this.actor = actor;
	}

	public abstract int getCombatLevel();

	public abstract String getName();

	public Actor getInteracting()
	{
		int i = actor.getInteracting();
		if (i == -1)
		{
			return null;
		}

		// logic taken from runeloader.
		if (i < 32767)
		{
			return client.getNpcs()[i];
		}

		// XXX is this correct for i = 32767 ?
		i = i - 32767 - 1;
		return client.getPlayers()[i];
	}

	public int getHealthRatio()
	{
		CombatInfoList combatInfoList = actor.getCombatInfoList();
		if (combatInfoList != null)
		{
			Node node = combatInfoList.getNode();
			Node next = node.getNext();
			if (next instanceof CombatInfoListHolder)
			{
				CombatInfoListHolder combatInfoListWrapper = (CombatInfoListHolder) next;
				CombatInfoList combatInfoList1 = combatInfoListWrapper.getCombatInfo1();

				Node node2 = combatInfoList1.getNode();
				Node next2 = node2.getNext();
				if (next2 instanceof CombatInfo1)
				{
					CombatInfo1 combatInfo = (CombatInfo1) next2;
					return combatInfo.getHealthRatio();
				}
			}
		}
		return -1;
	}

	public int getHealth()
	{
		CombatInfoList combatInfoList = actor.getCombatInfoList();
		if (combatInfoList != null)
		{
			Node node = combatInfoList.getNode();
			Node next = node.getNext();
			if (next instanceof CombatInfoListHolder)
			{
				CombatInfoListHolder combatInfoListWrapper = (CombatInfoListHolder) next;
				CombatInfo2 cf = combatInfoListWrapper.getCombatInfo2();
				return cf.getHealthScale();
			}
		}
		return -1;
	}

	public Point getLocalLocation()
	{
		return new Point(getX(), getY());
	}

	private int getX()
	{
		return actor.getX();
	}

	private int getY()
	{
		return actor.getY();
	}

	public int getAnimation()
	{
		return actor.getAnimation();
	}

	public int getModelHeight()
	{
		return actor.getModelHeight();
	}

	public Polygon getCanvasTilePoly()
	{
		int plane = client.getPlane();
		int halfTile = Perspective.LOCAL_TILE_SIZE / 2;

		Point p1 = Perspective.worldToCanvas(client, getX() - halfTile, getY() - halfTile, plane);
		Point p2 = Perspective.worldToCanvas(client, getX() - halfTile, getY() + halfTile, plane);
		Point p3 = Perspective.worldToCanvas(client, getX() + halfTile, getY() + halfTile, plane);
		Point p4 = Perspective.worldToCanvas(client, getX() + halfTile, getY() - halfTile, plane);

		if (p1 == null || p2 == null || p3 == null || p4 == null)
		{
			return null;
		}

		Polygon poly = new Polygon();
		poly.addPoint(p1.getX(), p1.getY());
		poly.addPoint(p2.getX(), p2.getY());
		poly.addPoint(p3.getX(), p3.getY());
		poly.addPoint(p4.getX(), p4.getY());

		return poly;
	}

	public Point getCanvasTextLocation(Graphics2D graphics, String text, int zOffset)
	{
		int plane = client.getPlane();

		Point p = Perspective.worldToCanvas(client, getLocalLocation().getX(), getLocalLocation().getY(), plane, zOffset);

		if (p == null)
		{
			return null;
		}

		FontMetrics fm = graphics.getFontMetrics();
		Rectangle2D bounds = fm.getStringBounds(text, graphics);
		int xOffset = p.getX() - (int) (bounds.getWidth() / 2);

		return new Point(xOffset, p.getY());
	}
}
