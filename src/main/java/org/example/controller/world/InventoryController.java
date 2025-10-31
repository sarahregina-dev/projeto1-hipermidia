package org.example.controller.world;

import org.example.model.Inventory;
import org.example.model.Item;

import java.util.List;

public class InventoryController {

    private final Inventory inventory;

    public InventoryController(Inventory inventory) {
        this.inventory = inventory;
    }

    public String pickUp(Item item){
        boolean success = inventory.add(item);
        if (success){
            return "Você pegou o item: " + item.getItemName();
        } else {
            return "Inventário cheio! Não é possível pegar o item: " + item.getItemName();
        }
    }

    public String drop(Item item){    // lembrar de depois fazer o drop, add na sala atual, mas no controller da sala
        boolean success = inventory.remove(item);
        if (success){
            return "Você largou o item: " + item.getItemName();
        } else {
            return "Item não encontrado no inventário: " + item.getItemName();
        }
    }

    public List<Item> getItems() {

        return inventory.getItems();
    }



    public boolean hasItemByName(String name) {
        for (Item item : inventory.getItems()) {
            if (item.getItemName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
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
