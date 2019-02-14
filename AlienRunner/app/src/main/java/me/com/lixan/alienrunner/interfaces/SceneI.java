package me.com.lixan.alienrunner.interfaces;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by LeakSun on 02/08/2019.
 */

public interface SceneI {

    public void updateFrame();
    public void drawObjects(Canvas canvas);
    public void terminate();
    public void handleTouch(MotionEvent event);
}
