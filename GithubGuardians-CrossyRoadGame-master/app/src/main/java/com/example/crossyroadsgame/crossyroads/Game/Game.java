package com.example.crossyroadsgame.crossyroads.Game;
import com.example.crossyroadsgame.crossyroads.obstacles.Obstacle;
import com.example.crossyroadsgame.crossyroads.vehicles.Car;
import com.example.crossyroadsgame.crossyroads.vehicles.Truck;
import com.example.crossyroadsgame.crossyroads.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector;
import android.graphics.Rect;
import java.util.Iterator;
import java.util.Random;
import android.util.Log;

enum Direction
{

    Left, Right, Forward
}
public class Game extends View
{
    private static final int Road_Width = 200;
    private static final int Land_Size = 600;

    //creating list instances of the objects
    //the cars, trucks and obstacles
    private List<Car> cars = new ArrayList<>();
    private List<Truck> trucks = new ArrayList<>();

    private List<Obstacle> obstacles = new ArrayList<>();
    private GestureDetector gesture;//initiating an object of GestureDetector

    private int playerPositionX = Road_Width / 2; // Start in the middle of the road
    private int playerPositionY = 500; // Initial player's position on the screen
    int step = 0;//counter to count the number of steps the player takes
    private Direction currentDirection = Direction.Forward; //this marks the players current direction

    private Random random = new Random();//generates random numbers to simulate
    //the randomness of generating obstacles such as cars, and tress in different positions
    private Handler handler = new Handler(Looper.getMainLooper());//this helps to post tasks to
    //be prefomed on the UI thread
    private static final int MAX_CARS = 3;
    private int Onroad = 0;

    int cameraY = 0;//this is what adjuect the camera to scroll down




    /*protected void onDraw(Canvas canvas) {
        // Draw a white background
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

        // Calculate the center of the screen
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        // Draw a red dot in the center
        Paint redDotPaint = new Paint();
        redDotPaint.setColor(Color.RED);
        float dotRadius = 25f; // Adjust the radius as needed
        canvas.drawCircle(centerX, centerY, dotRadius, redDotPaint);
    }*/


    public Game(Context context, AttributeSet attrs) {
        super(context);

        gesture = new GestureDetector(context, new GestureListener());

        //starting the game loop
        //this updates the game as the player continues to play
        handler.postDelayed(gameLoop, 0);
    }

    private Runnable gameLoop = new Runnable()//runs the game loop
    {
        @Override
        public void run()
        {
            updateGame();
            invalidate();
            handler.postDelayed(this,100);
        }
    };




    private void updateGame()//redraws the game
    {
       // movePlayer();
        moveObjects();
        checkCollisions();
        step++;
    }


    /*private void movePlayer()//moving according to the direction of the player
    {
        int stepSize = 10;
        switch(currentDirection)
        {//the subtraction or addition is based on the x axis

            case Left:
                if(playerPositionX > 0)
                {
                    playerPositionX -=stepSize;
                    cameraY +=stepSize;
                }
                break;
            case Right:
                if(playerPositionX < Road_Width - 50)
                {
                    playerPositionX += stepSize;
                    cameraY += stepSize;
                }
                break;
            case Forward://moving forward is moving the player in the positive y direction
                if(playerPositionY > 0)
                {
                    playerPositionY -= stepSize;
                    cameraY += stepSize;
                }
                break;
        }

    }*/
    //the creats the instances of the car giving
    //this sets their starting position and determines where or not they are moving from right-left to left-right
    //calls the car functions that pick which image to use for the car
    public Car createcar()
    {


        //this is where we want to write the code to set the obstable on the road

        if(Onroad < MAX_CARS)
        {
            Car car =  new Car();
            if(random.nextInt(2) ==1)
            {
                car.setX(Road_Width + random.nextInt(200));//this adjust teh x position
                car.setXdir(-(random.nextInt(10) + 10));
                car.setRandomCarImage("left");

            }
            //this helps to set the cars direction from left to right
            //need to updated based on android studio coordinate system
            else {
                car.setX(-200);//need to change based off coordinate system
                car.setXdir((random.nextInt(10)) + 10);
                car.setRandomCarImage("right");
            }
            Onroad++;
            return car;
        }
        else
        {
            return null;
        }
        //this sets the cars direction from right ot left



    }
    public Truck createTruck()
    {
        if(Onroad < MAX_CARS) {
            Truck truck = new Truck();
            //this sets the cars direction from right ot left
            if (random.nextInt(2) == 1) {
                truck.setX(Road_Width + random.nextInt(200));//this adjust teh x position
                truck.setXdir(-(random.nextInt(10) + 10));
                truck.setRandomCarImage("left");

            }
            //this helps to set the cars direction from left to right
            //need to updated based on android studio coordinate system
            else {
                truck.setX(-200);//need to change based off coordinate system
                truck.setXdir((random.nextInt(10)) + 10);
                truck.setRandomCarImage("right");
            }
            Onroad++;
            return truck;
        }
        else
        {
            return null;
        }

    }

