package com.pakachu.apaydinfitness.helpers;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class ImageProcess {
    Activity activity;

    public ImageProcess(Activity activity) {
        this.activity = activity;
    }

    public Drawable ColorInvert(Drawable drawable) {
            final float[] NEGATIVE = {
                    -1.0f, 0, 0, 0, 255, // red
                    0, -1.0f, 0, 0, 255, // green
                    0, 0, -1.0f, 0, 255, // blue
                    0, 0, 0, 1.0f, 0  // alpha
            };
            ColorFilter colorFilter = new ColorMatrixColorFilter(NEGATIVE);
            drawable.setColorFilter(colorFilter);
            return drawable;
    }

    public Drawable GetDrawableFromAssets(String assetLocation,boolean invert) {
        InputStream ims = null;
        try {
            ims = activity.getAssets().open(assetLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ims != null) {
            Drawable img = Drawable.createFromStream(ims, null);
            if(invert)
                img= ColorInvert(img);
            img.setBounds(0, 0, 150, 150);
            return img;

        } else return null;
    }

    public Bitmap GetBitmapFromAssets( String filePath) {
        AssetManager assetManager = activity.getAssets();
        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }
        return bitmap;
    }
}
