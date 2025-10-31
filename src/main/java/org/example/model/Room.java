package org.example.model;

import java.util.Dictionary;
import java.util.List;

public class Room {
    private String roomName;
    private String description;
    private List<Item> items;
    private Dictionary<String,String> adjacentRooms;
    private Monster monster;
    List<UseAction> useActions;

    public Room(String room_name, String description, List<Item> itemsList, Dictionary<String, String> adjacentRooms, Monster monster, List<UseAction> useActions) {
        this.roomName = room_name;
        this.description = description;
        this.items = itemsList;
        this.adjacentRooms = adjacentRooms;
        this.monster = monster;
        this.useActions = useActions;

    }
    public Monster getMonster() { return monster; }
    public void setMonsters(Monster monster) { this.monster = monster; }

    public List<UseAction> getUseActions() { return useActions; }
    public void setUseActions(List<UseAction> useActions) { this.useActions = useActions; }


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String room_name) {
        this.roomName = room_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
    public Item removeItemByName(String itemName) {
        Item item = getItemByName(itemName);
        if (item != null) {
            this.items.remove(item);
            return item;
        } else return null;

    }

    public Dictionary<String, String> getAdjacentRooms() {
        return adjacentRooms;
    }
    public Item getItemByName(String itemName) {
        for (Item item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void setAdjacentRooms(Dictionary<String, String> adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    public void addAdjacentRoom(String direction, String roomName) {
        this.adjacentRooms.put(direction, roomName);
    }
}


