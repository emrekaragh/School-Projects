/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yazlab212;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EmreKARA
 */
public class SubThreadCreator extends Thread {

    HashMap<String, myProgressBar> progressBars;
    Queue<Integer> jobList;
    CopyOnWriteArrayList<SubThread> tgr;
    int counter;

    public SubThreadCreator(HashMap progressBars, Queue jobList, String name) {
        this.progressBars = progressBars;
        this.jobList = jobList;
        this.setName(name);
        this.tgr = new CopyOnWriteArrayList<SubThread>();
        tgr.add(new SubThread(this.jobList, "SubThread1"));
        tgr.add(new SubThread(this.jobList, "SubThread2"));
        this.counter = 2;
    }

    @Override
    public void run() {
        double percentage;
        progressBars.put("MainThread", new myProgressBar("MainThread"));
        progressBars.get("MainThread").set4152(0);
        progressBars.put("SubThread1", new myProgressBar("SubThread1"));
        progressBars.get("SubThread1").set4152(0);
        progressBars.put("SubThread2", new myProgressBar("SubThread2"));
        progressBars.get("SubThread2").set4152(0);
        tgr.get(0).start();
        tgr.get(1).start();
        while (true) {
            try {
                Thread.sleep(900);
            } catch (InterruptedException ex) {
                Logger.getLogger(SubThreadCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

            Iterator<SubThread> iterator = tgr.iterator();
            while (iterator.hasNext()) {
                percentage = (double) (jobList.size() / 10000.0) * 100;
                progressBars.get("MainThread").set4152((int) percentage);
                SubThread s = iterator.next();
                percentage = (double) (s.subJobList.size() / 5000.0) * 100;
                System.out.println(s.getName() + ": " + (int) percentage + "%");
                try {
                    progressBars.get(s.getName()).set4152((int) percentage);
                } catch (Exception e) {
                    
                }
                if (percentage > 70) {
                    counter++;
                    tgr.add(new SubThread(this.jobList, ("SubThread" + counter)));
                    System.out.println("Pre Adding new Thread! => " + s.getName() + ": " + s.subJobList.size());
                    synchronized (s.subJobList) {
                        for (int i = 0; i < s.subJobList.size(); i++) {
//                            String pre = "  pre: " + s.subJobList.size() + " ," + tgr.get(counter-1).subJobList.size();
                                            

                            //System.out.print("  pre: " + s.subJobList.size() + " ," + tgr.get(counter-1).subJobList.size());
                            try {
                                tgr.get(counter - 1).subJobList.add(s.subJobList.remove());
                            } catch (Exception e) {
                                System.out.println("counter: " + counter);
                                
                                tgr.get(counter-1);
                                System.out.println("i: " + i);
                                System.out.println("  to post: " + s.subJobList.size() + " ," + tgr.get(counter-1).subJobList.size());
                                System.exit(0); 
                            }

                            //System.out.println("  to post: " + s.subJobList.size() + " ," + tgr.get(counter-1).subJobList.size());
                        }
                    }
                    progressBars.put(("SubThread" + counter), new myProgressBar(("SubThread" + counter)));
                    progressBars.get(("SubThread" + counter)).set4152((int) percentage);
                    System.out.println("Add new Thread! => " + s.getName() + ": " + s.subJobList.size() + " , " + tgr.get(counter - 1).getName() + ": " + tgr.get(counter - 1).subJobList.size());
                    tgr.get(counter - 1).start();
                } else if (percentage == 0 && tgr.size() > 2) {
                    System.out.println(s.getName() + " removed." + " size= " + s.subJobList.size());
                    s.interrupt();
                    progressBars.remove(s.getName());
                    //tgr.remove(s);
                }
            }
        }
    }

}
