package org.example.view.viewmodel;

import java.util.List;
import java.util.ArrayList;

public class RoomViewModel {

    public final String roomName;
    public final String roomDescription;
    public final String monsterDescription; // (Pode ser null)
    public final String exitsLine;

    public final List<ItemData> items;

    // Sub-classe de Item
    public static class ItemData {
        public final String name;
        public final String description;

        public ItemData(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    public RoomViewModel(String roomName, String roomDescription, String monsterDescription, String exitsLine) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.monsterDescription = monsterDescription;
        this.exitsLine = exitsLine;
        this.items = new ArrayList<>();
    }
}