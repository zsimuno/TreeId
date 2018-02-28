package com.example.treeid.treeid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    static String EXTRA_MESSAGE = "porodica";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(this, Anketa.class);
        i.putExtra(EXTRA_MESSAGE, "Bukva");
        startActivity(i);
}
}
