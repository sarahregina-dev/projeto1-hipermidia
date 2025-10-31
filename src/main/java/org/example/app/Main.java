package org.example.app;

import org.example.core.GameEngine;

public class Main {

    //inicializacao
    public static void main(String[] args) {
        GameBuilder gb = new GameBuilder();
        GameEngine engine = gb.createWorldEngine();
        engine.run();
    }
}
