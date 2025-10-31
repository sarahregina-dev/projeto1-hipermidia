package org.example.controller.world;

import org.example.model.Inventory;
import org.example.model.Item;

import java.util.List;

public class InventoryController {

    private final Inventory inventory;

    public InventoryController(Inventory inventory) {
        this.inventory = inventory;
    }

    public Boolean pickUp(Item item){
       return inventory.add(item);

    }

    public Boolean drop(Item item){
         return inventory.remove(item);

    }

    public List<Item> getItems() {

        return inventory.getItems();
    }

    public boolean hasItemByName(String name) {
        return inventory.hasItemByName(name);
    }

    public Item getItemByName(String name) {
        return inventory.getItemByName(name);
    }

    public int getMaxItems() {
        return inventory.getMaxItems();
    }

    public int setMaxItems(int maxItems) {
        inventory.setMaxItems(maxItems);
        return maxItems;
    }

    public int getCurrentSize() {
        return inventory.getItems().size();
    }






}
