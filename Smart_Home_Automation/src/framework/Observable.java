package framework;

import java.util.*;

/**
 * Implementation of Observer pattern
 * Allows objects to notify observers of state changes
 */
public class Observable {
    private List<Observer> observers = new ArrayList<>();
    private boolean changed = false;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        notifyObservers(null);
    }

    public void notifyObservers(Object arg) {
        if (changed) {
            for (Observer observer : observers) {
                observer.update(this, arg);
            }
            changed = false;
        }
    }

    protected void setChanged() {
        changed = true;
    }

    protected void clearChanged() {
        changed = false;
    }

    public boolean hasChanged() {
        return changed;
    }

    public int countObservers() {
        return observers.size();
    }
}

/**
 * Observer interface
 */
interface Observer {
    void update(Observable observable, Object arg);
}
