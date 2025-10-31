package org.example.core;

import org.example.controller.GameController;
import org.example.controller.world.WorldController;
import org.example.view.GameView;

public class GameEngine {

    private final WorldController worldController;
    private final GameController gameController;
    private final GameView gameView;


    public GameEngine(WorldController worldController, GameController gameController, GameView gameView) {
        this.worldController = worldController;
        this.gameController = gameController;
        this.gameView = gameView;
    }

    public void run() {
        //Nome do jogo e artes
        gameView.showTitleAndArt();
        gameView.showIntro();

        //Msg inicial
        String initialDescription = gameController.handleCommand("olhar");
        gameView.showMessage(initialDescription);
        while (true) {
            // Pega o input da View
            String input = gameView.getInput();

            String response = gameController.handleCommand(input);

            // 1. SEMPRE mostre a resposta.
            //    (A resposta pode ser "Você andou para o norte",
            //    "GAME OVER", ou "VOCÊ VENCEU!")
            gameView.showMessage(response);

            // 2. Verifique se o comando foi "sair"
            if (response.equals("__EXIT_GAME__")) {
                gameView.showGoodbye();
                break;
            }

            // 3. Verifique se o jogo terminou (vitória OU derrota)
            //    (A mensagem de vitória/derrota já foi impressa no passo 1)
            if (gameController.isGameOver()) {
                break;
            }

        }
    }
}