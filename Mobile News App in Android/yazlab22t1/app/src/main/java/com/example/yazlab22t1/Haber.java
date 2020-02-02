package com.example.yazlab22t1;

public class Haber {
    int id,goruntulenme,begenme,begenmeme;
    String baslik,icerik,gorsel,tur,tarih;
    public Haber(String baslik, String icerik, String gorsel, String tur, String tarih, int id, int goruntulenme, int begenme, int begenmeme){
        this.baslik = baslik;
        this.icerik = icerik;
        this.gorsel = gorsel;
        this.tur = tur;
        this.tarih = tarih;
        this.id = id;
        this.goruntulenme = goruntulenme;
        this.begenme = begenme;
        this.begenmeme = begenmeme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoruntulenme() {
        return goruntulenme;
    }

    public void setGoruntulenme(int goruntulenme) {
        this.goruntulenme = goruntulenme;
    }

    public int getBegenme() {
        return begenme;
    }

    public void setBegenme() {
        this.begenme += 1;
    }

    public int getBegenmeme() {
        return begenmeme;
    }

    public void setBegenmeme() {
        this.begenmeme += 1;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getGorsel() {
        return gorsel;
    }

    public void setGorsel(String gorsel) {
        this.gorsel = gorsel;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
}
