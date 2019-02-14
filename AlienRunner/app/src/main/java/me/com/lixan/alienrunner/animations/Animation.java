package me.com.lixan.alienrunner.animations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by LeakSun on 02/09/2019.
 */

public class Animation {

    private Bitmap[] frames;
    private int frameIndex;

    private float frameTime;
    private long lastFrame;

    private boolean isPlaying = false;

    public Animation(Bitmap[] frames, float animSpeed){

        this.frames = frames;
        this.frameIndex = 0;

        frameTime = animSpeed/frames.length;
        lastFrame = System.currentTimeMillis();
    }


    public boolean isPlaying(){
        return isPlaying;
    }

    public void playAnimation(){
        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    public void stopAnimation(){
        isPlaying = false;
    }

    public void updateFrame(){

        if(!isPlaying){
            return;
        }

        if(System.currentTimeMillis() - lastFrame > frameTime * 1000){
            frameIndex++;

            if(frameIndex >= frames.length){
                frameIndex = 0;
            }

            lastFrame = System.currentTimeMillis();
        }

    }

    public void drawFrame(Canvas canvas, Rect destination){

        if(!isPlaying){
            return;
        }

//        scaleAnimObject(destination);
        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());

    }

//    public void scaleAnimObject(Rect rect){
//        float widthHeightRatio = (float) (frames[frameIndex].getWidth()) / frames[frameIndex].getHeight();
//
//        if (rect.width() > rect.height()){
//            rect.left = rect.right - (int) (rect.height() * widthHeightRatio);
//        }else{
//            rect.top = rect.bottom - (int) (rect.width() * (1/widthHeightRatio));
//        }
//    }

}
