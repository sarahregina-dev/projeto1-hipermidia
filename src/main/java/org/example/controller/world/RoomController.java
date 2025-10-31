package org.example.controller.world;

import org.example.model.Item;
import org.example.model.Room;

import java.util.List;

public class RoomController {


    public RoomController() {

    }


    public boolean addItemToRoom(Room room, Item item) {
        List<Item>items = room.getItems();
        if(items.contains(item)){
            return false; //Não foi adicionado porque já estava na sala
        }else{
            items.add(item);
            return true; //Adicionado com sucesso
        }

    }

    public Item removeItemFromRoom(Room room, String itemName) {
        return room.removeItemByName(itemName);

    }

    //Todo:  get item on the floor, add item to the floor
}
