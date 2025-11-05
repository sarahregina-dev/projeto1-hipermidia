package org.example.controller.world;

import org.example.model.Item;

public class ItemController {

    private Item item;
    public ItemController(Item item) {
        this.item = item;
    }
    public String getItemName() {
        return item.getItemName();
    }
    public String getItemDescription() {
        return item.getDescription();
    }


}
