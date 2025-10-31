package org.example.model;

public class Item {

    private String name;
    private String description;

    public Item(String item_name, String description) {
        this.name = item_name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return name;
    }

    public void setItemName(String item_name) {
        this.name = item_name;
    }

    public Object getType() {
        return null;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}
