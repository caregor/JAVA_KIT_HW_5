package ru.gb.hw;

public class DiningPhilosophers {

    private static final int NUM_PHILOSOPHERS = 5;
    private static final Fork[] forks = new Fork[NUM_PHILOSOPHERS];

    public DiningPhilosophers() {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Fork();
        }
    }

    public void start() {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            new Thread(new Philosopher(i)).start();
        }
    }

    private static class Philosopher implements Runnable {
        private final int id;

        public Philosopher(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                think();
                eat();
            }
        }

        private void think() {
            System.out.println("Философ " + id + " размышляет");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void eat() {
            int leftFork = id;
            synchronized (forks[leftFork]) {
                while (!forks[leftFork].isAvailable()) {
                    try {
                        forks[leftFork].wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                forks[leftFork].setAvailable(false);
            }

            int rightFork = (id + 1) % NUM_PHILOSOPHERS;
            synchronized (forks[rightFork]) {
                while (!forks[rightFork].isAvailable()) {
                    try {
                        forks[rightFork].wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                forks[rightFork].setAvailable(false);
            }
            System.out.println("Философ " + id + " takes " + leftFork + " and " + rightFork);

            System.out.println("Философ " + id + " ест");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (forks[leftFork]) {
                forks[leftFork].setAvailable(true);
                forks[leftFork].notify();
            }

            synchronized (forks[rightFork]) {
                forks[rightFork].setAvailable(true);
                forks[rightFork].notify();
            }
        }
    }
}
