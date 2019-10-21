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
package net.runelite.client.plugins.itemcharges;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import java.util.concurrent.ScheduledExecutorService;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.GraphicID;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GraphicChanged;
import net.runelite.client.Notifier;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.ui.overlay.OverlayManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ItemChargePluginTest
{
	private static final String CHECK = "Your dodgy necklace has 10 charges left.";
	private static final String PROTECT = "Your dodgy necklace protects you. It has 9 charges left.";
	private static final String PROTECT_1 = "Your dodgy necklace protects you. <col=ff0000>It has 1 charge left.</col>";
	private static final String BREAK = "Your dodgy necklace protects you. <col=ff0000>It then crumbles to dust.</col>";

	private static final String CHECK_RING_OF_FORGING_FULL = "You can smelt 140 more pieces of iron ore before a ring melts.";
	private static final String CHECK_RING_OF_FORGING_ONE = "You can smelt one more piece of iron ore before a ring melts.";
	private static final String USED_RING_OF_FORGING = "You retrieve a bar of iron.";
	private static final String BREAK_RING_OF_FORGING = "<col=7f007f>Your Ring of Forging has melted.</col>";

	private static final String CHARGE_ONE_CHRONICLE = "You add a single charge to your book. It now has one charge.";
	private static final String CHARGE_FIVE_CHRONICLE = "You add 5 charges to your book. It now has 6 charges.";
	private static final String USED_LAST_CHRONICLE_CHARGE = "<col=ef1020>Your book has run out of charges.</col>";

	private static final String CHECK_XERICS_TALISMAN_CHARGES = "The talisman has 2 charges.";
	private static final String CHECK_XERICS_TALISMAN_SINGLE_CHARGE = "The talisman has one charge.";

	private static final String CHECK_SOULBEARER_CHARGE = "The soul bearer has 5 charges.";
	private static final String CHECK_SOULBEARER_SINGLE_CHARGE = "The soul bearer has one charge.";

	@Mock
	@Bind
	private Client client;

	@Mock
	@Bind
	private ScheduledExecutorService scheduledExecutorService;

	@Mock
	@Bind
	private RuneLiteConfig runeLiteConfig;

	@Mock
	@Bind
	private OverlayManager overlayManager;

	@Mock
	@Bind
	private Notifier notifier;

	@Mock
	@Bind
	private ItemChargeConfig config;

	@Inject
	private ItemChargePlugin itemChargePlugin;

	@Before
	public void before()
	{
		Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);
	}

	@Test
	public void testOnChatMessage()
	{
		ChatMessage chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", CHECK, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).dodgyNecklace(eq(10));
		reset(config);

		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", PROTECT, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).dodgyNecklace(eq(9));
		reset(config);

		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", PROTECT_1, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).dodgyNecklace(eq(1));
		reset(config);

		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", BREAK, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).dodgyNecklace(eq(10));
		reset(config);

		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", CHECK_RING_OF_FORGING_ONE, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).ringOfForging(eq(1));
		reset(config);

		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", CHECK_RING_OF_FORGING_FULL, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).ringOfForging(eq(140));
		reset(config);

		when(config.ringOfForging()).thenReturn(90);
		// Create equipment inventory with ring of forging
		ItemContainer equipmentItemContainer = mock(ItemContainer.class);
		when(client.getItemContainer(eq(InventoryID.EQUIPMENT))).thenReturn(equipmentItemContainer);
		Item[] items = new Item[EquipmentInventorySlot.RING.getSlotIdx() + 1];
		when(equipmentItemContainer.getItems()).thenReturn(items);
		Item ring = new Item(ItemID.RING_OF_FORGING, 1);
		items[EquipmentInventorySlot.RING.getSlotIdx()] = ring;
		// Run message
		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", USED_RING_OF_FORGING, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).ringOfForging(eq(89));
		reset(config);

		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", BREAK_RING_OF_FORGING, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).ringOfForging(eq(140));
		reset(config);

		//Chronicle tests
		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", CHARGE_ONE_CHRONICLE, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).chronicle(eq(1));
		reset(config);

		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", CHARGE_ONE_CHRONICLE, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", CHARGE_FIVE_CHRONICLE, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).chronicle(eq(6));
		reset(config);

		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", CHARGE_ONE_CHRONICLE, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", USED_LAST_CHRONICLE_CHARGE, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).chronicle(eq(0));
		reset(config);

		//Xeric talisman tests
		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", CHECK_XERICS_TALISMAN_CHARGES, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).xericTalisman(eq(2));
		reset(config);

		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", CHECK_XERICS_TALISMAN_SINGLE_CHARGE, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).xericTalisman(eq(1));
		reset(config);

		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", CHECK_SOULBEARER_CHARGE, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).soulBearer(eq(5));
		reset(config);

		chatMessage = new ChatMessage(null, ChatMessageType.GAMEMESSAGE, "", CHECK_SOULBEARER_SINGLE_CHARGE, "", 0);
		itemChargePlugin.onChatMessage(chatMessage);
		verify(config).soulBearer(eq(1));
		reset(config);
	}

	@Test
	public void testOnGraphicChanged()
	{
		Player player = mock(Player.class);
		when(player.getGraphic()).thenReturn(GraphicID.XERIC_TELEPORT);
		when(client.getLocalPlayer()).thenReturn(player);
		GraphicChanged graphicChanged = new GraphicChanged();
		graphicChanged.setActor(player);
		when(config.xericTalisman()).thenReturn(5);
		itemChargePlugin.onGraphicChanged(graphicChanged);
		verify(config).xericTalisman(eq(4));
		reset(config);
	}
}