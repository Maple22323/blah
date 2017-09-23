/*
 * Copyright (c) 2017, Tyler <https://github.com/tylerthardy>
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
package net.runelite.client.plugins.runepouch;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class RuneImageCache {
    private String[] RuneNames = { //ids from the varbit
            "None",
            "Air",		//1
            "Water",	//2
            "Earth",	//3
            "Fire",		//4
            "Mind",		//5
            "Chaos",	//6
            "Death",	//7
            "Blood",	//8
            "Cosmic",	//9
            "Nature",	//10
            "Law",		//11
            "Body",		//12
            "Soul",		//13
            "Astral",	//14
            "Mist",		//15
            "Mud",		//16
            "Dust",		//17
            "Lava",		//18
            "Steam",	//19
            "Smoke"		//20
    };
    private LoadingCache<Integer,BufferedImage> cache;
    public RuneImageCache(){
        cache = CacheBuilder.newBuilder()
                .maximumSize(RuneNames.length)
                .build(
                        new CacheLoader<Integer, BufferedImage>() {
                            @Override
                            public BufferedImage load(Integer runeId) throws Exception {
                                InputStream in = RunepouchOverlay.class.getResourceAsStream(RuneNames[runeId] + ".png");
                                return ImageIO.read(in);
                            }
                        }
                );
    }

    public BufferedImage getImage(int runeId)
    {
        try{
            return cache.get(runeId);
        } catch(ExecutionException e){
            return null;
        }
    }
}
