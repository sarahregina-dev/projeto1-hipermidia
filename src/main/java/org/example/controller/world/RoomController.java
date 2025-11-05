package org.example.controller.world;

import org.example.model.Item;
import org.example.model.Monster;
import org.example.model.Room;
import org.example.util.DirectionMapper;
import org.example.view.UiFormatter;
import org.example.view.viewmodel.RoomViewModel;

import java.util.*;

public class RoomController {


    public RoomController() {

    }

    public boolean addItemToRoom(Room room, Item item) {
        return room.addItem(item);
        }
    public Item removeItemFromRoom(Room room, String itemName) {
        return room.removeItemByName(itemName);
    }

    private List<Item> getItemsInRoom(Room room) {
        return room.getItems();
    }

    private String getRoomDescription(Room room) {
        return room.getDescription();
    }
    private String getRoomName(Room room) {
        return room.getRoomName();
    }
    private Item getItemByName(Room room, String itemName) {
        return room.getItemByName(itemName);
    }

    public Dictionary<String, String> getAdjacentRooms(Room room) {
        return room.getAdjacentRooms();
    }

    public Monster getMonster(Room room) {
        return room.getMonster();
    }

    public RoomViewModel createViewModel(Room room) {

        String name = this.getRoomName(room).toUpperCase(Locale.ROOT);


        String desc = this.getRoomDescription(room);

        Monster monster = this.getMonster(room);
        String monsterDesc = null;
        if (monster != null && !monster.isDefeated()) {
            monsterDesc = monster.getDescription();
        }

        // Prepara as Saídas
        Enumeration<String> dirs = this.getAdjacentRooms(room).keys();
        List<String> saidasPt = new ArrayList<>();
        while (dirs.hasMoreElements()) {
            String dirInternal = dirs.nextElement();
            String dirPlayer = DirectionMapper.toPlayer(dirInternal);
            saidasPt.add(dirPlayer);
        }
        String exitsLine = String.join(", ", saidasPt);


        RoomViewModel viewModel = new RoomViewModel(name, desc, monsterDesc, exitsLine);

        if (!this.getItemsInRoom(room).isEmpty()) {
            for (Item item : room.getItems()) {
                String rawName = item.getItemName();
                String formattedName = UiFormatter.formatInsideToOutside(rawName);
                String itemDesc = item.getDescription();
                viewModel.items.add(new RoomViewModel.ItemData(formattedName, itemDesc));
            }
        }

        return viewModel;
    }





}
