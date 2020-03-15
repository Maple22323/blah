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
package net.runelite.client.plugins.chatcommands;

import java.awt.event.KeyEvent;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.ScriptID;
import net.runelite.api.VarClientInt;
import net.runelite.api.VarClientStr;
import net.runelite.api.vars.InputType;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.input.KeyListener;

@Singleton
public class ChatKeyboardListener implements KeyListener
{
	@Inject
	private ChatCommandsConfig chatCommandsConfig;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (chatCommandsConfig.clearSingleWord().matches(e))
		{
			int inputTye = client.getVar(VarClientInt.INPUT_TYPE);
			String input = inputTye == InputType.NONE.getType()
				? client.getVar(VarClientStr.CHATBOX_TYPED_TEXT)
				: client.getVar(VarClientStr.INPUT_TEXT);

			if (input != null)
			{
				// remove trailing space
				while (input.endsWith(" "))
				{
					input = input.substring(0, input.length() - 1);
				}

				// grab OS so that clearing single word behaves like system's default
				String OS = System.getProperty("os.name").toLowerCase();
				String tempReplacement;

				if (OS.contains("win")) // windows default ctrl + backspace
				{
					// find next word
					int idx = input.lastIndexOf(' ') + 1;
					tempReplacement = input.substring(0, idx);
				}
				else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) // unix default ctrl + backspace
				{
					// find next word
					int idx = input.lastIndexOf(' ') + 1;
					tempReplacement = input.substring(0, idx);
				}
				else if (OS.contains("mac")) // mac default ctrl + backspace
				{
					// find next word
					int idx = input.lastIndexOf(' ') + 1;
					tempReplacement = input.substring(0, idx);
				}
				else if (OS.contains("sunos")) // solaris default ctrl + backspace
				{
					// find next word
					int idx = input.lastIndexOf(' ') + 1;
					tempReplacement = input.substring(0, idx);
				}
				else // catch all ctrl + backspace
				{
					// find next word
					int idx = input.lastIndexOf(' ') + 1;
					tempReplacement = input.substring(0, idx);
				}

				final String replacement = tempReplacement;

				clientThread.invoke(() -> applyText(inputTye, replacement));
			}
		}
		else if (chatCommandsConfig.clearChatBox().matches(e))
		{
			int inputTye = client.getVar(VarClientInt.INPUT_TYPE);
			clientThread.invoke(() -> applyText(inputTye, ""));
		}
	}

	private void applyText(int inputType, String replacement)
	{
		if (inputType == InputType.NONE.getType())
		{
			client.setVar(VarClientStr.CHATBOX_TYPED_TEXT, replacement);
			client.runScript(ScriptID.CHAT_PROMPT_INIT);
		}
		else if (inputType == InputType.PRIVATE_MESSAGE.getType())
		{
			client.setVar(VarClientStr.INPUT_TEXT, replacement);
			client.runScript(ScriptID.CHAT_TEXT_INPUT_REBUILD, "");
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}
}
