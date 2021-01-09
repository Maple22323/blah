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
package net.runelite.cache;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import net.runelite.cache.fs.Store;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Cache
{
	public static void main(String[] args) throws IOException, NumberFormatException
	{
		Options options = new Options();

		options.addOption("c", "cache", true, "cache base");
		options.addOption(null, "out", true, "selects an output location");

		options.addOption("i", "items", false, "set flag to dump items");
		options.addOption("n", "npcs", false, "set flag to dump npcs");
		options.addOption("o", "objects", false, "set flag to dump objects");
		options.addOption("s", "sprites", false, "set flag to dump sprites");
		options.addOption("t", "textures", false, "set flag to dump textures");
		options.addOption("m", "models", false, "set flag to dump models");
		options.addOption("f", "frames", false, "set flag to dump animation frames");
		options.addOption("a", "animations", false, "set flag to dump animations");

		options.addOption(null, "configIndex", true, "selects a config to dump");


		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try
		{
			cmd = parser.parse(options, args);
		}
		catch (ParseException ex)
		{
			System.err.println("Error parsing command line options: " + ex.getMessage());
			System.exit(-1);
			return;
		}

		String outdir = cmd.getOptionValue("out");
		if (outdir == null)
		{
			System.err.println("Output directory must be specified");
			System.exit(-1);
			return;
		}

		String cache = cmd.getOptionValue("cache");

		Store store = loadStore(cache);

		if (cmd.hasOption("items"))
		{
			String itemdir = outdir + "\\" + "Items";
			System.out.println("Dumping items to " + itemdir);
			dumpItems(store, new File(itemdir));
		}
		if (cmd.hasOption("npcs"))
		{
			String npcdir = outdir + "\\" + "Npcs";
			System.out.println("Dumping npcs to " + npcdir);
			dumpNpcs(store, new File(npcdir));
		}
		if (cmd.hasOption("objects"))
		{
			String objectdir = outdir + "\\" + "Objects";
			System.out.println("Dumping objects to " + objectdir);
			dumpObjects(store, new File(objectdir));
		}
		if (cmd.hasOption("sprites"))
		{
			String spritedir = outdir + "\\" + "Sprites";
			System.out.println("Dumping sprites to " + spritedir);
			dumpSprites(store, new File(spritedir));
		}
		if (cmd.hasOption("models"))
		{
			String modelsdir = outdir + "\\" + "Models";
			System.out.println("Dumping models to " + modelsdir);
			dumpModels(store, new File(modelsdir));
		}
		if (cmd.hasOption("frames"))
		{
			String framesdir = outdir + "\\" + "Frames";
			System.out.println("Dumping frames to " + framesdir);
			dumpAnimationFrames(store, new File(framesdir));
		}
		if (cmd.hasOption("animations"))
		{
			String animsdir = outdir + "\\" + "Animations";
			System.out.println("Dumping animations to " + animsdir);
			dumpAnimations(store, new File(animsdir));
		}
		if (cmd.hasOption("textures"))
		{
			String texturesdir = outdir + "\\" + "Textures";
			System.out.println("Dumping textures to " + texturesdir);
			dumpTextures(store, new File(texturesdir));
		}
		if (cmd.hasOption("configIndex"))
		{
			int configIndex = Integer.parseInt(cmd.getOptionValue("configIndex"));
			try
			{
				ConfigType configType = ConfigType.valueOf(configIndex).get();
				String outputDirectory = outdir + "\\" + configType.name();

				System.out.println("Dumping " + configType.name() + " to " + outputDirectory);
				dumpConfig(store, configType, new File(outputDirectory));
			}
			catch (NoSuchElementException e)
			{
				System.err.println(configIndex + " config type not found");
				return;
			}
		}

		System.out.println("Finished dumping");
	}

	private static Store loadStore(String cache) throws IOException
	{
		Store store = new Store(new File(cache));
		store.load();
		return store;
	}

	private static void dumpItems(Store store, File itemdir) throws IOException
	{
		ItemManager dumper = new ItemManager(store);
		dumper.load();
		dumper.export(itemdir);
		dumper.java(itemdir);
	}

	private static void dumpNpcs(Store store, File npcdir) throws IOException
	{
		NpcManager dumper = new NpcManager(store);
		dumper.load();
		dumper.dump(npcdir);
		dumper.java(npcdir);
	}

	private static void dumpObjects(Store store, File objectdir) throws IOException
	{
		ObjectManager dumper = new ObjectManager(store);
		dumper.load();
		dumper.dump(objectdir);
		dumper.java(objectdir);
	}

	private static void dumpSprites(Store store, File spritedir) throws IOException
	{
		SpriteManager dumper = new SpriteManager(store);
		dumper.load();
		dumper.export(spritedir);
	}

	private static void dumpModels(Store store, File modeldir) throws IOException
	{
		ModelManager dumper = new ModelManager(store);
		dumper.load();
		dumper.exportModelInfo(modeldir);
		dumper.export(modeldir);
	}

	private static void dumpAnimations(Store store, File animsdir) throws IOException
	{
		AnimationManager dumper = new AnimationManager(store);
		dumper.load();
		dumper.export(animsdir);
	}

	private static void dumpAnimationFrames(Store store, File framesdir) throws IOException
	{
		FramesManager dumper = new FramesManager(store);
		dumper.load();
		dumper.dump(framesdir);
	}

	private static void dumpTextures(Store store, File framesdir) throws IOException
	{
		TextureManager dumper = new TextureManager(store);
		dumper.load();
		dumper.exportJson(framesdir);
	}

	private static void dumpConfig(Store store, ConfigType configType, File directory)  throws IOException
	{
		ConfigManager dumper = new ConfigManager(store);
		try
		{
			dumper.load(configType);
			dumper.dump(directory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
