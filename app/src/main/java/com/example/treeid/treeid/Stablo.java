package com.example.treeid.treeid;

/**
 * Created by Nikola on 26.2.2018..
 */

public class Stablo {
    String ime;
    String lat_ime;
    String porodica;
    String visina;
    String plod;
    String kora_boja;
    String kora_tekstura;
    String krosnja;
    String link;


    public Stablo(String _ime, String _lat_ime, String _porodica, String _visina, String _plod, String _kora_boja, String _kora_tekstura, String _krosnja, String _link){
        ime     = _ime;
        lat_ime = _lat_ime;
        porodica= _porodica;
        visina  = _visina;
        plod    = _plod;
        kora_boja    = _kora_boja;
        kora_tekstura    = _kora_tekstura;
        krosnja = _krosnja;
        link    = _link;

    }

    public String getIme() {
        return ime;
    }

    public String getLat_ime(){
        return lat_ime;
    }

    public String getPorodica() {
        return porodica;
    }

    public String getKora_boja() {
        return kora_boja;
    }

    public String getKora_tekstura() {
        return kora_tekstura;
    }

    public String getPlod() {
        return plod;
    }

    public String getVisina() {
        return visina;
    }

    public String getKrosnja() {
        return krosnja;
    }

    public String getLink() {
        return link;
    }


}
