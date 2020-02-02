/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author EmreKARA
 */
public class Filtration {

    public Filtration() {

    }

    public ArrayList filter(String text) {
        ArrayList<String> dbValues = new ArrayList<String>();
        dbValues.add("Örnek Ad");
        dbValues.add("1998-05-27");
        dbValues.add("0000");
        dbValues.add("00.00");
        Pattern pTarihler1 = Pattern.compile("\\d{2}\\p{Punct}\\d{2}\\p{Punct}\\d{4}");//dd-mm-yyyy
        Pattern pTarihler2 = Pattern.compile("\\d{4}\\p{Punct}\\d{2}\\p{Punct}\\d{2}");//yyyy-mm-dd
        Pattern pFisler = Pattern.compile("[fF][ıiIİ][sşSŞ$] ?[nN][oOuUüÜ0] ?\\: *\\d*");
        Pattern pToplam = Pattern.compile("TOPLAM? ?[x*X ]\\d*\\p{Punct}\\d*");
        Pattern pUrunler = Pattern.compile("\\n.*[x*X ]\\d*,\\d*");
        //Pattern pUrunler = Pattern.compile(Pattern.quote("\\n") + "(.*?)" + Pattern.quote("[x*X ]\\d*\\p{Punct}\\d*"));

        String isletmeAdi = text.substring(0, text.indexOf("\n"));
        int str2 = text.indexOf("\n", text.indexOf("\n") + 1);
        isletmeAdi = isletmeAdi + text.substring(text.indexOf("\n"), str2);
        isletmeAdi = isletmeAdi.replaceAll("\n", " ");
        dbValues.set(0, isletmeAdi);

        Matcher mTarihler1 = pTarihler1.matcher(text);
        while (mTarihler1.find()) {
            String temp = mTarihler1.group().toString();;
            String date = "";
            date += temp.substring(6, 10) + "-" + temp.substring(3, 5) + "-" + temp.substring(0, 2);
            dbValues.set(1, date);
        }
        Matcher mTarihler2 = pTarihler2.matcher(text);
        while (mTarihler2.find()) {
            String temp = mTarihler2.group().toString();
            String date = temp.substring(0, 4) + "-" + temp.substring(5, 7) + "-" + temp.substring(8, 10);
            dbValues.set(1, date);
        }

        Matcher mFisler = pFisler.matcher(text);
        while (mFisler.find()) {
            String textFisler = mFisler.group();
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(textFisler);
            while (m.find()) {
                dbValues.set(2, m.group().toString());
            }
        }

        Matcher mToplam = pToplam.matcher(text);
        while (mToplam.find()) {
            String textToplam = mToplam.group();
            Pattern p = Pattern.compile("\\d+\\p{Punct}?\\d+?");
            Matcher m = p.matcher(textToplam);
            while (m.find()) {
                dbValues.set(3, m.group().toString());

            }
        }
        Matcher mUurunler = pUrunler.matcher(text);
        {
            while (mUurunler.find()) {
                dbValues.add(mUurunler.group().toString());
            }

        }

        return dbValues;
    }

}
