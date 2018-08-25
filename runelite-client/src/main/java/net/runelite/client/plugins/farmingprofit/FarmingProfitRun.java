/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
package net.runelite.client.plugins.farmingprofit;

import lombok.Getter;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.game.ItemManager;

import java.time.LocalDateTime;

class FarmingProfitRun
{

	@Getter
	private Crop crop;
	@Getter
	private int amount;
	@Getter
	private WorldPoint latestHarvestWorldPoint;
	@Getter
	private LocalDateTime latestHarvestTime;

	private int profit;
	private ItemManager itemManager;


	FarmingProfitRun(ItemManager itemManager, Crop crop, int amount, WorldPoint latestHarvest)
	{
		this.itemManager = itemManager;
		this.crop = crop;
		updateAmount(amount, latestHarvest);
	}

	private void updateAmount(int amount, WorldPoint harvestWorldPoint)
	{
		this.amount = amount;
		latestHarvestWorldPoint = harvestWorldPoint;
		updateProfit();
		latestHarvestTime = LocalDateTime.now();
	}

	void addAmount(int toAdd, WorldPoint harvestWorldPoint)
	{
		updateAmount(amount + toAdd, harvestWorldPoint);
	}

	int getProfit()
	{
		return profit;
	}

	private void updateProfit()
	{
		int seedPrice = itemManager.getItemPrice(crop.getSeedId());
		int productPrice = itemManager.getItemPrice(crop.getProductId());

		profit = productPrice * amount - seedPrice;
	}

}
