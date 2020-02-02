/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yazlab212;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EmreKARA
 */
public class SubThread extends Thread {

    Queue<Integer> jobList;
    Queue<Integer> subJobList;

    public SubThread(Queue jobList, String name) {
        this.jobList = jobList;
        this.subJobList = new LinkedList<>();
        this.setName(name);
    }

    @Override
    public void run() {
        GetRequestSubThread t1 = new GetRequestSubThread(jobList, subJobList, ("GetRequest" + this.getName()));
        ServeSubThread t2 = new ServeSubThread(subJobList, ("Serve" + this.getName()));
        t1.start();
        t2.start();
    }

}

class GetRequestSubThread extends Thread {

    Queue<Integer> jobList;
    Queue<Integer> subJobList;
    Random rand;

    public GetRequestSubThread(Queue jobList, Queue subJobList, String name) {
        this.jobList = jobList;
        this.subJobList = subJobList;
        this.setName(name);
        this.rand = new Random();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(GetRequestSubThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            int randomValue = rand.nextInt(500) + 1;
            if (subJobList.size() + randomValue > 5000) {
                randomValue = 5000 - subJobList.size();
                //System.out.println("Queue is Full!");
            }
            //System.out.println("PREMain: " + jobList.size() +"  "+this.getName() +": " + subJobList.size() + "  random: " + randomValue);
            synchronized (subJobList) {
                synchronized (jobList) {
                    for (int i = 0; i < randomValue; i++) {
                        try {
                            subJobList.add(jobList.remove());
                        } catch (Exception e) {
                        }
                    }
                }
            }
            //System.out.println("POSTMain: " + jobList.size() +"  " +this.getName()+": " + subJobList.size() + "  random: " + randomValue);

            //System.out.println(this.getName() + ": " + subJobList.size());
        }
    }
}

class ServeSubThread extends Thread {

    Queue<Integer> subJobList;
    Random rand;

    public ServeSubThread(Queue subJobList, String name) {
        this.subJobList = subJobList;
        this.setName(name);
        this.rand = new Random();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServeSubThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            int randomValue = rand.nextInt(50) + 1;
            try {
                for (int i = 0; i < randomValue; i++) {
                    synchronized (subJobList) {
                        subJobList.remove();
                    }
                }
            } catch (Exception e) {
                //System.out.println("Queue is Empty!");
            }
            //System.out.println(this.getName() + ": " + subJobList.size());
        }
    }
}
