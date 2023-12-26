package com.example.crossyroadsgame.crossyroads.obstacles;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Obstacle
{
    private int x;
    private int y;

    public Obstacle(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public void draw(Canvas canvas,int adjustmov)
    {
        int cameraadj = getY()+adjustmov;
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);//setting color of truck

        canvas.drawRect(getX(), cameraadj, getX()+getWidth(), cameraadj+height, paint);
    }

    private float width;
    private float height;

    public float getLeft()
    {
        return x;
    }
    public float getWidth() {
        return width;
    }
    public float getRight()
    {
        return x + getWidth();
    }

    public float getTop() {
        return y;
    }

    public float getBottom() {
        return y + height;
    }

    public int getY()
    {
        return y;
    }
    public int getX()
        {
            return x;
        }
}
