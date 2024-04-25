/*
 * Copyright (c) 2021, Alexsuperfly <alexsuperfly@users.noreply.github.com>
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
package net.runelite.client.plugins.cannon;

import com.google.inject.Guice;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.VarPlayer;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.Notifier;
import net.runelite.client.config.Notification;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CannonPluginTest
{

	@Inject
	private CannonPlugin plugin;

	@Mock
	@Bind
	private CannonConfig config;

	@Mock
	@Bind
	private CannonOverlay cannonOverlay;

	@Mock
	@Bind
	private CannonSpotOverlay cannonSpotOverlay;

	@Mock
	@Bind
	private InfoBoxManager infoBoxManager;

	@Mock
	@Bind
	private Notifier notifier;

	@Mock
	@Bind
	private ItemManager itemManager;

	@Mock
	@Bind
	private Client client;

	@Mock
	@Bind
	private OverlayManager overlayManager;

	private final VarbitChanged cannonAmmoChanged = new VarbitChanged();

	@Before
	public void before()
	{
		Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);
		cannonAmmoChanged.setVarpId(VarPlayer.CANNON_AMMO);
		plugin.setCannonWorldPoint(new WorldPoint(1, 1, 1));
	}

	@Test
	public void testThresholdNotificationShouldNotifyOnce()
	{
		when(config.showCannonNotifications()).thenReturn(Notification.ON);
		when(config.lowWarningThreshold()).thenReturn(10);

		for (int cballs = 15; cballs >= 8; --cballs)
		{
			cannonAmmoChanged.setValue(cballs);
			plugin.onVarbitChanged(cannonAmmoChanged);
		}

		verify(notifier, times(1)).notify(Notification.ON, "Your cannon has 10 cannon balls remaining!");
	}

	@Test
	public void testCannonPickedUpOrEmptiedShouldNotNotify()
	{
		cannonAmmoChanged.setValue(22);
		plugin.onVarbitChanged(cannonAmmoChanged);
		cannonAmmoChanged.setValue(0);
		plugin.onVarbitChanged(cannonAmmoChanged);

		verify(notifier, never()).notify(any(Notification.class), eq("Your cannon is out of ammo!"));
	}

	@Test
	public void testCannonOutOfAmmo()
	{
		cannonAmmoChanged.setValue(1);
		plugin.onVarbitChanged(cannonAmmoChanged);
		cannonAmmoChanged.setValue(0);
		plugin.onVarbitChanged(cannonAmmoChanged);

		verify(notifier, times(1)).notify(Notification.ON, "Your cannon is out of ammo!");
	}
}
