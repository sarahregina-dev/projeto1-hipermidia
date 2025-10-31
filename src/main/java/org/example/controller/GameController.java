package org.example.controller;
import org.example.controller.world.WorldController;
import org.example.util.DirectionMapper;

public class GameController { // só conversa com world controller.
    private final WorldController worldController;


    public GameController(WorldController worldController) {
        this.worldController = worldController;

    }

    public boolean isGameOver() {
        return worldController.getWorld().getCurrentRoom().getRoomName()
                .equalsIgnoreCase(worldController.getWorld().getEndingRoom().getRoomName());
    }

    private String normalizeDirection(String userDir) {
        String d = userDir.toLowerCase().trim();

        switch (d) {
            case "norte": return "north";
            case "sul":   return "south";
            case "leste": return "east";
            case "oeste": return "west";
            case "subir":    return "up";
            case "descer":  return "down";

            // caso a pessoa já digite em inglês (pra dev testar), aceita também
            case "north":
            case "south":
            case "east":
            case "west":
            case "up":
            case "down":
                return d;

            default:
                return null; // direção inválida
        }
    }


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

            case "sair", "quit", "exit" -> "__EXIT_GAME__";

            default -> "Comando não reconhecido.";
        };
    }

}
