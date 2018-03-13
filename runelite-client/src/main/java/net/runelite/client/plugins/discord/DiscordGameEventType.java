/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
package net.runelite.client.plugins.discord;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Skill;
import static net.runelite.api.Skill.AGILITY;
import static net.runelite.api.Skill.ATTACK;
import static net.runelite.api.Skill.CONSTRUCTION;
import static net.runelite.api.Skill.COOKING;
import static net.runelite.api.Skill.CRAFTING;
import static net.runelite.api.Skill.DEFENCE;
import static net.runelite.api.Skill.FARMING;
import static net.runelite.api.Skill.FIREMAKING;
import static net.runelite.api.Skill.FISHING;
import static net.runelite.api.Skill.FLETCHING;
import static net.runelite.api.Skill.HERBLORE;
import static net.runelite.api.Skill.HITPOINTS;
import static net.runelite.api.Skill.HUNTER;
import static net.runelite.api.Skill.MAGIC;
import static net.runelite.api.Skill.MINING;
import static net.runelite.api.Skill.PRAYER;
import static net.runelite.api.Skill.RANGED;
import static net.runelite.api.Skill.RUNECRAFT;
import static net.runelite.api.Skill.SLAYER;
import static net.runelite.api.Skill.SMITHING;
import static net.runelite.api.Skill.STRENGTH;
import static net.runelite.api.Skill.THIEVING;
import static net.runelite.api.Skill.WOODCUTTING;

@AllArgsConstructor
@Getter
public enum DiscordGameEventType
{
	IN_GAME("In Game", false, true),
	IN_MENU("In Menu", false, false),
	RAID("In Raid", "raids", 2, false, true),
	TRAINING_ATTACK(ATTACK, DiscordGameEventType::combatSkillChanged),
	TRAINING_DEFENCE(DEFENCE, DiscordGameEventType::combatSkillChanged),
	TRAINING_STRENGTH(STRENGTH, DiscordGameEventType::combatSkillChanged),
	TRAINING_HITPOINTS(HITPOINTS, DiscordGameEventType::combatSkillChanged),
	TRAINING_SLAYER(SLAYER, 1, DiscordGameEventType::combatSkillChanged),
	TRAINING_RANGED(RANGED, DiscordGameEventType::combatSkillChanged),
	TRAINING_MAGIC(MAGIC, DiscordGameEventType::combatSkillChanged),
	TRAINING_PRAYER(PRAYER),
	TRAINING_COOKING(COOKING),
	TRAINING_WOODCUTTING(WOODCUTTING),
	TRAINING_FLETCHING(FLETCHING),
	TRAINING_FISHING(FISHING, 1),
	TRAINING_FIREMAKING(FIREMAKING),
	TRAINING_CRAFTING(CRAFTING),
	TRAINING_SMITHING(SMITHING),
	TRAINING_MINING(MINING),
	TRAINING_HERBLORE(HERBLORE),
	TRAINING_AGILITY(AGILITY),
	TRAINING_THIEVING(THIEVING),
	TRAINING_FARMING(FARMING),
	TRAINING_RUNECRAFT(RUNECRAFT),
	TRAINING_HUNTER(HUNTER),
	TRAINING_CONSTRUCTION(CONSTRUCTION);

	private static final Set<Skill> COMBAT_SKILLS = Sets.newHashSet(ATTACK, STRENGTH, DEFENCE, HITPOINTS, SLAYER, RANGED, MAGIC);
	private Skill skill;
	private String imageKey;
	private String details;
	@Setter
	private String state;
	private boolean trackTime = true;
	private boolean considerDelay = true;
	private Function<DiscordGameEventType, Boolean> isChanged = (l) -> true;
	private int priority = 0;

	DiscordGameEventType(String details, String minigame, int priority, boolean considerDelay, boolean trackTime)
	{
		this.imageKey = getImageKey(minigame);
		this.details = details;
		this.priority = priority;
		this.considerDelay = considerDelay;
		this.trackTime = trackTime;
	}

	DiscordGameEventType(String details, boolean considerDelay, boolean trackTime)
	{
		this.imageKey = "default";
		this.details = details;
		this.considerDelay = considerDelay;
		this.trackTime = trackTime;
	}

	DiscordGameEventType(Skill skill, int priority, Function<DiscordGameEventType, Boolean> isChanged)
	{
		this.imageKey = getImageKey(skill);
		this.details = training(skill);
		this.priority = priority;
		this.isChanged = isChanged;
	}

	DiscordGameEventType(Skill skill, Function<DiscordGameEventType, Boolean> isChanged)
	{
		this.imageKey = getImageKey(skill);
		this.details = training(skill);
		this.isChanged = isChanged;
	}

	DiscordGameEventType(Skill skill)
	{
		this.imageKey = getImageKey(skill);
		this.details = training(skill);
	}

	DiscordGameEventType(Skill skill, int priority)
	{
		this.imageKey = getImageKey(skill);
		this.details = training(skill);
		this.priority = priority;
	}

	private static String getImageKey(final Skill skill)
	{
		return getImageKey(skill.getName().toLowerCase());
	}

	private static String getImageKey(final String what)
	{
		return "icon_" + what;
	}

	public static String training(final Skill skill)
	{
		return training(skill.getName());
	}

	private static String training(final String what)
	{
		return "Training: " + what;
	}

	static boolean combatSkillChanged(final DiscordGameEventType l)
	{
		for (Skill skill : Skill.values())
		{
			if (l.getSkill() == skill)
			{
				return !COMBAT_SKILLS.contains(skill);
			}
		}

		return true;
	}

	public static DiscordGameEventType fromSkill(final Skill skill)
	{
		switch (skill)
		{
			case ATTACK: return TRAINING_ATTACK;
			case DEFENCE: return TRAINING_DEFENCE;
			case STRENGTH: return TRAINING_STRENGTH;
			case RANGED: return TRAINING_RANGED;
			case PRAYER: return TRAINING_PRAYER;
			case MAGIC: return TRAINING_MAGIC;
			case COOKING: return TRAINING_COOKING;
			case WOODCUTTING: return TRAINING_WOODCUTTING;
			case FLETCHING: return TRAINING_FLETCHING;
			case FISHING: return TRAINING_FISHING;
			case FIREMAKING: return TRAINING_FIREMAKING;
			case CRAFTING: return TRAINING_CRAFTING;
			case SMITHING: return TRAINING_SMITHING;
			case MINING: return TRAINING_MINING;
			case HERBLORE: return TRAINING_HERBLORE;
			case AGILITY: return TRAINING_AGILITY;
			case THIEVING: return TRAINING_THIEVING;
			case SLAYER: return TRAINING_SLAYER;
			case FARMING: return TRAINING_FARMING;
			case RUNECRAFT: return TRAINING_RUNECRAFT;
			case HUNTER: return TRAINING_HUNTER;
			case CONSTRUCTION: return TRAINING_CONSTRUCTION;
		}

		return null;
	}
}
