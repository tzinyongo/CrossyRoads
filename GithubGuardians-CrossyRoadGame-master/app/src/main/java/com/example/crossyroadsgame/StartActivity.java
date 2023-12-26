package com.example.crossyroadsgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

// In StartActivity.java or whatever you named it
public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the start game button by its ID
        Button startGameButton = findViewById(R.id.startButton);

        // Set a click listener for the start game button
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the GameplayActivity when the button is clicked
                Intent intent = new Intent(StartActivity.this, GameplayActivity.class);
                startActivity(intent);
            }
        });
    }
}
