package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private int maxItems;


    public Inventory(int maxItems) {
        this.maxItems = maxItems;
        this.items = new ArrayList<>();
    }

    public boolean add(Item item) {
        if (items.size() >= maxItems) {
            return false;
        }
        items.add(item);
        return true;

    }
    public boolean remove(Item item) {
        if(!items.contains(item)){
            return false;
        }
        return items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItemByName(String itemName) {
        for (Item item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
    public boolean hasItemByName(String name) {
        for (Item item : this.items) {
            if (item.getItemName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }


    public int getMaxItems() {
        return maxItems;
    }

    public boolean isFull() {
        return items.size() >= maxItems;
    }

    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }


}
