package com.example.yazlab22t1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AyrintilarFragment extends Fragment implements View.OnClickListener {
    Haber haber;
    Button buttonBegenme;
    Button buttonBegenmeme;
    Button buttonGeri;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_ayrintilar, container, false);

        ImageView ivGorsel = (ImageView) RootView.findViewById(R.id.ivGorsel);
        TextView tvTarih = (TextView) RootView.findViewById(R.id.tvTarih);
        TextView tvBaslik = (TextView) RootView.findViewById(R.id.tvBaslik);
        TextView tvIcerik = (TextView) RootView.findViewById(R.id.tvIcerik);
        TextView tvGoruntulenme = (TextView) RootView.findViewById(R.id.tvGoruntulenme);
        TextView tvBegenme = (TextView) RootView.findViewById(R.id.tvBegenme);
        TextView tvBegenmeme = (TextView) RootView.findViewById(R.id.tvBegenmeme);
        buttonBegenme = (Button) RootView.findViewById(R.id.buttonBegenme);
        buttonBegenme.setOnClickListener(this);
        buttonBegenmeme = (Button) RootView.findViewById(R.id.buttonBegenmeme);
        buttonBegenmeme.setOnClickListener(this);
        buttonGeri = (Button) RootView.findViewById(R.id.buttonGeri);
        buttonGeri.setOnClickListener(this);

        Glide.with(this).load(""+this.haber.getGorsel()+"").into(ivGorsel);
        tvTarih.setText(this.haber.getTarih());
        tvBaslik.setText(this.haber.getBaslik());
        tvIcerik.setText("\n\n"+this.haber.getIcerik());
        tvGoruntulenme.setText(""+(this.haber.getGoruntulenme()+1));
        tvBegenme.setText(""+this.haber.getBegenme());
        tvBegenmeme.setText(""+this.haber.getBegenmeme());



        return RootView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonBegenme.getId()){
            new bbg().execute("http://192.168.1.22:8000/begenme/"+this.haber.getId());
            buttonBegenme.setVisibility(View.INVISIBLE);
            buttonBegenmeme.setVisibility(View.INVISIBLE);

        }
        else if (v.getId() == buttonBegenmeme.getId()){
            new bbg().execute("http://192.168.1.22:8000/begenmeme/"+this.haber.getId());
            buttonBegenme.setVisibility(View.INVISIBLE);
            buttonBegenmeme.setVisibility(View.INVISIBLE);
        }
        else if(v.getId() == buttonGeri.getId()){
            getFragmentManager().beginTransaction().replace(R.id.flma,new GirisFragment()).commit();
        }
    }
    public class bbg extends AsyncTask<String,String,String> {
        ArrayList<Haber> Haberler = new ArrayList<Haber>();

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader br = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "hata";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }

}
