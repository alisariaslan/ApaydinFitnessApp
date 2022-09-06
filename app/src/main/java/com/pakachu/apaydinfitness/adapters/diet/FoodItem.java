package com.pakachu.apaydinfitness.adapters.diet;

public class FoodItem {
    String foodName;
    int id,status;
    boolean isliquid;
    int gram;
    float protein,carb,fat;
    int id2;

    public FoodItem(String foodName, int id, int status, boolean isliquid, int gram, float protein, float carb, float fat) {
        this.foodName = foodName;
        this.id = id;
        this.status = status;
        this.isliquid = isliquid;
        this.gram = gram;
        this.protein = protein;
        this.carb = carb;
        this.fat = fat;
    }



}
