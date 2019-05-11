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
package net.runelite.api.widgets;

import java.awt.Rectangle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.runelite.api.Point;

<<<<<<< HEAD
=======
/**
 * An item that is being represented in a {@link Widget}.
 */
<<<<<<< HEAD
>>>>>>> upstream/master
public class WidgetItem
{
	private final int id;
	private final int quantity;
	private final int index;
	private final Rectangle canvasBounds;

	public WidgetItem(int id, int quantity, int index, Rectangle canvasBounds)
	{
		this.id = id;
		this.quantity = quantity;
		this.index = index;
		this.canvasBounds = canvasBounds;
	}

	@Override
	public String toString()
	{
		return "WidgetItem{" + "id=" + id + ", quantity=" + quantity + ", index=" + index + ", canvasBounds=" + canvasBounds + '}';
	}

<<<<<<< HEAD
=======
=======
@AllArgsConstructor
@ToString
@Getter
public class WidgetItem
{
>>>>>>> upstream/master
	/**
	 * The ID of the item represented.
	 *
	 * @see net.runelite.api.ItemID
	 */
<<<<<<< HEAD
>>>>>>> upstream/master
	public int getId()
	{
		return id;
	}

<<<<<<< HEAD
=======
=======
	private final int id;
>>>>>>> upstream/master
	/**
	 * The quantity of the represented item.
	 */
<<<<<<< HEAD
>>>>>>> upstream/master
	public int getQuantity()
	{
		return quantity;
	}

<<<<<<< HEAD
=======
=======
	private final int quantity;
>>>>>>> upstream/master
	/**
	 * The index position of this WidgetItem inside its parents
	 * WidgetItem array.
	 *
	 * @see Widget#getWidgetItems()
	 */
<<<<<<< HEAD
>>>>>>> upstream/master
	public int getIndex()
	{
		return index;
	}

<<<<<<< HEAD
=======
=======
	private final int index;
>>>>>>> upstream/master
	/**
	 * The area where the widget is drawn on the canvas.
	 */
<<<<<<< HEAD
>>>>>>> upstream/master
	public Rectangle getCanvasBounds()
	{
		return canvasBounds;
	}
=======
	private final Rectangle canvasBounds;
	/**
	 * The widget which contains this item.
	 */
	private final Widget widget;
>>>>>>> upstream/master

<<<<<<< HEAD
=======
	/**
	 * Gets the upper-left coordinate of where the widget is being drawn
	 * on the canvas.
	 *
	 * @return the upper-left coordinate of where this widget is drawn
	 */
>>>>>>> upstream/master
	public Point getCanvasLocation()
	{
		return new Point((int) canvasBounds.getX(), (int) canvasBounds.getY());
	}

}
