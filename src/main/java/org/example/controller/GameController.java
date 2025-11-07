package org.example.controller;

import org.example.controller.world.WorldController;
import org.example.model.Monster;
import org.example.model.Room;
import org.example.util.DirectionMapper;
import org.example.view.InventoryView;
import org.example.view.RoomView;
import org.example.view.UiFormatter;
import org.example.view.viewmodel.RoomViewModel;

//Game Controller se comunica com o WorldController para gerenciar a lógica do jogo
// e utiliza as views para apresentar informações ao jogador.

public class GameController {
    private final WorldController worldController;
    private final RoomView roomView;
    private final InventoryView inventoryView;

    public GameController(WorldController worldController) {
        this.worldController = worldController;
        this.roomView = new RoomView();
        this.inventoryView = new InventoryView();
    }


    public Monster getDefeatingMonster() {
        return worldController.getDefeatingMonster();
    }



    public String handleCommand(String input) {
        if (input == null || input.isBlank()) return "Não entendi... Crá-crá ♫ ♪";

        String cmd = input.trim().toLowerCase();

        String[] parts = cmd.split("\\s+", 2);
        String action = parts[0];
        String arg = (parts.length > 1) ? parts[1] : "";
        String formattedArg = UiFormatter.formatOutsideToInside(arg);

        return switch (action) {
            case "look", "olhar", "examinar", "examinar sala" -> {
                Room currentRoom = worldController.getCurrentRoom();
                RoomViewModel viewModel = worldController.getRoomController().createViewModel(currentRoom);
                yield roomView.renderRoom(viewModel);
            }

            case "inventario", "inventário", "mochila", "bag" ->
                    inventoryView.render(worldController.getInventoryController().createViewModel());

            case "ir", "go" -> {
                if (arg.isEmpty()) {
                    yield "Irrr para onde? ♫ ♪ ";
                }
                String internalDir = DirectionMapper.toInternal(arg);
                if (internalDir == null) {
                    yield "Parece que essa dirrreção não existe ♫ ♪. Use norte, sul, leste, oeste, up/subir ou down/descer .";
                }

                String moveResult = worldController.moveToRoomDirection(internalDir);

                yield switch (moveResult) {
                    case "__MOVE_ERROR_NO_EXIT__" -> "Não há saída nessa dirrreção ♫ ♪.";
                    case "__MOVE_ERROR_ROOM_NOT_FOUND__" -> "A sala para onde você está tentando irrr não existe ♫ ♪.";

                    default -> moveResult;
                    };

            }

            case "pegar", "pick" -> {
                if (arg.isEmpty()) yield "Pegarrr o quê?  ♫ ♪" ;
                String takeResult = worldController.takeItem(formattedArg);
                String formattedPickUpName = UiFormatter.formatInsideToOutside(formattedArg); //sempre retorna formatado bonitingo
                //  Retorna uma string de status
                yield switch (takeResult){
                    case "__TAKE_SUCCESS__" -> "Você pegou: "+ formattedPickUpName + " ♫ ♪ ";
                    case "__TAKE_ERROR_NOT_FOUND__" -> "Não há esse item aqui: " + formattedPickUpName + " ♫ ♪ ";
                    case "__TAKE_ERROR_FULL__" -> "♫ ♪ Seu inventárrrio está cheio. ♫ ♪ Não foi possível pegarrr: " + formattedPickUpName ;
                    default -> "Algo inesperrrado aconteceu..." + takeResult + " ♫ ♪  Crá-crá ";
                };
            }

            case "largar", "drop", "soltar" -> {

                if (arg.isEmpty()) yield "Largarrr o quê?  ♫ ♪";
                String dropResult = worldController.dropItem(formattedArg);
                String formattedDropName = UiFormatter.formatInsideToOutside(formattedArg); //sempre retorna formatado bonitingo

                yield switch (dropResult) {
                    case "__DROP_SUCCESS__" -> "♫ ♪ Você larrrgou: " + formattedDropName;
                    case "__DROP_ERROR_NOT_FOUND__" -> "Você não tem o item '" + formattedDropName + "' no inventárrrio. ♫ ♪ ";
                    case "__DROP_ERROR_GENERIC__" -> "Não foi possível largarr o item '" +formattedDropName + "'  ♫ ♪.";
                    default -> "Algo inesperrrado aconteceu... Crá-crá ♫ ♪ " + dropResult;
                };
            }


            case "usar", "use" -> {
                if (arg.isEmpty()) yield "Usarrr o quê? ♫ ♪";
                String useResult = worldController.useItem(formattedArg);

                yield switch (useResult){
                    case "__USE_ERROR_NOT_OWNED__" -> "Você não tem esse item: " + arg + " ♫ ♪ ";
                    case "__USE_ERROR_NO_ACTION__" -> "Nada aconteceu ao usarrr: " + arg + " ♫ ♪ Crá-crá  ♪ ";
                    default -> useResult; // Sucesso
                };
            }

            case "sair", "quit", "exit" -> "__EXIT_GAME__";

            default -> "Não entendi... Crá-crá  ♫ ♪ ";
        };
    }
}



