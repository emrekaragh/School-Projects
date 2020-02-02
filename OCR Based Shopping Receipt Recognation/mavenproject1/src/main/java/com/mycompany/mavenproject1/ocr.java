/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import net.sourceforge.tess4j.TesseractException;



/**
 *
 * @author EmreKARA
 */
public class ocr {
    public static void main (String[] args) throws TesseractException{
        girisJFrame giris = new girisJFrame();
        giris.setVisible(true);
        giris.setLocationRelativeTo(null);
        //ProductsjFrame.main(args);
        //TestPatterns.main(args);
    }
}
