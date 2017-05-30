package com.tvtracker;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.picasso.Transformation;
import com.tvtracker.tools.CenterCropGravityBottomTransformation;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class BitmapTransformationInstrumentedTest {
    private Bitmap transform(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Transformation transformation = new CenterCropGravityBottomTransformation();
        return transformation.transform(bitmap);
    }

    @Test
    public void cropSquareBitmap() throws Exception {
        int width = 200, height = 200;
        Bitmap transformed = transform(width, height);
        assertEquals(width, transformed.getWidth());
        assertEquals(width * 9 / 16, transformed.getHeight());
    }

    @Test
    public void cropVerticalBitmap() throws Exception {
        int width = 200, height = 800;
        Bitmap transformed = transform(width, height);
        assertEquals(width, transformed.getWidth());
        assertEquals(width * 9 / 16, transformed.getHeight());
    }

    @Test
    public void cropHorizontalBitmap() throws Exception {
        int width = 800, height = 200;
        Bitmap transformed = transform(width, height);
        assertEquals(height * 16 / 9, transformed.getWidth());
        assertEquals(height, transformed.getHeight());
    }

    @Test
    public void cropRightBitmap() throws Exception {
        int width = 160, height = 90;
        Bitmap transformed = transform(width, height);
        assertEquals(width, transformed.getWidth());
        assertEquals(height, transformed.getHeight());
    }
}
