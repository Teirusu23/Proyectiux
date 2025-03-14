package com.example.demo.componentes;

import javafx.scene.control.ProgressBar;

public class Hilo extends Thread{

    private ProgressBar per;
    public Hilo(String nombre,ProgressBar pgb){

        super(nombre);
        this.per = pgb;
    }

    @Override
    public void run() {
        super.run();
        double avance = 0;
        while (avance < 1)
        {
            avance += Math.random()*.01;
            this.per.setProgress(avance);
            try {
                sleep((long) (Math.random()*500));
            } catch (InterruptedException e) {

            }
        }
    }

    //    @Override
//    public void run() {
//        super.run();
//        for (int i = 1; i <= 10 ; i++) {
//            try {
//                sleep((long) (Math.random()*3000));
//                System.out.println(getName()+" llego al Km "+i);
//            } catch (InterruptedException e) {
//
//            }
//
//        }
//    }
}
