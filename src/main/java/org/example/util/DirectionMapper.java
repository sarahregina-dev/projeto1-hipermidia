package org.example.util;

public class DirectionMapper {

    // jogador -> direção interna (JSON / world)
    public static String toInternal(String userDir) {
        if (userDir == null) return null;
        String d = userDir.toLowerCase().trim();

        switch (d) {
            case "norte": return "north";
            case "sul":   return "south";
            case "leste": return "east";
            case "oeste": return "west";
            case "subir":    return "up";
            case "descer":  return "down";

            case "north":
            case "south":
            case "east":
            case "west":
            case "up":
            case "down":
                return d;

            default:
                return null; // inválida
        }
    }

    // direção interna -> texto pro jogador
    public static String toPlayer(String internalDir) {
        if (internalDir == null) return null;
        String d = internalDir.toLowerCase().trim();

        switch (d) {
            case "north": return "norte";
            case "south": return "sul";
            case "east":  return "leste";
            case "west":  return "oeste";
            case "up":    return "subir";
            case "down":  return "descer";
            default:      return internalDir; // fallback de segurança
        }
    }
}
