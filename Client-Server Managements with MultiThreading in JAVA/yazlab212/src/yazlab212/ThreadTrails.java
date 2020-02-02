/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yazlab212;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EmreKARA
 */
public class ThreadTrails {

    public static void main(String args[]) throws InterruptedException {
        GetRequest t1 = new GetRequest();
        Serve t2 = new Serve();
        t1.start();
        t2.start();

    }

}

class GetRequest extends Thread {

    GetRequest() {
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Serve.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(this.getName() + ": " + "0");
        }
    }
}

class Serve extends Thread {

    Serve() {
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Serve.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(this.getName() + ": " + "1");
        }
    }
}
