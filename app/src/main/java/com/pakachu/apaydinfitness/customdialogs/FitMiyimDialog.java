package com.pakachu.apaydinfitness.customdialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.pakachu.apaydinfitness.R;

import java.util.Locale;

public class FitMiyimDialog {
    private final Dialog dialog;

    TextView tv_dialogSonuclar;
    RadioButton radioButton10;

    private int yas = 0;
    private int boy = 0;
    private float kilo = 0f;

    public FitMiyimDialog(Activity activity) {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.fitmiyim_dialog);

        TextView tv_dialogBaslik = dialog.findViewById(R.id.tv_dialogBaslik);
        TextView tv_dialogLink = dialog.findViewById(R.id.tv_dialogLink);
        radioButton10 = dialog.findViewById(R.id.radioButton10);
        tv_dialogSonuclar = dialog.findViewById(R.id.tv_dialogSonuclar);

        EditText et_yazyas = dialog.findViewById(R.id.et_yazyas);
        EditText et_yazboy = dialog.findViewById(R.id.et_yazboy);
        EditText et_yazkilo = dialog.findViewById(R.id.et_yazkilo);

        tv_dialogBaslik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmiss();
            }
        });
        tv_dialogLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.pakachu.fitmiyim")));
            }
        });

        et_yazyas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(""))
                {
                    yas= Integer.parseInt(s.toString());
                    Hesapla();
                }
            }
        });

        et_yazboy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(""))
                {
                    boy= Integer.parseInt(s.toString());
                    Hesapla();
                }
            }
        });

        et_yazkilo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(""))
                {
                    kilo= Integer.parseInt(s.toString());
                    Hesapla();
                }
            }
        });

        radioButton10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Hesapla();
            }
        });


        int nightModeFlags = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                dialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(activity, R.drawable.dialog_background_night));
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                dialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(activity, R.drawable.dialog_background));
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    private void Hesapla() {
        if (yas > 0 && boy > 0 && kilo > 0) {
            boolean erkek = radioButton10.isChecked();
            float bmi = kilo / ((boy / 100f) * (boy / 100f));
            float women = 15.8f;
            float man = 16.2f;
            float sex;
            if (erkek)
                sex = man;
            else
                sex = women;
            float fatRatio = (1.20f * bmi) + (0.23f * yas) - sex;
            String sonucString = "Vücut Kitle Endeksiniz: "+(int)bmi;
            sonucString+="\nYağ Oranınız: "+(int)fatRatio+"\n\n";

            if (bmi < 18.5) //AŞIRI ZAYIF
            {
                sonucString+="Üzgünüz. Aşırı zayıfsın!";

            } else if (bmi >= 18.5 && bmi <= 24.9999999) //NORMAL
            {
                sonucString+="Tebrikler. Fit birisiniz. Böyle kalmaya devam edin.";
            } else if (bmi >= 25 && bmi <= 30) //KİLOLU
            {
                sonucString+="Maalesef kilon olması gerekenden fazla. Üzgünüz. Fit değilsin!.";
            } else if (bmi > 30) //OBEZ
            {
                sonucString+="Sen kendini aşmışsın. Maalesef obez kategorisine giriyorsun. Spora ne dersin?";
            }

            tv_dialogSonuclar.setText(sonucString);
        }

    }

    public void show(boolean locked) {
        dialog.setCancelable(!locked);
        dialog.show();
    }

    public void dissmiss() {
        dialog.dismiss();
    }
}
