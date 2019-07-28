/*
 * Copyright (c) 2018, Chickensalad <https://github.com/rubeng123>
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
package net.runelite.client.ui.components;

import net.runelite.client.util.ImageUtil;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * A class that acts like a switch that plays an animation when its state changes.
 */
public class ToggleSwitch extends JLabel implements ActionListener
{
	private static final ImageIcon[] FRAME_ARRAY;
	private static final int TOTAL_FRAMES = 5;

	private boolean isSwitchOn = true;

	private int currentFrame = 4;
	private Timer animator;

	static
	{
		FRAME_ARRAY = new ImageIcon[TOTAL_FRAMES];
		for (int i = 0; i < TOTAL_FRAMES; ++i)
		{

			BufferedImage image = ImageUtil.getResourceStreamFromClass(ToggleSwitch.class, "toggleswitch/" + i + ".png");
			FRAME_ARRAY[i] = new ImageIcon(image);
		}
	}

	/**
	 * Constructs a ToggleSwitch object.
	 */
	public ToggleSwitch(boolean isOn)
	{
		//20 is for 20ms between each frame of the animation.
		animator = new Timer(20, this);
		setStateWithoutPerformingAnimation(isOn);
	}

	/**
	 * Sets the state of the switch instantly without performing an animation.
	 *
	 * @param isOn Whether the switch is toggled on or not.
	 */
	public void setStateWithoutPerformingAnimation(boolean isOn)
	{
		animator.stop();
		this.isSwitchOn = isOn;
		setToolTipText(!isOn ? "Disable plugin" : "Enable plugin");
		currentFrame = !isOn ? 0 : 4;
		repaint();
	}

	/**
	 * Toggle the state of the switch and perform the animation.
	 *
	 * @param isOn Whether the switch is toggled on or not.
	 */
	public void toggle(boolean isOn)
	{
		this.isSwitchOn = isOn;
		setToolTipText(!isOn ? "Disable plugin" : "Enable plugin");
		if (!animator.isRunning())
		{
			animator.start();
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		FRAME_ARRAY[currentFrame].paintIcon(this, g, 0,  2);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// If the goal state is ON, increment animation frames until we reach the last,
		// repainting for each frame.
		// Then stop the animation.
		if (isSwitchOn)
		{
			if (currentFrame < 4)
			{
				currentFrame++;
				repaint();
			}
			else
			{
				animator.stop();
			}
		}
		// If the goal state is OFF, decrement animation frames until we reach the last,
		// repainting for each frame.
		// Then stop the animation
		else
		{
			if (currentFrame > 0)
			{
				currentFrame--;
				repaint();
			}
			else
			{
				animator.stop();
			}
		}
	}
}
