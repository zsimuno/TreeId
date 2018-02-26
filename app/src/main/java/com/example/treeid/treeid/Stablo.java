package com.example.treeid.treeid;

/**
 * Created by Nikola on 26.2.2018..
 */

public class Stablo {
    String ime;
    String lat_ime;
    String porodica;
    String list;
    String visina;
    String plod;
    String kora;
    String krosnja;
    String link;


    public Stablo(String _ime, String _lat_ime, String _porodica, String _list, String _visina, String _plod, String _kora, String _krosnja, String _link){
        ime     = _ime;
        lat_ime = _lat_ime;
        porodica= _porodica;
        list    = _list;
        visina  = _visina;
        plod    = _plod;
        kora    = _kora;
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

    public String getList() {
        return list;
    }

    public String getKora() {
        return kora;
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
