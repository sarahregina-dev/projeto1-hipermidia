package org.example.view;

import org.example.view.viewmodel.RoomViewModel;

import java.util.*;

public class RoomView {

    public String renderRoom(RoomViewModel viewModel) {
        List<String> finalLines = new ArrayList<>();

        finalLines.add(viewModel.roomName);
        finalLines.add("");

        finalLines.add("");

        finalLines.addAll(UiFormatter.wrapText(viewModel.roomDescription,60));

        if(!viewModel.items.isEmpty()){
            finalLines.add("");
            finalLines.add("Você vê:");

            for (RoomViewModel.ItemData item : viewModel.items) {
                String raw = "- " + item.name + ": " + item.description;
                finalLines.addAll(UiFormatter.wrapText(raw, 60));
            }
        }

        if (viewModel.monsterDescription!=null){  //essa parte está meio inutil atualmente, pois a logica de combate acontece antes daqui.  O if nunca é verdadeiro
            finalLines.add("");
            finalLines.add("Perigo à vista: ");
            finalLines.addAll(UiFormatter.wrapText(viewModel.monsterDescription,60));

        }
        finalLines.add("");
        finalLines.add("Saídas:");
        finalLines.addAll(UiFormatter.wrapText(viewModel.exitsLine, 60));

        return UiFormatter.boxify(finalLines);

    }
}
