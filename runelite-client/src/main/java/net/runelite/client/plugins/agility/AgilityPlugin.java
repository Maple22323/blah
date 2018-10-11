/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
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
package net.runelite.client.plugins.agility;

import com.google.common.collect.ImmutableSet;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import static net.runelite.api.Skill.AGILITY;
import static net.runelite.client.plugins.agility.AgilityTimer.UnderwaterAgility;
import static net.runelite.client.plugins.agility.AgilityTimer.AgilityArena;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.DecorativeObjectChanged;
import net.runelite.api.events.DecorativeObjectDespawned;
import net.runelite.api.events.DecorativeObjectSpawned;
import net.runelite.api.events.ExperienceChanged;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.GroundObjectChanged;
import net.runelite.api.events.GroundObjectDespawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.WallObjectChanged;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

@PluginDescriptor(
	name = "Agility",
	description = "Show helpful information about agility courses and obstacles",
	tags = {"grace", "marks", "overlay", "shortcuts", "skilling", "traps"}
)
@Slf4j
public class AgilityPlugin extends Plugin
{
	private static final int AGILITY_ARENA_REGION_ID = 11157;
	private static final Set<Integer> UNDERWATER_AGILITY_REGION_ID = ImmutableSet.of(15008, 15264);

	@Getter
	private final Map<TileObject, Tile> obstacles = new HashMap<>();

	@Getter
	private Tile markOfGrace;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private AgilityOverlay agilityOverlay;

	@Inject
	private LapCounterOverlay lapCounterOverlay;

	@Inject
	private Notifier notifier;

	@Inject
	private Client client;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Inject
	private AgilityConfig config;

	@Inject
	private ItemManager itemManager;

	@Getter
	private AgilitySession session;

	private int lastAgilityXp;
	private WorldPoint lastArenaTicketPosition;
	private WorldPoint lastUnderwaterChestPoint;

	@Provides
	AgilityConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AgilityConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(agilityOverlay);
		overlayManager.add(lapCounterOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		removeAgilityArenaTimer();
		removeUnderwaterAgilityTimer();
		overlayManager.remove(agilityOverlay);
		overlayManager.remove(lapCounterOverlay);
		markOfGrace = null;
		obstacles.clear();
		session = null;
	}

	@Subscribe
	public void onGameStateChange(GameStateChanged event)
	{
		switch (event.getGameState())
		{
			case HOPPING:
			case LOGIN_SCREEN:
				session = null;
				lastArenaTicketPosition = null;
				removeAgilityArenaTimer();
				break;
			case LOADING:
				markOfGrace = null;
				obstacles.clear();
				break;
			case LOGGED_IN:
				if (!isInAgilityArena())
				{
					lastArenaTicketPosition = null;
					removeAgilityArenaTimer();
				}
				break;
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!config.showAgilityArenaTimer())
		{
			removeAgilityArenaTimer();
		}

		if (!config.showUnderwaterAgilityTimer())
		{
			removeUnderwaterAgilityTimer();
		}
	}

	@Subscribe
	public void onExperienceChanged(ExperienceChanged event)
	{
		if (event.getSkill() != AGILITY || !config.showLapCount())
		{
			return;
		}

		// Determine how much EXP was actually gained
		int agilityXp = client.getSkillExperience(AGILITY);
		int skillGained = agilityXp - lastAgilityXp;
		lastAgilityXp = agilityXp;

		// Get course
		Courses course = Courses.getCourse(client.getLocalPlayer().getWorldLocation().getRegionID());
		if (course == null
			|| (course.getCourseEndWorldPoints().length == 0
			? Math.abs(course.getLastObstacleXp() - skillGained) > 1
			: Arrays.stream(course.getCourseEndWorldPoints()).noneMatch(wp -> wp.equals(client.getLocalPlayer().getWorldLocation()))))
		{
			return;
		}

		if (session != null && session.getCourse() == course)
		{
			session.incrementLapCount(client);
		}
		else
		{
			session = new AgilitySession(course);
			// New course found, reset lap count and set new course
			session.resetLapCount();
			session.incrementLapCount(client);
		}
	}

	@Subscribe
	public void onItemSpawned(ItemSpawned itemSpawned)
	{
		if (obstacles.isEmpty())
		{
			return;
		}

		final Item item = itemSpawned.getItem();
		final Tile tile = itemSpawned.getTile();

		if (item.getId() == ItemID.MARK_OF_GRACE)
		{
			markOfGrace = tile;
		}
	}

