package com.example.teatrnachaynoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ActorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actors);

        Intent intent = getIntent();
        String hrefTxt = intent.getStringExtra("href");
        Log.i("Log", hrefTxt);
    }
}
