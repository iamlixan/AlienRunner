package me.com.lixan.alienrunner.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import me.com.lixan.alienrunner.MainActivity;
import me.com.lixan.alienrunner.R;
import me.com.lixan.alienrunner.animations.Animation;
import me.com.lixan.alienrunner.animations.AnimationManager;
import me.com.lixan.alienrunner.interfaces.GameObject;

/**
 * Created by LeakSun on 02/07/2019.
 */

public class PlayerObject implements GameObject {

    private Rect player;
    private int color;

    private Animation playerIdle;
    private Animation playerMoveRight;
    private Animation playerMoveLeft;

    private AnimationManager animationManager;


    public PlayerObject(Rect rect, int color){
        this.player = rect;
        this.color = color;

        Bitmap idlePlayer = BitmapFactory.decodeResource(MainActivity.context.getResources(), R.drawable.alien_blue_idle);
        Bitmap walk1 = BitmapFactory.decodeResource(MainActivity.context.getResources(), R.drawable.alien_blue_walk1);
        Bitmap walk2 = BitmapFactory.decodeResource(MainActivity.context.getResources(), R.drawable.alien_blue_walk2);

        playerIdle = new Animation(new Bitmap[]{idlePlayer}, 2);
        playerMoveRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        Bitmap walkLeft1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), matrix, false);
        Bitmap walkLeft2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), matrix, false);

        playerMoveLeft = new Animation(new Bitmap[]{walkLeft1, walkLeft2}, 0.5f);

        animationManager = new AnimationManager(new Animation[]{playerIdle, playerMoveRight, playerMoveLeft});

    }

    @Override
    public void drawObject(Canvas canvas) {

//        Paint paint = new Paint();
//        paint.setColor(color);
//        canvas.drawRect(player, paint);

        animationManager.drawCanvas(canvas, player);


    }

    @Override
    public void updateFrame() {

        animationManager.updateFrame();
    }

    public void updatePoint(Point point){

        float oldLeft = player.left;


        int left = point.x - (player.width() / 2);
        int top = point.y - (player.height() / 2);
        int right = point.x + (player.width() / 2);
        int bottom = point.y + (player.height() / 2);

        player.set(left, top, right, bottom);


        //state: 0 = idle, 1 = right, 2 = left
        int playerState;

        if(player.left - oldLeft > 5){
            playerState = 1;
        }
        else if(player.left - oldLeft < -5){
            playerState = 2;
        }else{
            playerState = 0;
        }

        animationManager.playAnimation(playerState);
        animationManager.updateFrame();

    }

    public Rect getPlayer(){
        return player;
    }

}
