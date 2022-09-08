package com.pakachu.apaydinfitness.customdialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.pakachu.apaydinfitness.R;

public class MyCustomDialog {
    private final Dialog dialog;
    private final TextView tv_baslik;
    private final TextView tv_content;
    private final ImageView iv_dialog;
    public Button positive, negative, neutral;

    public MyCustomDialog(Activity activity) {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.custom_dialog);

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

        positive = dialog.findViewById(R.id.btn_positive);
        negative = dialog.findViewById(R.id.btn_negative);
        neutral = dialog.findViewById(R.id.btn_neutral);
        tv_baslik = dialog.findViewById(R.id.tv_dialogBaslik);
        tv_content = dialog.findViewById(R.id.tv_dialogtext1);
        iv_dialog = dialog.findViewById(R.id.iv_dialog);
    }


    public void Toast(String info) {
        tv_content.setText(info);
        show(false);
    }

    public void setCaption(String baslik) {
        tv_baslik.setText(baslik);
    }

    public void setContent(String yazi) {
        tv_content.setText(yazi);
    }

    public void setImage(Bitmap bitmap) {
        iv_dialog.setVisibility(View.VISIBLE);
        iv_dialog.setImageBitmap(bitmap);
    }

    public void setButtons(String positiveString) {
        positive.setText(positiveString);
        positive.setVisibility(View.VISIBLE);
    }

    public void setButtons(String positiveString, String negativeString) {
        setButtons(positiveString);
        negative.setText(negativeString);
        negative.setVisibility(View.VISIBLE);
    }

    public void setButtons(String positiveString, String negativeString, String neutralString) {
        setButtons(positiveString, negativeString);
        neutral.setText(neutralString);
        neutral.setVisibility(View.VISIBLE);
    }

    public void show(boolean locked) {
        dialog.setCancelable(!locked);
        dialog.show();
    }

    public void dissmiss() {
        dialog.dismiss();
    }
}