	@Subscribe
	public void onItemDespawned(ItemDespawned itemDespawned)
	{
		final Item item = itemDespawned.getItem();

		if (item.getId() == ItemID.MARK_OF_GRACE)
		{
			markOfGrace = null;
		}
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		if (isInAgilityArena())
		{
			// Hint arrow has no plane, and always returns the current plane
			WorldPoint newTicketPosition = client.getHintArrowPoint();
			WorldPoint oldTickPosition = lastArenaTicketPosition;

			lastArenaTicketPosition = newTicketPosition;

			if (oldTickPosition != null && newTicketPosition != null
				&& (oldTickPosition.getX() != newTicketPosition.getX() || oldTickPosition.getY() != newTicketPosition.getY()))
			{
				log.debug("Ticked position moved from {} to {}", oldTickPosition, newTicketPosition);

				if (config.notifyAgilityArena())
				{
					notifier.notify("Ticket location changed");
				}

				if (config.showAgilityArenaTimer())
				{
					showNewAgilityArenaTimer();
				}
			}
		}

		if (isUnderWater())
		{
			WorldPoint newUnderwaterLocation = client.getHintArrowPoint();
			WorldPoint oldUnderwaterLocation = lastUnderwaterChestPoint;
			lastUnderwaterChestPoint = newUnderwaterLocation;

			if (oldUnderwaterLocation != null && newUnderwaterLocation != null
					&& (oldUnderwaterLocation.getX() != newUnderwaterLocation.getX()
					|| oldUnderwaterLocation.getY() != newUnderwaterLocation.getY()))
			{
				if (config.showUnderwaterAgilityTimer())
				{
					showUnderwaterAgilityTimer();
				}
			}
		}
	}

	private boolean isInAgilityArena()
	{
		Player local = client.getLocalPlayer();

		if (local == null)
		{
			return false;
		}

		WorldPoint location = local.getWorldLocation();
		return location.getRegionID() == AGILITY_ARENA_REGION_ID;
	}

	private boolean isUnderWater()
	{
		Player local = client.getLocalPlayer();

		if (local == null)
		{
			return false;
		}
		WorldPoint location = local.getWorldLocation();
		return UNDERWATER_AGILITY_REGION_ID.contains(location.getRegionID());
	}

	private void removeUnderwaterAgilityTimer()
	{
		removeAgilityTimer(UnderwaterAgility);
	}

	private void showUnderwaterAgilityTimer()
	{
		createAgilityTimer(UnderwaterAgility);
	}

	private void removeAgilityArenaTimer()
	{
		removeAgilityTimer(AgilityArena);
	}

	private void showNewAgilityArenaTimer()
	{
		createAgilityTimer(AgilityArena);
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		onTileObject(event.getTile(), null, event.getGameObject());
	}

	@Subscribe
	public void onGameObjectChanged(GameObjectChanged event)
	{
		onTileObject(event.getTile(), event.getPrevious(), event.getGameObject());
	}

	@Subscribe
	public void onGameObjectDeSpawned(GameObjectDespawned event)
	{
		onTileObject(event.getTile(), event.getGameObject(), null);
	}

	@Subscribe
	public void onGroundObjectSpawned(GroundObjectSpawned event)
	{
		onTileObject(event.getTile(), null, event.getGroundObject());
	}

	@Subscribe
	public void onGroundObjectChanged(GroundObjectChanged event)
	{
		onTileObject(event.getTile(), event.getPrevious(), event.getGroundObject());
	}

	@Subscribe
	public void onGroundObjectDeSpawned(GroundObjectDespawned event)
	{
		onTileObject(event.getTile(), event.getGroundObject(), null);
	}

	@Subscribe
	public void onWallObjectSpawned(WallObjectSpawned event)
	{
		onTileObject(event.getTile(), null, event.getWallObject());
	}

	@Subscribe
	public void onWallObjectChanged(WallObjectChanged event)
	{
		onTileObject(event.getTile(), event.getPrevious(), event.getWallObject());
	}

	@Subscribe
	public void onWallObjectDeSpawned(WallObjectDespawned event)
	{
		onTileObject(event.getTile(), event.getWallObject(), null);
	}

	@Subscribe
	public void onDecorativeObjectSpawned(DecorativeObjectSpawned event)
	{
		onTileObject(event.getTile(), null, event.getDecorativeObject());
	}

	@Subscribe
	public void onDecorativeObjectChanged(DecorativeObjectChanged event)
	{
		onTileObject(event.getTile(), event.getPrevious(), event.getDecorativeObject());
	}

	@Subscribe
	public void onDecorativeObjectDeSpawned(DecorativeObjectDespawned event)
	{
		onTileObject(event.getTile(), event.getDecorativeObject(), null);
	}

	private void onTileObject(Tile tile, TileObject oldObject, TileObject newObject)
	{
		obstacles.remove(oldObject);

		if (newObject == null)
		{
			return;
		}

		if (Obstacles.COURSE_OBSTACLE_IDS.contains(newObject.getId()) ||
			Obstacles.SHORTCUT_OBSTACLE_IDS.contains(newObject.getId()) ||
			(Obstacles.TRAP_OBSTACLE_IDS.contains(newObject.getId())
				&& Obstacles.TRAP_OBSTACLE_REGIONS.contains(newObject.getWorldLocation().getRegionID())))
		{
			obstacles.put(newObject, tile);
		}
	}
	private AgilityTimerTimer createAgilityTimer(final AgilityTimer timer)
	{
		removeAgilityTimer(timer);
		BufferedImage image = itemManager.getImage(timer.getItemID());
		AgilityTimerTimer countdownTimer = new AgilityTimerTimer(timer, this, image);
		countdownTimer.setTooltip(timer.getDescription());
		infoBoxManager.addInfoBox(countdownTimer);
		return countdownTimer;
	}

	private void removeAgilityTimer(AgilityTimer timer)
	{
		infoBoxManager.removeIf(t -> t instanceof AgilityTimerTimer && ((AgilityTimerTimer) t).getTimer() == timer);
	}
}