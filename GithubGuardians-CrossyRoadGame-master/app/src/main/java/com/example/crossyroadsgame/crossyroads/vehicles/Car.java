package com.example.crossyroadsgame.crossyroads.vehicles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;




public class Car extends Vehicle
{
    private int color;
    private float width;
    private float height;
    private float xdir;
    private float ydir;
    private String filename;
    private ImageView im;
    private int x;
    private int y;


    public Car()
    {
        super();
        x = 0;
        y =0;
        xdir = 0;
        ydir = 0;
        im =null;
        this.color = color;
        this.width = width;
        this.height = height;


    }public int getX()
{
    return  x;
}
    public void setX(int randval) { this.x = randval;}

    //setters and getters for the y cord
    public void setY(int randval) { this.y = randval;}
    public int getY()
    {
        return  y;
    }

    //setters and getters for xdirection
    public int getXdir(){return (int) xdir;}
    public void setXdir(int ran) { this.xdir = ran;}

    //setters and getters for ydirection
    public int getYdir(){return (int) ydir;}
    public void setYdir(int ran) {this.ydir = ran;}

    //sets the
    public Car(ImageView carImageView)
    {
        super();
        this.im = im;
    }

    //setters and getters for the x cord


    public void setSpeed(int speed)
    {
        this.speed = speed;
    }
    public int getSpeed()
    {
        return speed;
    }

    public float getWidth()
    {
        if(im == null)
        {
            return 10;
        }
        else
            return width;
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


    public void draw(Canvas canvas, int adjustmov)
    {
        int cameraadj = getY()+adjustmov;
        Paint paint = new Paint();
        paint.setColor(Color.RED);//setting the color of the car

        canvas.drawCircle(x + width / 2, y + height / 2 + adjustmov, width / 2, paint);
    }

    @Override
    public void move() {
        x += xdir;
    }//only want to move on the x axis

    public void setRandomCarImage(String direction)
    {
        String ranCar = randomCar(direction);
        setImage(ranCar);
    }

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

    private String randomCar(String direction)
    {
        String carIm = "";
        if(direction.equals("left"))
        {
            carIm = "car1redfacingleft";
        }
        else if(direction.equals("right"))
        {
            carIm = "car2bluefacingright";
        }
        return carIm;
    }
}
