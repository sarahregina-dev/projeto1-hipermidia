package org.example.view;

import java.util.ArrayList;
import java.util.List;

public class UiFormatter {

    // Quebra um parágrafo longo em várias linhas com largura máxima "width"
    public static List<String> wrapText(String text, int width) {
        List<String> lines = new ArrayList<>();
        if (text == null || text.isBlank()) {
            return lines;
        }

        String[] words = text.split("\\s+");
        StringBuilder currentLine = new StringBuilder();

        for (String w : words) {
            if (currentLine.length() == 0) {
                currentLine.append(w);
            } else if (currentLine.length() + 1 + w.length() <= width) {
                currentLine.append(" ").append(w);
            } else {
                lines.add(currentLine.toString());
                currentLine.setLength(0);
                currentLine.append(w);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    // Recebe várias linhas já prontas e coloca em uma moldura
    public static String boxify(List<String> contentLines) {
        // acha o comprimento máximo de linha
        int maxLen = 0;
        for (String line : contentLines) {
            if (line.length() > maxLen) {
                maxLen = line.length();
            }
        }

        String horizontal = "+" + "-".repeat(maxLen + 2) + "+\n";

        StringBuilder sb = new StringBuilder();
        sb.append(horizontal);
        for (String line : contentLines) {
            int pad = maxLen - line.length();
            sb.append("| ")
                    .append(line)
                    .append(" ".repeat(pad))
                    .append(" |\n");
        }
        sb.append(horizontal);

        return sb.toString();
    }

}
