package com.example.cchiv.tools;

import com.example.cchiv.Settings;
import javafx.scene.paint.Color;

public class Tile extends Plate {

    private static final int OFFSET = 8;

    private static final int DIMENSION_BOARD = 4;

    private int value;
    private Vector position;

    public Tile(int value, Vector position, float partitionX, float partitionY) {
        super(position.getX()*(partitionX + OFFSET) + OFFSET, position.getY()*(partitionY + OFFSET) + OFFSET, partitionX, partitionY);

        this.value = value;
        this.position = position;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;

        if(value != -1)
            setText(String.valueOf(value));
        else setText(null);
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void render() {
        Color color = Settings.colors.get(value);
        setRectangle(color);
    }

    public static void swap(Tile tile1, Tile tile2) {
        int value = tile1.getValue();
        tile1.setValue(tile2.getValue());
        tile2.setValue(value);
    }
}
