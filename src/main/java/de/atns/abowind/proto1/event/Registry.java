package de.atns.abowind.proto1.event;

import java.util.HashSet;
import java.util.Set;

/**
 * @author tbaum
 * @since 11.09.2009 09:35:46
 */
public class Registry {
// ------------------------------ FIELDS ------------------------------

    private final Set<EventConsumer> consumer = new HashSet<EventConsumer>();

// -------------------------- OTHER METHODS --------------------------

    public void deregister(EventConsumer c) {
        consumer.remove(c);
    }

    public synchronized void publishEvent(Event event) {
        for (EventConsumer eventConsumer : consumer) {
            eventConsumer.onEvent(event);
        }
    }

    public void register(EventConsumer c) {
        consumer.add(c);
    }
}


