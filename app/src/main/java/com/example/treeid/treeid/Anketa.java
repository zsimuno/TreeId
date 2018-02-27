package com.example.treeid.treeid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Anketa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anketa);


        Intent i = new Intent(this, Anketa.class);
        i.putExtra("Ime", "Makedonski hrast");
        startActivity(i);
    }
}
