package com.pakachu.apaydinfitness.adapters.idman;

public class ProgramItem {
    String programAdi;
    int imageId=0;
    int pos;

    public ProgramItem(String programAdi,int pos) {
        this.programAdi = programAdi;
        this.pos = pos;
    }

    public ProgramItem(String programAdi, int resimID,int pos) {
        this.programAdi = programAdi;
        this.imageId = resimID;
        this.pos = pos;
    }
}
