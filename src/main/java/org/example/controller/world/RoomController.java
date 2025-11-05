package org.example.controller.world;

import org.example.model.Item;
import org.example.model.Room;

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

}
