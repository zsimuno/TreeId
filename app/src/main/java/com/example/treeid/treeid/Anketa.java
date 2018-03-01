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
import android.widget.Toast;

import java.util.ArrayList;


public class Anketa extends BaseActivity {

    String rez_visina, rez_plod, rez_krošnja, rez_kora_boja, rez_kora_tekstura;
    boolean oznacen = false;
    ArrayList<Stablo> stabla = new ArrayList<Stablo>();
    TextView pitanje;
    RadioGroup rg;
    ImageView[] slika;
    RadioButton[] gumb;
    Button btn;
    int indeks;
    int brojStabala, brojOpcija;
    int brojPitanja = 0; //redni broj pitanja


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anketa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        Intent intent = getIntent();

        int porodica = Integer.parseInt(intent.getStringExtra("Porodica"));


        DBAdapter database = new DBAdapter(this);
        database.open();
        //this.deleteDatabase("database");
        stabla = database.getStablaFromPorodica(porodica);
        brojStabala = stabla.size();

        database.close();
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.lin_anketa);


        ActionBar.LayoutParams lparams = new ActionBar.LayoutParams( //tako da širinom zauzima cijeli ekran
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

        pitanje = new TextView(this);
        pitanje.setLayoutParams(lparams);
        layout1.addView(pitanje);


        rg = new RadioGroup(this);

        layout1.addView(rg);
        napraviPitanje();
        btn = new Button(this);
        btn.setText("Dalje");
        layout1.addView(btn);
        btn.setOnClickListener(idiDalje);
    }

    private View.OnClickListener idiDalje = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for(int i = 0; i < brojOpcija; ++i)
            {
                if(gumb[i].isChecked() && brojPitanja == 4)
                {
                    napraviRez(brojPitanja);
                    String test = rez_visina + " " + rez_plod + " " + rez_krošnja + " " + rez_kora_boja + " " + rez_kora_tekstura;
                    Toast.makeText(Anketa.this, test ,Toast.LENGTH_LONG).show();
                    Intent in = new Intent(Anketa.this, Rezultat.class);

                    int[] provjera = new int[brojStabala];
                    for(int j = 0; j < brojStabala; ++j)
                    {
                        // Provjerava za svako stablo koliko se rezultata podudara
                        if(rez_visina   == stabla.get(i).getVisina())                    ++provjera[i];
                        if(rez_plod     == stabla.get(i).getPlod())                      ++provjera[i];
                        if(rez_krošnja  == stabla.get(i).getKrosnja())                   ++provjera[i];
                        if(stabla.get(i).getKora_boja().contains(rez_kora_boja))         ++provjera[i];
                        if(stabla.get(i).getKora_tekstura().contains(rez_kora_tekstura)) ++provjera[i];

                    }

                    // Traži indeks od stabla koje ima najviše podudaranja
                    int najveci = 0;
                    for(int j = 0; j < brojStabala; ++j)
                    {
                        if(provjera[j] > provjera[najveci]) najveci = j;
                    }

                    in.putExtra("Ime", stabla.get(najveci).getIme());
//                    Toast.makeText(Anketa.this, test ,Toast.LENGTH_LONG).show();
                    startActivity(in);

                }
                else if(gumb[i].isChecked())
                {
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
        }
    };
    private void napraviPitanje() {
        ArrayList<String> lista = new ArrayList<String>();
        Toast.makeText(Anketa.this, Integer.toString(brojPitanja) ,Toast.LENGTH_LONG).show();

        switch(brojPitanja){
            case 0:

                pitanje.setText("Izaberite približnu visinu stabla:");
                for(int i = 0; i < brojStabala; ++i)
                    if(!lista.contains(stabla.get(i).getVisina()))
                        lista.add(stabla.get(i).getVisina());

                brojOpcija = lista.size();
                dodajGumbove(brojOpcija);

                for(int i = 0; i < brojOpcija; ++i)
                    gumb[i].setText(lista.get(i));


                break;
            case 1:
                brojOpcija = brojStabala;
                dodajGumboveSlike(brojOpcija);
                pitanje.setText("Izaberite sliku ploda:");
                for(int i = 0; i < brojOpcija; ++i){
                    int id = getResources().getIdentifier(stabla.get(i).getPlod().split("\\.")[0],"drawable",getPackageName());//ovo radi normalno
                    slika[i].setImageResource(id);

                    gumb[i].setText("Slika " + String.valueOf(i + 1));
                }

                break;
            case 2:
                lista.clear();
                pitanje.setText("Kakvu boju kora ima ?");
                //ako je sadržana boja mladog stabla i starog, tada su one odvojene zarezom
                for(int i = 0; i < brojStabala; ++i){
                    if(stabla.get(i).getKora_boja().contains(","))
                    {
                        //ako već ne sadrži tu boju ubacujemo u listu
                        if(!lista.contains(stabla.get(i).getKora_boja().split(",")[0]))
                            lista.add(stabla.get(i).getKora_boja().split(",")[0]);
                        if(!lista.contains(stabla.get(i).getKora_boja().split(",")[1]))
                            lista.add(stabla.get(i).getKora_boja().split(",")[1]);
                    }
                    else
                    {
                        if(!lista.contains(stabla.get(i).getKora_boja()))
                            lista.add(stabla.get(i).getKora_boja());
                    }

                }
                brojOpcija = lista.size();
                dodajGumbove(brojOpcija);

                for(int i = 0; i < brojOpcija; ++i)
                {
                    gumb[i].setText(lista.get(i));
                }
                break;
            case 3:
                lista.clear();
                pitanje.setText("Kakvu teksturu kora ima ?");

                for(int i = 0; i < brojStabala; ++i){
                    if(stabla.get(i).getKora_tekstura().contains(","))
                    {
                        //ako već ne sadrži tu boju ubacujemo u listu
                        if(!lista.contains(stabla.get(i).getKora_tekstura().split(",")[0]))
                            lista.add(stabla.get(i).getKora_tekstura().split(",")[0]);
                        if(!lista.contains(stabla.get(i).getKora_tekstura().split(",")[1]))
                            lista.add(stabla.get(i).getKora_tekstura().split(",")[1]);
                    }
                    else
                    {
                        if(!lista.contains(stabla.get(i).getKora_tekstura()))
                            lista.add(stabla.get(i).getKora_tekstura());
                    }


                }
                brojOpcija = lista.size();
                dodajGumbove(brojOpcija);

                for(int i = 0; i < brojOpcija; ++i)
                {
                    gumb[i].setText(lista.get(i));
                }

                break;

            case 4:
                brojOpcija = brojStabala;
                dodajGumboveSlike(brojStabala);
                pitanje.setText("Izaberite sliku krošnje: ");
                for(int i = 0; i < brojOpcija; ++i) {
                    int id = getResources().getIdentifier(stabla.get(i).getKrosnja().split("\\.")[0], "drawable", getPackageName());

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
                rez_visina = (String)gumb[indeks].getText();
                break;
            case 1:
                rez_plod = stabla.get(indeks).getPlod();
                break;
            case 2:
                rez_kora_boja = (String)gumb[indeks].getText();
                break;
            case 3:
                rez_kora_tekstura = (String)gumb[indeks].getText();
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
    private void dodajGumbove(int n)
    {
        rg.clearCheck();
        rg.removeAllViews();
        gumb = new RadioButton[n];
        for(int i = 0; i < n; ++i)
        {
            gumb[i] = new RadioButton(this);
            gumb[i].setId(i);
            rg.addView(gumb[i]);
        }
        //po default-u označen prvi gumb
        rg.check(0);
    }
    private void dodajGumboveSlike(int n)
    {
        rg.clearCheck();
        rg.removeAllViews();
        int slika_id;
        slika = new ImageView[n];
        gumb = new RadioButton[n];
        for(int i = 0; i < n; ++i)
        {
            gumb[i] = new RadioButton(this);
            slika[i] = new ImageView(this);

            gumb[i].setId(i);

            rg.addView(slika[i]);
            rg.addView(gumb[i]);

            if(brojPitanja == 1)
                slika_id = getResources().getIdentifier(stabla.get(i).getPlod().split("\\.")[0], "drawable", getPackageName());
            else
                slika_id = getResources().getIdentifier(stabla.get(i).getKrosnja().split("\\.")[0], "drawable", getPackageName());

            slika[i].setTooltipText(Integer.toString(slika_id));
            slika[i].setMaxWidth(300);
            slika[i].setMaxHeight(300);
            slika[i].setAdjustViewBounds(true);


            //još listener za povećavanje svake od ovih slika
            slika[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ImageView imageView = (ImageView) findViewById(R.id.zoom);
                    imageView.setImageResource(Integer.parseInt((String)view.getTooltipText()));
                    imageView.setVisibility(View.VISIBLE);
                }
            });

        }
        //po default-u označen prvi gumb
        rg.check(0);
    }
}
