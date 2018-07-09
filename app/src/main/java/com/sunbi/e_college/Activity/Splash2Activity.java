package com.sunbi.e_college.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;


import com.sunbi.e_college.R;

import java.util.concurrent.TimeUnit;

public class Splash2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);


        ImageView view = (ImageView)findViewById(R.id.splash);

        Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump);
        view.startAnimation(hyperspaceJump);

//        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//
//        anim.setInterpolator(new LinearInterpolator());
//
//        anim.setDuration(700);
//
//        view.startAnimation(anim);


        Thread timer = new Thread(){
            public void run(){
                try{
                    TimeUnit.MILLISECONDS.sleep(2000);



                }
                catch(Exception ex){}

                Intent i = new Intent(Splash2Activity.this, MainActivity.class);
                startActivity(i);
            }

        };

        timer.start();



    }

}
