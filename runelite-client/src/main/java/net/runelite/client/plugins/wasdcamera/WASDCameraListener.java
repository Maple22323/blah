/*
 * Copyright (c) 2018, Abexlry <abexlry@gmail.com>
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
package net.runelite.client.plugins.wasdcamera;

import java.awt.event.KeyEvent;
import javax.inject.Inject;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.MouseListener;

public class WASDCameraListener extends MouseListener implements KeyListener
{

	@Inject
	private WASDCameraPlugin plugin;

	@Override
	public void keyTyped(KeyEvent e)
	{
		// Consume key type if can't type, logged in and in focus
		if (!plugin.canType && plugin.loggedIn && plugin.inFocus)
		{
			e.consume();
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		// Handle key press if logged in and in focus
		if (plugin.loggedIn && plugin.inFocus)
		{
			plugin.handleKeyPress(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// Release camera keys if can't type, logged in and in focus
		if (!plugin.canType && plugin.loggedIn && plugin.inFocus)
		{
			plugin.releaseCameraKey(e);
		}
	}
}
