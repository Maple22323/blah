package net.runelite.client.plugins.randall.http.handlers.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.Player;
import net.runelite.client.plugins.randall.RandallPlugin;
import net.runelite.client.plugins.randall.interfaces.ConectServerHandlerInterface;
import net.runelite.client.plugins.randall.models.ItemModel;
import net.runelite.client.plugins.randall.models.PlayerModel;

import java.io.IOException;
import java.util.Arrays;

public class PlayerHandler implements ConectServerHandlerInterface {
    private final Client client;
    private final RandallPlugin plugin;

    public PlayerHandler(RandallPlugin plugin, Client client) {
        super();
        this.plugin = plugin;
        this.client = client;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String requestURI = String.valueOf(exchange.getRequestURI());

        try {

            if (requestMethod.equals("GET") && requestURI.equals("/player/")) {
                getPlayer(exchange);
            } else if (requestMethod.equals("GET") && requestURI.equals("/player/list/")) {
                getPlayers(exchange);
            } else if (requestMethod.equals("GET") && requestURI.equals("/player/inventory/")) {
                getInventory(exchange);
            } else if (requestMethod.equals("POST") && requestURI.equals("/player/highlight/")) {
                highlightPlayer(exchange);
            } else if (requestMethod.equals("POST") && requestURI.equals("/player/unhighlight/")) {
                unhighlightPlayer(exchange);
            } else if (requestMethod.equals("POST") && requestURI.equals("/player/unhighlight/all/")) {
                unhighlightAll(exchange);
            } else {
                exchange.sendResponseHeaders(405, 0);
            }

        } catch (Exception exc) {
            JsonObject error = new JsonObject();
            error.addProperty("error", Arrays.toString(exc.getStackTrace()));
            writeResponse(exchange, 500, error);
        }
        exchange.close();
    }

    private void getPlayer(HttpExchange exchange) throws IOException {
        Player player = client.getLocalPlayer();

        JsonObject data = new JsonObject();
        data.addProperty("email", client.getUsername());
        data.add("player", new PlayerModel(client, player).toJson());
        writeResponse(exchange, 200, data);
    }

    private void getPlayers(HttpExchange exchange) throws IOException {
        JsonArray data = new JsonArray();
        client.getPlayers().forEach(player -> data.add(new PlayerModel(client, player).toJson()));
        writeResponse(exchange, 200, data);
    }

    private void getInventory(HttpExchange exchange) throws IOException {
        JsonArray data = new JsonArray();
        ItemContainer container = client.getItemContainer(InventoryID.INVENTORY);

        if (container != null) {
            Arrays.stream(container.getItems()).forEach(item -> data.add(new ItemModel(client, item).toJson()));
        }
        writeResponse(exchange, 200, data);
    }

    private void highlightPlayer(HttpExchange exchange) throws IOException {
        JsonObject postData = getPostData(exchange);
        int playerId = postData.get("player_id").getAsInt();

        if (!plugin.highlightedPlayers.contains(playerId)) {
            plugin.highlightedPlayers.add(playerId);
        }
        writeResponse(exchange, 200, postData);
    }

    private void unhighlightPlayer(HttpExchange exchange) throws IOException {
        JsonObject postData = getPostData(exchange);
        int playerId = postData.get("player_id").getAsInt();

        if (plugin.highlightedPlayers.contains(playerId)) {
            int index = plugin.highlightedPlayers.indexOf(playerId);
            plugin.highlightedPlayers.remove(index);
        }
        writeResponse(exchange, 200, postData);
    }

    private void unhighlightAll(HttpExchange exchange) throws IOException {
        plugin.highlightedPlayers.clear();
        writeResponse(exchange, 200, new JsonObject());
    }
}
