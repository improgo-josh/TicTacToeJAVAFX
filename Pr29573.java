/**
 * Josh Improgo
 * PROJECT 2: JAVAFX GUI Tic Tac Toe
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Pr29573 extends Application implements EventHandler<ActionEvent> {
    final String FONTFAMILY = "monospace";
    final String FONTSIZE = "12";
    final String T_FONTSIZE = "130";

    final String GAME_IS_TIED = "Game is tied!";
    final int MIN = 0, MAX = 2;

    int turnCount = 0;
    int player = 1;
    int playerOneScore = 0, opponentScore = 0, tieScore = 0;
    boolean againstPlayer = false;
    Scene primaryScene, initialScene;

    final int SCORE_LABEL_ROW = 5;
    final String playerOneStr = "Player One (X): ";
    final String playerTwoStr = "Player Two (O): ";
    final String computerStr = "Computer Score (O): ";
    final String tieStr = "Tie: ";
    //padding
    final int H_PADDING = 20;
    final int V_PADDING = 20;

    //set width and height for Pr29573 buttons
    final int T_MAX_WIDTH = 300, T_MAX_HEIGHT = 300;

    //set width and height for tictactoe interactive buttons
    final int I_MAX_WIDTH = 150, I_MAX_HEIGHT = 50;
    //set width and height of initial buttons
    final int INITIAL_MAX_WIDTH = 100, INITIAL_MAX_HEIGHT = 20;

    //set width and height for scene
    final int SCENE_WIDTH = T_MAX_WIDTH*3, SCENE_HEIGHT = T_MAX_HEIGHT*3;

    final int INITIAL_SCENE_WIDTH = 300, INITIAL_SCENE_HEIGHT = 100;


    //Pr29573 buttons
    Button[][] btn = new Button[3][3];

    Button btn_back, btn_restart, btn_playAgain;

    Label lbl_opponent, lbl_playerOne, lbl_tie, lbl_turn;
    Label lbl_opponentScore, lbl_playerOneScore, lbl_tieScore;

    //initial scene

    //Back, choose versus
    Button btn_againstPlayer, btn_againstComputer;

    //labels
    Label lbl_against;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        int player = 1;

        /**
         * Primary Scene
         */

        GridPane primaryRoot = new GridPane();
        primaryRoot.setAlignment(Pos.CENTER);

        //create buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btn[i][j] = new Button("");
                btn[i][j].setOnAction(this);
                btn[i][j].setPrefSize(T_MAX_WIDTH, T_MAX_HEIGHT);
                btn[i][j].setStyle("-fx-font: " + T_FONTSIZE + " " + FONTFAMILY + ";");
                btn[i][j].setTextFill(Color.GRAY);
                primaryRoot.add(btn[i][j], i, j+2);
            }
        }

        //initialize all nodes
        lbl_opponent = new Label(computerStr);
        lbl_opponentScore = new Label("0");
        lbl_playerOne = new Label(playerOneStr);
        lbl_playerOneScore = new Label("0");
        lbl_tie = new Label(tieStr);
        lbl_tieScore = new Label("0");
        lbl_turn = new Label("");
        setTurnString();

        btn_back = new Button("Back");
        btn_restart = new Button("Restart");
        btn_playAgain = new Button("Play Again?");

        //set minimum sizes
        btn_back.setMinSize(I_MAX_WIDTH, I_MAX_HEIGHT);
        btn_restart.setMinSize(I_MAX_WIDTH, I_MAX_HEIGHT);
        btn_playAgain.setMinSize(I_MAX_WIDTH, I_MAX_HEIGHT);

        //set styles to nodes
        lbl_opponent.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");
        lbl_opponentScore.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");
        lbl_playerOne.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");
        lbl_playerOneScore.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");
        lbl_tie.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");
        lbl_tieScore.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");
        lbl_turn.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");

        btn_back.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");
        btn_restart.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");
        btn_playAgain.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");

        //add all nodes into root
        primaryRoot.add(lbl_playerOne, 0, SCORE_LABEL_ROW);
        primaryRoot.add(lbl_playerOneScore, 0, SCORE_LABEL_ROW+1);
        primaryRoot.add(lbl_tie, 1, SCORE_LABEL_ROW);
        primaryRoot.add(lbl_tieScore, 1, SCORE_LABEL_ROW+1);
        primaryRoot.add(lbl_opponent, 2, SCORE_LABEL_ROW);
        primaryRoot.add(lbl_opponentScore, 2, SCORE_LABEL_ROW+1);
        primaryRoot.add(lbl_turn, 1, /*SCORE_LABEL_ROW -1*/0);
        primaryRoot.add(btn_restart, 2, 0);
        primaryRoot.add(btn_back, 0, 0);
        primaryRoot.add(btn_playAgain,1,3);

        //set alignments of nodes
        primaryRoot.setHalignment(lbl_playerOne, HPos.CENTER);
        primaryRoot.setHalignment(lbl_playerOneScore, HPos.CENTER);
        primaryRoot.setHalignment(lbl_tie, HPos.CENTER);
        primaryRoot.setHalignment(lbl_tieScore, HPos.CENTER);
        primaryRoot.setHalignment(lbl_opponent, HPos.CENTER);
        primaryRoot.setHalignment(lbl_opponentScore, HPos.CENTER);
        primaryRoot.setHalignment(lbl_turn, HPos.CENTER);
        primaryRoot.setHalignment(btn_restart, HPos.RIGHT);
        primaryRoot.setHalignment(btn_playAgain, HPos.CENTER);

        primaryRoot.setValignment(btn_playAgain, VPos.TOP);

        btn_playAgain.setVisible(false);

        primaryScene = new Scene(primaryRoot, SCENE_WIDTH, SCENE_HEIGHT);

        /**
         * end of primary scene
         */

        /**
         * Initial Scene
         */

        //initialize variables
        Pane initialRoot = new Pane();

        btn_againstPlayer = new Button("Player");
        btn_againstComputer = new Button("Computer");

        lbl_against = new Label("Choose who to play against");

        //set styles
        btn_againstPlayer.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");
        btn_againstComputer.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");

        lbl_against.setStyle("-fx-font: " + FONTSIZE + " " + FONTFAMILY + ";");

        //set alignment
        lbl_against.setAlignment(Pos.TOP_CENTER);

        //set minimum sizes
        btn_againstPlayer.setMinSize(INITIAL_MAX_WIDTH, INITIAL_MAX_HEIGHT);
        btn_againstComputer.setMinSize(btn_againstPlayer.getMinWidth(), btn_againstPlayer.getMinHeight());
        lbl_against.setMinSize(INITIAL_SCENE_WIDTH, INITIAL_SCENE_HEIGHT);

        //get minimum width and height
        double minWidth = btn_againstPlayer.getMinWidth();
        double minHeight = btn_againstPlayer.getMinHeight();

        //set layouts of buttons
        btn_againstPlayer.setLayoutY(INITIAL_SCENE_HEIGHT/2 - minHeight/2);
        btn_againstComputer.setLayoutY(btn_againstPlayer.getLayoutY());

        btn_againstPlayer.setLayoutX(INITIAL_SCENE_WIDTH/2 - (minWidth + H_PADDING/2));
        btn_againstComputer.setLayoutX(btn_againstPlayer.getLayoutX() + minWidth + H_PADDING);

        //add nodes to root
        initialRoot.getChildren().addAll(lbl_against, btn_againstPlayer, btn_againstComputer);

        initialScene = new Scene(initialRoot, INITIAL_SCENE_WIDTH, INITIAL_SCENE_HEIGHT);

        /**
         * End of Initial Scene
         */

        //set scenes
        primaryStage.setScene(initialScene);
        primaryStage.setTitle("Play Against?");
        primaryStage.show();


        //action events
        btn_back.setOnMouseClicked((Event e) -> {
            restart();
            primaryStage.setScene(initialScene);

        });

        btn_restart.setOnMouseClicked((Event e) -> {
            restart();
        });

        btn_playAgain.setOnMouseClicked((Event e) -> {
            playAgain();
        });

        btn_againstPlayer.setOnMouseClicked((Event e)-> {
            primaryStage.setTitle("Pr29573");
            primaryStage.setScene(primaryScene);
            lbl_opponent.setText(playerTwoStr);
            againstPlayer = true;
        });

        btn_againstComputer.setOnMouseClicked((Event e) -> {
            primaryStage.setTitle("Pr29573");
            primaryStage.setScene(primaryScene);
            lbl_opponent.setText(computerStr);
            againstPlayer = false;

            lbl_turn.setText("Your move");
            if (player == 3) {
                computerPlaySmartMove();
            }
        });

    }

    //handle the events
    public void handle(ActionEvent e) {
        if (e.getSource() instanceof Button) {
            int playerMoved = player;
            Button b = ((Button) e.getSource());
            if (b.getText() == "") {
                if (againstPlayer) {
                    if (player == 1) {
                        b.setText("X");
                    } else {
                        b.setText("O");
                    }
                    b.setDisable(true);
                    turnCount++;
                    setNextPlayer();
                    setTurnString();

                    if (gameIsWon()) {
                        disableAllButtons(true);
                        lbl_turn.setText("PLAYER " + playerMoved + " HAS WON!");
                        if (playerMoved == 1) {
                            playerOneScore++;
                            lbl_playerOneScore.setText("" + playerOneScore);
                        } else {
                            opponentScore++;
                            lbl_opponentScore.setText("" + opponentScore);
                        }

                        btn_playAgain.setVisible(true);
                        return;
                    } else if (gameIsTied()) {
                        disableAllButtons(true);
                        lbl_turn.setText(GAME_IS_TIED);
                        tieScore++;
                        lbl_tieScore.setText("" + tieScore);
                        btn_playAgain.setVisible(true);
                        return;
                    }
                } else {
                    b.setText("X");
                    b.setDisable(true);
                    turnCount++;
                    setNextPlayer();
                    if (gameIsWon()) {
                        disableAllButtons(true);
                        lbl_turn.setText("PLAYER ONE HAS WON!");
                        playerOneScore++;
                        lbl_playerOneScore.setText("" + playerOneScore);
                        btn_playAgain.setVisible(true);
                        return;
                    } else if (gameIsTied()) {
                        disableAllButtons(true);
                        lbl_turn.setText(GAME_IS_TIED);
                        tieScore++;
                        lbl_tieScore.setText("" + tieScore);
                        btn_playAgain.setVisible(true);
                        return;
                    }

                    computerPlaySmartMove();

                    if (gameIsWon()) {
                        disableAllButtons(true);
                        lbl_turn.setText("THE COMPUTER HAS WON!");
                        opponentScore++;
                        lbl_opponentScore.setText("" + opponentScore);
                        btn_playAgain.setVisible(true);
                        return;
                    } else if (gameIsTied()) {
                        disableAllButtons(true);
                        lbl_turn.setText(GAME_IS_TIED);
                        tieScore++;
                        lbl_tieScore.setText("" + tieScore);
                        btn_playAgain.setVisible(true);
                        return;
                    }
                }
            }
        }
    }

    //set the next player
    public void setNextPlayer() {
        if (againstPlayer) {
            if (player == 1)
                player = 2;
            else
                player = 1;
        } else {
            if (player == 1)
                player = 3;
            else
                player = 1;
        }
    }

    //set the string of players
    public void setTurnString() {
        lbl_turn.setText("It is Player " + getPlayerStr() + "'s" + " turn");
    }

    //get the player string
    public String getPlayerStr() {
        if (player == 1)
            return "1";
        else
            return "2";
    }

    //check if the game is won
    public boolean gameIsWon() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                String marker = btn[row][column].getText();

                if (marker != "" && ( //not empty
                        ( marker == btn[(row+1)%3][column].getText() && marker == btn[(row+2)%3][column].getText() ) || //vertical
                                ( marker == btn[row][(column+1)%3].getText() && marker == btn[row][(column+2)%3].getText() ) || //horizontal
                                ( marker == btn[(row+1)%3][(column+1)%3].getText() && marker == btn[(row+2)%3][(column+2)%3].getText() && row == column) || //down diagonal
                                ( marker == btn[Math.abs((row-1)%3)][(column+1)%3].getText() && marker == btn[Math.abs((row-2)%3)][(column+2)%3].getText() &&
                                        row + column == 2 && Math.abs((row-1)%3) + (column+1)%3 == 2 && Math.abs((row-2)%3) + (column+2)%3 == 2 ) //up diagonal
                )
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    //check if game is tied
    public boolean gameIsTied() {
        return turnCount == 9;
    }

    //disable all buttons
    public void disableAllButtons(Boolean b) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btn[i][j].setDisable(b);
            }
        }
    }

    //play a random move
    public void computerPlayRandomMove() {
        int randomX, randomY;
        do {
            randomX = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
            randomY = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
        } while (btn[randomX][randomY].getText() != "");

        Button b = btn[randomX][randomY];
        b.setText("O");
        b.setDisable(true);

        turnCount++;
        System.out.println("Play random move");
        setNextPlayer();
    }


    //make a move based on the computer's algorithms
    public void computerPlaySmartMove() {
        int randomX, randomY;
        if (turnCount == 0) {
            System.out.println("FIRST MOVE");
            /** Board pattern:
             *
             * |    |    |    |
             * | 00 | 01 | 02 |
             * |    |    |    |
             * -----------------
             * |    |    |    |
             * | 10 | 11 | 12 |
             * |    |    |    |
             * -----------------
             * |    |    |    |
             * | 20 | 21 | 22 |
             * |    |    |    |
             *
             *
             * The added values of X and Y
             * |    |    |    |
             * | 0  |  1 |  2 |
             * |    |    |    |
             * -----------------
             * |    |    |    |
             * |  1 |  2 |  3 |
             * |    |    |    |
             * -----------------
             * |    |    |    |
             * |  2 |  3 |  4 |
             * |    |    |    |
             *
             * Aiming for the optimal move of going for the corners (0, 2, and 4) where the numbers are not 1
             *
             *
             */
            do {
                randomX = MIN + (int) (Math.random() * ((MAX - MIN) + 1));
                randomY = MIN + (int) (Math.random() * ((MAX - MIN) + 1));
            } while (randomX % 2 != 0 || randomY % 2 != 0);
            System.out.println("X: " + randomX + " | Y: " + randomY);
            //essentially looking at a platform in which the computer makes the optimal move of choosing a corner at all times
            Button b = btn[randomX][randomY];
            b.setText("O");
            b.setDisable(true);

            turnCount++;
            setNextPlayer();
        } else if (turnCount == 1) {
            System.out.println("SECOND MOVE");
            if (!btn[1][1].getText().equals("X")) {
                Button b = btn[1][1]; //optimal move is corner
                b.setText("O");
                b.setDisable(true);

                turnCount++;
                setNextPlayer();
            } else {
                do {
                    randomX = MIN + (int) (Math.random() * ((MAX - MIN) + 1));
                    randomY = MIN + (int) (Math.random() * ((MAX - MIN) + 1));
                } while (randomX % 2 != 0 || randomY % 2 != 0);
                System.out.println("X: " + randomX + " | Y: " + randomY);
                //essentially looking at a platform in which the computer makes the optimal move of choosing a corner at all times
                Button b = btn[randomX][randomY];
                b.setText("O");
                b.setDisable(true);

                turnCount++;
                setNextPlayer();
            }
        } else {
            int temp = turnCount;
            findOptimalMove();
            if (turnCount != temp)
                return;
            computerCheckVerticals("X");
            if (turnCount != temp)
                return;
            computerCheckHorizontals("X");
            if (turnCount != temp)
                return;
            computerCheckDiagonals("X");
            if (turnCount != temp)
                return;
            computerPlayRandomMove();
        }

    }

    //find the optimal move for the computer to play
    public void findOptimalMove() {
        computerCheckVerticals("O");
        computerCheckHorizontals("O");
        computerCheckDiagonals("O");
        System.out.println("Finding optimal move");
    }

    //check the verticals of tictactoe
    public void computerCheckVerticals(String marker) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (btn[i][j].getText().equals(marker)) {
                    count++;
                }
            }
            if (count == 2) {
                for (int j = 0; j < 3; j++) {
                    if (btn[i][j].getText().equals("")) {
                        Button b = btn[i][j];
                        b.setText(marker == "X" ? getDifferentMarker(marker) : "O");
                        b.setDisable(true);
                        turnCount++;
                        System.out.println("PLAYING VERTICAL MOVE");
                        setNextPlayer();
                        return;
                    }
                }
            }
            count = 0;
        }
    }

    //check the horizontals of tictactoe
    public void computerCheckHorizontals(String marker) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (btn[j][i].getText().equals(marker)) {
                    count++;
                }
            }
            if (count == 2) {
                for (int j = 0; j < 3; j++) {
                    if (btn[j][i].getText().equals("")) {
                        Button b = btn[j][i];
                        b.setText(marker == "X" ? getDifferentMarker(marker) : "O");
                        b.setDisable(true);
                        turnCount++;
                        System.out.println("PLAYING HORIZONTAL MOVE");
                        setNextPlayer();
                        return;
                    }
                }
            }
            count = 0;
        }
    }

    //check the diagnols of tictactoe
    public void computerCheckDiagonals(String marker) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j+i == 2 && btn[i][j].getText().equals(marker)) { //check down to up diagonal
                    count++;
                }
            }
            if (count == 2) {
                for (int j = 0; j < 3; j++) {
                    if (j+i == 2 && btn[i][j].getText().equals("")) {
                        Button b = btn[i][j];
                        b.setText(marker == "X" ? getDifferentMarker(marker) : "O");
                        b.setDisable(true);
                        turnCount++;
                        System.out.println("PLAYING DOWN TO UP DIAGONAL MOVE");
                        setNextPlayer();
                        return;
                    }
                }
            }
            count = 0;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == i && btn[i][j].getText().equals(marker)) { //check down to up diagonal
                    count++;
                }
            }
            if (count == 2) {
                for (int j = 0; j < 3; j++) {
                    if (j == i && btn[i][j].getText().equals("")) {
                        Button b = btn[i][j];
                        b.setText(marker == "X" ? getDifferentMarker(marker) : "O");
                        b.setDisable(true);
                        turnCount++;
                        System.out.println("PLAYING UP TO DOWN DIAGONAL MOVE");
                        setNextPlayer();
                        return;
                    }
                }
            }
            count = 0;
        }
    }

    //get a different marker
    public String getDifferentMarker(String marker) {
        if (marker.equals("X"))
            marker = "O";
        else
            marker = "X";
        return marker;
    }

    //restart the game
    public void restart() {
        setButtonsToEmpty();
        disableAllButtons(false);
        setTurnString();

        btn_playAgain.setVisible(false);

        playerOneScore = 0;
        opponentScore = 0;
        tieScore = 0;

        lbl_playerOneScore.setText("" + playerOneScore);
        lbl_opponentScore.setText("" + opponentScore);
        lbl_tieScore.setText("" + tieScore);

        turnCount = 0;

        if (!againstPlayer) {
            lbl_turn.setText("Your move");
            if (player == 3) {
                computerPlaySmartMove();
            } else {
                setNextPlayer();
            }
        }
    }

    //method to play the game again
    public void playAgain() {
        setButtonsToEmpty();
        disableAllButtons(false);
        setTurnString();

        btn_playAgain.setVisible(false);

        turnCount = 0;

        if (!againstPlayer && player == 3) {
            computerPlaySmartMove();
            lbl_turn.setText("Your move");
        }
    }

    //sets buttons to empty
    public void setButtonsToEmpty() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btn[i][j].setText("");
            }
        }
        lbl_turn.setText("");
    }
}
