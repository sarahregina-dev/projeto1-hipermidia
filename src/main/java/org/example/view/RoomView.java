package org.example.view;

import org.example.controller.world.RoomController;
import org.example.model.Item;
import org.example.model.Monster;
import org.example.model.Room;
import org.example.util.DirectionMapper;

import java.util.*;

public class RoomView {

    public String renderRoom(RoomController roomController, Room room) {
        List<String> finalLines = new ArrayList<>();

        // título: nome da sala em caps
        finalLines.add(roomController.getRoomName(room).toUpperCase(Locale.ROOT));
        finalLines.add("");

        // descrição da sala max 60
        finalLines.addAll(UiFormatter.wrapText(room.getDescription(), 60));

        // itens visíveis
        if (!roomController.getItemsInRoom(room).isEmpty()) {
            finalLines.add("");
            finalLines.add("Você vê:");

            for (Item item : room.getItems()) {
                String raw = "- " + item.getItemName()
                        + ": " + item.getDescription();
                finalLines.addAll(UiFormatter.wrapText(raw, 60));
            }
        }

        // monstro presente
        Monster monster = roomController.getMonster(room);
        if (monster != null && !monster.isDefeated()) {
            finalLines.add("");
            finalLines.add("Perigo aqui:");
            finalLines.addAll(UiFormatter.wrapText(monster.getDescription(), 60));
        }


        // saídas
        finalLines.add("");
        finalLines.add("Saídas:");

        Enumeration<String> dirs = room.getAdjacentRooms().keys();
        List<String> saidasPt = new ArrayList<>();
        while (dirs.hasMoreElements()) {
            String dirInternal = dirs.nextElement(); // "north"
            String dirPlayer = DirectionMapper.toPlayer(dirInternal); // "norte"
            saidasPt.add(dirPlayer);
        }

        String exitsLine = String.join(", ", saidasPt);
        finalLines.addAll(UiFormatter.wrapText(exitsLine, 60));

        // devolve tudo na caixa
        return UiFormatter.boxify(finalLines);
    }
}
