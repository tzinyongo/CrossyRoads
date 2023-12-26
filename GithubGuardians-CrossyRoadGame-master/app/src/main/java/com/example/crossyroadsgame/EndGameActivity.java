package com.example.crossyroadsgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_end);

        // Find the replay button by its ID
        Button replayButton = findViewById(R.id.replayButton);

        // Set a click listener for the replay button
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the function to handle replay
                replayGame();
            }
        });

        // You can add more initialization code here if needed
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, GameplayActivity.class);
    }

    // Function to handle replay button click
    public void replayGame() {
        // Assuming you want to restart the gameplay activity
        // You may also want to pass data like the player's score to the new gameplay instance
        // Here, we start the GameplayActivity without passing any extra data
        startActivity(GameplayActivity.newIntent(this));

        // Finish the current activity to remove it from the back stack
        finish();
    }

    // Function to handle close button click
    public void closeGameOverScreen(View view) {
        // Finish the current activity to go back
        finish();
    }
}
