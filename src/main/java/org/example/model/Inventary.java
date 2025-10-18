package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Inventary {
    private List<Item> items;
    private int maxItems;


    public Inventary(int maxItems) {
        this.maxItems = maxItems;
        this.items = new ArrayList<>();
    }

    public boolean addItem(Item item) {
        if (items.size() >= maxItems) {
            return false;
        }
        items.add(item);
        return true;

    }

    public List<Item> getItems() {
        return items;
    }

    public int getMaxItems() {
        return maxItems;
    }


}
