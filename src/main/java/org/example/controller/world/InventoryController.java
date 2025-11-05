package org.example.controller.world;

import org.example.model.Inventory;
import org.example.model.Item;
import org.example.view.InventoryViewModel;

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

    private List<Item> getItems() {

        return inventory.getItems();
    }

    public boolean hasItemByName(String name) {
        return inventory.hasItemByName(name);
    }

    public Item getItemByName(String name) {
        return inventory.getItemByName(name);
    }

    private int getMaxItems() {
        return inventory.getMaxItems();
    }

    public int setMaxItems(int maxItems) {
        inventory.setMaxItems(maxItems);
        return maxItems;
    }

    private int getCurrentSize() {
        return inventory.getItems().size();
    }

    public InventoryViewModel createViewModel() {

        int current = this.getCurrentSize();
        int max = this.getMaxItems();
        List<Item> itemsDoModel = this.getItems();


        InventoryViewModel viewModel = new InventoryViewModel(current, max);


        for (Item itemModel : itemsDoModel) {
            String nome = itemModel.getItemName();
            String desc = itemModel.getDescription();
            viewModel.items.add(new InventoryViewModel.ItemData(nome, desc));
        }

        return viewModel;
    }



}
