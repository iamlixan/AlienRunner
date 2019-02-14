package me.com.lixan.alienrunner.panels;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import me.com.lixan.alienrunner.MainActivity;
import me.com.lixan.alienrunner.MainThread;
import me.com.lixan.alienrunner.scenes.SceneManager;

/**
 * Created by LeakSun on 02/07/2019.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private final String TAG = "GamePanel";

    private MainThread mainThread;
    private SceneManager sceneManager;


    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);
        MainActivity.context = context;

        mainThread = new MainThread(getHolder(), this);
        sceneManager = new SceneManager();

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mainThread = new MainThread(getHolder(), this);
        MainActivity.INIT_TIME = System.currentTimeMillis();
        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        boolean retry = true;

        while (retry){
            try {
                Log.d(TAG, "surfaceDestroyed: WILL PUT ON CODE HERE");
                mainThread.setRunning(false);
                mainThread.start();
            }catch (Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);

        sceneManager.handleTouchEvent(event);

        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        sceneManager.drawScene(canvas);

    }

    public void updateFrames(){
        //TODO

        sceneManager.updateScene();

    }


}
