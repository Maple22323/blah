/*
 * Copyright (c) 2021, Jordan Atwood <nightfirecat@protonmail.com>
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
package net.runelite.client.plugins.skillcalculator.skills;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.ItemID;
import net.runelite.client.game.ItemManager;

@AllArgsConstructor
@Getter
public enum HerbloreAction implements ItemSkillAction
{
	GUAM_LEAF(ItemID.GUAM_LEAF, 3, 2.5f),
	ATTACK_POTION_3(ItemID.ATTACK_POTION3, 3, 25),
	MARRENTILL(ItemID.MARRENTILL, 5, 3.8f),
	ANTIPOISON_3(ItemID.ANTIPOISON3, 5, 37.5f),
	RELICYMS_BALM_3(ItemID.RELICYMS_BALM3, 8, 40),
	TARROMIN(ItemID.TARROMIN, 11, 5),
	STRENGTH_POTION_3(ItemID.STRENGTH_POTION3, 12, 50),
	SERUM_207_3(ItemID.SERUM_207_3, 15, 50),
	GUTHIX_REST_3(ItemID.GUTHIX_REST3, 18, 59),
	GUAM_TAR(ItemID.GUAM_TAR, 19, 30),
	HARRALANDER(ItemID.HARRALANDER, 20, 6.3f),
	COMPOST_POTION_3(ItemID.COMPOST_POTION3, 22, 60),
	RESTORE_POTION_3(ItemID.RESTORE_POTION3, 22, 62.5f),
	RANARR_WEED(ItemID.RANARR_WEED, 25, 7.5f),
	ENERGY_POTION_3(ItemID.ENERGY_POTION3, 26, 67.5f),
	TOADFLAX(ItemID.TOADFLAX, 30, 8),
	DEFENCE_POTION_3(ItemID.DEFENCE_POTION3, 30, 75),
	MARRENTILL_TAR(ItemID.MARRENTILL_TAR, 31, 42.5f),
	AGILITY_POTION_3(ItemID.AGILITY_POTION3, 34, 80),
	COMBAT_POTION_3(ItemID.COMBAT_POTION3, 36, 84),
	PRAYER_POTION_3(ItemID.PRAYER_POTION3, 38, 87.5f),
	TARROMIN_TAR(ItemID.TARROMIN_TAR, 39, 55),
	IRIT_LEAF(ItemID.IRIT_LEAF, 40, 8.8f),
	HARRALANDER_TAR(ItemID.HARRALANDER_TAR, 44, 72.5f),
	SUPER_ATTACK_3(ItemID.SUPER_ATTACK3, 45, 100),
	AVANTOE(ItemID.AVANTOE, 48, 10),
	SUPERANTIPOISON_3(ItemID.SUPERANTIPOISON3, 48, 106.3f),
	FISHING_POTION_3(ItemID.FISHING_POTION3, 50, 112.5f),
	SUPER_ENERGY_3(ItemID.SUPER_ENERGY3, 52, 117.5f),
	HUNTER_POTION_3(ItemID.HUNTER_POTION3, 53, 120),
	KWUARM(ItemID.KWUARM, 54, 11.3f),
	GOADING_POTION_3(ItemID.GOADING_POTION3, 54, 132),
	IRIT_TAR(ItemID.IRIT_TAR, 55, 85),
	SUPER_STRENGTH_3(ItemID.SUPER_STRENGTH3, 55, 125),
	MAGIC_ESSENCE_POTION_3(ItemID.MAGIC_ESSENCE3, 57, 130),
	HUASCA(ItemID.HUASCA, 58, 11.8f),
	PRAYER_REGENERATION_POTION(ItemID.PRAYER_REGENERATION_POTION3, 58, 132),
	SNAPDRAGON(ItemID.SNAPDRAGON, 59, 11.8f),
	WEAPON_POISON(ItemID.WEAPON_POISON, 60, 137.5f),
	ALCOAUGMENTATOR(ItemID.ALCOAUGMENTATOR, 60, 190),
	LIPLACK_LIQUOR(ItemID.LIPLACK_LIQUOR, 60, 190),
	MAMMOTHMIGHT_MIX(ItemID.MAMMOTHMIGHT_MIX, 60, 190),
	SUPER_RESTORE_3(ItemID.SUPER_RESTORE3, 63, 142.5f),
	MYSTIC_MANA_AMALGAM(ItemID.MYSTIC_MANA_AMALGAM, 63, 215),
	CADANTINE(ItemID.CADANTINE, 65, 12.5f),
	SANFEW_SERUM_3(ItemID.SANFEW_SERUM3, 65, 160),
	SUPER_DEFENCE_3(ItemID.SUPER_DEFENCE3, 66, 150),
	MARLEYS_MOONLIGHT(ItemID.MARLEYS_MOONLIGHT, 66, 240),
	LANTADYME(ItemID.LANTADYME, 67, 13.1f),
	ANTIDOTE_PLUS_4(ItemID.ANTIDOTE4, 68, 155),
	ANTIFIRE_POTION_3(ItemID.ANTIFIRE_POTION3, 69, 157.5f),
	AZURE_AURA_MIX(ItemID.AZURE_AURA_MIX, 69, 265),
	DIVINE_SUPER_ATTACK_POTION_4(ItemID.DIVINE_SUPER_ATTACK_POTION4, 70, 2),
	DIVINE_SUPER_DEFENCE_POTION_4(ItemID.DIVINE_SUPER_DEFENCE_POTION4, 70, 2),
	DIVINE_SUPER_STRENGTH_POTION_4(ItemID.DIVINE_SUPER_STRENGTH_POTION4, 70, 2),
	DWARF_WEED(ItemID.DWARF_WEED, 70, 13.8f),
	RANGING_POTION_3(ItemID.RANGING_POTION3, 72, 162.5f),
	AQUALUX_AMALGAM(ItemID.AQUALUX_AMALGAM, 72, 290),
	WEAPON_POISON_PLUS(ItemID.WEAPON_POISON_5937, 73, 165),
	DIVINE_RANGING_POTION_4(ItemID.DIVINE_RANGING_POTION4, 74, 2),
	TORSTOL(ItemID.TORSTOL, 75, 15),
	MEGALITE_LIQUID(ItemID.MEGALITE_LIQUID, 75, 315),
	MAGIC_POTION_3(ItemID.MAGIC_POTION3, 76, 172.5f),
	STAMINA_POTION_3(ItemID.STAMINA_POTION3, 77, 76.5f),
	STAMINA_POTION_4(ItemID.STAMINA_POTION4, 77, 102),
	DIVINE_MAGIC_POTION_4(ItemID.DIVINE_MAGIC_POTION4, 78, 2),
	ZAMORAK_BREW_3(ItemID.ZAMORAK_BREW3, 78, 175),
	ANTILEECH_LOTION(ItemID.ANTILEECH_LOTION, 78, 340),
	ANTIDOTE_PLUS_PLUS_4(ItemID.ANTIDOTE4_5952, 79, 177.5f),
	BASTION_POTION_3(ItemID.BASTION_POTION3, 80, 155),
	BATTLEMAGE_POTION_3(ItemID.BATTLEMAGE_POTION3, 80, 155),
	SARADOMIN_BREW_3(ItemID.SARADOMIN_BREW3, 81, 180),
	MIXALOT(ItemID.MIXALOT, 81, 365),
	WEAPON_POISON_PLUS_PLUS(ItemID.WEAPON_POISON_5940, 82, 190),
	EXTENDED_ANTIFIRE_3(ItemID.EXTENDED_ANTIFIRE3, 84, 82.5f),
	EXTENDED_ANTIFIRE_4(ItemID.EXTENDED_ANTIFIRE4, 84, 110),
	ANCIENT_BREW_4(ItemID.ANCIENT_BREW4, 85, 190),
	DIVINE_BASTION_POTION_4(ItemID.DIVINE_BASTION_POTION4, 86, 2),
	DIVINE_BATTLEMAGE_POTION_4(ItemID.DIVINE_BATTLEMAGE_POTION4, 86, 2),
	ANTIVENOM_3(ItemID.ANTIVENOM3, 87, 90),
	ANTIVENOM_4(ItemID.ANTIVENOM4, 87, 120),
	MENAPHITE_REMEDY_3(ItemID.MENAPHITE_REMEDY3, 88, 200),
	SUPER_COMBAT_POTION_4(ItemID.SUPER_COMBAT_POTION4, 90, 150),
	FORGOTTEN_BREW_4(ItemID.FORGOTTEN_BREW4, 91, 145),
	SUPER_ANTIFIRE_4(ItemID.SUPER_ANTIFIRE_POTION4, 92, 130),
	EXTENDED_ANTIVENOM_PLUS_4(ItemID.EXTENDED_ANTIVENOM4, 94, 80),
	ANTIVENOM_PLUS_4(ItemID.ANTIVENOM4_12913, 94, 125),
	DIVINE_SUPER_COMBAT_POTION_4(ItemID.DIVINE_SUPER_COMBAT_POTION4, 97, 2),
	EXTENDED_SUPER_ANTIFIRE_3(ItemID.EXTENDED_SUPER_ANTIFIRE3, 98, 120),
	EXTENDED_SUPER_ANTIFIRE_4(ItemID.EXTENDED_SUPER_ANTIFIRE4, 98, 160),
	;

	private final int itemId;
	private final int level;
	private final float xp;

	@Override
	public boolean isMembers(final ItemManager itemManager)
	{
		return true;
	}
}
