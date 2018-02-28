package com.example.treeid.treeid;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class Anketa extends AppCompatActivity {

    String rez_visina, rez_plod, rez_krošnja, rez_kora_boja, rez_kora_tekstura;
    boolean oznacen = false;
    ArrayList<Stablo> stabla = new ArrayList<Stablo>();
    TextView pitanje;
    RadioGroup rg;
    ImageView[] slika;
    RadioButton[] gumb;
    Button btn;
    int indeks;
    int brojOpcija;
    int brojPitanja = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anketa);

        Intent intent = getIntent();

        String porodica = intent.getStringExtra("porodica");


        DBAdapter database = new DBAdapter(this);
        database.open();
        //this.deleteDatabase("database");
        stabla = database.getStablaFromPorodica(porodica);
        brojOpcija = stabla.size();

        database.close();
        strukturirajView();
    }

    private void strukturirajView() {
        LinearLayout layout1 = findViewById(R.id.tekst_vert);


        ActionBar.LayoutParams lparams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        pitanje = new TextView(this);
        pitanje.setLayoutParams(lparams);
        layout1.addView(pitanje);


        rg = new RadioGroup(this);

        layout1.addView(rg);
        //niz potrebnih slika
        slika = new ImageView[brojOpcija];
        //niz potrebnih gumbova
        gumb = new RadioButton[brojOpcija];

        for(int i = 0; i < brojOpcija; ++i)
        {
            gumb[i] = new RadioButton(this);
            gumb[i].setId(i);
            rg.addView(gumb[i]);
            slika[i] = new ImageView(this);
            slika[i].setTooltipText(Integer.toString(i));
            slika[i].setAdjustViewBounds(true);
            slika[i].setMaxWidth(300);
            slika[i].setMaxHeight(300);
            indeks = i;
            slika[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView imageView = (ImageView) findViewById(R.id.zoom);
                    int id = getResources().getIdentifier(stabla.get(indeks).getKrosnja(), "drawable", getPackageName());
                    imageView.setImageResource(id);
                    imageView.setVisibility(View.VISIBLE);
                }
            });
            layout1.addView(slika[i]);
        }
        rg.check(0);
        napraviPitanje();
        btn = new Button(this);
        btn.setText("Dalje");
        layout1.addView(btn);
        btn.setOnClickListener(idiDalje);
    }

    private View.OnClickListener idiDalje = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rg.check(0);
            for(int i = 0; i < brojOpcija; ++i)
            {
                if(gumb[i].isChecked() && brojPitanja == 4){
                    //resetiram broj pitanja
                    brojPitanja = 0;

                }
                else if(gumb[i].isChecked()) {
                    indeks = i;
                    napraviRez(brojPitanja);
                    if(brojPitanja == 3)
                    {
                        btn.setText("Pogledaj rezultat");
                    }
                    brojPitanja++;
                    napraviPitanje();

                }
            }
            rg.check(0);
        }
    };
    private void napraviPitanje() {

        switch(brojPitanja){
            case 0:
                pitanje.setText("Izaberite približnu visinu stabla:");
                for(int i = 0; i < brojOpcija; ++i) {
                    gumb[i].setText(stabla.get(i).getVisina());
                }

                break;
            case 1:
                pitanje.setText("Izaberite sliku ploda:");
                for(int i = 0; i < brojOpcija; ++i){
                    int id = getResources().getIdentifier(stabla.get(i).getPlod(),"drawable",getPackageName());
                    slika[i].setImageResource(id);
                    gumb[i].setText("Slika " + String.valueOf(i + 1));
                }

                break;
            case 2:
                pitanje.setText("Kakvu boju kora ima ?");
                for(int i = 0; i < brojOpcija; ++i){
                    gumb[i].setText(stabla.get(i).getKora_boja());
                    slika[i].setImageDrawable(null);

                }

                break;
            case 3:
                pitanje.setText("Kakvu teksturu kora ima ?");
                for(int i = 0; i < brojOpcija; ++i){
                    gumb[i].setText(stabla.get(i).getKora_tekstura());
                }

                break;

            case 4:
                pitanje.setText("Izaberite sliku krošnje: ");
                for(int i = 0; i < brojOpcija; ++i) {
                    int id = getResources().getIdentifier(stabla.get(i).getKrosnja(), "drawable", getPackageName());
                    slika[i].setImageResource(id);
                    gumb[i].setText("Slika " + String.valueOf(i + 1));
                }
                break;
        }
    }

    private void napraviRez(int n)
    {
        switch(n){
            case 0:
                rez_visina = stabla.get(indeks).getVisina();
                break;
            case 1:
                rez_plod = stabla.get(indeks).getPlod();
                break;
            case 2:
                rez_kora_boja = stabla.get(indeks).getKora_boja();
                break;
            case 3:
                rez_kora_tekstura = stabla.get(indeks).getKora_tekstura();
                break;
            case 4:
                rez_krošnja = stabla.get(indeks).getKrosnja();
                break;
        }
    }
    //---na ponovni klik uvećane slike zatvori sliku---
    public void ZatvoriSliku(View view) {
        ImageView iv = (ImageView) findViewById(R.id.zoom);
        iv.setVisibility(View.INVISIBLE);

    }


}
