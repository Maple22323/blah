/*
 * Copyright (c) 2020 Mitchell <https://github.com/Mitchell-Kovacs>
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
package net.runelite.client.plugins.pyramidplunder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.time.temporal.ValueRange;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.ObjectComposition;
import net.runelite.api.Point;
import net.runelite.api.Skill;
import net.runelite.api.TileObject;
import net.runelite.api.Varbits;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import static net.runelite.client.plugins.pyramidplunder.PyramidPlunderPlugin.GRAND_GOLD_CHEST_CLOSED_ID;
import static net.runelite.client.plugins.pyramidplunder.PyramidPlunderPlugin.GRAND_GOLD_CHEST_ID;
import static net.runelite.client.plugins.pyramidplunder.PyramidPlunderPlugin.SARCOPHAGUS_CLOSED_ID;
import static net.runelite.client.plugins.pyramidplunder.PyramidPlunderPlugin.SARCOPHAGUS_ID;
import static net.runelite.client.plugins.pyramidplunder.PyramidPlunderPlugin.SPEARTRAP_ID;
import static net.runelite.client.plugins.pyramidplunder.PyramidPlunderPlugin.TOMB_DOOR_CLOSED_ID;
import static net.runelite.client.plugins.pyramidplunder.PyramidPlunderPlugin.TOMB_DOOR_WALL_IDS;
import static net.runelite.client.plugins.pyramidplunder.PyramidPlunderPlugin.URN_CLOSED_IDS;
import static net.runelite.client.plugins.pyramidplunder.PyramidPlunderPlugin.URN_IDS;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.util.ColorUtil;

@Slf4j
class PyramidPlunderOverlay extends Overlay
{
	private static final int MAX_DISTANCE = 2350;

	private final Client client;
	private final PyramidPlunderPlugin plugin;
	private final PyramidPlunderConfig config;

	private static final Map<ValueRange, Integer> MIN_REQ_PER_ROOM = constructMinReqPerRoomMap();



	private static Map<ValueRange, Integer> constructMinReqPerRoomMap() {
		Map<ValueRange, Integer> minimumRequirementAndRoom = new HashMap<>();
		minimumRequirementAndRoom.put(ValueRange.of(21, 30), 1);
		minimumRequirementAndRoom.put(ValueRange.of(31, 40), 2);
		minimumRequirementAndRoom.put(ValueRange.of(41, 50), 3);
		minimumRequirementAndRoom.put(ValueRange.of(51, 60), 4);
		minimumRequirementAndRoom.put(ValueRange.of(61, 70), 5);
		minimumRequirementAndRoom.put(ValueRange.of(71, 80), 6);
		minimumRequirementAndRoom.put(ValueRange.of(81, 90), 7);
		minimumRequirementAndRoom.put(ValueRange.of(91, 99), 8);
		return Collections.unmodifiableMap(minimumRequirementAndRoom);
	}

	private Integer penultimateRoom;
	private int thievingLevel;



	@Inject
	private PyramidPlunderOverlay(Client client, PyramidPlunderPlugin plugin, PyramidPlunderConfig config)
	{
		super(plugin);
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
		this.client = client;
		this.plugin = plugin;
		this.config = config;

		this.thievingLevel = client.getRealSkillLevel(Skill.THIEVING);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Widget ppWidget = client.getWidget(WidgetInfo.PYRAMID_PLUNDER_DATA);
		if (ppWidget == null)
		{
			return null;
		}
		ppWidget.setHidden(config.hideTimer());

		if(this.penultimateRoom == null) {
			this.penultimateRoom = getPenultimateRoom();
		}
		if(this.thievingLevel != client.getRealSkillLevel(Skill.THIEVING)) {
			this.thievingLevel = client.getRealSkillLevel(Skill.THIEVING);
			this.penultimateRoom = getPenultimateRoom();
		}

		// Highlight convex hulls of urns, chests, and sarcophagus
		int currentFloor = client.getVar(Varbits.PYRAMID_PLUNDER_ROOM);
		for (GameObject object : plugin.getObjectsToHighlight())
		{
			if (shouldHighlightObjectOnFloor(object.getId(), currentFloor) && !objectTooFarAway(object))
			{
				ObjectComposition imposter = client.getObjectDefinition(object.getId()).getImpostor();

				if (URN_CLOSED_IDS.contains(imposter.getId())
					|| GRAND_GOLD_CHEST_CLOSED_ID == imposter.getId()
					|| SARCOPHAGUS_CLOSED_ID == imposter.getId())
				{
					Shape shape = object.getConvexHull();

					if (shape != null)
					{
						OverlayUtil.renderPolygon(graphics, shape, config.highlightContainersColor());
					}
				}
			}
		}

		Point mousePosition = client.getMouseCanvasPosition();

		// Highlight clickboxes of speartraps and tomb doors
		plugin.getTilesToHighlight().forEach((object, tile) ->
		{
			if (!config.highlightDoors() && TOMB_DOOR_WALL_IDS.contains(object.getId())
				|| !config.highlightSpeartraps() && SPEARTRAP_ID == object.getId()
				|| tile.getPlane() != client.getPlane()
				|| objectTooFarAway(object))
			{
				return;
			}

			Color highlightColor;
			if (SPEARTRAP_ID == object.getId())
			{
				// this varbit is set to 1 when you enter a room and 0 once you get passed the spike traps
				if (client.getVar(Varbits.PYRAMID_PLUNDER_ROOM_LOCATION) != 1)
				{
					return;
				}

				highlightColor = config.highlightSpeartrapsColor();
			}
			else
			{
				ObjectComposition imposter = client.getObjectDefinition(object.getId()).getImpostor();
				if (imposter.getId() != TOMB_DOOR_CLOSED_ID)
				{
					return;
				}

				highlightColor = config.highlightDoorsColor();
			}

			Shape objectClickbox = object.getClickbox();
			if (objectClickbox != null)
			{
				if (objectClickbox.contains(mousePosition.getX(), mousePosition.getY()))
				{
					graphics.setColor(highlightColor.darker());
				}
				else
				{
					graphics.setColor(highlightColor);
				}

				graphics.draw(objectClickbox);
				graphics.setColor(ColorUtil.colorWithAlpha(highlightColor, highlightColor.getAlpha() / 5));
				graphics.fill(objectClickbox);
			}
		});

		return null;
	}

	private boolean objectTooFarAway(TileObject object) {
		LocalPoint playerLocation = client.getLocalPlayer().getLocalLocation();

		return object.getLocalLocation().distanceTo(playerLocation) >= MAX_DISTANCE;
	}

	private boolean shouldHighlightObjectOnFloor(int objectId, int currentFloor) {
		if(URN_IDS.contains(objectId)) {
			boolean currentFloorPenultimateAndUp = currentFloor >= this.penultimateRoom;
			return currentFloor >= config.highlightUrnsFloor() || config.highlightPenultimateUrns() && currentFloorPenultimateAndUp && URN_IDS.contains(objectId);
		} else if(GRAND_GOLD_CHEST_ID == objectId) {
			return currentFloor >= config.highlightChestFloor();
		} else if(SARCOPHAGUS_ID == objectId) {
			return currentFloor >= config.highlightSarcophagusFloor();
		}
		return false;
	}

	private Integer getPenultimateRoom()
	{
		AtomicReference<Integer> penultimateRoom = new AtomicReference<>();
		MIN_REQ_PER_ROOM.forEach((minRequirement, roomNumber) -> {
			log.info(String.valueOf(minRequirement));
			if(minRequirement.isValidIntValue(thievingLevel)) {
				penultimateRoom.set(roomNumber == 1 ? 1 : roomNumber - 1);
			}
		});
		return penultimateRoom.get();
	}
}
