package com.example.yazlab22t1;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;

public class MyCard extends android.support.v7.widget.CardView {
    Haber Haber;
    public MyCard(@NonNull Context context, Haber gelenHaber) {
        super(context);
        this.Haber = gelenHaber;
        this.setCardBackgroundColor(Color.parseColor("#e0e200"));
        this.setRadius((float)50F);
        this.addView(new MyLayout(getContext(), gelenHaber));



    }

    public com.example.yazlab22t1.Haber getHaber() {
        return Haber;
    }

    public void setHaber(com.example.yazlab22t1.Haber haber) {
        Haber = haber;
    }
}
