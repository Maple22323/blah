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
package net.runelite.client.plugins.hiscore;

import net.runelite.client.hiscore.HiscoreClient;
import static net.runelite.client.plugins.hiscore.HiscorePanel.formatNumber;
import net.runelite.client.hiscore.HiscoreEndpoint;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HiscorePanelTest
{
	@Test
	public void testConstructor()
	{
		HiscorePlugin plugin = mock(HiscorePlugin.class);
		when(plugin.getWorldEndpoint()).thenReturn(HiscoreEndpoint.NORMAL);
		new HiscorePanel(null, plugin, mock(HiscoreConfig.class),
			mock(NameAutocompleter.class), mock(HiscoreClient.class));
	}

	@Test
	public void testFormatNumber()
	{
		assertEquals("398", formatNumber(398));
		assertEquals("5000", formatNumber(5000));
		assertEquals("7682", formatNumber(7682));
		assertEquals("12k", formatNumber(12398));
		assertEquals("219k", formatNumber(219824));
		assertEquals("56m", formatNumber(56_300_000));
		assertEquals("199m", formatNumber(199_999_999));
		assertEquals("200m", formatNumber(200_000_000));
		assertEquals("4600m", formatNumber(4_600_000_000L));
	}
}
