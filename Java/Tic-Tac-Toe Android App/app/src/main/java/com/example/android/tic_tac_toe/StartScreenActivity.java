/* StartScreenActivity:
activity for choosing 1 or 2 players. Transfers that data to GameActivity.
 */

package com.example.android.tic_tac_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartScreenActivity extends AppCompatActivity {

    public static final String NUM_PLAYERS_MESSAGE = "numPlayersMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);
    }

    public void chosePlayer(View view) {
        int numPlayers;
        if(view.getId() == R.id.button_player1)
            numPlayers = 1;
        else
            numPlayers = 2;
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(NUM_PLAYERS_MESSAGE, numPlayers);
        startActivity(intent);
    }
}
