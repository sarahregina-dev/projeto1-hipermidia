package org.example.core;

import org.example.controller.GameController;
import org.example.controller.world.WorldController;
import org.example.model.Monster;
import org.example.view.GameView;


// Controla o Loop principal do jogo

public class GameEngine {

    private final GameController gameController;
    private final GameView gameView;


    public GameEngine(GameController gameController, GameView gameView) {
        this.gameController = gameController;
        this.gameView = gameView;
    }

    public void run() {
        // Nome do jogo e artes
        gameView.showTitleAndArt();
        gameView.showIntro();

        while (true) {
            String input = gameView.getInput();

            if (input.trim().equalsIgnoreCase("entrar")) {
                break; // Sai do pré-loop e começa o jogo
            } else if (input.trim().equalsIgnoreCase("sair")) {
                gameView.showGoodbye(); // Permite que o jogador desista
                return;
            } else {
                gameView.showMessage("Digite 'entrar' para começar ou 'sair' para desistir.");
            }
        }

        // Msg inicial
        String initialDescription = gameController.handleCommand("olhar");
        gameView.showMessage(initialDescription);

        while (true) {
            String input = gameView.getInput();

            String response = gameController.handleCommand(input);

            if (response.equals("__EXIT_GAME__")) {
                gameView.showGoodbye();
                break;
            }

            //   Se o sinal de derrota foi recebido
            else if (response.equals("__GAME_OVER_DEFEAT__")) {
                Monster monster = gameController.getDefeatingMonster();
                String desc = null;
                String name = null;
                if (monster != null) {
                    desc = monster.getDescription();
                    name = monster.getName();
                }


                gameView.showGameOver(name,desc);
                break; // O jogo acabou (gameController.isGameOver() já é true)
            }

            // Se o sinal de vitoria foi recebido
            else if (response.endsWith("__GAME_WON__")) {
                //A mensagem pode ter um prefixo antes do sinal, por exemplo, a mensagem de derrota/vitoria do combate
                //Remove o sinal e exibe o prefixo, se houver
                String prefix = response.replace("__GAME_WON__", "");
                if (!prefix.trim().isEmpty()) {
                    gameView.showMessage(prefix);
                }
                gameView.showGameWon();
                break; // O jogo acabou (gameController.isGameOver() é true)
            }

            else {
                gameView.showMessage(response);
            }


        }
    }
}