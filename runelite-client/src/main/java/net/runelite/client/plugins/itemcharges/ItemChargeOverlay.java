/*
 * Copyright (c) 2017, Seth <Sethtroll3@gmail.com>
 * Copyright (c) 2019, Aleios <https://github.com/aleios>
 * Copyright (c) 2020, Unmoon <https://github.com/unmoon>
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
package net.runelite.client.plugins.itemcharges;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.inject.Inject;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.ui.overlay.components.TextComponent;

class ItemChargeOverlay extends WidgetItemOverlay
{
	private final ItemChargePlugin itemChargePlugin;
	private final ItemChargeConfig config;

	@Inject
	ItemChargeOverlay(ItemChargePlugin itemChargePlugin, ItemChargeConfig config)
	{
		this.itemChargePlugin = itemChargePlugin;
		this.config = config;
		showOnInventory();
		showOnEquipment();
	}

	@Override
	public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
	{
		ArrayList<Integer> chargesList = new ArrayList<>();
		ItemWithConfig itemWithConfig = ItemWithConfig.findItem(itemId);
		if (itemWithConfig != null)
		{
			if (!itemWithConfig.getType().getEnabled().test(config))
			{
				return;
			}
			for (int i = 0; i < itemWithConfig.getConfigKey().length; i++)
			{
				chargesList.add(i, itemChargePlugin.getItemCharges(itemWithConfig.getConfigKey()[i]));
			}
		}
		else
		{
			ItemWithCharge chargeItem = ItemWithCharge.findItem(itemId);
			if (chargeItem == null)
			{
				return;
			}

			ItemChargeType type = chargeItem.getType();
			if (!type.getEnabled().test(config))
			{
				return;
			}

			chargesList.add(0, chargeItem.getCharges());
		}

		graphics.setFont(FontManager.getRunescapeSmallFont());

		final Rectangle bounds = widgetItem.getCanvasBounds();
		ArrayList<TextComponent> textComponents = new ArrayList<>();
		final TextComponent textComponent0 = new TextComponent();
		final TextComponent textComponent1 = new TextComponent();
		final TextComponent textComponent2 = new TextComponent();
		final TextComponent textComponent3 = new TextComponent();
		textComponents.add(textComponent0);
		textComponents.add(textComponent1);
		textComponents.add(textComponent2);
		textComponents.add(textComponent3);
		textComponents.get(0).setPosition(new Point(bounds.x - 1, bounds.y + 15));
		textComponents.get(1).setPosition(new Point(bounds.x - 1, bounds.y + 30));
		textComponents.get(2).setPosition(new Point(bounds.x - 16, bounds.y + 15));
		textComponents.get(3).setPosition(new Point(bounds.x - 16, bounds.y + 30));

		for (int i = 0; i < chargesList.size(); i++)
		{
			textComponents.get(i).setText((chargesList.get(i) < 0) ? " " : String.valueOf(chargesList.get(i)));
			textComponents.get(i).setColor(itemChargePlugin.getColor(chargesList.get(i)));
			textComponents.get(i).render(graphics);
		}
	}
}
