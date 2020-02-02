package com.example.yazlab22t1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GirisFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    LinearLayout linearLayoutHaberler;
    Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_giris, container, false);
        linearLayoutHaberler = (LinearLayout) RootView.findViewById(R.id.linearlayout1);


        spinner = (Spinner) RootView.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return RootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                linearLayoutHaberler.removeAllViews();
                linearLayoutHaberler.addView(spinner);
                new bg().execute("http://192.168.1.22:8000/listele");
                break;
            case 1:
                linearLayoutHaberler.removeAllViews();
                linearLayoutHaberler.addView(spinner);
                new bg().execute("http://192.168.1.22:8000/listele/SonDakika");
                break;
            case 2:
                linearLayoutHaberler.removeAllViews();
                linearLayoutHaberler.addView(spinner);
                new bg().execute("http://192.168.1.22:8000/listele/Spor");
                break;
            case 3:
                linearLayoutHaberler.removeAllViews();
                linearLayoutHaberler.addView(spinner);
                new bg().execute("http://192.168.1.22:8000/listele/Siyaset");
                break;
            case 4:
                linearLayoutHaberler.removeAllViews();
                linearLayoutHaberler.addView(spinner);
                new bg().execute("http://192.168.1.22:8000/listele/Ekonomi");
                break;
            case 5:
                linearLayoutHaberler.removeAllViews();
                linearLayoutHaberler.addView(spinner);
                new bg().execute("http://192.168.1.22:8000/listele/Saglik");
                break;
            case 6:
                linearLayoutHaberler.removeAllViews();
                linearLayoutHaberler.addView(spinner);
                new bg().execute("http://192.168.1.22:8000/listele/Gundem");
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        new bg().execute("http://192.168.1.22:8000/listele");
    }

    public class bg extends AsyncTask<String,String,String> {
        ArrayList<Haber> Haberler = new ArrayList<Haber>();

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader br = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                String satir;
                String dosya = "";
                while ((satir = br.readLine()) != null) {
                    Log.d("satir:", satir);
                    dosya += satir;
                }
                return dosya;
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
            //super.onPostExecute(s);
            try {
                JSONArray JSArray = new JSONArray(s);
                for (int i = 0; i < JSArray.length(); i++) {
                    JSONObject jo = (JSONObject) JSArray.get(i);
                    int id = jo.getInt("id") ;
                    int begenme = jo.getInt("begenme") ;
                    int begenmeme = jo.getInt("begenmeme") ;
                    String tur = jo.getString("tur");
                    String baslik = jo.getString("baslik");
                    String icerik = jo.getString("icerik");
                    String gorsel = jo.getString("gorsel");
                    String tarih = jo.getString("tarih");
                    int goruntulenme=jo.getInt("goruntulenme");
                    Haber h = new Haber(baslik,icerik,gorsel,tur,tarih,id,goruntulenme,begenme,begenmeme);
                    Haberler.add(h);
                    final MyCard haber = new MyCard(getContext(), h);
                    linearLayoutHaberler.addView(haber);
                    haber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AyrintilarFragment ayrinti = new AyrintilarFragment();
                            ayrinti.haber=((MyCard)view).getHaber();
                            new bbg().execute("http://192.168.1.22:8000/goruntulenme/"+ayrinti.haber.getId());
                            getFragmentManager().beginTransaction().replace(R.id.flma,ayrinti).commit();


                        }
                    });
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
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
