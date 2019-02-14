package me.com.lixan.alienrunner.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import me.com.lixan.alienrunner.interfaces.SceneI;

/**
 * Created by LeakSun on 02/08/2019.
 */

public class SceneManager {

    private ArrayList<SceneI> sceneIs;
    public static int ACTIVE_SCENE;

    public SceneManager(){
        ACTIVE_SCENE = 0;
        sceneIs = new ArrayList<>();
        sceneIs.add(new GameplayScene());
    }

    public void updateScene(){
        sceneIs.get(ACTIVE_SCENE).updateFrame();
    }

    public void drawScene(Canvas canvas){
        sceneIs.get(ACTIVE_SCENE).drawObjects(canvas);
    }

    public void handleTouchEvent(MotionEvent motionEvent){
        sceneIs.get(ACTIVE_SCENE).handleTouch(motionEvent);
    }

}
