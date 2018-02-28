package com.example.treeid.treeid;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Integer[] imageIDs = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7,
            R.drawable.pic8,
            R.drawable.pic9,
            R.drawable.pic10,
            R.drawable.pic11
    };

    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBAdapter db = new DBAdapter(this);
        db.open();

        Toast.makeText(this,"tekst",
                Toast.LENGTH_LONG).show();
        ArrayList<Stablo> c = db.getAllStabla();
        for (Stablo s: c) {
            Toast.makeText(this,
                    "ime: " + s.getIme() + "\n" +
                            "lat ime: " + s.getLat_ime() + "\n" +
                            "porodica:  " + s.getPorodica() + "\n" +
                            "link: " + s.getLink(),
                    Toast.LENGTH_LONG).show();
        }
        db.close();
        // Dodaj radio grupu
        /*rg = new RadioGroup(this);
        for (int i = 0; i <= 10; ++i)
        {
            // Jedan radio button i ID mu je broj porodice od lista
            RadioButton rb = new RadioButton(this);
            rb.setId(i+1);
            rb.setText(Integer.toString(i+1));
            rb.setChecked((i == 0) ? true : false);

            // Slika lista
            ImageView iv = new ImageView(this);
            iv.setTooltipText(Integer.toString(i));
            iv.setImageResource(imageIDs[i]);
            iv.setAdjustViewBounds(true);
            iv.setMaxWidth(300);
            iv.setMaxHeight(300);

            // Listener koji uvećava sliku (točnije stavlja da je R.id.zoom visible i stavlja sliku a R.id.zoom je preko cijelog ekrana)
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView imageView = (ImageView) findViewById(R.id.zoom);
                    imageView.setImageResource(imageIDs[Integer.parseInt(view.getTooltipText().toString())]);
                    imageView.setVisibility(View.VISIBLE);
                }
            });

            // Line separator
            View v = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDisplayMetrics().density * 1);
            v.setLayoutParams(lp);
            v.setBackgroundColor(Color.BLACK);

            // Dodaje sve gore u RadioGroup
            rg.addView(rb);
            rg.addView(iv);
            rg.addView(v);
        }

        // Stavljamo RadioGroup u ScrollView tako se lista listova može scrollati
        ((ScrollView) findViewById(R.id.listovi)).addView(rg);
}

    //---šalje odabrani list tj. šalje porodicu kojoj list pripada---
    public void Posalji(View view) {
        int broj = rg.getCheckedRadioButtonId();
        Intent in = new Intent(this, Anketa.class);
        in.putExtra("Porodica", Integer.toString(broj));
        startActivity(in);
    }

    //---na ponovni klik uvećane slike zatvori sliku---
    public void ZatvoriSliku(View view) {
        ImageView iv = (ImageView) findViewById(R.id.zoom);
        iv.setVisibility(View.INVISIBLE);
*/
    }
}
