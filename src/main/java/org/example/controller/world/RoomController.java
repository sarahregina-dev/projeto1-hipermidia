package org.example.controller.world;

import org.example.model.Item;
import org.example.model.Monster;
import org.example.model.Room;

import java.util.Dictionary;
import java.util.List;

public class RoomController {


    public RoomController() {

    }

    public boolean addItemToRoom(Room room, Item item) {
        return room.addItem(item);
        }


    public Item removeItemFromRoom(Room room, String itemName) {
        return room.removeItemByName(itemName);

    }

    public List<Item> getItemsInRoom(Room room) {
        return room.getItems();
    }

    public String getRoomDescription(Room room) {
        return room.getDescription();
    }
    public String getRoomName(Room room) {
        return room.getRoomName();
    }
    public Item getItemByName(Room room, String itemName) {
        return room.getItemByName(itemName);
    }

    public Dictionary<String, String> getAdjacentRooms(Room room) {
        return room.getAdjacentRooms();
    }

    public Monster getMonster(Room room) {
        return room.getMonster();
    }


}
