/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yazlab212;

import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EmreKARA
 */
public class MainThread extends Thread {

    Queue<Integer> jobList;

    public MainThread(Queue jobList, String name) {
        this.jobList = jobList;
        this.setName(name);
    }

    @Override
    public void run() {
        GetRequestMainThread t1 = new GetRequestMainThread(jobList, "GetRequestMainThread1");
        ServeMainThread t2 = new ServeMainThread(jobList, "ServeMainThread1");
        t1.start();
        t2.start();
    }

}

class GetRequestMainThread extends Thread {

    Queue<Integer> jobList;
    Random rand;

    public GetRequestMainThread(Queue jobList, String name) {
        this.jobList = jobList;
        this.setName(name);
        this.rand = new Random();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(GetRequestMainThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            int randomValue = rand.nextInt(1000) + 1;
            synchronized (jobList) {
                if (jobList.size() + randomValue > 10000) {
                    randomValue = 10000 - jobList.size();
                    //System.out.println("Queue is Full!");
                }
                for (int i = 0; i < randomValue; i++) {

                    jobList.add(0);
                }
            }
            //System.out.println(this.getName() + ": " + jobList.size());
        }
    }
}

class ServeMainThread extends Thread {

    Queue<Integer> jobList;
    Random rand;

    public ServeMainThread(Queue jobList, String name) {
        this.jobList = jobList;
        this.setName(name);
        this.rand = new Random();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServeMainThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            int randomValue = rand.nextInt(50) + 1;
            synchronized (jobList) {
                try {
                    for (int i = 0; i < randomValue; i++) {

                        try {
                            jobList.remove();
                        } catch (Exception e) {
                        }
                    }

                } catch (Exception e) {
                    //System.out.println("Queue is Empty!");
                }
            }
            //System.out.println(this.getName() + ": " + jobList.size());
        }
    }
}
