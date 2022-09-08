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

public class QRDialog {
    private final Dialog dialog;
    private final TextView tv_baslik;
    public final ImageView iv_dialog;

    public QRDialog(Activity activity) {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.qr_dialog);

        dialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(activity, R.drawable.dialog_background));

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        tv_baslik = dialog.findViewById(R.id.tv_dialogBaslik);
        iv_dialog = dialog.findViewById(R.id.iv_dialog);
    }

    public void setCaption(String baslik) {
        tv_baslik.setText(baslik);
    }

    public void setImage(Bitmap bitmap) {
        iv_dialog.setVisibility(View.VISIBLE);
        iv_dialog.setImageBitmap(bitmap);
    }

    public void show(boolean locked) {
        dialog.setCancelable(!locked);
        dialog.show();
    }

    public void dissmiss() {
        dialog.dismiss();
    }
}
