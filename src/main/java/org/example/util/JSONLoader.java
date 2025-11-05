package org.example.util;

import org.example.model.Item;
import org.example.model.Monster;
import org.example.model.Room;
import org.example.model.UseAction;
import org.example.model.World;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class JSONLoader {


     //Carrega o mundo

    public static World loadFromFile(String path) {
        try {
            // Lê e parseia o JSON
            String raw = Files.readString(Paths.get(path));
            JSONObject root = new JSONObject(raw);

            //Extrai os infos globais
            String startRoomName = root.getString("main");
            String endRoomName = root.getString("exit");
            int maxInventoryItems = root.getInt("max_itens");

            // Parseia  as salas
            JSONObject roomsObject = root.getJSONObject("rooms");
            Map<String, Room> roomsByName = parseRooms(roomsObject);

            // Obtém as instâncias das salas de início e fim
            Room startRoom = roomsByName.get(startRoomName);
            Room endRoom = roomsByName.get(endRoomName);

            if (startRoom == null || endRoom == null) {
                throw new RuntimeException("Salas 'main' ou 'exit' não encontradas no mapa de salas.");
            }

            return new World(
                    roomsByName,
                    startRoom,
                    startRoom,      // currentRoom começa na sala inicial
                    endRoom,
                    maxInventoryItems
            );

        } catch (Exception e) {
            throw new RuntimeException("Falha ao carregar mundo do arquivo: " + path, e);
        }
    }

    //Parseia cada sala individualmente.

    private static Map<String, Room> parseRooms(JSONObject roomsObject) {
        Map<String, Room> roomsByName = new HashMap<>();

        for (String roomName : roomsObject.keySet()) {
            JSONObject roomJson = roomsObject.getJSONObject(roomName);
            Room room = parseSingleRoom(roomName, roomJson);
            roomsByName.put(roomName, room);
        }
        return roomsByName;
    }

    //Parseia um único objeto de sala do JSON

    private static Room parseSingleRoom(String roomName, JSONObject roomJson) {
        String description = roomJson.getString("description");

        List<Item> items = parseItems(roomJson);
        Dictionary<String, String> exits = parseExits(roomJson);
        Monster monster = parseMonster(roomJson);
        List<UseAction> useActions = parseUseActions(roomJson);

        return new Room(
                roomName,
                description,
                items,
                exits,
                monster,
                useActions
        );
    }

    //Extrai a lista de itens de uma sala
    private static List<Item> parseItems(JSONObject roomJson) {
        List<Item> items = new ArrayList<>();
        if (roomJson.has("itens")) {
            JSONObject itensJson = roomJson.getJSONObject("itens");
            for (String itemName : itensJson.keySet()) {
                String itemDesc = itensJson.getString(itemName);
                items.add(new Item(itemName, itemDesc));
            }
        }
        return items;
    }

    //Extrai as saídas (direções) de uma sala

    private static Dictionary<String, String> parseExits(JSONObject roomJson) {
        Dictionary<String, String> exits = new Hashtable<>();
        String[] directions = {"north", "south", "east", "west", "up", "down"};

        for (String dir : directions) {
            if (roomJson.has(dir)) {
                String destinationRoomName = roomJson.getString(dir);
                exits.put(dir, destinationRoomName);
            }
        }
        return exits;
    }

    //Extrai o monstro de uma sala, se existir

    private static Monster parseMonster(JSONObject roomJson) {
        if (roomJson.has("monster") && !roomJson.isNull("monster")) {
            JSONObject m = roomJson.getJSONObject("monster");
            String mName = m.getString("name");
            String mDesc = m.getString("description");
            String defeatItem = m.getString("defeat_item");
            String defeatMsg = m.getString("defeat_message");
            return new Monster(mName, mDesc, defeatItem, defeatMsg);
        }
        return null;
    }

    //Extrai a lista de ações "use" (interações com itens) de uma sala

    private static List<UseAction> parseUseActions(JSONObject roomJson) {
        List<UseAction> useActions = new ArrayList<>();

        if (roomJson.has("use")) {
            org.json.JSONArray useArray = roomJson.getJSONArray("use");
            for (int i = 0; i < useArray.length(); i++) {
                org.json.JSONObject useObj = useArray.getJSONObject(i);

                String itemName = useObj.getString("item");
                String useDesc = useObj.getString("description");

                JSONObject actionObj = useObj.getJSONObject("action");

                // Lemos as propriedades de dentro do objeto action
                String type = actionObj.getString("type");
                String direction = actionObj.has("direction") ? actionObj.getString("direction") : null;
                String toRoom = actionObj.has("to") ? actionObj.getString("to") : null;
                String targetItem = actionObj.has("target_item") ? actionObj.getString("target_item") : null;

                // String targetMonster = actionObj.has("target_monster") ? actionObj.getString("target_monster") : null;


                UseAction ua = new UseAction(
                        itemName,
                        useDesc,
                        type,
                        direction,
                        toRoom,
                        targetItem,
                        null
                );
                useActions.add(ua);
            }
        }
        return useActions;
    }
}