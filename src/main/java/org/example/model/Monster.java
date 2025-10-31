package org.example.model;

public class Monster {

    private final String name;
    private final String description;
    private final String defeatItem;     // nome do item que derrota
    private final String defeatMessage;  // mensagem ao derrotar
    private boolean defeated;            // estado atual

    public Monster(String name,
                   String description,
                   String defeatItem,
                   String defeatMessage) {
        this.name = name;
        this.description = description;
        this.defeatItem = defeatItem;
        this.defeatMessage = defeatMessage;
        this.defeated = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDefeatItem() {
        return defeatItem;
    }

    public String getDefeatMessage() {
        return defeatMessage;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }
}
