package org.example.controller.world;

import org.example.model.*;
import org.example.view.InventoryView;
import org.example.view.RoomView;

import java.util.Iterator;

public class WorldController {

    private World world;
    private Inventory inventory;
    private InventoryController inventoryController;
    private RoomController roomController;

    public WorldController(World worldData) {
        this.world = worldData;
        this.inventory = new Inventory(worldData.getInventaryMaxItems());
        this.inventoryController = new InventoryController(this.inventory);
        this.roomController = new RoomController();
    }

    public InventoryController getInventoryController() {
        return inventoryController;
    }

    public World getWorld() {
        return this.world;
    }

    public Room getCurrentRoom() {
        return world.getCurrentRoom();
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public boolean isGameOver() {
        return world.isGameOver();
    }
    public Monster getDefeatingMonster() {
        return world.getDefeatingMonster();
    }



    /**
     *
     * Move o jogador, executa combate E verifica a vitória.
     */
    public String moveToRoomDirection(String direction) {
        Room currentRoom = world.getCurrentRoom();
        String nextRoomName = currentRoom.getAdjacentRooms().get(direction);
        if(nextRoomName == null) {
            return "__MOVE_ERROR_NO_EXIT__";
        }
        Room nextRoom = world.getRooms().get(nextRoomName);
        if (nextRoom == null) {
            return "__MOVE_ERROR_ROOM_NOT_FOUND__";
        }
        // Mover o jogador
        world.setCurrentRoom(nextRoom);
        String responsePrefix = "";

        // LÓGICA DE COMBATE
        Monster monster = nextRoom.getMonster();
        if (monster != null && !monster.isDefeated()) {
            String defeatItem = monster.getDefeatItem();

            if (inventoryController.hasItemByName(defeatItem)) {
                // Vitória no Combate
                monster.setDefeated(true);
                responsePrefix = monster.getDefeatMessage() + "\n\n";
                Item item = inventoryController.getItemByName(defeatItem);
                inventoryController.drop(item);
            } else {
                // Derrota (GAME OVER)
                world.setGameOver(true);
                world.setDefeatingMonster(monster);

                return "__GAME_OVER_DEFEAT__";
            }
        }

        // VITÓRIA
        if (nextRoom == world.getEndingRoom()) {
            world.setGameOver(true);
            return responsePrefix + "__GAME_WON__";
        }
        // JOGO CONTINUA
        // Retorna a msg de combate (se houver) + descrição da sala
        return responsePrefix + nextRoom.getDescription();


    }


    public String takeItem(String itemName){
        Room current = world.getCurrentRoom();
        Item removedItem = roomController.removeItemFromRoom(current, itemName);
        if (removedItem == null) {
            return "__TAKE_ERROR_NOT_FOUND__";
        }
        boolean pickUpSucess = inventoryController.pickUp(removedItem);

        if(pickUpSucess){
            return "__TAKE_SUCCESS__";
        } else {
            // Devolve o item para a sala
            roomController.addItemToRoom(current, removedItem);
            return "__TAKE_ERROR_FULL__";
        }



    }

    public String dropItem(String itemName){
        Room current = world.getCurrentRoom();
        Item itemToDrop = inventoryController.getItemByName(itemName);

        if (itemToDrop == null) {
            return "__DROP_ERROR_NOT_FOUND__";

        }

        boolean dropSucess = inventoryController.drop(itemToDrop);

        if(dropSucess){
            roomController.addItemToRoom(current, itemToDrop);
            return "__DROP_SUCCESS__";
        }else {
            return "__DROP_ERROR_GENERIC";
        }
    }

    public String useItem(String itemName) {
        Room current = world.getCurrentRoom();


        //  O jogador tem o item?
        if (!inventoryController.hasItemByName(itemName)) {
            return "__USE_ERROR_NOT_OWNED__";
        }
        // O item faz algo nesta sala?
        UseAction action = findUseActionForItem(current, itemName);
        if (action == null) {
            return "__USE_ERROR_NO_ACTION__";
        }
        applyUseAction(current, action);

        //  Se chegou aqui deu certo
        // Retorne a descrição do JSON
        return action.getDescription();
    }



    private UseAction findUseActionForItem(Room room, String itemName) {
        for (UseAction ua : room.getUseActions()) {
            if (ua.getItemName().equalsIgnoreCase(itemName)) {
                return ua;
            }
        }
        return null;
    }

    private void applyUseAction(Room room, UseAction action) {
        switch (action.getType()) {
            case "abrir_direcao" -> {
                if(action.getDirection()!=null && action.getToRoom()!=null) {
                    room.getAdjacentRooms().put(
                            action.getDirection(),
                            action.getToRoom()
                    );
                }

            }
            case "fechar_direcao" -> {
                if(action.getDirection()!=null) {
                    room.getAdjacentRooms().remove(action.getDirection());

                }
            }
            case "aparecer_item_na_sala" -> {

                if(action.getTargetItem()!=null){
                    room.getItems().add(
                            new Item(action.getTargetItem(), "Um item apareceu aqui.")
                    );
                }
            }
            case "sumir_item_da_sala" -> {
                Item itemParaSumir = inventoryController.getItemByName(action.getTargetItem());
                if(itemParaSumir!=null){
                    inventoryController.drop(itemParaSumir);
                }
            }
            case "derrotar_monstro" -> {
                Monster m = room.getMonster();
                if (m != null && !m.isDefeated()) {
                    m.setDefeated(true);
                }
            }
            default -> {
                // desconhecido: não toma nenhuma ação

            }
        }
    }


}





