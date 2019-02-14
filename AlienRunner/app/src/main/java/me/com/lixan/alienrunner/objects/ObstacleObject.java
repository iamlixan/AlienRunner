package me.com.lixan.alienrunner.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import me.com.lixan.alienrunner.MainActivity;
import me.com.lixan.alienrunner.interfaces.GameObject;

/**
 * Created by LeakSun on 02/08/2019.
 */

public class ObstacleObject implements GameObject {


    private Rect obstacle;
    private Rect obstacle2;
    private int rectHeight;
    private int color;
    private int startX;
    private int playerGap;

    private Bitmap obstacleImg;

    public ObstacleObject(int rectHeight, int color, int startX, int startY, int playerGap){

        this.color = color;
        obstacle = new Rect(0, startY, startX, startY + rectHeight);
        obstacle2 = new Rect(startX + playerGap, startY, MainActivity.SCREEN_WIDTH, startY + rectHeight);

//        obstacleImg = BitmapFactory.decodeResource(MainActivity.context.getResources(), R.drawable.obstacle_img);

    }

    @Override
    public void drawObject(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(color);

//        canvas.drawBitmap(obstacleImg, null, obstacle, paint);
//        canvas.drawBitmap(obstacleImg, null, obstacle2, paint);

        canvas.drawRect(obstacle, paint);
        canvas.drawRect(obstacle2, paint);
    }

    @Override
    public void updateFrame() {

    }

    public boolean isPlayerCollided(PlayerObject rect){

//        if(obstacle.contains(rect.getPlayer().left, rect.getPlayer().top) ||
//                obstacle.contains(rect.getPlayer().right, rect.getPlayer().top) ||
//                obstacle.contains(rect.getPlayer().left, rect.getPlayer().bottom) ||
//                obstacle.contains(rect.getPlayer().right, rect.getPlayer().bottom))
//        {
//            return true;
//        }else{
//            return false;
//        }

        return Rect.intersects(obstacle, rect.getPlayer()) || Rect.intersects(obstacle2, rect.getPlayer());
    }

    public Rect getObstacle(){
        return obstacle;
    }

    public void incrementYvalue(float y){

        obstacle.top += y;
        obstacle.bottom += y;

        obstacle2.top += y;
        obstacle2.bottom += y;

    }


}
