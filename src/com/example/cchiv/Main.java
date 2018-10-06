package com.example.cchiv;

import com.example.cchiv.interfaces.OnUpdateScore;
import com.example.cchiv.tools.Board;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;

import static javafx.scene.input.KeyCode.ESCAPE;

public class Main extends Application {

    private AnimationTimer animationTimer = null;

    private ObservableList<Node> nodes;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Settings.DIMENSIONS_WINDOW_X, Settings.DIMENSIONS_WINDOW_Y);

        nodes = root.getChildren();

        File file = new File("./res/logo.png");
        if(file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);

            ImageView imageView = new ImageView();
            Image image = new Image(fileInputStream, 50, 50, true, true);
            imageView.setX(25);
            imageView.setY(100);
            imageView.setImage(image);

            primaryStage.getIcons().add(image);

            nodes.add(imageView);
        }

        Text scoreText = addContainerTextNode(32+48+156, 32, 125, 115, Pos.CENTER, 1000, "SCORE");
        addContainerTextNode(32+48+156+32+125, 32, 125, 115, Pos.CENTER,0, "BEST");

        Color labelColor = Color.rgb(249,180,120);
        addTextNode(25, 163 + 50, 0, TextAlignment.LEFT, Pos.BASELINE_LEFT, labelColor, "RESTART");
        addTextNode(Settings.DIMENSIONS_WINDOW_X - 50, 163 + 50, 0, TextAlignment.LEFT, Pos.BASELINE_RIGHT, labelColor, "UNDO");

        Group group = new Group();
        Rectangle rectangle = new Rectangle(Settings.DIMENSIONS_X, Settings.DIMENSIONS_Y);
        rectangle.setFill(Color.LIGHTGRAY);
        group.getChildren().add(rectangle);

        group.setLayoutX(25);
        group.minHeight(Settings.DIMENSIONS_X);
        group.minWidth(Settings.DIMENSIONS_Y);
        group.setLayoutY(Settings.DIMENSIONS_WINDOW_Y - 75 - Settings.DIMENSIONS_Y);
        Board board = new Board(group, score -> scoreText.setText(String.valueOf(score)), Settings.DIMENSIONS_X, Settings.DIMENSIONS_Y);

        nodes.add(group);

        scene.setOnKeyPressed((event -> {
            if(event.getCode() == ESCAPE) {
                if(animationTimer != null) {
                    animationTimer.stop();
                    primaryStage.close();
                }
            }

            board.onBoardKeyCallback(event.getCode());
        }));

        LongProperty lastUpdateTime = new SimpleLongProperty(0);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                long elapsedTime = timestamp - lastUpdateTime.get();
                if(elapsedTime > 60000) {
                    board.render();
                    lastUpdateTime.set(timestamp);
                }
            }
        };
        animationTimer.start();

        primaryStage.setTitle(Settings.GAME_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Text addTextNode(int posX, int posY, int dimenW, TextAlignment textAlignment, Pos position, Color color, String label) {
        Text textScoreCurrent = new Text(posX, posY, label);
        textScoreCurrent.setFill(color);
        textScoreCurrent.setWrappingWidth(dimenW);
        textScoreCurrent.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        textScoreCurrent.setTextAlignment(textAlignment);

        nodes.add(textScoreCurrent);

        return textScoreCurrent;
    }

    private Text addContainerTextNode(int posX, int posY, int dimenW, int dimenH, Pos position, int value, String label) {
        Color color = Color.rgb(187, 173, 160);
        Rectangle rectangle = new Rectangle(dimenW, dimenH, color);
        rectangle.setX(posX);
        rectangle.setY(posY);
        rectangle.setArcHeight(16);
        rectangle.setArcWidth(16);

        nodes.add(rectangle);

        float partitionH = dimenH / 2.0f;

        Color labelColor = Color.rgb(219,149,68);

        addTextNode(posX, (int) (posY + partitionH) - 8,
                dimenW,
                TextAlignment.CENTER, position,
                labelColor, label);

        return addTextNode(posX, (int) ((posY + partitionH) * 1.5f) - 8,
                dimenW,
                TextAlignment.CENTER, position, Color.WHITE, String.valueOf(value));
    }
}