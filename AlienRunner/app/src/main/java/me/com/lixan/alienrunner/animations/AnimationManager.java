package me.com.lixan.alienrunner.animations;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by LeakSun on 02/09/2019.
 */

public class AnimationManager {

    private Animation[] animations;
    private int animIndex = 0;

    public AnimationManager(Animation[] animations){

        this.animations = animations;
    }

    public void playAnimation(int animIndex){

        for(int i = 0; animations.length > i; i++){
            if (i == animIndex){
                if (!animations[animIndex].isPlaying()){
                    animations[i].playAnimation();
                }
            }else{
                animations[i].stopAnimation();
            }
        }
        this.animIndex = animIndex;
    }

    public void drawCanvas(Canvas canvas, Rect rect){

        if(animations[animIndex].isPlaying()){
            animations[animIndex].drawFrame(canvas, rect);
        }
    }

    public void updateFrame(){

        if(animations[animIndex].isPlaying()){
            animations[animIndex].updateFrame();
        }
    }


}
