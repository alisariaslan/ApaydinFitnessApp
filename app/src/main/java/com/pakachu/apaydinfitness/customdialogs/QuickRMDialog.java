package com.pakachu.apaydinfitness.customdialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.databinding.QuickrmDialogBinding;

public class QuickRMDialog {
    private final Dialog dialog;
    TextView tv_dialogSonuclar;

    public QuickRMDialog(Activity activity) {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.quickrm_dialog);

        TextView tv_dialogBaslik = dialog.findViewById(R.id.tv_dialogBaslik);
        TextView tv_dialogLink = dialog.findViewById(R.id.tv_dialogLink);
        TextView tv_dialogtext2 = dialog.findViewById(R.id.tv_dialogtext2);
        tv_dialogSonuclar = dialog.findViewById(R.id.tv_dialogSonuclar);

        EditText et_yazkg = dialog.findViewById(R.id.et_yazkg);
        EditText et_yazrm = dialog.findViewById(R.id.et_yazrm);

        tv_dialogBaslik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmiss();
            }
        });
        tv_dialogLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.pakachu.quickrm")));
            }
        });

        et_yazkg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    kg = Integer.parseInt(s.toString());
                    tv_dialogtext2.setText(s + " kiloyu gücüm kalmayana dek en fazla kaç sefer kaldırabiliyorum?");
                    Hesapla();
                }

            }
        });

        et_yazrm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    tekrar = Integer.parseInt(s.toString());
                    Hesapla();
                }
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

    private int kg = 0;
    private int tekrar = 0;

    private void Hesapla() {
        if (kg > 0 && tekrar > 0) {
            float sonuc = (kg / (1.0278f - (0.0278f * tekrar)));
            String sonucString = kg+" kiloyu en fazla "+tekrar+" sefer kaldırabiliyorsam Bryizcki 'nin förmülüne göre ben:";
            sonucString+="\n\n1 seferde en fazla "+(int)sonuc+" kilo kaldırabilirim.";
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
