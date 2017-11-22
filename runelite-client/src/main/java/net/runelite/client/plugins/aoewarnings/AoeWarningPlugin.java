/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
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
package net.runelite.client.plugins.aoewarnings;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Binder;
import com.google.inject.Provides;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ProjectileMoved;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.Overlay;

import javax.inject.Inject;

@PluginDescriptor(
		name = "AoE projectile warning plugin"
)
public class AoeWarningPlugin extends Plugin
{
	@Inject
	AoeWarningOverlay overlay;

	@Override
	public void configure(Binder binder)
	{
		binder.bind(AoeWarningOverlay.class);
	}

	@Provides
	AoeWarningConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AoeWarningConfig.class);
	}

	@Override
	public Overlay getOverlay()
	{
		return overlay;
	}

	@Subscribe
	public void onProjectileMoved(ProjectileMoved event)
	{
		overlay.onProjectileMoved(event);
	}
}
