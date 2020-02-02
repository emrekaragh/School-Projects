package com.example.yazlab22t1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LayoutGorsel extends android.support.v7.widget.AppCompatImageView {
    public LayoutGorsel(Context context,String gorsel) {
        super(context);
        Glide.with(this).load(""+gorsel+"").into(this);
        //this.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground));
        //BitmapDrawable ob = new BitmapDrawable(getResources(), getBitmapFromURL(gorsel));
        //this.setBackgroundDrawable(ob);
    }

}
