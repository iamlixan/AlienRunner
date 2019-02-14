package me.com.lixan.alienrunner.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import me.com.lixan.alienrunner.MainActivity;
import me.com.lixan.alienrunner.R;

/**
 * Created by LeakSun on 02/12/2019.
 */

public class TitleScreen extends AppCompatActivity {

    Button startBTN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_screen);

        startBTN = findViewById(R.id.startBTN);

        startBTN.getBackground().setAlpha(51);
        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

    }
}
