package com.jdswarehouse.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.jdswarehouse.R;
import com.jdswarehouse.Utilites.GPSTracker;
import com.jdswarehouse.Utilites.Preferences;


public class SplashActivity extends AppCompatActivity {

    int SPLASH_DISPLAY_LENGTH = 3000;
    GPSTracker gpsTracker;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setStatusBar();
        gpsTracker = new GPSTracker(SplashActivity.this);
        if(gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Preferences.getInstance().setLatitude(String.valueOf(latitude));
            Preferences.getInstance().setLongitude(String.valueOf(longitude));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Preferences.getInstance().isLogIn()) {
                    startMain();

                } else {
                    startLogin();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void startLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        this.finish();
    }

    private void startMain() {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        this.finish();
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


}
