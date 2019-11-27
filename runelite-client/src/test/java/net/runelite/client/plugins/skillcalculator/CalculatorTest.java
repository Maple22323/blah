/**
 * Copyright (c) 2019, dekvall <https://github.com/dekvall>
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
package net.runelite.client.plugins.skillcalculator;

import net.runelite.api.Experience;
import net.runelite.client.plugins.skillcalculator.beans.SkillDataEntry;
import net.runelite.client.plugins.skillcalculator.beans.XpScaling;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorTest
{
	@Test
	public void testScaledActionCount()
	{
		int startXP = Experience.getXpForLevel(1);
		int goalXP = Experience.getXpForLevel(44);

		// Just the action
		SkillDataEntry action = mock(SkillDataEntry.class);
		when(action.getXp()).thenReturn(200d);
		assertEquals(279, SkillCalculator.calculateActionSequence(action, startXP, goalXP, 1f).getActionCount());

		// With unbounded scaling
		XpScaling scaling = mock(XpScaling.class);
		when(action.getXpScalings()).thenReturn(new XpScaling[]{scaling});
		when(scaling.getStartLevel()).thenReturn(1);
		when(scaling.getEndLevel()).thenReturn(99);
		when(scaling.getLevelCoefficient()).thenReturn(9d);
		assertEquals(117, SkillCalculator.calculateActionSequence(action, startXP, goalXP, 1f).getActionCount());

		// With bounded scaling
		when(scaling.getMaxResult()).thenReturn(220d);
		assertEquals(138, SkillCalculator.calculateActionSequence(action, startXP, goalXP, 1f).getActionCount());
	}
}
