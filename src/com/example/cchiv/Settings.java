package com.example.cchiv;

import javafx.scene.paint.Color;

import java.util.HashMap;

public class Settings {

    public static final String GAME_TITLE = "2048";

    public static final int DIMENSIONS_WINDOW_X = 550;
    public static final int DIMENSIONS_WINDOW_Y = 800;

    public static final int DIMENSIONS_X = 500;
    public static final int DIMENSIONS_Y = 500;

    public static HashMap<Integer, Color> colors;

    static {
        colors = new HashMap<>();
        colors.put(2, Color.rgb(238, 228, 218));
        colors.put(4, Color.rgb(237, 224, 200));
        colors.put(8, Color.rgb(242, 177, 121));
        colors.put(16, Color.rgb(245, 149, 99));
        colors.put(32, Color.rgb(246, 124, 95));
        colors.put(64, Color.rgb(246, 94, 59));
        colors.put(128, Color.rgb(237, 207, 114));
        colors.put(256, Color.rgb(237, 204, 97));
        colors.put(512, Color.rgb(237, 200, 80));
        colors.put(1024, Color.rgb(237, 197, 63));
        colors.put(2048, Color.rgb(237, 194, 46));
        colors.put(4096, Color.rgb(220, 80, 220));
        colors.put(8192, Color.rgb(160, 50, 160));
        colors.put(16384, Color.rgb(110, 20, 50));
        colors.put(-1, Color.rgb(245, 250, 250));
    }
}