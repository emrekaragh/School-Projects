package com.example.yazlab22t1;

import android.content.Context;
import android.widget.LinearLayout;


public class MyLayout extends LinearLayout{

    public MyLayout(Context context, Haber gelenHaber){
        super(context);
        String baslik =gelenHaber.getBaslik() ;
        String gorsel = gelenHaber.getGorsel();
        this.setOrientation(VERTICAL);
        this.addView(new ObjectBilgi(getContext(),baslik));
        this.addView(new LayoutGorsel(getContext(), gorsel));

    }

}
