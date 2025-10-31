package org.example.app;



import org.example.controller.GameController;
import org.example.controller.world.WorldController;
import org.example.core.GameEngine;
import org.example.model.World;
import org.example.util.JSONLoader;
import org.example.view.GameView;

import java.util.*;

public class GameBuilder {

//monta o jogo

    public GameEngine createWorldEngine() {

        Scanner scanner = new Scanner(System.in);

        World worldData = null;

        try {
            worldData = JSONLoader.loadFromFile("src/main/resources/world.json");

        } catch (Exception e) {
            System.err.println("Erro fatal: Não foi possível carregar o arquivo do mundo.");
            e.printStackTrace();
            System.exit(1);
        }

        if (worldData == null) {
            System.err.println("Não foi possível continuar: dados do mundo não carregados.");
            System.exit(1);
        }

        WorldController worldController = new WorldController(worldData);

        GameController gameController = new GameController(worldController);
        GameView gameView = new GameView(scanner);
        return new GameEngine(gameController, gameView);


    }


}
