package com.example.demo.componentes;

public class Hilo extends Thread{

    public Hilo(String nombre){
        super(nombre);
    }

    @Override
    public void run() {
        super.run();
        for (int i = 1; i <= 10 ; i++) {
            try {
                sleep((long) (Math.random()*3000));
                System.out.println(getName()+" llego al Km "+i);
            } catch (InterruptedException e) {

            }

        }
    }
}
