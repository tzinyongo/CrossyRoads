package com.example.crossyroadsgame.crossyroads.vehicles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

public class Truck extends Vehicle
{
    private float width;
    private float height;
    private int x;
    private int y;
    private float xdir;
    private float ydir;
    private ImageView im;
    public Truck()
    {
        super();
        x = 0;
        y =0;
        im =null;
        width = 0;
    }


    public void setX(int randval) { this.x = randval;}

    //setters and getters for the y cord
    public void setY(int randval) { this.y = randval;}

    //setters and getters for xdirection
    public int getXdir(){return (int) xdir;}
    public void setXdir(int ran) { this.xdir = ran;}

    //setters and getters for ydirection
    public int getYdir(){return (int) ydir;}
    public void setYdir(int ran) {this.ydir = ran;}
    public int getX()
    {
        return x;
    }

    public void draw(Canvas canvas, int adjustmov)
    {
        int cameraadj = getY()+adjustmov;
        Paint paint = new Paint();
        paint.setColor(Color.RED);//setting the color of the car

        canvas.drawRect(getX(), cameraadj, getX()+getWidth(), cameraadj+height, paint);
        //draws teh car on the canvas
    }
    @Override
    public void move()
    {
        x += xdir;
    }

    public float getWidth() {
        if(im == null)
        {
            return 10;
        }
        else
            return im.getWidth();
    }

    public float getLeft()
    {
        return x;
    }

    public float getRight()
    {
        return x + im.getWidth();
    }

    public float getTop() {
        return y;
    }

    public float getBottom() {
        return y + im.getHeight();
    }

    public int getY()
    {
        return y;
    }

    //this
    public void setRandomCarImage(String direction)
    {
        String ranTruck = randomTruck(direction);//picks the picture according to the direction that you are going
        //then saves it to a string
        setImage(ranTruck);//passes that string to a function that saves the image to our image variable im
    }

    //method that sets the image variable
    public int setImage(String filename)
    {
        int resourceId =0;
        try
        {
            resourceId = im.getResources().getIdentifier(filename, "drawable", im.getContext().getPackageName());
            im.setImageResource(resourceId);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return resourceId;
    }

    private String randomTruck(String direction)
    {
        String carIm = "";
        if(direction.equals("left"))
        {
            carIm = "motorcycle_yellow";
        }
        else if(direction.equals("right"))
        {
            carIm = "truck1facingright";
        }
        return carIm;
    }
}
