package com.app.beyondlottotv.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.app.beyondlottotv.R;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView versionNo = findViewById(R.id.version);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String appVersion = packageInfo.versionName;
            int appVersionCode = packageInfo.versionCode;

            versionNo.setText("Version."+appVersion + " - 20231027");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Delay the execution for the specified duration
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);

                // Finish the splash activity to prevent going back to it
                finish();
            }
        }, SPLASH_DELAY);
    }
}