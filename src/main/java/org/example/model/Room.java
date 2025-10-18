package org.example.model;

import java.util.Dictionary;
import java.util.List;

public class Room {
    private String roomName;
    private String description;
    private List<Item> items;
    private Dictionary<String,String> adjacentRooms;

    public Room(String room_name, String description, List<Item> itemsList, Dictionary<String, String> adjacentRooms) {
        this.roomName = room_name;
        this.description = description;
        this.items = itemsList;
        this.adjacentRooms = adjacentRooms;
    }

    public String getRoom_name() {
        return roomName;
    }

    public void setRoom_name(String room_name) {
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

    public Dictionary<String, String> getAdjacentRooms() {
        return adjacentRooms;
    }

    public void setAdjacentRooms(Dictionary<String, String> adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }
}
