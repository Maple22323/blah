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
 */package net.runelite.client.plugins.achievementdiary;

import lombok.Getter;
import net.runelite.api.Skill;
import net.runelite.api.Quest;

@Getter
public class Requirement
{
	private final Skill skill;
	private final Quest quest;
	private final String customRequirement;
	private final int levelRequirement;
	private final boolean started;
	private final Requirement[] altRequirements;

	public Requirement(Skill skill, int levelRequirement, Requirement... altRequirements)
	{
		this.skill = skill;
		this.quest = null;
		this.customRequirement = null;
		this.levelRequirement = levelRequirement;
		this.started = false;
		this.altRequirements = altRequirements;
	}

	public Requirement(Quest quest, Requirement... altRequirements)
	{
		this.skill = null;
		this.quest = quest;
		this.customRequirement = null;
		this.levelRequirement = 0;
		this.started = false;
		this.altRequirements = altRequirements;
	}

	public Requirement(Quest quest, boolean started, Requirement... altRequirements)
	{
		this.skill = null;
		this.quest = quest;
		this.customRequirement = null;
		this.levelRequirement = 0;
		this.started = started;
		this.altRequirements = altRequirements;
	}

	public Requirement(String customRequirement, int levelRequirement, Requirement... altRequirements)
	{
		this.skill = null;
		this.quest = null;
		this.customRequirement = customRequirement;
		this.levelRequirement = levelRequirement;
		this.started = false;
		this.altRequirements = altRequirements;
	}
}
