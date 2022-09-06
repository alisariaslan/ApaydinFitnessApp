package com.pakachu.apaydinfitness.adapters.rekor;

public class RekorItem {
    String baslik;
    String yazi;
    int siralama;
    boolean ismale;

    public RekorItem(String baslik, String yazi, int siralama, boolean ismale) {
        this.baslik = baslik;
        this.yazi = yazi;
        this.siralama = siralama;
        this.ismale = ismale;
    }
}
