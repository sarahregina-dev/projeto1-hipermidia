package org.example.view;

import org.example.model.Item;
import org.example.controller.world.InventoryController;
import java.util.ArrayList;
import java.util.List;

public class InventoryView {

    public String render(InventoryController controller) {
        List<String> lines = new ArrayList<>();

        lines.add("INVENTÁRIO");
        lines.add("");

        int current = controller.getCurrentSize();
        int max = controller.getMaxItems();
        lines.add("Espaço: " + current + " / " + max);
        lines.add("");

        List<Item> items = controller.getItems();

        if (items.isEmpty()) {
            lines.add("Seu inventário está vazio.");
        } else {
            lines.add("Você está carregando:");
            for (Item item : items) {
                String raw = "- " + item.getItemName() + ": " + item.getDescription();
                lines.addAll(UiFormatter.wrapText(raw, 60));
            }
        }

        return UiFormatter.boxify(lines);
    }
}
