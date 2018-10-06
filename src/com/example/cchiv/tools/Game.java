package com.example.cchiv.tools;

import com.example.cchiv.Settings;
import com.example.cchiv.interfaces.OnUpdateScore;
import javafx.scene.canvas.GraphicsContext;

public class Game {

    private Board board;

    private int score = 0;

    public Game(GraphicsContext graphicsContext, int score) {
//        board = new Board(null, Settings.DIMENSIONS_X, Settings.DIMENSIONS_Y);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int points) {
        score += points;
    }
}
