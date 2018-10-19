/*
 * Copyright (c) 2017, Steve <steve.rs.dev@gmail.com>
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
package net.runelite.client.plugins.xpglobes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Experience;
import net.runelite.api.Point;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.plugins.xptracker.XpTrackerService;
import net.runelite.client.ui.SkillColor;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

public class XpGlobesOverlay extends Overlay
{
	private static final int MINIMUM_STEP = 10;
	private static final int PROGRESS_RADIUS_START = 90;
	private static final int PROGRESS_RADIUS_REMAINDER = 0;
	private static final int DEFAULT_START_Y = 10;
	private static final int TOOLTIP_RECT_SIZE_X = 150;
	private static final Color DARK_OVERLAY_COLOR = new Color(0, 0, 0, 180);

	private final Client client;
	private final XpGlobesPlugin plugin;
	private final XpGlobesConfig config;
	private final XpTrackerService xpTrackerService;
	private final PanelComponent xpTooltip = new PanelComponent();
	private final SkillIconManager iconManager;

	@Inject
	private XpGlobesOverlay(
		Client client,
		XpGlobesPlugin plugin,
		XpGlobesConfig config,
		XpTrackerService xpTrackerService,
		SkillIconManager iconManager)
	{
		this.iconManager = iconManager;
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		this.xpTrackerService = xpTrackerService;
		setPosition(OverlayPosition.TOP_CENTER);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		final int queueSize = plugin.getXpGlobesSize();
		if (queueSize == 0)
		{
			return null;
		}

		class GlobeComparator implements Comparator<XpGlobe>
		{
			public int compare(XpGlobe a, XpGlobe b)
			{
				return a.getSkill().compareTo(b.getSkill());
			}
		}

		List<XpGlobe> sortedXpGlobes = plugin.getXpGlobes();
		sortedXpGlobes.sort(new GlobeComparator());

		int curDrawX = 0;
		for (final XpGlobe xpGlobe : sortedXpGlobes)
		{
			renderProgressCircle(graphics, xpGlobe, curDrawX, 0, getBounds());
			curDrawX += MINIMUM_STEP + config.xpOrbSize();
		}

		// Get width of markers
		final int markersLength = (queueSize * (config.xpOrbSize())) + ((MINIMUM_STEP) * (queueSize - 1));
		return new Dimension(markersLength, config.xpOrbSize());
	}

	private void renderProgressCircle(Graphics2D graphics, XpGlobe skillToDraw, int x, int y, Rectangle bounds)
	{
		double radiusCurrentXp = skillToDraw.getSkillProgressRadius();
		double radiusToGoalXp = 360; //draw a circle

		Ellipse2D backgroundCircle = drawEllipse(graphics, x, y);

		drawSkillImage(graphics, skillToDraw, x, y);

		Point mouse = client.getMouseCanvasPosition();
		int mouseX = mouse.getX() - bounds.x;
		int mouseY = mouse.getY() - bounds.y;

		// If mouse is hovering the globe
		if (backgroundCircle.contains(mouseX, mouseY))
		{
			// Fill a darker overlay circle
			graphics.setColor(DARK_OVERLAY_COLOR);
			graphics.fill(backgroundCircle);

			drawProgressLabel(graphics, skillToDraw, x, y);

			if (config.enableTooltips())
			{
				drawTooltip(graphics, skillToDraw, backgroundCircle);
			}
		}

		graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		drawProgressArc(
			graphics,
			x, y,
			config.xpOrbSize(), config.xpOrbSize(),
			PROGRESS_RADIUS_REMAINDER, radiusToGoalXp,
			5,
			config.progressOrbOutLineColor()
		);
		drawProgressArc(
			graphics,
			x, y,
			config.xpOrbSize(), config.xpOrbSize(),
			PROGRESS_RADIUS_START, radiusCurrentXp,
			config.progressArcStrokeWidth(),
			config.enableCustomArcColor() ? config.progressArcColor() : SkillColor.find(skillToDraw.getSkill()).getColor());
	}

	private void drawProgressLabel(Graphics2D graphics, XpGlobe globe, int x, int y)
	{
		final int currentExp = globe.getCurrentXp();
		final int goalExp = globe.getGoalXp();
		final int expForLevel = Experience.getXpForLevel(globe.getCurrentLevel());

		// Convert to int just to limit the decimal cases
		String progress = (int) (globe.getSkillProgress(expForLevel, currentExp, goalExp)) + "%";

		final FontMetrics metrics = graphics.getFontMetrics();
		int drawX = x + (config.xpOrbSize() / 2) - (metrics.stringWidth(progress) / 2);
		int drawY = y + (config.xpOrbSize() / 2) + (metrics.getHeight() / 2);

		OverlayUtil.renderTextLocation(graphics, new Point(drawX, drawY), progress, Color.WHITE);
	}

	private void drawProgressArc(Graphics2D graphics, int x, int y, int w, int h, double radiusStart, double radiusEnd, int strokeWidth, Color color)
	{
		Stroke stroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		graphics.setColor(color);
		graphics.draw(new Arc2D.Double(
			x, y,
			w, h,
			radiusStart, radiusEnd,
			Arc2D.OPEN));
		graphics.setStroke(stroke);
	}

	private Ellipse2D drawEllipse(Graphics2D graphics, int x, int y)
	{
		graphics.setColor(config.progressOrbBackgroundColor());
		Ellipse2D ellipse = new Ellipse2D.Double(x, y, config.xpOrbSize(), config.xpOrbSize());
		graphics.fill(ellipse);
		graphics.draw(ellipse);
		return ellipse;
	}

	private void drawSkillImage(Graphics2D graphics, XpGlobe xpGlobe, int x, int y)
	{
		BufferedImage skillImage = iconManager.getSkillImage(xpGlobe.getSkill());

		if (skillImage == null)
		{
			return;
		}

		graphics.drawImage(
			skillImage,
			x + (config.xpOrbSize() / 2) - (skillImage.getWidth() / 2),
			y + (config.xpOrbSize() / 2) - (skillImage.getHeight() / 2),
			null
		);
	}

	private void drawTooltip(Graphics2D graphics, XpGlobe mouseOverSkill, Ellipse2D drawnGlobe)
	{
		//draw tooltip under the globe of the mouse location
		int x = (int) drawnGlobe.getX() - (TOOLTIP_RECT_SIZE_X / 2) + (config.xpOrbSize() / 2);
		int y = (int) drawnGlobe.getY() + config.xpOrbSize() + 10;

		// reset the timer on XpGlobe to prevent it from disappearing while hovered over it
		mouseOverSkill.setTime(Instant.now());

		String skillName = mouseOverSkill.getSkillName();
		String skillLevel = Integer.toString(mouseOverSkill.getCurrentLevel());

		DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
		String skillCurrentXp = decimalFormat.format(mouseOverSkill.getCurrentXp());

		xpTooltip.getChildren().clear();
		xpTooltip.setPreferredLocation(new java.awt.Point(x, y));
		xpTooltip.setPreferredSize(new Dimension(TOOLTIP_RECT_SIZE_X, 0));

		xpTooltip.getChildren().add(LineComponent.builder()
			.left(skillName)
			.right(skillLevel)
			.build());

		xpTooltip.getChildren().add(LineComponent.builder()
			.left("Current xp:")
			.leftColor(Color.ORANGE)
			.right(skillCurrentXp)
			.build());

		if (mouseOverSkill.getGoalXp() != -1)
		{
			int actionsLeft = xpTrackerService.getActionsLeft(mouseOverSkill.getSkill());
			if (actionsLeft != Integer.MAX_VALUE)
			{
				String actionsLeftString = decimalFormat.format(actionsLeft);
				xpTooltip.getChildren().add(LineComponent.builder()
					.left("Actions left:")
					.leftColor(Color.ORANGE)
					.right(actionsLeftString)
					.build());
			}

			int xpLeft = mouseOverSkill.getGoalXp() - mouseOverSkill.getCurrentXp();
			String skillXpToLvl = decimalFormat.format(xpLeft);
			xpTooltip.getChildren().add(LineComponent.builder()
				.left("Xp to level:")
				.leftColor(Color.ORANGE)
				.right(skillXpToLvl)
				.build());

			int xpHr = xpTrackerService.getXpHr(mouseOverSkill.getSkill());
			if (xpHr != 0)
			{
				String xpHrString = decimalFormat.format(xpHr);
				xpTooltip.getChildren().add(LineComponent.builder()
					.left("Xp per hour:")
					.leftColor(Color.ORANGE)
					.right(xpHrString)
					.build());
			}
		}

		xpTooltip.render(graphics);
	}
}
