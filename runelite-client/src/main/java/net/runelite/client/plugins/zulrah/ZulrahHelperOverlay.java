/*
 * Copyright (c) 2017, Aria <aria@ar1as.space>
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
package net.runelite.client.plugins.zulrah;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.client.RuneLite;
import net.runelite.client.plugins.zulrah.patterns.*;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;

class ZulrahHelperOverlay extends Overlay {
    private final Client client = RuneLite.getClient();

   private Point startTile;
   private int index = 0;
   private int currentPattern = -1;
   private ZulrahInstance previousInstance;
   private ZulrahInstance currentInstance;
   private final ZulrahPattern[] patterns;
   private NPC zulrah = null; //Potential race condition, symptoms might be flickering overlay

    ZulrahHelperOverlay() {
        super(OverlayPosition.DYNAMIC);

        patterns = new ZulrahPattern[4];
        patterns[0] = new ZulrahPatternA(client);
        patterns[1] = new ZulrahPatternB(client);
        patterns[2] = new ZulrahPatternC(client);
        patterns[3] = new ZulrahPatternD(client);
    }


    @Override
    public Dimension render(Graphics2D graphics) {

        if(client.getGameState() != GameState.LOGGED_IN || startTile == null || zulrah == null) return null;
        //TODO: Add prayer checking and health warning

        graphics.setColor(Color.WHITE);

        if(currentPattern == -1) {
            graphics.drawString("Unknown", 200, 200);
            graphics.drawString("current: " + currentInstance, 200, 215);
            graphics.drawString("Previous:" + previousInstance, 200, 230);
            graphics.drawString("index: " + index, 200, 245);
        } else {
            patterns[currentPattern].render(graphics, startTile, index);
        }


        return null;
    }

    void update()
    {
        try {
            NPC[] npcs = client.getNpcs();
            zulrah = null;
            for (NPC npc : npcs) {
                if (npc == null) continue;
                if (npc.getName().equals("Zulrah")) {
                    zulrah = npc;
                    break;
                }
            }

            if (zulrah == null)
            {
                if(startTile != null) {
                    startTile = null;
                    System.out.println("SET START TILE TO NULL");
                }
                return;
            }

            // Just entered
            if (startTile == null || zulrah.getLocalLocation().distanceTo(startTile) > 17000) {
                if(startTile != null) {
                    System.out.println("ZULRAH DISATANCE: " + zulrah.getLocalLocation().distanceTo(startTile));
                }
                startTile = zulrah.getLocalLocation();
                startTile = Perspective.localToWorld(client, startTile);
                index = 0;
                currentPattern = -1;
                System.out.println("Start Tile: " + startTile.toString());
                previousInstance = null;
                currentInstance = null;
            }

            ZulrahInstance temp = new ZulrahInstance(zulrah, startTile);

            if (currentInstance == null) {
                System.out.println("currentInstance set to temp");
                currentInstance = temp;
            }

            if (!currentInstance.equals(temp)) {
                previousInstance = currentInstance;
                currentInstance = temp;
                ++index;
            }

            if (currentPattern == -1) {
                int potential = 0;
                int potentialPattern = -1;

                for (int i = 0; i < patterns.length; ++i) {
                    if (patterns[i].accept(index, currentInstance)) {
                        //System.out.println("PATTERN POTENTIAL: " + i);
                        potential++;
                        potentialPattern = i;
                    }
                }
                if (potential == 1) {
                    currentPattern = potentialPattern;
                }
            } else {
                if (patterns[currentPattern].canReset(index)) {
                    if (currentInstance.equals(patterns[currentPattern].get(0))) {
                        currentPattern = -1;
                        index = 0;
                        currentInstance = null;
                    }
                }
            }

        } catch(NullPointerException e) {
            //I'm so sorry
            e.printStackTrace();
        }

    }
}
