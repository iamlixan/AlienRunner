package me.com.lixan.alienrunner.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import me.com.lixan.alienrunner.MainActivity;

/**
 * Created by LeakSun on 02/08/2019.
 */

public class ObstacleManager {

    private ArrayList<ObstacleObject> obstacleObjects;
    private int playerGapWidth;
    private int obstacleGapsWidth;
    private int obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;

    private int score = 0;

    public ObstacleManager(int playerGapWidth, int obstacleGapsWidth, int obstacleHeight, int color){

        this.playerGapWidth = playerGapWidth;
        this.obstacleGapsWidth = obstacleGapsWidth;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        obstacleObjects = new ArrayList<>();

        generateObstacles();
    }

    private void generateObstacles(){
        int currentY = -5 * MainActivity.SCREEN_HEIGHT / 4;

        // obstacleObjects.get(obstacleObjects.size() - 1).getObstacle().bottom < 0
        while(currentY < 0){
            int startX = (int) (Math.random() * (MainActivity.SCREEN_WIDTH - playerGapWidth));
            obstacleObjects.add(new ObstacleObject(obstacleHeight, color, startX, currentY, playerGapWidth));
            currentY += obstacleHeight + obstacleGapsWidth;
        }
    }

    public void updateFrame(){

        if(startTime < MainActivity.INIT_TIME){
            startTime = MainActivity.INIT_TIME;
        }
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        float frameSpeed = (float) ((Math.sqrt(1 + (startTime - initTime) / 2000)) * MainActivity.SCREEN_HEIGHT / 10000.0f);

        for(ObstacleObject obstacleObject : obstacleObjects){
            obstacleObject.incrementYvalue(frameSpeed * elapsedTime);
        }

        if(obstacleObjects.get(obstacleObjects.size() - 1).getObstacle().top >= MainActivity.SCREEN_HEIGHT){
            int startX = (int) (Math.random() * (MainActivity.SCREEN_WIDTH - playerGapWidth));
            int startY = obstacleObjects.get(0).getObstacle().top - obstacleHeight - obstacleGapsWidth;
            obstacleObjects.add(0, new ObstacleObject(obstacleHeight, color, startX, startY, playerGapWidth));
            score++;
            obstacleObjects.remove(obstacleObjects.size() - 1);
        }
    }

    public void drawObjects(Canvas canvas){

        for (ObstacleObject obstacleObject : obstacleObjects){
            obstacleObject.drawObject(canvas);
        }
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);

    }

    public boolean isPlayerCollide(PlayerObject playerObject){

        for (ObstacleObject obstacleObject : obstacleObjects){
            if (obstacleObject.isPlayerCollided(playerObject)){
                return true;
            }
//            else{
//                return false;
//            }
        }
        return false;
    }





}
