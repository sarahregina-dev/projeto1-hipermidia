package org.example.view.viewmodel;

import java.util.List;
import java.util.ArrayList;

public class InventoryViewModel {

    public final int currentSize;
    public final int maxItems;
    public final List<ItemData> items;

    // Sub-classe com os dados do Item que a View precisa
    public static class ItemData {
        public final String name;
        public final String description;

        public ItemData(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    // Construtor
    public InventoryViewModel(int currentSize, int maxItems) {
        this.currentSize = currentSize;
        this.maxItems = maxItems;
        this.items = new ArrayList<>();
    }
}