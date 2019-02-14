package me.com.lixan.alienrunner;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import me.com.lixan.alienrunner.panels.GamePanel;

/**
 * Created by LeakSun on 02/07/2019.
 */

public class MainThread extends Thread {

    private final String TAG = "MainThread";
    public static final int MAX_FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean isRunning;
    public static Canvas canvas = null;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();

        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;

    }

    public void setRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        long startTime, waitTime, totalTime = 0;
        long timeInMillis = 1000/MAX_FPS;
        long targetTime = 1000/MAX_FPS;
        int frameCount = 0;


        while (isRunning){
            startTime = System.nanoTime();


            try{
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    gamePanel.updateFrames();
                    gamePanel.draw(canvas);
                }

            } catch (Exception ex){
                ex.printStackTrace();
            }
            finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            timeInMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeInMillis;

            try{
                if (waitTime > 0){
                    Thread.sleep(waitTime);
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            
            if(frameCount == MAX_FPS){
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                Log.d(TAG, "run: AVERAGE FPS: " + averageFPS);
            }
        }


    }
}
