/*
 * Copyright (c) 2019, Adam <Adam@sigterm.info>
 * Copyright (c) 2017, Robbie <https://github.com/rbbi>
 * Copyright (c) 2018, SomeoneWithAnInternetConnection
 * Copyright (c) 2020, Dennis <me@dennis.dev>
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

package net.runelite.client.plugins.grandexchange;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.primitives.Shorts;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.inject.Provides;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.inject.Inject;
import javax.swing.SwingUtilities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.GrandExchangeOffer;
import net.runelite.api.GrandExchangeOfferState;
import net.runelite.api.ItemComposition;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.ScriptID;
import net.runelite.api.VarClientStr;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GrandExchangeOfferChanged;
import net.runelite.api.events.GrandExchangeSearched;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.Notifier;
import net.runelite.client.account.AccountSession;
import net.runelite.client.account.SessionManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.events.SessionClose;
import net.runelite.client.events.SessionOpen;
import net.runelite.client.game.ItemManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.QuantityFormatter;
import net.runelite.client.util.Text;
import net.runelite.http.api.ge.GrandExchangeClient;
import net.runelite.http.api.ge.GrandExchangeTrade;
import net.runelite.http.api.item.ItemStats;
import net.runelite.http.api.osbuddy.OSBGrandExchangeClient;
import net.runelite.http.api.osbuddy.OSBGrandExchangeResult;
import org.apache.commons.text.similarity.FuzzyScore;

@PluginDescriptor(
	name = "Grand Exchange",
	description = "Provide additional and/or easier access to Grand Exchange information",
	tags = {"external", "integration", "notifications", "panel", "prices", "trade"}
)
@Slf4j
public class GrandExchangePlugin extends Plugin
{
	private static final int OFFER_CONTAINER_ITEM = 21;
	private static final int OFFER_DEFAULT_ITEM_ID = 6512;
	private static final OSBGrandExchangeClient CLIENT = new OSBGrandExchangeClient();
	private static final String OSB_GE_TEXT = "<br>OSBuddy Actively traded price: ";

	private static final String BUY_LIMIT_GE_TEXT = "<br>Buy limit: ";
	private static final Gson GSON = new Gson();
	private static final TypeToken<Map<Integer, Integer>> BUY_LIMIT_TOKEN = new TypeToken<Map<Integer, Integer>>()
	{
	};

	static final String SEARCH_GRAND_EXCHANGE = "Search Grand Exchange";

	private static final int MAX_RESULT_COUNT = 250;

	private static final FuzzyScore FUZZY = new FuzzyScore(Locale.ENGLISH);

	private static final Color FUZZY_HIGHLIGHT_COLOR = new Color(0x800000);

	@Getter(AccessLevel.PACKAGE)
	private NavigationButton button;

	@Getter(AccessLevel.PACKAGE)
	private GrandExchangePanel panel;

	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private boolean hotKeyPressed;

	@Inject
	private GrandExchangeInputListener inputListener;

	@Inject
	private ItemManager itemManager;

	@Inject
	private MouseManager mouseManager;

	@Inject
	private KeyManager keyManager;

	@Inject
	private Client client;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private GrandExchangeConfig config;

	@Inject
	private Notifier notifier;

	@Inject
	private ScheduledExecutorService executorService;

	@Inject
	private SessionManager sessionManager;

	@Inject
	private ConfigManager configManager;

	private Widget grandExchangeText;
	private Widget grandExchangeItem;

	private int osbItem;
	private OSBGrandExchangeResult osbGrandExchangeResult;

	private GrandExchangeClient grandExchangeClient;

	private boolean wasFuzzySearch;

	/**
	 * Logic from {@link org.apache.commons.text.similarity.FuzzyScore}
	 */
	@VisibleForTesting
	static List<Integer> findFuzzyIndices(String term, String query)
	{
		List<Integer> indices = new ArrayList<>();

		// fuzzy logic is case insensitive. We normalize the Strings to lower
		// case right from the start. Turning characters to lower case
		// via Character.toLowerCase(char) is unfortunately insufficient
		// as it does not accept a locale.
		final String termLowerCase = term.toLowerCase();
		final String queryLowerCase = query.toLowerCase();

		// the position in the term which will be scanned next for potential
		// query character matches
		int termIndex = 0;

		for (int queryIndex = 0; queryIndex < queryLowerCase.length(); queryIndex++)
		{
			final char queryChar = queryLowerCase.charAt(queryIndex);

			boolean termCharacterMatchFound = false;
			for (; termIndex < termLowerCase.length()
					&& !termCharacterMatchFound; termIndex++)
			{
				final char termChar = termLowerCase.charAt(termIndex);

				if (queryChar == termChar)
				{
					indices.add(termIndex);

					// we can leave the nested loop. Every character in the
					// query can match at most one character in the term.
					termCharacterMatchFound = true;
				}
			}
		}

		return indices;
	}

	private SavedOffer getOffer(int slot)
	{
		String offer = configManager.getConfiguration("geoffer." + client.getUsername().toLowerCase(), Integer.toString(slot));
		if (offer == null)
		{
			return null;
		}
		return GSON.fromJson(offer, SavedOffer.class);
	}

	private void setOffer(int slot, SavedOffer offer)
	{
		configManager.setConfiguration("geoffer." + client.getUsername().toLowerCase(), Integer.toString(slot), GSON.toJson(offer));
	}

	private void deleteOffer(int slot)
	{
		configManager.unsetConfiguration("geoffer." + client.getUsername().toLowerCase(), Integer.toString(slot));
	}

	@Provides
	GrandExchangeConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(GrandExchangeConfig.class);
	}

	@Override
	protected void startUp()
	{
		panel = injector.getInstance(GrandExchangePanel.class);

		final BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), "ge_icon.png");

		button = NavigationButton.builder()
			.tooltip("Grand Exchange")
			.icon(icon)
			.priority(3)
			.panel(panel)
			.build();

		clientToolbar.addNavigation(button);

		if (config.quickLookup())
		{
			mouseManager.registerMouseListener(inputListener);
			keyManager.registerKeyListener(inputListener);
		}

		AccountSession accountSession = sessionManager.getAccountSession();
		if (accountSession != null)
		{
			grandExchangeClient = new GrandExchangeClient(accountSession.getUuid());
		}

		osbItem = -1;
		osbGrandExchangeResult = null;
	}

	@Override
	protected void shutDown()
	{
		clientToolbar.removeNavigation(button);
		mouseManager.unregisterMouseListener(inputListener);
		keyManager.unregisterKeyListener(inputListener);
		grandExchangeText = null;
		grandExchangeItem = null;
		grandExchangeClient = null;
	}

	@Subscribe
	public void onSessionOpen(SessionOpen sessionOpen)
	{
		AccountSession accountSession = sessionManager.getAccountSession();
		if (accountSession.getUuid() != null)
		{
			grandExchangeClient = new GrandExchangeClient(accountSession.getUuid());
		}
		else
		{
			grandExchangeClient = null;
		}
	}

	@Subscribe
	public void onSessionClose(SessionClose sessionClose)
	{
		grandExchangeClient = null;
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("grandexchange"))
		{
			if (event.getKey().equals("quickLookup"))
			{
				if (config.quickLookup())
				{
					mouseManager.registerMouseListener(inputListener);
					keyManager.registerKeyListener(inputListener);
				}
				else
				{
					mouseManager.unregisterMouseListener(inputListener);
					keyManager.unregisterKeyListener(inputListener);
				}
			}
		}
	}

	@Subscribe
	public void onGrandExchangeOfferChanged(GrandExchangeOfferChanged offerEvent)
	{
		final int slot = offerEvent.getSlot();
		final GrandExchangeOffer offer = offerEvent.getOffer();

		ItemComposition offerItem = itemManager.getItemComposition(offer.getItemId());
		boolean shouldStack = offerItem.isStackable() || offer.getTotalQuantity() > 1;
		BufferedImage itemImage = itemManager.getImage(offer.getItemId(), offer.getTotalQuantity(), shouldStack);
		SwingUtilities.invokeLater(() -> panel.getOffersPanel().updateOffer(offerItem, itemImage, offer, slot));

		submitTrades(slot, offer);

		updateConfig(slot, offer);
	}

	private void submitTrades(int slot, GrandExchangeOffer offer)
	{
		if (grandExchangeClient == null)
		{
			return;
		}

		if (offer.getState() != GrandExchangeOfferState.BOUGHT && offer.getState() != GrandExchangeOfferState.SOLD &&
			offer.getState() != GrandExchangeOfferState.CANCELLED_BUY && offer.getState() != GrandExchangeOfferState.CANCELLED_SELL)
		{
			return;
		}

		// Cancelled offers may have been cancelled before buying/selling any items
		if (offer.getQuantitySold() == 0)
		{
			return;
		}

		SavedOffer savedOffer = getOffer(slot);
		if (!shouldUpdate(savedOffer, offer))
		{
			return;
		}

		// getPrice() is the price of the offer, not necessarily what the item bought at
		int priceEach = offer.getSpent() / offer.getQuantitySold();

		GrandExchangeTrade grandExchangeTrade = new GrandExchangeTrade();
		grandExchangeTrade.setBuy(offer.getState() == GrandExchangeOfferState.BOUGHT || offer.getState() == GrandExchangeOfferState.CANCELLED_BUY);
		grandExchangeTrade.setItemId(offer.getItemId());
		grandExchangeTrade.setQuantity(offer.getQuantitySold());
		grandExchangeTrade.setPrice(priceEach);

		log.debug("Submitting trade: {}", grandExchangeTrade);
		grandExchangeClient.submit(grandExchangeTrade);
	}

	private void updateConfig(int slot, GrandExchangeOffer offer)
	{
		if (offer.getState() == GrandExchangeOfferState.EMPTY)
		{
			deleteOffer(slot);
		}
		else
		{
			SavedOffer savedOffer = new SavedOffer();
			savedOffer.setItemId(offer.getItemId());
			savedOffer.setQuantitySold(offer.getQuantitySold());
			savedOffer.setTotalQuantity(offer.getTotalQuantity());
			savedOffer.setPrice(offer.getPrice());
			savedOffer.setSpent(offer.getSpent());
			savedOffer.setState(offer.getState());
			setOffer(slot, savedOffer);
			updateLimitTimer(offer);
		}
	}

	private boolean shouldUpdate(SavedOffer savedOffer, GrandExchangeOffer grandExchangeOffer)
	{
		if (savedOffer == null)
		{
			return false;
		}

		// Only update offer if state has changed
		return savedOffer.getState() != grandExchangeOffer.getState();
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (!this.config.enableNotifications() || event.getType() != ChatMessageType.GAMEMESSAGE)
		{
			return;
		}

		String message = Text.removeTags(event.getMessage());

		if (message.startsWith("Grand Exchange:"))
		{
			this.notifier.notify(message);
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGIN_SCREEN)
		{
			panel.getOffersPanel().resetOffers();
		}
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		// At the moment, if the user disables quick lookup, the input listener gets disabled. Thus, isHotKeyPressed()
		// should always return false when quick lookup is disabled.
		// Replace the default option with "Search ..." when holding alt
		if (client.getGameState() != GameState.LOGGED_IN || !hotKeyPressed)
		{
			return;
		}

		final MenuEntry[] entries = client.getMenuEntries();
		final MenuEntry menuEntry = entries[entries.length - 1];
		final int widgetId = menuEntry.getParam1();
		final int groupId = WidgetInfo.TO_GROUP(widgetId);

		switch (groupId)
		{
			case WidgetID.BANK_GROUP_ID:
				// Don't show for view tabs and such
				if (WidgetInfo.TO_CHILD(widgetId) != WidgetInfo.BANK_ITEM_CONTAINER.getChildId())
				{
					break;
				}
			case WidgetID.INVENTORY_GROUP_ID:
			case WidgetID.BANK_INVENTORY_GROUP_ID:
			case WidgetID.GRAND_EXCHANGE_INVENTORY_GROUP_ID:
			case WidgetID.SHOP_INVENTORY_GROUP_ID:
				menuEntry.setOption(SEARCH_GRAND_EXCHANGE);
				menuEntry.setType(MenuAction.RUNELITE.getId());
				client.setMenuEntries(entries);
		}
	}

	@Subscribe
	public void onFocusChanged(FocusChanged focusChanged)
	{
		if (!focusChanged.isFocused())
		{
			setHotKeyPressed(false);
		}
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event)
	{
		switch (event.getGroupId())
		{
			// Grand exchange was opened.
			case WidgetID.GRAND_EXCHANGE_GROUP_ID:
				Widget grandExchangeOffer = client.getWidget(WidgetInfo.GRAND_EXCHANGE_OFFER_CONTAINER);
				grandExchangeText = client.getWidget(WidgetInfo.GRAND_EXCHANGE_OFFER_TEXT);
				grandExchangeItem = grandExchangeOffer.getDynamicChildren()[OFFER_CONTAINER_ITEM];
				break;
			// Grand exchange was closed (if it was open before).
			case WidgetID.INVENTORY_GROUP_ID:
				grandExchangeText = null;
				grandExchangeItem = null;
				break;
		}
	}

	@Subscribe
	public void onScriptPostFired(ScriptPostFired event)
	{
		// GE offers setup init
		if (event.getScriptId() == ScriptID.GE_OFFERS_SETUP_BUILD)
		{
			rebuildGeText();
		}
		else if (event.getScriptId() == ScriptID.GE_ITEM_SEARCH && config.highlightSearchMatch())
		{
			highlightSearchMatches();
		}
	}

	private void highlightSearchMatches()
	{
		if (!wasFuzzySearch)
		{
			return;
		}
		String input = client.getVar(VarClientStr.INPUT_TEXT);

		String underlineTag = "<u=" + ColorUtil.colorToHexCode(FUZZY_HIGHLIGHT_COLOR) + ">";

		Widget results = client.getWidget(WidgetInfo.CHATBOX_GE_SEARCH_RESULTS);
		Widget[] children = results.getDynamicChildren();
		int resultCount = children.length / 3;

		for (int i = 0; i < resultCount; i++)
		{
			Widget itemNameWidget = children[i * 3 + 1];
			String itemName = itemNameWidget.getText();

			List<Integer> indices;
			String otherName = itemName.replace('-', ' ');
			if (!itemName.contains("-") || FUZZY.fuzzyScore(itemName, input) >= FUZZY.fuzzyScore(otherName, input))
			{
				indices = findFuzzyIndices(itemName, input);
			}
			else
			{
				indices = findFuzzyIndices(otherName, input);
			}
			Collections.reverse(indices);

			StringBuilder newItemName = new StringBuilder(itemName);
			for (int index : indices)
			{
				if (itemName.charAt(index) == ' ' || itemName.charAt(index) == '-')
				{
					continue;
				}

				newItemName.insert(index + 1, "</u>");
				newItemName.insert(index, underlineTag);
			}

			itemNameWidget.setText(newItemName.toString());
		}
	}

	@Subscribe
	public void onGrandExchangeSearched(GrandExchangeSearched event)
	{
		wasFuzzySearch = false;

		GrandExchangeSearchMode searchMode = config.geSearchMode();
		final String input = client.getVar(VarClientStr.INPUT_TEXT);
		if (searchMode == GrandExchangeSearchMode.DEFAULT || input.isEmpty())
		{
			return;
		}

		event.consume();

		client.setGeSearchResultIndex(0);

		int resultCount = 0;
		if (searchMode == GrandExchangeSearchMode.FUZZY_FALLBACK)
		{
			List<Integer> ids = IntStream.range(0, client.getItemCount())
					.mapToObj(itemManager::getItemComposition)
					.filter(item -> item.isTradeable() && item.getNote() == -1
						&& item.getName().toLowerCase().contains(input))
					.limit(MAX_RESULT_COUNT + 1)
					.sorted(Comparator.comparing(ItemComposition::getName))
					.map(ItemComposition::getId)
					.collect(Collectors.toList());
			if (ids.size() > MAX_RESULT_COUNT)
			{
				client.setGeSearchResultCount(-1);
				client.setGeSearchResultIds(null);
			}
			else
			{
				resultCount = ids.size();
				client.setGeSearchResultCount(resultCount);
				client.setGeSearchResultIds(Shorts.toArray(ids));
			}
		}

		if (resultCount == 0)
		{
			// We do this so that for example the items "Anti-venom ..." are still at the top
			// when searching "anti venom"
			ToIntFunction<ItemComposition> getScore = item ->
			{
				int score = FUZZY.fuzzyScore(item.getName(), input);
				if (item.getName().contains("-"))
				{
					return Math.max(FUZZY.fuzzyScore(item.getName().replace('-', ' '), input), score);
				}
				return score;
			};

			List<Integer> ids = IntStream.range(0, client.getItemCount())
					.mapToObj(itemManager::getItemComposition)
					.filter(item -> item.isTradeable() && item.getNote() == -1)
					.filter(item -> getScore.applyAsInt(item) > 0)
					.sorted(Comparator.comparingInt(getScore).reversed()
						.thenComparing(ItemComposition::getName))
					.limit(MAX_RESULT_COUNT)
					.map(ItemComposition::getId)
					.collect(Collectors.toList());

			client.setGeSearchResultCount(ids.size());
			client.setGeSearchResultIds(Shorts.toArray(ids));

			wasFuzzySearch = true;
		}
	}

	@Subscribe
	public void onScriptCallbackEvent(ScriptCallbackEvent event)
	{
		if (!event.getEventName().equals("setGETitle") || !config.showTotal())
		{
			return;
		}

		long total = 0;
		GrandExchangeOffer[] offers = client.getGrandExchangeOffers();
		for (GrandExchangeOffer offer : offers)
		{
			if (offer != null)
			{
				total += offer.getPrice() * offer.getTotalQuantity();
			}
		}

		if (total == 0L)
		{
			return;
		}

		StringBuilder titleBuilder = new StringBuilder(" (");

		if (config.showExact())
		{
			titleBuilder.append(QuantityFormatter.formatNumber(total));
		}
		else
		{
			titleBuilder.append(QuantityFormatter.quantityToStackSize(total));
		}

		titleBuilder.append(')');

		// Append to title
		String[] stringStack = client.getStringStack();
		int stringStackSize = client.getStringStackSize();

		stringStack[stringStackSize - 1] += titleBuilder.toString();
	}

	private void setLimitReset(int itemId)
	{
		String lastLimit = getLimitReset(itemId);
		if (!lastLimit.equals("None"))
		{
			return; // do not update if previous limit has not expired
		}
		String itemIdStr = Integer.toString(itemId);
		Instant currentDateTime = Instant.now();
		configManager.setConfiguration("gelimitreset",
			client.getUsername().toLowerCase() + itemIdStr, currentDateTime);
	}

	private String getLimitReset(int itemId)
	{
		String itemIdStr = Integer.toString(itemId);
		Instant lastDateTime = configManager.getConfiguration("gelimitreset",
			client.getUsername().toLowerCase() + itemIdStr, Instant.class);
		if (lastDateTime == null)
		{
			return "None";
		}
		Instant now = Instant.now();
		long minuteDifference = ChronoUnit.MINUTES.between(lastDateTime, now);
		if (minuteDifference / 60.0 > 4.0)
		{
			return "None";
		}
		else
		{
			int minsLeft = 240 - (int) minuteDifference;
			int hours = minsLeft / 60;
			int minutes = minsLeft % 60;
			String limitResetString = hours + ":" + minutes;
			return limitResetString;
		}
	}

	private void updateLimitTimer(GrandExchangeOffer offer)
	{
		if (offer.getState() == GrandExchangeOfferState.BOUGHT ||
			(offer.getQuantitySold() > 0 &&
				offer.getState() == GrandExchangeOfferState.BUYING))
		{
			setLimitReset(offer.getItemId());
		}
	}

	private void rebuildGeText()
	{
		if (grandExchangeText == null || grandExchangeItem == null || grandExchangeItem.isHidden())
		{
			return;
		}

		final Widget geText = grandExchangeText;
		final int itemId = grandExchangeItem.getItemId();

		if (itemId == OFFER_DEFAULT_ITEM_ID || itemId == -1)
		{
			// This item is invalid/nothing has been searched for
			return;
		}

		String[] lines = geText.getText().split("<br>");
		String text = lines[0]; // remove any limit or OSB ge values

		if (config.enableGELimits())
		{
			final ItemStats itemStats = itemManager.getItemStats(itemId, false);

			// If we have item buy limit, append it
			if (itemStats != null && itemStats.getGeLimit() > 0)
			{
				text += BUY_LIMIT_GE_TEXT + QuantityFormatter.formatNumber(itemStats.getGeLimit());
			}
		}

		if (config.enableGELimitReset())
		{
			text += " (" + getLimitReset(itemId) + ")";
		}

		geText.setText(text);

		if (!config.enableOsbPrices())
		{
			return;
		}

		// If we already have the result, use it
		if (osbGrandExchangeResult != null && osbGrandExchangeResult.getItem_id() == itemId && osbGrandExchangeResult.getOverall_average() > 0)
		{
			geText.setText(text + OSB_GE_TEXT + QuantityFormatter.formatNumber(osbGrandExchangeResult.getOverall_average()));
		}

		if (osbItem == itemId)
		{
			// avoid starting duplicate lookups
			return;
		}

		osbItem = itemId;

		log.debug("Looking up OSB item price {}", itemId);

		final String start = text;
		executorService.submit(() ->
		{
			try
			{
				final OSBGrandExchangeResult result = CLIENT.lookupItem(itemId);
				osbGrandExchangeResult = result;
				// Update the text on the widget too
				geText.setText(start + OSB_GE_TEXT + QuantityFormatter.formatNumber(result.getOverall_average()));
			}
			catch (IOException e)
			{
				log.debug("Error getting price of item {}", itemId, e);
			}
		});
	}
}
