package org.example.controller;

import org.example.controller.world.WorldController;
import org.example.model.Room;
import org.example.util.DirectionMapper;
import org.example.view.InventoryView;
import org.example.view.RoomView;

public class GameController {
    private final WorldController worldController;

    private final RoomView roomView;
    private final InventoryView inventoryView;

    public GameController(WorldController worldController) {
        this.worldController = worldController;

        //  O GameController cria (instancia) as Views
        this.roomView = new RoomView();
        this.inventoryView = new InventoryView();
    }


    public boolean isGameOver() {
        return worldController.isGameOver();
    }



    public String handleCommand(String input) {
        if (input == null || input.isBlank()) return "Não entendi.";

        String cmd = input.trim().toLowerCase();

        String[] parts = cmd.split("\\s+", 2);
        String action = parts[0];
        String arg = (parts.length > 1) ? parts[1] : "";

        return switch (action) {
            case "look", "olhar", "examinar", "examinar sala" -> {
                // 1. Pega os DADOS do WorldController
                Room currentRoom = worldController.getCurrentRoom();
                // 2. Passa os DADOS para a View renderizar
                yield roomView.renderRoom(currentRoom);
            }

            case "inventario", "inventário", "mochila", "bag" -> {
                // 1. Pega o InventoryController (que tem os dados)
                // 2. Passa para a View renderizar
                yield inventoryView.render(worldController.getInventoryController());
            }

            case "ir" -> {
                if (arg.isEmpty()) {
                    yield "Ir pra onde?";
                }


                String internalDir = DirectionMapper.toInternal(arg);
                if (internalDir == null) {
                    yield "Parece que essa direção não existe. Use norte, sul, leste ou oeste.";
                }

                // O moveToRoomDirection  retorna a string
                // final, incluindo GAME OVER, VITÓRIA, ou a descrição da sala.
                yield worldController.moveToRoomDirection(internalDir);
            }

            case "pegar" -> {
                if (arg.isEmpty()) yield "Pegar o quê?";
                //  Retorna uma string de status
                yield worldController.takeItem(arg);
            }

            case "largar", "drop" -> {
                if (arg.isEmpty()) yield "Largar o quê?";
                // Retorna uma string de status
                yield worldController.dropItem(arg);
            }


            case "usar", "use" -> {
                if (arg.isEmpty()) yield "Usar o quê?";
                //  Retorna uma string de status do puzzle
                yield worldController.useItem(arg);
            }

            case "sair", "quit", "exit" -> "__EXIT_GAME__";

            default -> "Comando não reconhecido.";
        };
    }
}



