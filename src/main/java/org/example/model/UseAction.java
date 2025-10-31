package org.example.model;

public class UseAction {

    private final String itemName;     // item que ativa: "vela"
    private final String description;  // texto narrativo: "Você acende a vela..."
    private final String type;         // "abrir_direcao", "sumir_item", "derrotar_monstro", ...

    // campos opcionais
    private final String direction;    // pra abrir/fechar direção
    private final String toRoom;       // sala destino pra abrir
    private final String targetItem;   // pra sumir/aparecer item
    private final String targetMonster;// pra derrotar monstro específico (opcional)

    public UseAction(String itemName,
                     String description,
                     String type,
                     String direction,
                     String toRoom,
                     String targetItem,
                     String targetMonster) {
        this.itemName = itemName;
        this.description = description;
        this.type = type;
        this.direction = direction;
        this.toRoom = toRoom;
        this.targetItem = targetItem;
        this.targetMonster = targetMonster;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getDirection() {
        return direction;
    }

    public String getToRoom() {
        return toRoom;
    }

    public String getTargetItem() {
        return targetItem;
    }

    public String getTargetMonster() {
        return targetMonster;
    }
}
