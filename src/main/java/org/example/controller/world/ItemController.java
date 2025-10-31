package org.example.controller.world;

import org.example.model.Item;
import org.example.model.Room;

public class ItemController {
    private final WorldController worldController;

    public ItemController(WorldController worldController) {
        this.worldController = worldController;
    }

    public String useItem(Item item, Room currentRoom) {
        return switch (item.getItemName()) {
            case "chave" -> "Destrancou a porta com a chave.";
            case "poção" -> "Você bebe a poção e sente-se revigorado.";
            case "tocha" -> "A tocha ilumina a sala.";
            default -> "Nada acontece.";
        };
    }

    public String inspectItem(Item item) {
        String desc = item.getDescription();
        if (item.getType().equals("key")) {
            desc += " Parece uma chave antiga.";
        }
        return desc;
    }

/*    public Optional<String> triggerEvent(Item item, Room room) {
        if (item.getItemName().equalsIgnoreCase("amuleto") && room.getRoom_name().equals("templo")) {
            // desbloqueia nova saída
           room.addAdjacentRoom("south", "escada secreta"); //supondo que escada_secreta já existe
            return Optional.of("O amuleto brilha e revela uma escada!");
        }
        return Optional.empty();
    }
*/


}

