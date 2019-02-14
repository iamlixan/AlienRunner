package me.com.lixan.alienrunner.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import me.com.lixan.alienrunner.R;

/**
 * Created by LeakSun on 02/12/2019.
 */

public class SplashScreen extends AppCompatActivity{


    ImageView imageView;
    TextView createdBy, mName;
    Animation animation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        imageView = findViewById(R.id.imageView);
        createdBy = findViewById(R.id.createdByTxt);
        mName = findViewById(R.id.myName);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_anim);
        imageView.setAnimation(animation);
        createdBy.setAnimation(animation);
        mName.setAnimation(animation);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), TitleScreen.class);
//                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in_anim, R.anim.fade_out_anim);
//                startActivity(intent, activityOptions.toBundle());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        }, 5000);

    }

}
