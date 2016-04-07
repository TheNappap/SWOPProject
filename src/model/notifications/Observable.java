package model.notifications;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private final List<Observer> observers;

    public Observable() {
        this.observers = new ArrayList<Observer>();
    }

    public abstract String getInfo();

    public void attach(Observer observer) {

    }

    public void detach(Observer observer) {

    }
}
