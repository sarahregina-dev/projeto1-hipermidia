package org.example.controller;

// <<< IMPORTANTE: Adicione os imports das suas Views e Modelos >>>
import org.example.controller.world.WorldController;
import org.example.model.Room;
import org.example.util.DirectionMapper;
import org.example.view.InventoryView;
import org.example.view.RoomView;
// (Talvez você precise de 'org.example.controller.world.InventoryController')

public class GameController {
    private final WorldController worldController;

    // <<< MUDANÇA (Passo 1): As Views agora moram aqui, no GameController >>>
    private final RoomView roomView;
    private final InventoryView inventoryView;
    // (Presumi que você tem um DirectionMapper, como no seu código original)
    // private final DirectionMapper directionMapper;

    public GameController(WorldController worldController) {
        this.worldController = worldController;

        // <<< MUDANÇA (Passo 2): O GameController cria (instancia) as Views >>>
        this.roomView = new RoomView();
        this.inventoryView = new InventoryView();
        // this.directionMapper = new DirectionMapper(); // Se você tiver um
    }

    /**
     * <<< CORRIGIDO (Passo 3) >>>
     * Agora delega a pergunta para o WorldController,
     * que sabe tanto da vitória quanto da derrota (Forma 1).
     */
    public boolean isGameOver() {
        return worldController.isGameOver();
    }

    /**
     * <<< REMOVIDO (Passo 4) >>>
     * Este método não estava sendo usado (era código morto).
     * O 'DirectionMapper.toInternal' já faz esse papel.
     */
    // private String normalizeDirection(String userDir) { ... }


    public String handleCommand(String input) {
        if (input == null || input.isBlank()) return "Não entendi.";

        String cmd = input.trim().toLowerCase();

        String[] parts = cmd.split("\\s+", 2);
        String action = parts[0];
        String arg = (parts.length > 1) ? parts[1] : "";

        // <<< MUDANÇA (Passo 5): O Switch foi atualizado para usar as Views >>>
        return switch (action) {
            case "look", "olhar", "examinar", "examinar sala" -> {
                // 1. Pega os DADOS do WorldController
                Room currentRoom = worldController.getCurrentRoom();
                // 2. Passa os DADOS para a View renderizar
                yield roomView.renderRoom(currentRoom);
            }

            case "inventario", "inventário", "mochila", "bag" -> {
                // 1. Pega o InventoryController (que tem os dados)
                // (Assumindo que worldController.getInventoryController() existe)
                // 2. Passa para a View renderizar
                yield inventoryView.render(worldController.getInventoryController());
            }

            case "ir" -> {
                if (arg.isEmpty()) {
                    yield "Ir pra onde?";
                }

                // (Sua lógica original está ótima)
                String internalDir = DirectionMapper.toInternal(arg);
                if (internalDir == null) {
                    yield "Parece que essa direção não existe. Use norte, sul, leste ou oeste.";
                }

                // (PERFEITO!) O moveToRoomDirection já retorna a string
                // final, incluindo GAME OVER, VITÓRIA, ou a descrição da sala.
                yield worldController.moveToRoomDirection(internalDir);
            }

            case "pegar" -> {
                if (arg.isEmpty()) yield "Pegar o quê?";
                // (Correto!) Retorna uma string de status
                yield worldController.takeItem(arg);
            }

            case "largar", "drop" -> {
                if (arg.isEmpty()) yield "Largar o quê?";
                // (Correto!) Retorna uma string de status
                yield worldController.dropItem(arg);
            }

           // <<< ADICIONADO (Passo 6) >>>
            case "usar", "use" -> {
                if (arg.isEmpty()) yield "Usar o quê?";
                // (Correto!) Retorna uma string de status do puzzle
                yield worldController.useItem(arg);
            }

            case "sair", "quit", "exit" -> "__EXIT_GAME__";

            default -> "Comando não reconhecido.";
        };
    }
}






/*** import org.example.controller.world.WorldController;
import org.example.model.Monster;
import org.example.model.Room;
import org.example.util.DirectionMapper;
public class GameController { // só conversa com world controller.
    private final WorldController worldController;

    public GameController(WorldController worldController) {
        this.worldController = worldController;
    }


     * CORRIGIDO: Agora delega a pergunta para o WorldController,
     * que sabe tanto da vitória quanto da derrota (Forma 1).

    public boolean isGameOver() {
        return worldController.isGameOver();
    }

    /**
     * REMOVIDO: Este método não estava sendo usado.
     * O 'DirectionMapper.toInternal' já faz esse papel.

    // private String normalizeDirection(String userDir) { ... }


    public String handleCommand(String input) {
        if (input == null || input.isBlank()) return "Não entendi.";

        String cmd = input.trim().toLowerCase();

        // divide em comando e argumento (ex: "ir norte" -> cmd="ir", arg="norte")
        String[] parts = cmd.split("\\s+", 2);
        String action = parts[0];
        String arg = (parts.length > 1) ? parts[1] : "";

        return switch (action) {
            case "look", "olhar", "examinar", "examinar sala" ->
                    worldController.describeCurrentRoom();

            case "inventario", "inventário", "mochila", "bag" ->
                    worldController.listItemsInInventory();

            case "ir" -> {
                if (arg.isEmpty()) {
                    yield "Ir pra onde?";
                }

                // (Seu código aqui usando DirectionMapper está correto)
                String internalDir = DirectionMapper.toInternal(arg);
                if (internalDir == null) {
                    yield "Parece que essa direção não existe. Use norte, sul, leste ou oeste.";
                }

                yield worldController.moveToRoomDirection(internalDir);
            }

            case "pegar" -> {
                if (arg.isEmpty()) yield "Pegar o quê?";
                yield worldController.takeItem(arg);
            }

            case "largar", "drop" -> {
                if (arg.isEmpty()) yield "Largar o quê?";
                yield worldController.dropItem(arg);
            }

            /**
             * ADICIONADO: O comando 'use' (usar) estava faltando.

            case "usar", "use" -> {
                if (arg.isEmpty()) yield "Usar o quê?";
                yield worldController.useItem(arg);
            }

            case "sair", "quit", "exit" -> "__EXIT_GAME__";

            default -> "Comando não reconhecido.";
        };
    }
}
*/


