package org.example.core;

import org.example.controller.GameController;
import org.example.controller.world.WorldController;
import org.example.model.Monster;
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

        // <<< MUDANÇA: O LOOP AGORA INTERCEPTA OS SINAIS >>>
        while (true) {
            // Pega o input da View
            String input = gameView.getInput();

            String response = gameController.handleCommand(input);

            // --- Verifique os SINAIS ESPECIAIS ---

            // 1. Verifique se o jogador quer sair do jogo
            if (response.equals("__EXIT_GAME__")) {
                gameView.showGoodbye();
                break;
            }

            // 2. Verifique se o sinal de DERROTA foi recebido
            else if (response.equals("__GAME_OVER_DEFEAT__")) {
                Monster monster = gameController.getDefeatingMonster();


                gameView.showGameOver(monster);
                break; // O jogo acabou (gameController.isGameOver() já é true)
            }

            // 3. Verifique se o sinal de VITÓRIA foi recebido
            else if (response.endsWith("__GAME_WON__")) {
                // Pode haver um prefixo (ex: msg de combate)
                String prefix = response.replace("__GAME_WON__", "");
                if (!prefix.trim().isEmpty()) {
                    gameView.showMessage(prefix);
                }
                gameView.showGameWon();
                break; // O jogo acabou (gameController.isGameOver() é true)
            }

            // mensagem normal
            else {
                gameView.showMessage(response);
            }


        }
    }
}