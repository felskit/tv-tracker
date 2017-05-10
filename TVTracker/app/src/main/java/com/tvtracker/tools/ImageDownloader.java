package com.tvtracker.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    private ImageView mImageView;
    private Boolean mCrop = false;

    public ImageDownloader(ImageView imageView) {
        this.mImageView = imageView;
    }

    public ImageDownloader(ImageView imageView, Boolean crop) {
        this.mImageView = imageView;
        this.mCrop = crop;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap mIcon = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return mIcon;
    }

    protected void onPostExecute(Bitmap result) {
        if (mCrop) {
            result = centerCropGravityBottom(result);
        }
        mImageView.setImageBitmap(result);
    }

    private Bitmap centerCropGravityBottom(Bitmap source) {
        int x, y, width = source.getWidth(), height = source.getHeight();
        if (width < height) {
            x = 0;
            height = width * 9 / 16;
            y = source.getHeight() - height;
        }
        else {
            x = (width - height) / 2;
            y = 0;
            width = height * 16 / 9;
        }
        Bitmap result = Bitmap.createBitmap(source, x, y, width, height);
        if (result != source) {
            source.recycle();
        }
        return result;
    }
}