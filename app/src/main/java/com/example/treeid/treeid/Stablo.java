package com.example.treeid.treeid;

/**
 * Created by Nikola on 26.2.2018..
 */

public class Stablo {
    String ime;
    String list;
    String visina;
    String plod;
    String kora;
    String krosnja;

    public Stablo(String _ime, String _list, String _visina, String _plod, String _kora, String _krosnja){
        ime = _ime;
        list = _list;
        visina = _visina;
        plod = _plod;
        kora = _kora;
        krosnja = _krosnja;

    }

    public String getIme() {
        return ime;
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
}
