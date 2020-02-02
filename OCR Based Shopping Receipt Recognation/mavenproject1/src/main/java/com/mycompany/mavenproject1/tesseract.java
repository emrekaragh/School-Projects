/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.io.File;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author EmreKARA
 */
public class tesseract {

    private static Tesseract getTesseract() {
        Tesseract instance = new Tesseract();
        instance.setDatapath("C:\\tesseract\\tessdata");
        instance.setLanguage("tur");
        //instance.setHocr(true);
        return instance;
    }

    public static String doTesseract(String path) throws TesseractException {
        String folderPath = path;
        Tesseract tesseract = getTesseract();
        File file = new File(folderPath);
        if (file.isFile()) {
            String result = tesseract.doOCR(file);
            //System.out.println(result);
            /* requiredString = result.substring(0, result.indexOf("\n"));
                int str2 = result.indexOf("\n", result.indexOf("\n") + 1);
                requiredString = requiredString + result.substring(result.indexOf("\n"), str2);*/
            //String tarih = 
            //System.out.println("\n\n************************ NEXT FİLE **************************************\n");

            return result;

        }
        return null;
    }

}
