package de.atns.abowind.proto1.event;

import de.atns.abowind.proto1.event.Event;

/**
 * @author tbaum
 * @since 11.09.2009 09:31:36
 */
public interface EventConsumer {
// -------------------------- OTHER METHODS --------------------------

    void onEvent(Event event);
}
