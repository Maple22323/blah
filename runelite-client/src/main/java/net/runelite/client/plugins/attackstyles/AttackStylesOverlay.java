/*
 * Copyright (c) 2017, honeyhoney <https://github.com/honeyhoney>
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
package net.runelite.client.plugins.attackstyles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.ComponentOrientation;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.TooltipComponent;
import net.runelite.client.util.ColorUtil;

class AttackStylesOverlay extends OverlayPanel
{
	private final AttackStylesPlugin plugin;
	private final AttackStylesConfig config;
	private final SkillIconManager iconManager;

	@Inject
	private AttackStylesOverlay(AttackStylesPlugin plugin, AttackStylesConfig config, SkillIconManager iconManager)
	{
		super(plugin);
		setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
		this.plugin = plugin;
		this.config = config;
		this.iconManager = iconManager;
		getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "Attack style overlay"));
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		boolean warnedSkillSelected = plugin.isWarnedSkillSelected();

		if (warnedSkillSelected || config.alwaysShowStyle())
		{
			final AttackStyle attackStyle = plugin.getAttackStyle();

			if (attackStyle == null)
			{
				return null;
			}

			TooltipComponent tooltipComponent = new TooltipComponent();
			tooltipComponent.setText(ColorUtil.wrapWithColorTag(
				attackStyle.getName(),
				warnedSkillSelected ? Color.RED : Color.WHITE));
			tooltipComponent.setBackgroundColor(ComponentConstants.NO_BACKGROUND_COLOR);
			panelComponent.getChildren().add(tooltipComponent);

			for (Skill skill : attackStyle.getSkills())
			{
				BufferedImage bufferedImage = iconManager.getSkillImage(skill);
				if (bufferedImage != null)
				{
					panelComponent.getChildren().add(new ImageComponent(bufferedImage));
				}
			}

			panelComponent.setOrientation(ComponentOrientation.HORIZONTAL);
			panelComponent.setGap(new Point(2, 0));

			return super.render(graphics);
		}

		return null;
	}
}
