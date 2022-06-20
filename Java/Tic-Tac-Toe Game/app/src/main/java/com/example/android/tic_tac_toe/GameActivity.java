/* GameActivity:
the main activity, which handles the flow of the game and the onscreen content. Uses the GameBoard and GameAI classes
to handle turns, screen reconfigurations, screen updates, and the game ending. The id of the buttons in the activity_game_layout
contain coordinate values that uniquely identify each button for when it is pressed or needs to be accessed.
 */

package com.example.android.tic_tac_toe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Point;

public class GameActivity extends AppCompatActivity {

    private GameBoard game;
    private GameAI ai;
    private int numPlayers;

    private TextView playerView;

    private final String BUTTON_NAME_FORMAT = "row%dcol%d";
    // the id given to each button follows this format (ex. col2row0)
    private final int BUTTON_ROW_INDEX = 3;
    private final int BUTTON_COL_INDEX = 7;
    // the string indexes of row and col values in BUTTON_NAME_FORMAT

    public final static String BOARD_MESSAGE = "board";
    public final static String CURRENT_TURN_MESSAGE = "currentTurn";
    public final static String TURN_COUNT_MESSAGE = "turnCount";
    public final static String CURRENT_STATUS_MESSAGE = "currentStatus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        playerView = findViewById(R.id.current_turn_textview);
        if(savedInstanceState == null)
            beginGame();
        else
            resumeGame(savedInstanceState);
        numPlayers = getIntent().getIntExtra(StartScreenActivity.NUM_PLAYERS_MESSAGE, 2);
        if(numPlayers == 1)
            startAI();
        // ai is only started if the game is one player
        updateCurrentTurnView();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(BOARD_MESSAGE, game.getBoard());
        savedInstanceState.putString(CURRENT_TURN_MESSAGE, game.getCurrentTurn());
        savedInstanceState.putInt(TURN_COUNT_MESSAGE, game.getTurnCount());
        savedInstanceState.putSerializable(CURRENT_STATUS_MESSAGE, ai.getCurrentStatus());
    }

    private void beginGame() {
        game = new GameBoard();
        ai = new GameAI(game);
        game.computeFirstTurn();
    }

    /*
    Resumes the game with former state. All buttons must be updated with the correct symbol.
     */
    private void resumeGame(Bundle savedInstanceState) {
        game = new GameBoard((String[][])savedInstanceState.getSerializable(BOARD_MESSAGE), savedInstanceState.getString(CURRENT_TURN_MESSAGE), savedInstanceState.getInt(TURN_COUNT_MESSAGE));
        ai = new GameAI(game, (GameAI.Statuses)savedInstanceState.getSerializable(CURRENT_STATUS_MESSAGE));
        updateButtonText();
    }

    /*
    Loops through all buttons and updates them to the correct symbol.
     */
    private void updateButtonText() {
        for(int i = 0; i < GameBoard.BOARD_SIZE; i++)
            for(int j = 0; j < GameBoard.BOARD_SIZE; j++)
                getTicTacToeButton(i, j).setText(game.getSpotValue(i, j));
    }

    /*
    Updates AI status at the beginning of the game. Should be noted that the ai object is always created, even when the game is two player, but
    its status is STOPPED by default. startAI() updates the status of the ai so that it is prepared to make a turn.
     */
    private void startAI() {
        if(game.getTurnSymbol() == GameBoard.PLAYER_TWO_SYMBOL)
            ai.setCurrentStatus(GameAI.Statuses.MAKING_TURN);
        // if first turn belongs to ai, make turn
        else
            ai.setCurrentStatus(GameAI.Statuses.WAITING);
        // otherwise, wait to make turn
        runAITimer();
    }

    /*
    To create a delay effect for the ai makings its turn, the ai turn algorithm is scheduled on a delay continuously using a handler.
    Code will only execute if the ai is supposed to make its turn (status is MAKING_TURN). The general code for this was borrowed from the class text.
     */
    private void runAITimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(ai.isMakingTurn()) {
                    AITurnMade();
                    ai.setCurrentStatus(GameAI.Statuses.WAITING);
                    // resets itself to waiting
                }
                handler.postDelayed(this, GameAI.AI_DELAY);
            }
        });
    }

    /*
    Called when a button is clicked by the user. Checks if user has selected a valid spot, updates the corresponding button, and proceeds with the next turn,
    notifying the ai that its time to make its turn.
     */
    public void turnMade(View view) {
        if (ai.isMakingTurn())
            return;
        // prevents user from clicking buttons when ai algorithm is being executed
        String coordinates = getResources().getResourceEntryName(view.getId());
        // gets a string of the id (in the form of BUTTON_NAME_FORMAT)
        int row = Character.getNumericValue(coordinates.charAt(BUTTON_ROW_INDEX));
        int col = Character.getNumericValue(coordinates.charAt(BUTTON_COL_INDEX));
        if (game.isOpen(row , col)) {
            game.setBlock(row, col);
            ((Button)view).setText(game.getTurnSymbol());
            handleGameFinishedConditions(row, col);
            if(ai.isWaiting())
                ai.setCurrentStatus(GameAI.Statuses.MAKING_TURN);
            // tell AI to makes it turn (provided user has made a successful turn)
        }
    }

    /*
    Helper method for the AI's turn. AI makes its turn, and the corresponding button to the selected spot is update.
     */
    private void AITurnMade() {
        Point selectedSpot = ai.doTurn();
        getTicTacToeButton(selectedSpot.x, selectedSpot.y).setText(game.getTurnSymbol());
        handleGameFinishedConditions(selectedSpot.x, selectedSpot.y);
    }

    /*
    Called after every turn is made to check if the game is finished (won or tied). If so, a custom alertDialog is created and the ai is stopped.
    Otherwise, the next turn occurs.
     */
    private void handleGameFinishedConditions(int row, int col) {
        if(game.isWon(row, col)) {
            ai.setCurrentStatus(GameAI.Statuses.STOPPED);
            createAlertDialog(String.format(getString(R.string.win_message), game.getTurnPlayerNumber()));
        }
        else if(game.isTied()) {
            ai.setCurrentStatus(GameAI.Statuses.STOPPED);
            createAlertDialog(getString(R.string.tie_message));
        }
        else 
            nextTurn();
    }

    /*
    Creates an alertDialog to notify the user that the game is tied or won by either player. Prompts the user if they want to player again.
    If yes, game is restarted. If no, game returns to start screen. General code for this borrowed from developer.android.com (official documentation)
     */
    private void createAlertDialog(String endMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(endMessage);
        alertDialog.setMessage(getString(R.string.play_again));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alert_btn_yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(GameActivity.this, GameActivity.class);
                        intent.putExtra(StartScreenActivity.NUM_PLAYERS_MESSAGE, numPlayers);
                        startActivity(intent);
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.alert_btn_no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(GameActivity.this, StartScreenActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        alertDialog.show();
    }

    private void nextTurn() {
        game.changeTurn();
        updateCurrentTurnView();
    }

    /*
    Updates label that says whose turn it is.
     */
    private void updateCurrentTurnView() {
        playerView.setText(String.format(getString(R.string.current_player_notice), game.getTurnPlayerNumber()));
    }

    private Button getTicTacToeButton(int row, int col) {
        String buttonName = String.format(BUTTON_NAME_FORMAT, row, col);
        int buttonId = getResources().getIdentifier(buttonName, "id", getPackageName());
        return findViewById(buttonId);
    }
}
