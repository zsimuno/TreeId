package com.example.treeid.treeid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Rezultat extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat);

        Intent intent = getIntent();
        String Ime = intent.getStringExtra("Ime");


        DBAdapter database = new DBAdapter(this);
        database.open();
        Stablo stablo = database.getStablo(Ime);
        database.close();

        String Lat_naziv = stablo.getLat_ime();
        String Link = stablo.getLink();

        TextView tv1 = (TextView) findViewById(R.id.naziv);
        tv1.setText(Ime + " (" + Lat_naziv + ")");

        TextView tv2 = (TextView) findViewById(R.id.link);
        tv2.setText(Link);
        Linkify.addLinks(tv2, Linkify.ALL);


        ImageView iv = (ImageView) findViewById(R.id.rezSlika);
        final int id = getResources().getIdentifier(stablo.getKrosnja().split("\\.")[0],"drawable",getPackageName());
        iv.setImageResource(id);
        iv.setAdjustViewBounds(true);
        iv.setMaxWidth(300);
        iv.setMaxHeight(300);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = (ImageView) findViewById(R.id.zoom);
                imageView.setImageResource(id);
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }

    //---na ponovni klik uvećane slike zatvori sliku---
    public void ZatvoriSliku(View view) {
        ImageView iv = (ImageView) findViewById(R.id.zoom);
        iv.setVisibility(View.INVISIBLE);

    }

    public void Ponovno(View view){
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
    }
}
