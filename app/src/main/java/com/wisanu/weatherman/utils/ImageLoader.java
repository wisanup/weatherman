package com.wisanu.weatherman.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

public class ImageLoader {

    //in memory cache
    private LruCache<String, Bitmap> mMemoryCache;

    public ImageLoader() {

        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * Display image from supplied URL
     * @param url the URL pointing to the source of the image to be displayed
     * @param imageView the ImageView to show the image
     */
    public void displayImage(String url, ImageView imageView) {
        if (url != null){
            Bitmap image = getBitmapFromMemCache(url);
            if (image != null){
                imageView.setImageBitmap(image);
            }else{
                new LoadBitmapTask(imageView, url).execute(url);
            }
        }
    }

   // The helper class for load then display the image in the background
   private class LoadBitmapTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView mImageView;
        private String mUrl;

        public LoadBitmapTask(ImageView imageView, String url){
            mImageView = imageView;
            mUrl = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // download image
            HttpClient httpClient = new HttpClient();
            return httpClient.getImage(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitMap) {
            if (bitMap != null){
                // store in cache
                addBitmapToMemoryCache(mUrl, bitMap);
                // display the image
                mImageView.setImageBitmap(bitMap);
            }
        }
    }
}
