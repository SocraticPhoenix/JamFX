package io.github.socraticphoenix.jamfx;

import io.github.socraticphoenix.occurrence.manager.EventManager;
import io.github.socraticphoenix.occurrence.manager.dispatch.SimpleEventDispatch;
import io.github.socraticphoenix.occurrence.manager.dispatch.SynchronizedEventDispatch;
import io.github.socraticphoenix.occurrence.manager.listener.AggregateEventListeners;
import io.github.socraticphoenix.occurrence.manager.listener.EventListeners;
import io.github.socraticphoenix.occurrence.manager.listener.SynchronizedEventListeners;
import javafx.event.Event;

import java.util.LinkedList;

public class JamEnvironment {
    private EventManager<Event> manager;
    private AggregateEventListeners<Event> listeners;

    public JamEnvironment() {
        this.listeners = new AggregateEventListeners<>(new LinkedList<>());
        this.manager = new EventManager<>(new SynchronizedEventDispatch<>(this, new SimpleEventDispatch<>()), new SynchronizedEventListeners<>(this, this.listeners));
    }

    public EventManager<Event> eventManager() {
        return manager;
    }

    public void add(EventListeners<Event> listeners) {
        synchronized (this) {
            this.listeners.getBacking().add(listeners);
        }
    }

}
