package com.example.as9221.kmeancluster;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

public class Splashscreen extends Activity {
    ImageView iv;
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setIcon(R.drawable.aru_logo_img);
       // setTitle("Developed by Dr. Antesar Shabut");
        StartAnimations();
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        final TextView aru = (TextView) findViewById(R.id.ARU);
        final TextView dev = (TextView) findViewById(R.id.Developer);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);


        splashTread = new Thread() {
            @Override
            public void run() {
                //aru.setVisibility(aru.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
                //dev.setVisibility(dev.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3000) {
                        sleep(100);
                        waited += 100;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //aru.setVisibility(aru.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
                                //dev.setVisibility(dev.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
                            }
                        });

                    }

                    Intent intent = new Intent(Splashscreen.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finishActivity();
                    //Splashscreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();

    }

    public void finishActivity() {
        //finish();
        iv = null;
        Runtime.getRuntime().gc();
        System.gc();
    }


    @Override
    protected void onDestroy() {
        finishActivity();
        super.onDestroy();

    }

}
