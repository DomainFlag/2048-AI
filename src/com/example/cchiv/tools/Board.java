package com.example.cchiv.tools;

import com.example.cchiv.Settings;
import com.example.cchiv.interfaces.OnUpdateScore;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Board {

    private OnUpdateScore onUpdateScore;

    private int dimensionX;
    private int dimensionY;

    private float partitionX;
    private float partitionY;

    private static final int OFFSET = 8;

    private static final int DIMENSION_BOARD = 4;

    private int score = 0;

    private Tile[][] board;

    public Board(Group group, OnUpdateScore onUpdateScore, int dimensionX, int dimensionY) {
        this.onUpdateScore = onUpdateScore;
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;

        board = new Tile[DIMENSION_BOARD][DIMENSION_BOARD];
        partition();

        for(int g = 0; g < DIMENSION_BOARD; g++)
            for(int h = 0; h < DIMENSION_BOARD; h++) {
                Vector position = new Vector(g, h);
                board[g][h] = new Tile(-1, position, partitionX, partitionY);
                group.getChildren().add(board[g][h].getGridPane());
            }

        generateTile();
    }

    public void onBoardKeyCallback(KeyCode keyCode) {
        switch(keyCode) {
            case UP : {
                animateTiles(KeyCode.UP);
                generateTile();
                break;
            }
            case LEFT: {
                animateTiles(KeyCode.LEFT);
                generateTile();
                break;
            }
            case RIGHT: {
                animateTiles(KeyCode.RIGHT);
                generateTile();
                break;
            }
            case DOWN: {
                animateTiles(KeyCode.DOWN);
                generateTile();
                break;
            }
        }
    }

    private void manageTilesLeft(int h, int g) {
        if(g > 0) {
            if(board[g-1][h].getValue() == -1) {
                Tile.swap(board[g][h], board[g-1][h]);
            } else if(board[g][h].getValue() != -1) {
                if(board[g][h].getValue() == board[g-1][h].getValue()) {
                    onUpdateTileScore(board[g][h], board[g-1][h]);
                }
            }

            manageTilesLeft(h, g-1);
        }
    }

    private void manageTilesRight(int h, int g) {
        if(g < DIMENSION_BOARD - 1) {
            if(board[g+1][h].getValue() == -1) {
                Tile.swap(board[g][h], board[g+1][h]);
            } else if(board[g][h].getValue() != -1) {
                if(board[g][h].getValue() == board[g+1][h].getValue()) {
                    onUpdateTileScore(board[g][h], board[g+1][h]);
                }
            }

            manageTilesRight(h, g+1);
        }
    }

    private void manageTilesUp(int h, int g) {
        if(h > 0) {
            if(board[g][h-1].getValue() == -1) {
                Tile.swap(board[g][h], board[g][h-1]);
            } else if(board[g][h].getValue() != -1) {
                if(board[g][h].getValue() == board[g][h-1].getValue()) {
                    onUpdateTileScore(board[g][h], board[g][h-1]);
                }
            }

            manageTilesUp(h-1, g);
        }
    }

    private void manageTilesDown(int h, int g) {
        if(h < DIMENSION_BOARD - 1) {
            if(board[g][h+1].getValue() == -1) {
                Tile.swap(board[g][h], board[g][h+1]);
            } else if(board[g][h].getValue() != -1) {
                if(board[g][h].getValue() == board[g][h+1].getValue()) {
                    onUpdateTileScore(board[g][h], board[g][h+1]);
                }
            }

            manageTilesDown(h+1, g);
        }
    }

    private void onUpdateTileScore(Tile oldTile, Tile currentTile) {
        oldTile.setValue(-1);
        int scoreTile = currentTile.getValue() * 2;

        currentTile.setValue(scoreTile);

        score += scoreTile;
        onUpdateScore.onUpdateScore(score);
    }

    public void animateTiles(KeyCode direction) {
        switch(direction) {
            case LEFT : {
                for(int h = 0; h < DIMENSION_BOARD; h++) {
                    for(int g = 0; g < DIMENSION_BOARD; g++) {
                        manageTilesLeft(h, g);
                    }
                }

                break;
            }
            case RIGHT : {
                for(int h = 0; h < DIMENSION_BOARD; h++) {
                    for(int g = DIMENSION_BOARD - 1; g >= 0; g--) {
                        manageTilesRight(h, g);
                    }
                }

                break;
            }
            case UP : {
                for(int g = 0; g < DIMENSION_BOARD; g++) {
                    for(int h = DIMENSION_BOARD - 1; h >= 0; h--) {
                        manageTilesUp(h, g);
                    }
                }

                break;
            }
            case DOWN : {
                for(int g = 0; g < DIMENSION_BOARD; g++) {
                    for(int h = DIMENSION_BOARD - 1; h >= 0; h--) {
                        manageTilesDown(h, g);
                    }
                }

                break;
            }
        }
    }

    public void generateTile() {
        ArrayList<Tile> tiles = new ArrayList<>();

        for(int g = 0; g < DIMENSION_BOARD; g++) {
            for(int h = 0; h < DIMENSION_BOARD; h++) {
                if(board[g][h].getValue() == -1) {
                    tiles.add(board[g][h]);
                }
            }
        }

        if(tiles.size() > 0) {
            Random random = new Random();
            int pos = random.nextInt(tiles.size());

            tiles.get(pos).setValue(2);

            score += 2;
        }
    }

    private void partition() {
        partitionX = (float) (dimensionX  - (DIMENSION_BOARD + 1) * OFFSET) / DIMENSION_BOARD;
        partitionY = (float) (dimensionY  - (DIMENSION_BOARD + 1) * OFFSET) / DIMENSION_BOARD;
    }

    public void render() {
        for(int g = 0; g < DIMENSION_BOARD; g++)
            for(int h = 0; h < DIMENSION_BOARD; h++)
                board[g][h].render();
    }
}
