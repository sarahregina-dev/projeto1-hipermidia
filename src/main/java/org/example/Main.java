package org.example;

import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        InputStream is = Main.class.getResourceAsStream("/world.json");
        if (is == null) throw new RuntimeException("world.json não encontrado!");
        String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(text);
        System.out.println(json.toString(2));
    }
}
