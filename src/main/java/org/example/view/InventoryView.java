package org.example.view;

import org.example.model.Item;
import org.example.controller.world.InventoryController;
import java.util.ArrayList;
import java.util.List;

public class InventoryView {

    public String render(InventoryViewModel viewModel) {
        List<String> lines = new ArrayList<>();

        lines.add("INVENTÁRIO");
        lines.add("");

        lines.add("Espaço: " + viewModel.currentSize + " / " + viewModel.maxItems);
        lines.add("");

        List<InventoryViewModel.ItemData> items = viewModel.items;

        if (items.isEmpty()) {
            lines.add("Seu inventário está vazio.");
        } else {
            lines.add("Você está carregando:");

            for (InventoryViewModel.ItemData item : items) {
                String raw = "- " + item.name + ": " + item.description;
                lines.addAll(UiFormatter.wrapText(raw, 60));
            }
        }
        return UiFormatter.boxify(lines);

    }

}
