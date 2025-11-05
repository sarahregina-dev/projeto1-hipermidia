package org.example.app;

import org.example.core.GameEngine;

/*
* Trabalho 1 de Implementação da Disciplina Hipermídia.
*
* Alunas:
* Amanda Almeida Cardoso
* Sarah Regina Bezerra Sousa
*
* Professor: Carlos Salles
*
* Novembro 2025
*
* */


public class Main {

    //inicializacao
    public static void main(String[] args) {
        GameBuilder gb = new GameBuilder();
        GameEngine engine = gb.createWorldEngine();
        engine.run();
    }
}
