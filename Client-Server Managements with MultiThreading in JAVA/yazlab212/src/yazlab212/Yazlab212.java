/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yazlab212;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author EmreKARA
 */
public class Yazlab212 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        //ThreadTrails.main(args);
        HashMap<String, myProgressBar> progressBars = new LinkedHashMap<String, myProgressBar>();
        Queue<Integer> jobList = new LinkedList<>();
        MainThread main1 = new MainThread(jobList, "MainThread");
        main1.start();
        SubThreadCreator stc1 = new SubThreadCreator(progressBars,jobList, "SubThreadCreator");
        stc1.start();
        SystemInterface si1 = new SystemInterface(progressBars);
        si1.setVisible(true);
        si1.setLocationRelativeTo(null);
    }
    
}
