package com.pakachu.apaydinfitness.adapters.idman_olustur;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ItemItem {
    public String text;
    public int switchInt;
    public int index;
    public int mainIndex;
    public ConstraintLayout constraintLayout;

    public ItemItem(String text,int switchInt,int index,int mainIndex) {
        this.text = text;
        this.switchInt=switchInt;
        this.index= index;
        this.mainIndex = mainIndex;
    }
}
