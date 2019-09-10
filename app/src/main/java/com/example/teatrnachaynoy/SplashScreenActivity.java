package com.example.teatrnachaynoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler myHandler = new Handler();

        int splashTime = (int) 2000L;
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToMainActivity();
            }
        }, splashTime);

    }

    private void goToMainActivity() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
        finish();
    }
}
