/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author EmreKARA
 */
public class TestPatterns {

    public static void main(String[] args) {
        String sTarihler = "04.02.2016 \n"
                + "TARİH: 22/12/2018 \n"
                + "14.05.2018 \n"
                + "TARİH: 06.04.,2010 \n"
                + "TARİH : 06.02.2013 ";
        String sFisler = "FişNo: 0028\n"
                + "FİS NO: 00281\n"
                + "FişNo: 0087\n"
                + "FİŞ NÜ: 0004\n"
                + "Fi$ NO : 0251";
        String sToplam = "TOPLAM 19,88\n"
                + "TOPLAM *11,85\n"
                + "TOPLAM x78,82\n"
                + "TOPLA x*14,8\n"
                + "TOPLAM *32,33";

        Pattern pTarihler = Pattern.compile("\\d{2}\\p{Punct}\\d{2}\\p{Punct}\\d{4}|\\d{4}\\p{Punct}\\d{2}\\p{Punct}\\d{2}");
        Pattern pFisler = Pattern.compile("[fF][ıiIİ][sşSŞ$] ?[nN][oOuUüÜ0] ?\\: *\\d*");
        Pattern pToplam = Pattern.compile("TOPLAM? ?[x*X ]\\d*\\p{Punct}\\d*");

        Matcher mTarihler = pTarihler.matcher(sTarihler);
        Matcher mFisler = pFisler.matcher(sFisler);
        Matcher mToplam = pToplam.matcher(sToplam);

        System.out.println("TARİHLER: ");
        while (mTarihler.find()) {
            System.out.println("Text:" + mTarihler.group());

        }
        System.out.println("\n\n");

        System.out.println("FİŞLER: ");
        while (mFisler.find()) {
            String text = mFisler.group();
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(text);
            while (m.find()) {
                System.out.println("text: " + m.group().toString());

            }

        }
        System.out.println("\n\n");

        System.out.println("TOPLAM: ");
        while (mToplam.find()) {
            String textToplam = mToplam.group();
            Pattern p = Pattern.compile("\\d+\\p{Punct}?\\d+?");
            Matcher m = p.matcher(textToplam);
            while(m.find()){
                System.out.println("text: " + m.group().toString());
            }
        }
        System.out.println("\n\n");
    }
;
}
