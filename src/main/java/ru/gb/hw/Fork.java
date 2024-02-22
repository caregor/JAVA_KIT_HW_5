package ru.gb.hw;

public class Fork {
    private boolean available = true;

    public synchronized boolean isAvailable() {
        return available;
    }

    public synchronized void setAvailable(boolean available) {
        this.available = available;
    }
}
