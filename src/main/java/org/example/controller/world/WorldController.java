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



    /**
     *
     * Move o jogador, executa combate E verifica a vitória.
     */
    public String moveToRoomDirection(String direction) {
        Room currentRoom = world.getCurrentRoom();
        String nextRoomName = currentRoom.getAdjacentRooms().get(direction);

        if (nextRoomName != null) {
            Room nextRoom = world.getRooms().get(nextRoomName);
            if (nextRoom != null) {
                // Mover o jogador
                world.setCurrentRoom(nextRoom);   // Atualiza a sala atual do mundo

                String responsePrefix = ""; // Mensagem de combate, se houver

                //  LÓGICA DE COMBATE
                Monster monster = nextRoom.getMonster();
                if (monster != null && !monster.isDefeated()) {
                    String defeatItem = monster.getDefeatItem();

                    if (inventoryController.hasItemByName(defeatItem)) {
                        // Vitória no Combate
                        monster.setDefeated(true); // Marca o monstro como morto
                        // (Opcional: consumir o item)
                        // inventoryController.removeItem(defeatItem);
                        responsePrefix = monster.getDefeatMessage() + "\n\n";
                        Item item = inventoryController.getItemByName(defeatItem);
                        inventoryController.drop(item);


                    } else {
                        //  Derrota (GAME OVER)
                        world.setGameOver(true); // AVISA QUE O JOGO ACABOU
                        return "GAME OVER: " + monster.getDescription() + "\n" +
                                "Você não tem o item [" + defeatItem + "] para se defender!";
                    }
                }

                //VITÓRIA
                // (Só chega aqui se sobreviveu ao passo 2)
                if (nextRoom == world.getEndingRoom()) {
                    world.setGameOver(true); // AVISA QUE O JOGO ACABOU
                    return responsePrefix + "VOCÊ VENCEU! Você encontrou a saída!\n\n" +
                            nextRoom.getDescription(); // Retorna a descrição crua
                }

                // JOGO CONTINUA (Moveu, não morreu, não venceu)
                // Retorna a msg de combate (se houver) + descrição da sala
                return responsePrefix + nextRoom.getDescription();

            } else {
                return "A sala para onde você está tentando ir não existe.";
            }
        } else {
            return "Não há saída nessa direção.";
        }
    }


    public String takeItem(String itemName){
        Room current = world.getCurrentRoom();
        Item removedItem = roomController.removeItemFromRoom(current, itemName);

        if(removedItem != null){
            String result = inventoryController.pickUp(removedItem);
            if (result.startsWith("Inventário cheio")) {
                roomController.addItemToRoom(current, removedItem);
                return "Não foi possível pegar o item: " + itemName;
            } else {
                return result;
            }
        }else{
            return "Item não encontrado na sala: " + itemName;
        }
    }

    public String dropItem(String itemName){
        Room current = world.getCurrentRoom();
        if (inventoryController.hasItemByName(itemName)){
            Item itemToDrop = inventoryController.getItemByName(itemName);
            if(itemToDrop== null){
                return "Item não encontrado no inventário: " + itemName;
            }
            String result = inventoryController.drop(itemToDrop);
            if(result.startsWith("Você largou")){
                roomController.addItemToRoom(current, itemToDrop);
                return result;
            } else {
                inventoryController.pickUp(itemToDrop);
                return "Não foi possível soltar o item: " + itemName + " aqui.";
            }
        } else {
            return "Item não encontrado no inventário: " + itemName;
        }
    }

    public String useItem(String itemName) {
        Room current = world.getCurrentRoom();


        // 1. Tenta achar uma useAction da sala
        UseAction action = findUseActionForItem(current, itemName);
        if (action == null) {
            return "Nada acontece.";
        }

        // 2. Exigir que o jogador tenha o item
        if (!inventoryController.hasItemByName(itemName)) {
            return "Você não tem " + itemName + ".";
        }

        // 3. Aplica o efeito
        applyUseAction(current, action);

        // (Opcional: consumir o item)
         Item item = inventoryController.getItemByName(itemName);
         inventoryController.drop(item);

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
                    removeItemFromRoomByName(room, action.getTargetItem());
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

    private void removeItemFromRoomByName(Room room, String targetName) {
        Iterator<Item> it = room.getItems().iterator();
        while (it.hasNext()) {
            Item i = it.next();
            if (i.getItemName().equalsIgnoreCase(targetName)) {
                it.remove();
                break;
            }
        }
    }



}