    private void moveObjects() //one of first functions called that creates instance of an objects and moves it
    {
        //move cars
        for (Car car : cars) {
            car.move();
        }

        //now to add cars periodically
        if(random.nextInt(100) < 15)
        {
            Car newCar = createcar();
            if(newCar != null)
            {
                cars.add(newCar);
            }

        }

        for (Truck truck : trucks)
        {
            truck.move();
        }

        if(random.nextInt(100) < 20)
        {
            Truck newTruck = createTruck();
            if(newTruck != null)
            {
                trucks.add(newTruck);
            }

        }

        ///this is where we are moving the screen




        //move trucks

        //add new cars and trucks periodically
        //in both conditionaly statements we are generating a number that is between 0 and 100
        //and we check if that number, lets say x, is less that sum arbitrary value y
        //meaning that there is a y% chance of the objects being generated
        //if its true then it will generate that object, in this case either a truck or car
        //y tells us the frequecy of the objects appearing, if we want them to appear more often
        //then we shld increase y if less frequent then decrease y



        /*if (random.nextInt(100) < 10) {
            trucks.add(new Truck(random.nextInt(Road_Width), getHeight(), 3, 50, 50));
        }*/

        //now we want to remove cars that have gone off the road

        //THIS IS FOR CARS////////////////////////////



        //moves off the left side of screen
        cars.removeIf(car -> car.getX() < 0);
        //this iterates over the list of trucks and their attributes
        //checking if the specific car's x value is less than 0 meaning it moved off the
        //left side of the screen
        //if true then it removes the value



        //car.getX + car.getWidth represents the right side of the car
        //car.getX gives us the x coordinate of the left side of the car
            //this is typical based on the coordinate system; it referes to how many units to the right from the leftest
            //most edge is the car located
            //ex: if the number is 50, we know that the car is located 50 units to the right of the left edge of the page
        //car.getWidth is the width of the car
        //so adding these two together gives you the right side of the car

        //if car moves off the right of screen
        Iterator<Car> carIteratorR = cars.iterator();//this iterates over the list of trucks and their attributes
        while (carIteratorR.hasNext()) {
            Car car = carIteratorR.next();
            if (car.getX() + car.getWidth() > getWidth()) {
                carIteratorR.remove();
            }
        }

        //THIS IS FOR THE TRUCKS////////////////////////////////

        //if move off left side
        trucks.removeIf(truck -> truck.getX() < 0);
        //this is essentially saying for each truck check if the x coordinate is less than 0

        //if move off right side
        Iterator<Truck> truckIteratorR = trucks.iterator();
        while (truckIteratorR.hasNext()) {
            Truck truck = truckIteratorR.next();
            if (truck.getX() + truck.getWidth() > getWidth()) {
                truckIteratorR.remove();
            }
        }
        //need to revisit this function
        if(step>30)
        {
            for(Car car: cars)
            {
                int currentspeed = car.getSpeed();
                currentspeed += 5;
                car.setSpeed(currentspeed);
            }
        }

    }



