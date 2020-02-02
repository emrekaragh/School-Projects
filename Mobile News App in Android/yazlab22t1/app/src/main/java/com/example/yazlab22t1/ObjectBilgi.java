package com.example.yazlab22t1;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

public class ObjectBilgi extends android.support.v7.widget.AppCompatTextView {
    public ObjectBilgi(Context context) {
        super(context);
        this.setText("Deneme");
        this.setGravity(TEXT_ALIGNMENT_CENTER);
        this.setBackgroundColor(Color.parseColor("#FF2300"));
    }

    public ObjectBilgi(Context context, String bilgi) {
        super(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 30);
        this.setLayoutParams(params);
        this.setText(bilgi);
        this.setBackgroundColor(Color.parseColor("#a6c9eb"));
    }

    public ObjectBilgi(Context context, int i) {
        super(context);
        this.setText("-------");
        this.setTextColor(Color.parseColor("#FFFFFF"));
        this.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }
}
