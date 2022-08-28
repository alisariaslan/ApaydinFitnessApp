package com.pakachu.apaydinfitness.games.xox;


import android.util.Log;

public class Patterns {
    public int contains(String str, char p) {
        char[] chars = str.toCharArray();

        for (char c : chars
        ) {
            Log.e("game", "" + c);
        }

        int minusOne = -1;
        if (chars[1 + minusOne] == p) {
            if (chars[5 + minusOne] == p)
                if (chars[9 + minusOne] == p)
                    return 1;
        }

        if (chars[3 + minusOne] == p) {
            if (chars[5 + minusOne] == p)
                if (chars[7 + minusOne] == p)
                    return 1;
        }

        for (int i = 0; i < 3; i++) {
            if (chars[1 + minusOne + i] == p) {
                if (chars[4 + minusOne + i] == p)
                    if (chars[7 + minusOne + i] == p)
                        return 1;
            }
        }

        for (int i = 0; i < 9; i += 3) {
            if (chars[1 + minusOne + i] == p) {
                if (chars[2 + minusOne + i] == p)
                    if (chars[3 + minusOne + i] == p)
                        return 1;
            }
        }

        if(!str.contains("."))
            return 2;

        return 0;
    }

    public int ai_attack(String str, char p) {
        char[] chars = str.toCharArray();
        int minusOne = -1;

        //YATAY CIFT KATMAN

        //XX.
        for (int i = 0; i < 9; i += 3) {
            if (chars[1 + minusOne + i] == p) {
                if (chars[2 + minusOne + i] == p)
                    if (chars[3 + minusOne + i] == '.')
                        return 3 + minusOne + i;
            }
        }

        //.XX
        for (int i = 0; i < 9; i += 3) {
            if (chars[3 + minusOne + i] == p) {
                if (chars[2 + minusOne + i] == p)
                    if (chars[1 + minusOne + i] == '.')
                        return 1 + minusOne + i;
            }
        }

        //X.X
        for (int i = 0; i < 9; i += 3) {
            if (chars[1 + minusOne + i] == p) {
                if (chars[3 + minusOne + i] == p)
                    if (chars[2 + minusOne + i] == '.')
                        return 2 + minusOne + i;
            }
        }

        //DIKEY CIFT KATMAN

            /*
        X
        X
        .
            */
        for (int i = 0; i < 3; i++) {
            if (chars[1 + minusOne + i] == p) {
                if (chars[4 + minusOne + i] == p)
                    if (chars[7 + minusOne + i] == '.')
                        return 7 + minusOne + i;
            }
        }

            /*
        .
        X
        X
            */
        for (int i = 0; i < 3; i++) {
            if (chars[7 + minusOne + i] == p) {
                if (chars[4 + minusOne + i] == p)
                    if (chars[1 + minusOne + i] == '.')
                        return 1 + minusOne + i;
            }
        }

            /*
        X
        .
        X
            */
        for (int i = 0; i < 3; i++) {
            if (chars[7 + minusOne + i] == p) {
                if (chars[1 + minusOne + i] == p)
                    if (chars[4 + minusOne + i] == '.')
                        return 4 + minusOne + i;
            }
        }

        //CAPRAZ CIFT KATMAN

            /*
        X
         .
          X
            */
        if (chars[1 + minusOne] == p) {
            if (chars[9 + minusOne] == p)
                if (chars[5 + minusOne] == '.')
                    return 5 + minusOne;
        }

            /*
          X
         .
        X
             */
        if (chars[3 + minusOne] == p) {
            if (chars[7 + minusOne] == p)
                if (chars[5 + minusOne] == '.')
                    return 5 + minusOne;
        }

            /*
        X
         X
          .
            */
        if (chars[1 + minusOne] == p) {
            if (chars[5 + minusOne] == p)
                if (chars[9 + minusOne] == '.')
                    return 9 + minusOne;
        }

            /*
        .
         X
          X
            */
        if (chars[9 + minusOne] == p) {
            if (chars[5 + minusOne] == p)
                if (chars[1 + minusOne] == '.')
                    return 1 + minusOne;
        }

            /*
          X
         X
        .
            */
        if (chars[3 + minusOne] == p) {
            if (chars[5 + minusOne] == p)
                if (chars[7 + minusOne] == '.')
                    return 7 + minusOne;
        }

            /*
          .
         X
        X
            */
        if (chars[7 + minusOne] == p) {
            if (chars[5 + minusOne] == p)
                if (chars[3 + minusOne] == '.')
                    return 3 + minusOne;
        }

        return -1;
    }

}
