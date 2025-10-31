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

    // <<< MUDANÇA: As Views (RoomView, InventoryView) foram REMOVIDAS daqui.
    // O GameController é quem vai ficar com elas.

    // (Vamos transformar seu 'initWorld' em um construtor de verdade)
    public WorldController(World worldData) {
        this.world = worldData;
        this.inventory = new Inventory(worldData.getInventaryMaxItems());
        this.inventoryController = new InventoryController(this.inventory);
        this.roomController = new RoomController();
    }

    // --- Getters (Métodos de Leitura de Estado) ---

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

    /**
     * <<< NOVO MÉTODO (Forma 1) >>>
     * Apenas repassa a informação do Modelo (World) para o GameController.
     */
    public boolean isGameOver() {
        return world.isGameOver();
    }


    // --- Métodos Removidos ---

    // <<< REMOVIDO: describeCurrentRoom() >>>
    // A responsabilidade de chamar a RoomView agora é do GameController.

    // <<< REMOVIDO: listItemsInInventory() >>>
    // A responsabilidade de chamar a InventoryView agora é do GameController.


    // --- Métodos de Ação (Mutação de Estado) ---

    /**
     * <<< MÉTODO PRINCIPAL (Forma 1) >>>
     * Move o jogador, executa combate E verifica a vitória.
     */
    public String moveToRoomDirection(String direction) {
        Room currentRoom = world.getCurrentRoom();
        String nextRoomName = currentRoom.getAdjacentRooms().get(direction); // (Assumindo que a direção já está em minúsculo, ex: "north")

        if (nextRoomName != null) {
            Room nextRoom = world.getRooms().get(nextRoomName);
            if (nextRoom != null) {
                // 1. Mover o jogador
                world.setCurrentRoom(nextRoom);   // Atualiza a sala atual do mundo

                String responsePrefix = ""; // Mensagem de combate, se houver

                // 2. LÓGICA DE COMBATE (Forma 1)
                Monster monster = nextRoom.getMonster();
                if (monster != null && !monster.isDefeated()) {
                    String defeatItem = monster.getDefeatItem();

                    if (inventoryController.hasItemByName(defeatItem)) {
                        // 2a. Vitória no Combate
                        monster.setDefeated(true); // Marque o monstro como morto
                        // (Opcional: consumir o item)
                        // inventoryController.removeItem(defeatItem);
                        responsePrefix = monster.getDefeatMessage() + "\n\n";
                        Item item = inventoryController.getItemByName(defeatItem);
                        inventoryController.drop(item);


                    } else {
                        // 2b. Derrota (GAME OVER)
                        world.setGameOver(true); // AVISA QUE O JOGO ACABOU
                        return "GAME OVER: " + monster.getDescription() + "\n" +
                                "Você não tem o item [" + defeatItem + "] para se defender!";
                    }
                }

                // 3. LÓGICA DE VITÓRIA
                // (Só chega aqui se sobreviveu ao passo 2)
                if (nextRoom == world.getEndingRoom()) {
                    world.setGameOver(true); // AVISA QUE O JOGO ACABOU
                    return responsePrefix + "VOCÊ VENCEU! Você encontrou a saída!\n\n" +
                            nextRoom.getDescription(); // Retorna a descrição crua
                }

                // 4. JOGO CONTINUA (Moveu, não morreu, não venceu)
                // Retorna a msg de combate (se houver) + descrição da sala
                return responsePrefix + nextRoom.getDescription();

            } else {
                return "A sala para onde você está tentando ir não existe.";
            }
        } else {
            return "Não há saída nessa direção.";
        }
    }

    // O seu 'takeItem' e 'dropItem' estão bons.
    // Não precisam de mudança.
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

        // 2. Opcional: exigir que o jogador tenha o item
        if (!inventoryController.hasItemByName(itemName)) {
            return "Você não tem " + itemName + ".";
        }

        // 3. Aplica o efeito
        applyUseAction(current, action);

        // (Opcional: consumir o item)
         Item item = inventoryController.getItemByName(itemName);
         inventoryController.drop(item); // (use 'drop' ou 'remove')

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
                room.getAdjacentRooms().put(
                        action.getDirection(),
                        action.getToRoom()
                );
            }
            case "fechar_direcao" -> {
                room.getAdjacentRooms().remove(action.getDirection());
            }
            case "aparecer_item_na_sala" -> {
                // cria item novo na sala
                room.getItems().add(
                        new Item(action.getTargetItem(), "Um item apareceu aqui.")
                );
            }
            case "sumir_item_da_sala" -> {
                removeItemFromRoomByName(room, action.getTargetItem());
            }
            case "derrotar_monstro" -> {
                Monster m = room.getMonster();
                if (m != null && !m.isDefeated()) {
                    m.setDefeated(true);
                }
            }
            default -> {
                // tipo desconhecido → não faz nada

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



















/*
    private World world;
    private Inventory inventory;
    private InventoryController inventoryController;
    private RoomController roomController;
    private RoomView roomView;
    private InventoryView inventoryView;


    //inicialização do mundo. O WorldData vem do GameController que irá carregar o mundo do arquivo
    public void initWorld(World worldData) {
        this.world = worldData;
        this.inventory = new Inventory(worldData.getInventaryMaxItems());
        this.inventoryController = new InventoryController(this.inventory);
        this.roomController = new RoomController();
        this.roomView = new RoomView();
        this.inventoryView = new InventoryView();

    }
    // Método para o GameController/GameEngine saber se o jogo acabou
    public boolean isGameOver() {
        return world.isGameOver(); // (Adicione 'isGameOver' ao seu 'World.java')
    }
    public InventoryController getInventoryController() {
        return inventoryController;
    }

    public World getWorld() {
        return this.world;
    }

    //Lendo o estado de jogo

    public Room getCurrentRoom() {
        return world.getCurrentRoom();
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }

    public String describeCurrentRoom() {

        return roomView.renderRoom(world.getCurrentRoom());
        //return roomController.lookAround(world.getCurrentRoom());
    }

    public String listItemsInInventory() {
        return inventoryView.render(inventoryController);
    }

    //Movimentando entre salas
    public String moveToRoomDirection(String direction) {
        Room currentRoom = world.getCurrentRoom();
        String nextRoomName = currentRoom.getAdjacentRooms().get(direction);
        if (nextRoomName != null) {
            Room nextRoom = world.getRooms().get(nextRoomName);
            if (nextRoom != null) {
                world.setCurrentRoom(nextRoom);   //atualiza a sala atual do mundo
                return roomView.renderRoom(nextRoom);
                        //roomController.lookAround(nextRoom);  //envia o look around da nova sala tb

            } else {
                return "A sala para onde você está tentando ir não existe.";
            }
        } else {
            return "Não há saída nessa direção.";
        }
    }

    //Remove da sala e  adiciona no inventário

    public String takeItem(String itemName){
        Room current = world.getCurrentRoom();

        Item removedItem = roomController.removeItemFromRoom(current, itemName);

        if(removedItem != null){
            String result = inventoryController.pickUp(removedItem);
            if (result.startsWith("Inventário cheio")) {
                // Se não conseguiu pegar, devolve o item para a sala
                roomController.addItemToRoom(current, removedItem);
                return "Não foi possível pegar o item: " + itemName;
            } else {
                return result;
            }
        }else{
            return "Item não encontrado na sala: " + itemName;
        }

    }

    // Remove do inventário e adiciona na sala atual

    public String dropItem(String itemName){
        Room current = world.getCurrentRoom();
        //verifica se o inventário tem o item
        if (inventoryController.hasItemByName(itemName)){
            Item itemToDrop = inventoryController.getItemByName(itemName);
            if(itemToDrop== null){
                return "Item não encontrado no inventário: " + itemName;
            }
            String result = inventoryController.drop(itemToDrop);
            if(result.startsWith("Você largou")){
                //adiciona na sala
                roomController.addItemToRoom(current, itemToDrop);
                return result;
            } else {
                //readiciona no inventário se não conseguiu largar
                inventoryController.pickUp(itemToDrop);
                return "Não foi possível soltar o item: " + itemName + " aqui.";
            }

        } else {
            return "Item não encontrado no inventário: " + itemName;
        }
    }
    //ENTENDER
    public String useItem(String itemName) {
        Room current = world.getCurrentRoom();

        Monster monster = current.getMonster();
        if (monster != null && !monster.isDefeated()) {
            // precisa ter o item certo
            if (monster.getDefeatItem() != null &&
                    monster.getDefeatItem().equalsIgnoreCase(itemName)) {

                // opcional: remover do inventário
                if (inventoryController.hasItemByName(itemName)) {
                    Item item = inventoryController.getItemByName(itemName);
                    inventoryController.drop(item);
                }

                monster.setDefeated(true);
                return monster.getDefeatMessage();
            } else {
                return "Isso não afeta " + monster.getName() + ".";
            }
        }



        // 2. se não tem monstro (ou já está derrotado), tenta achar uma useAction da sala
        UseAction action = findUseActionForItem(current, itemName);
        if (action == null) {
            return "Nada acontece.";
        }

        // opcional: exigir que o jogador tenha o item
        if (!inventoryController.hasItemByName(itemName)) {
            return "Você não tem " + itemName + ".";
        }

        // aplica o efeito
        applyUseAction(current, action);

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
                room.getAdjacentRooms().put(
                        action.getDirection(),   // já deve estar em inglês
                        action.getToRoom()
                );
            }
            case "fechar_direcao" -> {
                room.getAdjacentRooms().remove(action.getDirection());
            }
            case "aparecer_item_na_sala" -> {
                // cria item novo na sala
                room.getItems().add(
                        new Item(action.getTargetItem(), "Um item apareceu aqui.")
                );
            }
            case "sumir_item_da_sala" -> {
                removeItemFromRoomByName(room, action.getTargetItem());
            }
            case "derrotar_monstro" -> {
                Monster m = room.getMonster();
                if (m != null && !m.isDefeated()) {
                    m.setDefeated(true);
                }
            }
            default -> {
                // tipo desconhecido → não faz nada
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

*/



}





