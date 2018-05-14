/*
 * Copyright (c) 2018, Lotto <https://github.com/devLotto>
 * Copyright (c) 2018, Raqes <j.raqes@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.uicustomizer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.widgets.WidgetInfo;

@Getter
@AllArgsConstructor
enum WidgetOffset
{
	RESIZABLE_2010_QUEST_HIGHLIGHT(Skin.AROUND_2010, WidgetInfo.RESIZABLE_VIEWPORT_QUEST_TAB, null, null, 33, null),
	FIXED_2010_QUEST_HIGHLIGHT(Skin.AROUND_2010, WidgetInfo.FIXED_VIEWPORT_QUEST_TAB, null, null, 33, null),
	FIXED_2010_FRIENDS_HIGHLIGHT(Skin.AROUND_2010, WidgetInfo.FIXED_VIEWPORT_FRIEND_TAB, null, null, 33, null),
	FIXED_2005_COMBAT_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_COMBAT_TAB, 19, 2, null, null),
	FIXED_2005_COMBAT_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_COMBAT_ICON, 26, null, null, null),
	FIXED_2005_SKILL_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_SKILLS_TAB, 55, null, 32, null),
	FIXED_2005_SKILL_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_SKILLS_ICON, 52, null, null, null),
	FIXED_2005_QUEST_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_QUEST_TAB, 82, 1, 30, null),
	FIXED_2005_QUEST_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_QUEST_ICON, 81, null, null, null),
	FIXED_2005_INVENTORY_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_INVENTORY_TAB, null, null, 45, null),
	FIXED_2005_INVENTORY_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_INVENTORY_ICON, 117, null, null, null),
	FIXED_2005_EQUIPMENT_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_EQUIPMENT_TAB, 153, 2, 30, null),
	FIXED_2005_EQUIPMENT_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_EQUIPMENT_ICON, 150, null, null, null),
	FIXED_2005_PRAYER_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_PRAYER_TAB, 180, null, 32, null),
	FIXED_2005_PRAYER_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_PRAYER_ICON, 179, null, null, null),
	FIXED_2005_MAGIC_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_MAGIC_TAB, 206, 2, 33, null),
	FIXED_2005_MAGIC_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_MAGIC_ICON, 205, 3, null, null),
	FIXED_2005_CLAN_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_CLAN_TAB, 15, null, null, null),
	FIXED_2005_CLAN_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_CLAN_ICON, 22, 0, null, null),
	FIXED_2005_FRIEND_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_FRIEND_TAB, 50, null, 33, null),
	FIXED_2005_FRIEND_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_FRIEND_ICON, 48, null, null, null),
	FIXED_2005_IGNORE_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_IGNORE_TAB, 78, null, 30, null),
	FIXED_2005_IGNORE_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_IGNORE_ICON, 76, null, null, null),
	FIXED_2005_LOGOUT_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_LOGOUT_TAB, 107, null, 45, null),
	FIXED_2005_LOGOUT_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_LOGOUT_ICON, 114, null, null, null),
	FIXED_2005_SETTINGS_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_SETTINGS_TAB, 148, null, 30, null),
	FIXED_2005_SETTINGS_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_SETTINGS_ICON, 149, null, null, null),
	FIXED_2005_EMOTE_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_EMOTE_TAB, 177, null, 30, null),
	FIXED_2005_EMOTE_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_EMOTE_ICON, 179, null, null, null),
	FIXED_2005_MUSIC_HIGHLIGHT(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_MUSIC_TAB, 207, null, 30, null),
	FIXED_2005_MUSIC_ICON(Skin.AROUND_2005, WidgetInfo.FIXED_VIEWPORT_MUSIC_ICON, 202, 5, null, null);

	private Skin skin;
	private WidgetInfo widgetInfo;
	private Integer offsetX;
	private Integer offsetY;
	private Integer width;
	private Integer height;
}