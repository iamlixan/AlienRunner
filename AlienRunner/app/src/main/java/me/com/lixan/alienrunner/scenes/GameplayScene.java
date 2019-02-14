package me.com.lixan.alienrunner.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import me.com.lixan.alienrunner.MainActivity;
import me.com.lixan.alienrunner.objects.ObstacleManager;
import me.com.lixan.alienrunner.objects.PlayerObject;
import me.com.lixan.alienrunner.SensorData;
import me.com.lixan.alienrunner.interfaces.SceneI;

/**
 * Created by LeakSun on 02/08/2019.
 */

public class GameplayScene implements SceneI {

    private PlayerObject playerObject;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private boolean isPlayerMoving = false;
    private boolean isGameOver = false;

    private long gameOverTime;

    private int playerStartPointX = MainActivity.SCREEN_WIDTH/2;
    private int playerStartPointY = 3*MainActivity.SCREEN_HEIGHT/4;

    private SensorData sensorData;
    private long frameTime;

    public GameplayScene(){

        playerObject = new PlayerObject(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));

        int playerStartPointX = MainActivity.SCREEN_WIDTH/2;
        int playerStartPointY = 3*MainActivity.SCREEN_HEIGHT/4;

        playerPoint = new Point(playerStartPointX, playerStartPointY);
        playerObject.updatePoint(playerPoint);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.RED);

        sensorData = new SensorData();
        sensorData.registerSensors();
        frameTime = System.currentTimeMillis();
    }


    @Override
    public void updateFrame() {

        if(!isGameOver){

            if(frameTime < MainActivity.INIT_TIME){
                frameTime = MainActivity.INIT_TIME;
            }

            int timeElapsed = (int) (System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();

            if(sensorData.getOrientation() != null && sensorData.getStartOrientation() != null){
                float pitch = sensorData.getOrientation()[1] - sensorData.getStartOrientation()[1];
                float roll = sensorData.getOrientation()[2] - sensorData.getStartOrientation()[2];

                float xSpeed = 2 * roll * MainActivity.SCREEN_WIDTH / 700f;
                float ySpeed = pitch * MainActivity.SCREEN_HEIGHT / 700f;

                if(Math.abs(xSpeed * timeElapsed) > 5){
                    playerPoint.x += xSpeed * timeElapsed;
                }else{
                    playerPoint.x += 0;
                }

                if(Math.abs(ySpeed * timeElapsed) > -5){
                    playerPoint.y -= ySpeed * timeElapsed;
                }else{
                    playerPoint.y = 0;
                }

                if(playerPoint.x < 0){
                    playerPoint.x = 0;
                }else if(playerPoint.x > MainActivity.SCREEN_WIDTH){
                    playerPoint.x = MainActivity.SCREEN_WIDTH;
                }

            }

            if(playerPoint.y < 0){
                playerPoint.y = 0;
            }else if(playerPoint.y > MainActivity.SCREEN_HEIGHT){
                playerPoint.y = MainActivity.SCREEN_HEIGHT;
            }


            playerObject.updatePoint(playerPoint);
            obstacleManager.updateFrame();

            if(obstacleManager.isPlayerCollide(playerObject)){
                isGameOver = true;
                gameOverTime = System.currentTimeMillis();

            }
        }

    }

    @Override
    public void drawObjects(Canvas canvas) {

        canvas.drawColor(Color.WHITE);
        playerObject.drawObject(canvas);
        obstacleManager.drawObjects(canvas);

        if(isGameOver){

            Paint paint = new Paint();
            paint.setTextSize(80);
            paint.setColor(Color.MAGENTA);
            drawText(canvas, paint, "GAME OVER!");

        }

    }

    public void drawText(Canvas canvas, Paint paint, String text){
        Rect r = new Rect();
        canvas.getClipBounds(r);
        paint.setTextAlign(Paint.Align.LEFT);

        int rHeight = r.height();
        int rWidth = r.width();

        paint.getTextBounds(text, 0, text.length(), r);

        float x = rWidth / 2f - r.width() / 2f - r.left;
        float y = rHeight / 2f + r.height() / 2f - r.bottom;

        canvas.drawText(text, x , y, paint);

    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void handleTouch(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!isGameOver && playerObject.getPlayer().contains((int) event.getX(), (int) event.getY())){
                    isPlayerMoving = true;
                }

                if(isGameOver && (System.currentTimeMillis() - gameOverTime) >= 2000){
                    resetGame();
                    isGameOver = false;
                    sensorData.initGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!sensorData.hasSensors()){
                    if(!isGameOver && isPlayerMoving){
                        playerPoint.set((int) event.getX(), (int) event.getY());
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isPlayerMoving = false;
                break;
        }
    }

    public void resetGame(){

        playerPoint = new Point(playerStartPointX, playerStartPointY);
        playerObject.updatePoint(playerPoint);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.RED);

        isPlayerMoving = false;


    }
}