    private void checkCollisions()//we want to check if the player hit an obstacle
            //car, truck, tree, etc..
    {

        //collision check if the cars are moving left
        for(Car car: cars)
        {
            if (playerPositionX < car.getRight() && playerPositionX + 50 > car.getLeft() &&
                playerPositionY < car.getBottom() && playerPositionY + 50 > car.getTop()) {
                endGame();
                return;//early exits if collision is detected
            }

            //collision check if the cars are moving right
            if (playerPositionX + 50 > car.getLeft() && playerPositionX < car.getRight() &&
               playerPositionY + 50 > car.getTop() && playerPositionY < car.getBottom()) {
                endGame();
                return;
            }
            //this checks if the
        }

        for(Truck truck: trucks)
        {
            if (playerPositionX < truck.getRight() && playerPositionX + 50 > truck.getLeft() &&
                    playerPositionY < truck.getBottom() && playerPositionY + 50 > truck.getTop()) {
                endGame();
                return;
            }

            //collision check if the trucks are moving right
            if (playerPositionX + 50 > truck.getLeft() && playerPositionX < truck.getRight() &&
                    playerPositionY + 50 > truck.getTop() && playerPositionY < truck.getBottom()) {
                endGame();
                return;
            }
        }


        //This is checking if the player collides with stationary obstacles
        for(Obstacle obstacle : obstacles)
        {
            if (playerPositionX < obstacle.getRight() && playerPositionX + 50 > obstacle.getLeft() &&
                    playerPositionY < obstacle.getBottom() && playerPositionY + 50 > obstacle.getTop()) {
                endGame();
                return;//Exit early if collision is detected
            }

            //collision check if the cars are moving right
            if (playerPositionX + 50 > obstacle.getLeft() && playerPositionX < obstacle.getRight() &&
                    playerPositionY + 50 > obstacle.getTop() && playerPositionY < obstacle.getBottom()) {
                endGame();
                return;//Exit early if collision is detected
            }
        }

    }

    private void endGame()
    {
        handler.removeCallbacks(gameLoop);//ends the game loop

        //now we want to dispaly a dialog box that indicated that the game is over
        AlertDialog.Builder build = new AlertDialog.Builder(getContext());
        build.setTitle("Game Over")
                .setMessage("Game Over, Steps taken: " + step)
                .setPositiveButton("Start Over", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();//restarts the game
                    }
                })
                .setCancelable(false)//disabling canceling with the back button
                .show();
    }

    private void restartGame()
    {
        //initializes everything to initial values
        playerPositionX = Road_Width / 2;
        playerPositionY = 500;
        step = 0;
        currentDirection = Direction.Forward;

        // Clear lists of cars, trucks, and obstacles
        cars.clear();
        trucks.clear();
        obstacles.clear();

        // Restart the game loop
        handler.postDelayed(gameLoop, 0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {//MotionEvent is given by Andriod and it handles touching movements on screens
        return gesture.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener
    {//this is a class that will allow users to prefom like taps, swips, and long presses

        //creating the threshold for what is considered a swipe or not
        private static final int Swipe_Threshold = 50;//50 represents the number of pixels user has to cross; this is the distance the person travels
        private static final int Swipe_velocity_min = 50;

        @Override
        public boolean onDown(MotionEvent touch)//this signifies that the user has touched the screen
        {//called when the users touches the screen
            return true; //if return true then it is tracking the rest of movements
            //such as move up and move down
        }

        public boolean onFling(MotionEvent up, MotionEvent down, float velocityX, float velocityY)
        {//up and down represent the up and down gestures when the user touches the screen
            //velocityX and velocityY represent the gestures in pixels per second along the x and y axis

            //calculating the distance in the x and in the y is traveled
            float distanceX = down.getX() - up.getX();
            float distanceY = down.getY() - up.getY();

            //calculates a horizontal swipe
            if(Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > Swipe_Threshold &&
            Math.abs(velocityX) > Swipe_velocity_min)
            {

                if(distanceX > 0) //relative to the origin (0,0)
                {
                    //swipe to the right
                    currentDirection = Direction.Right;
                }
                else
                {
                    //swipe to the left
                    currentDirection = Direction.Left;
                }
            }

            //forward swipe
            else if(Math.abs(distanceY) > Math.abs(distanceX) && Math.abs(distanceY) > Swipe_Threshold &&
            Math.abs(velocityY) > Swipe_velocity_min)
            {
                if(distanceY > 0)
                {
                    currentDirection = Direction.Forward;
                }
            }
            invalidate();

            return super.onFling(up,down,velocityX,velocityY);
            //reterns essentially "what ever is normally calculated for a swipe" always return it if the users motion is
            // a swipe


        }

    }
}
