package com.pakachu.apaydinfitness.adapters.duyuru;

public class DuyuruItem {
    String text;
    int id;
    boolean okunmus;

    public DuyuruItem(String text,int id,boolean okunmus) {
        this.text = text;
        this.id = id;
        this.okunmus=okunmus;
    }
}
