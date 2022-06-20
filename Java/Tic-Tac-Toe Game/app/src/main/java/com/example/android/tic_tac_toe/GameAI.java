/* GameAI:
provides a set of methods for the ai to choose a spot. Currently only finds the opens spots and chooses a random one to
place its symbol. Uses statuses to indicate when its not activated, when its waiting to make its turn, and when its in
the process of making its turn. These are used in the main activity to the threaded implementation.
 */

package com.example.android.tic_tac_toe;

import android.graphics.Point;
import java.util.ArrayList;

public class GameAI {

    public static final int AI_DELAY = 2500;
    public enum Statuses{STOPPED, WAITING, MAKING_TURN};
    private Statuses currentStatus;
    private GameBoard game;

    public GameAI(GameBoard game) {
        this.game = game;
        currentStatus = Statuses.STOPPED;
    }

    /*
    Separate constructor for reconfiguration.
     */
    public GameAI(GameBoard game, Statuses currentStatus) {
        this.game = game;
        this.currentStatus = currentStatus;
    }
    /*
    the ai makes its turn, returning its selected Point. Currently chooses a random open spot.
     */
    public Point doTurn() {
        ArrayList<Point> openSpots = new ArrayList<>(GameBoard.BOARD_SIZE * GameBoard.BOARD_SIZE);
        for(int i = 0; i < GameBoard.BOARD_SIZE; i++)
            for(int j = 0; j < GameBoard.BOARD_SIZE; j++)
                if(game.isOpen(i,j))
                    openSpots.add(new Point(i, j));
        Point randomSpot = openSpots.get((int)(Math.random() * openSpots.size()));
        game.setBlock(randomSpot.x, randomSpot.y);
        return randomSpot;
    }

    public boolean isMakingTurn() {
        return currentStatus == Statuses.MAKING_TURN;
    }

    public boolean isWaiting() {
        return currentStatus == Statuses.WAITING;
    }

    public void setCurrentStatus(Statuses currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Statuses getCurrentStatus() {
        return currentStatus;
    }
}
