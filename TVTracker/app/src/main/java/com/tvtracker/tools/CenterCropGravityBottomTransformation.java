package com.tvtracker.tools;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class CenterCropGravityBottomTransformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int x, y, width = source.getWidth(), height = source.getHeight();
        if (width * 9 / 16 < height) {
            x = 0;
            height = width * 9 / 16;
            y = source.getHeight() - height;
        } else {
            int old_width = width;
            width = height * 16 / 9;
            x = (old_width - width) / 2;
            y = 0;
        }
        Bitmap result = Bitmap.createBitmap(source, x, y, x + width <= source.getWidth() ? width : source.getWidth() - x, height);
        if (result != source) {
            source.recycle();
        }
        return result;
    }

    @Override
    public String key() {
        return "centerCropGravityBottom";
    }
}
