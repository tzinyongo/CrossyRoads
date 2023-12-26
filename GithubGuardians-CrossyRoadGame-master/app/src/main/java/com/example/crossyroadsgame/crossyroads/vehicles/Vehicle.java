package com.example.crossyroadsgame.crossyroads.vehicles;

public class Vehicle
{
    protected int x;
    protected int y;
    protected int speed;

    public Vehicle()
    {
        this.x = x;
        this.y = y;

    }



    public void move()
    {
        x +=speed;
    }

}
