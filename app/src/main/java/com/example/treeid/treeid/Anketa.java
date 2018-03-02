package com.example.treeid.treeid;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
    ArrayList<Stablo> stabla = new ArrayList<Stablo>();
    TextView pitanje;
    RadioGroup rg;
    ImageView[] slika;
    RadioButton[] gumb;
    Button btn;
    int porodica;
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

        porodica = Integer.parseInt(intent.getStringExtra("Porodica"));

        DBAdapter database = new DBAdapter(this);
        database.open();
        //this.deleteDatabase("database");
        stabla = database.getStablaFromPorodica(porodica);
        brojStabala = stabla.size();

        database.close();
        LinearLayout layout = (LinearLayout) findViewById(R.id.lin_anketa);


        ActionBar.LayoutParams lparams = new ActionBar.LayoutParams( //tako da širinom zauzima cijeli ekran
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

        pitanje = new TextView(this);
        pitanje.setLayoutParams(lparams);
        pitanje = (TextView)getLayoutInflater().inflate(R.layout.text_template_layout, null);
        layout.addView(pitanje);


        rg = new RadioGroup(this);

        layout.addView(rg);
        napraviPitanje();
        btn = new Button(this);

        btn = (Button)getLayoutInflater().inflate(R.layout.btn_template_layout,null);
        btn.setText(R.string.btnDalje);
        layout.addView(btn);

        btn.setOnClickListener(idiDalje);

        //Toast.makeText(Anketa.this,Integer.toString(brojPitanja),Toast.LENGTH_SHORT).show();
    }
    //zbog okretanja ekrana
    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("rez_visina", rez_visina);
        outState.putString("rez_plod", rez_plod);
        outState.putString("rez_krošnja", rez_krošnja);
        outState.putString("rez_kora_boja", rez_kora_boja);
        outState.putString("rez_kora_tekstura", rez_kora_tekstura);
        outState.putInt("brojPitanja",brojPitanja);
        outState.putInt("porodica",porodica);
        outState.putInt("indeks",indeks);

        super.onSaveInstanceState(outState);
        //Toast.makeText(Anketa.this,Integer.toString(brojPitanja) + " save",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        rez_visina = savedInstanceState.getString("rez_visina");
        rez_plod = savedInstanceState.getString("rez_plod");
        rez_krošnja = savedInstanceState.getString("rez_krošnja");
        rez_kora_boja = savedInstanceState.getString("rez_kora_boja");
        rez_kora_tekstura = savedInstanceState.getString("rez_kora_tekstura");
        brojPitanja = savedInstanceState.getInt("brojPitanja");
        indeks = savedInstanceState.getInt("indeks");

        napraviPitanje();
    }

    private View.OnClickListener idiDalje = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for(int i = 0; i < brojOpcija; ++i)
            {
                if(gumb[i].isChecked() && brojPitanja == 4)
                {
                    napraviRez(brojPitanja);

                    Intent in = new Intent(Anketa.this, Rezultat.class);

                    int[] provjera = new int[brojStabala];
                    for(int j = 0; j < brojStabala; ++j)
                    {
                        // Provjerava za svako stablo koliko se rezultata podudara
                        if(rez_visina.equals(stabla.get(i).getVisina()))                  ++provjera[i];
                        if(rez_plod.equals(stabla.get(i).getPlod()))                      ++provjera[i];
                        if(rez_krošnja.equals(stabla.get(i).getKrosnja()))                ++provjera[i];
                        if(stabla.get(i).getKora_boja().contains(rez_kora_boja))          ++provjera[i];
                        if(stabla.get(i).getKora_tekstura().contains(rez_kora_tekstura))  ++provjera[i];

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
                        btn.setText(R.string.btnRez);
                    }
                    brojPitanja++;
                    napraviPitanje();

                }
            }
        }
    };
    private void napraviPitanje() {
        ArrayList<String> lista = new ArrayList<String>();
        String tekst;

        switch(brojPitanja){
            case 0:
                pitanje.setText(R.string.pitanje1);
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
                pitanje.setText(R.string.pitanje2);
                for(int i = 0; i < brojOpcija; ++i){
                    int id = getResources().getIdentifier(stabla.get(i).getPlod().split("\\.")[0],"drawable",getPackageName());//ovo radi normalno
                    slika[i].setImageResource(id);
                    tekst = "Slika " + String.valueOf(i + 1);
                    gumb[i].setText(tekst);
                }

                break;
            case 2:
                lista.clear();
                pitanje.setText(R.string.pitanje3);
                //ako je sadržana boja mladog stabla i starog, tada su one odvojene zarezom
                for(int i = 0; i < brojStabala; ++i){
                    if(stabla.get(i).getKora_boja().contains(","))
                    {
                        //ako već ne sadrži tu boju ubacujemo u listu
                        if(!lista.contains(stabla.get(i).getKora_boja().split(",")[0]))
                            lista.add(stabla.get(i).getKora_boja().split(",")[0].trim());
                        if(!lista.contains(stabla.get(i).getKora_boja().split(",")[1]))
                            lista.add(stabla.get(i).getKora_boja().split(",")[1].trim());
                    }
                    else
                    {
                        if(!lista.contains(stabla.get(i).getKora_boja()))
                            lista.add(stabla.get(i).getKora_boja().trim());
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
                pitanje.setText(R.string.pitanje4);

                for(int i = 0; i < brojStabala; ++i){
                    if(stabla.get(i).getKora_tekstura().contains(","))
                    {
                        //ako već ne sadrži tu boju ubacujemo u listu
                        if(!lista.contains(stabla.get(i).getKora_tekstura().split(",")[0]))
                            lista.add(stabla.get(i).getKora_tekstura().split(",")[0].trim());
                        if(!lista.contains(stabla.get(i).getKora_tekstura().split(",")[1]))
                            lista.add(stabla.get(i).getKora_tekstura().split(",")[1].trim());
                    }
                    else
                    {
                        if(!lista.contains(stabla.get(i).getKora_tekstura()))
                            lista.add(stabla.get(i).getKora_tekstura().trim());
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
                pitanje.setText(R.string.pitanje5);
                for(int i = 0; i < brojOpcija; ++i) {
                    int id = getResources().getIdentifier(stabla.get(i).getKrosnja().split("\\.")[0], "drawable", getPackageName());
                    tekst = "Slika " + String.valueOf(i + 1);
                    slika[i].setImageResource(id);
                    gumb[i].setText(tekst);
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

            View lineSeparator = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDisplayMetrics().density * 1);
            lineSeparator.setLayoutParams(lp);
            lineSeparator.setBackgroundColor(Color.parseColor("#006400"));

            rg.addView(lineSeparator);
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
            slika[i].setPadding(30,40,0,0);
            gumb[i].setPadding(30,0,0,40);
            gumb[i].setGravity(Gravity.TOP);
            gumb[i].setId(i);

            rg.addView(slika[i]);
            rg.addView(gumb[i]);

            View lineSeparator = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDisplayMetrics().density * 1);
            lineSeparator.setLayoutParams(lp);
            lineSeparator.setBackgroundColor(Color.parseColor("#006400"));

            rg.addView(lineSeparator);

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
