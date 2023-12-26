package com.example.crossyroadsgame;
import com.example.crossyroadsgame.crossyroads.Game.Game;
import com.example.crossyroadsgame.crossyroads.vehicles.Car;
import com.example.crossyroadsgame.crossyroads.vehicles.Truck;
import com.example.crossyroadsgame.crossyroads.vehicles.Vehicle;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.view.animation.TranslateAnimation;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameplayActivity extends AppCompatActivity {

    private static final int NUM_INITIAL_GRASS_ROWS = 10;
    private static final int ROW_HEIGHT_DP = 85;
    private Random random = new Random();
    private Handler handler = new Handler();

    private boolean userClickedUpArrow = false;
    private LinearLayout llGameContainer;

    private Game game;
    private boolean isCarRoad = true;
    private static final int MIN_VEHICLES_ON_ROAD = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        llGameContainer = findViewById(R.id.llGameContainer);

        findViewById(R.id.upArrowButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the upward arrow click
                userClickedUpArrow = true;
                game = new Game(getApplicationContext(), null);
            }

        });

        // Add initial grass rows
        for (int i = 0; i < NUM_INITIAL_GRASS_ROWS; i++) {
            addGrassRow();
        }

        // Start the game loop
        startGameLoop();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, GameplayActivity.class);
    }

    private void addGrassRow() {
        ImageView grassRow = new ImageView(this);
        grassRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) dpToPx(ROW_HEIGHT_DP)));
        grassRow.setImageResource(R.drawable.grass);
        grassRow.setScaleType(ImageView.ScaleType.FIT_XY);
        llGameContainer.addView(grassRow);
    }

    private void startGameLoop() {
        final Handler handler = new Handler();

        // Start the game loop immediately
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Game loop logic goes here
                // Check if the up arrow is clicked
                if (userClickedUpArrow) {
                    // Remove the bottom row
                    removeBottomRow();

                    // Add a new row at the top
                    addRandomRowAtTop();

                    // Reset the user input flag
                    userClickedUpArrow = false;
                }

                // Check again after a delay
                handler.postDelayed(this, 100); // Adjust the delay as needed
            }
        });
    }

    private void removeBottomRow() {
        // Remove the bottom row from llGameContainer
        if (llGameContainer.getChildCount() > 0) {
            llGameContainer.removeViewAt(llGameContainer.getChildCount() - 1);
        }
    }

    private void addRandomRowAtTop() {
        // Add a random row at the top of llGameContainer (either grass or concrete)
        if (new Random().nextBoolean()) {
            addGrassRowAtTop();
        } else {
            addConcreteRowAtTop();
        }
    }

    private void addGrassRowAtTop() {
        // Add a grass row at the top of llGameContainer
        ImageView grassRow = new ImageView(this);
        grassRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) dpToPx(ROW_HEIGHT_DP)));
        grassRow.setImageResource(R.drawable.grass);
        grassRow.setScaleType(ImageView.ScaleType.FIT_XY);
        llGameContainer.addView(grassRow, 0); // Add at the beginning
    }
    private void creationofVehicle()
    {
        while (llGameContainer.getChildCount() < MIN_VEHICLES_ON_ROAD) {
            boolean addCars = random.nextBoolean();
            String direction = (random.nextBoolean()) ? "left" : "right";

            if (addCars) {
                addCarRoadRowAtTop(direction);
            } else {
                addTruckRoadRowAtTop(direction);
            }
        }
    }

    private void addConcreteRowAtTop() {

        // Ensure at least 3 vehicles on the road
        creationofVehicle();

        // Add a concrete row at the top of llGameContainer
        ImageView concreteRow = new ImageView(this);
        concreteRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) dpToPx(ROW_HEIGHT_DP)));
        concreteRow.setImageResource(R.drawable.concrete);
        concreteRow.setScaleType(ImageView.ScaleType.FIT_XY);
        llGameContainer.addView(concreteRow, 0); // Add at the beginning

        startVehicleAnimationsDelayed();
    }

    private void startVehicleAnimationsDelayed()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                creationofVehicle(); // Ensure there are enough vehicles on the road
                startVehicleAnimations(); // Start the vehicle animations
                addConcreteRowAtTop(); // Add the concrete row
            }
        }, 500); // Delay before starting animations
    }

    /*private void addRandomVehicleOnConcreteRoad() {
        // Decide whether to add a car or a truck on the concrete road
        boolean addCar = random.nextBoolean();
        String direction = (random.nextBoolean()) ? "left" : "right";

        // Create the chosen vehicle
        if (addCar) {
            addCarRoadRowAtTop(direction);
        } else {
            addTruckRoadRowAtTop(direction);
        }
    }*/



    private void startVehicleAnimations()
    {
        List<View> childViews = new ArrayList<>();
        for (int i = 0; i < llGameContainer.getChildCount(); i++) {
            childViews.add(llGameContainer.getChildAt(i));
        }


        for (View childView : childViews) {
            if (childView instanceof ImageView) {
                ImageView vehicleImageView = (ImageView) childView;
                Vehicle vehicle = (Vehicle) vehicleImageView.getTag();

                // Retrieve the corresponding Car or Truck object based on the tag
                Object tag = vehicleImageView.getTag();
                if (tag instanceof Car) {
                    moveCarAcrossRoad((Car) tag, vehicleImageView);
                } else if (tag instanceof Truck) {
                    moveTruckAcrossRoad((Truck) tag, vehicleImageView);
                }
            }
        }
    }


    private void addCarRoadRowAtTop(String direction) {
        for(int i = 0; i < MIN_VEHICLES_ON_ROAD; i++)
        {
            Car car = game.createcar();
            int iD = car.setImage(direction);

            ImageView carImageView = new ImageView(this);
            carImageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            carImageView.setImageResource(iD);
            carImageView.setTag(car);
            llGameContainer.addView(carImageView, 0);
            moveCarAcrossRoad(car, carImageView);
        }

    }
    private void addTruckRoadRowAtTop(String direction) {
        for(int i = 0; i < MIN_VEHICLES_ON_ROAD; i++) {
            Truck truck = game.createTruck();

            int iD = truck.setImage(direction);

            ImageView truckImageView = new ImageView(this);
            truckImageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            truckImageView.setImageResource(iD);
            truckImageView.setTag(truck);
            llGameContainer.addView(truckImageView, 0);
            moveTruckAcrossRoad(truck, truckImageView);
        }

    }



    private float dpToPx(float dp) {
        // Helper method to convert density-independent pixels (dp) to pixels (px)
        float density = getResources().getDisplayMetrics().density;
        return dp * density;
    }

    private void moveCarAcrossRoad(Car car, ImageView carImageView) {
        float distance = car.getXdir();

        // Create a translation animation for the carImageView
        ObjectAnimator animator = ObjectAnimator.ofFloat(carImageView, "translationX", carImageView.getTranslationX() + distance);

        // Set the duration of the animation (adjust as needed)
        animator.setDuration(500);

        // Set the animation listener to remove the view when the animation ends
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                llGameContainer.removeView(carImageView);
            }
        });

        // Start the animation
        animator.start();
    }

    private void moveTruckAcrossRoad(Truck truck, ImageView truckImageView)
    {
        float distance = truck.getXdir();

        // Create a translation animation for the truckImageView
        ObjectAnimator animator = ObjectAnimator.ofFloat(truckImageView, "translationX", truckImageView.getTranslationX() + distance);

        // Set the duration of the animation (adjust as needed)
        animator.setDuration(500);

        // Set the animation listener to remove the view when the animation ends
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                llGameContainer.removeView(truckImageView);
            }
        });

        // Start the animation
        animator.start();

    }

}
