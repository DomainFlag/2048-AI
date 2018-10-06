package com.example.cchiv.tools;

import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class Plate {

    private static final int BORDER_RADIUS = 6;

    private GridPane gridPane;
    private TranslateTransition translateTransition;
    private Text text;
    private Rectangle rectangle;

    private static final int ANIMATION_TIME = 300;

    public Plate(float posX, float posY, float width, float height) {
        text = new Text();
        text.setTextAlignment(TextAlignment.CENTER);
        rectangle = new Rectangle(width, height);
        rectangle.setArcWidth(BORDER_RADIUS);
        rectangle.setArcHeight(BORDER_RADIUS);

        GridPane.setHalignment(text, HPos.CENTER);

        gridPane = new GridPane();
        gridPane.setLayoutX(posX);
        gridPane.setLayoutY(posY);
        gridPane.getChildren().addAll(rectangle, text);


        gridPane.setAlignment(Pos.CENTER);

//        setTranslateTransition(gridPane);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    private void setTranslateTransition(Node node) {
        translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(ANIMATION_TIME));

        translateTransition.setNode(node);
        translateTransition.setByX(300);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();
    }

    public void setText(String value) {
        this.text.setText(value);
    }

    public void setRectangle(Paint color) {
        rectangle.setFill(color);
    }

    public void setRectangle(double positionX, double positionY) {
        rectangle.setX(positionX);
        rectangle.setY(positionY);
    }
}
