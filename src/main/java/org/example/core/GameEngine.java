package org.example.core;

import org.example.controller.GameController;
import org.example.controller.world.WorldController;
import org.example.view.GameView; // Importa a nova view

public class GameEngine {

    private final WorldController worldController;
    private final GameController gameController;
    private final GameView gameView; // <- Recebe GameView, não Scanner

    // Construtor atualizado
    public GameEngine(WorldController worldController, GameController gameController, GameView gameView) {
        this.worldController = worldController;
        this.gameController = gameController;
        this.gameView = gameView;
    }

    public void run() {
        // Delega a apresentação para a View
        gameView.showTitleAndArt();
        gameView.showIntro();

        // Mostra o estado inicial (usando o método genérico da View)
        gameView.showMessage(worldController.describeCurrentRoom());

        while (true) {
            // Pega o input da View
            String input = gameView.getInput();

            String response = gameController.handleCommand(input);

            // Dica: Crie uma constante para "__EXIT_GAME__" no GameController
            if (response.equals("__EXIT_GAME__")) {
                gameView.showGoodbye(); // Delega para a View
                break;
            } else if (gameController.isGameOver()) {
                gameView.showMessage(worldController.describeCurrentRoom()); // Estado final
                gameView.showGameWon(); // Delega para a View
                break;
            } else {
                gameView.showMessage(response); // Mostra a resposta
            }
        }
    }
}