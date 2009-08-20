package de.atns.abowind.proto1.constants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

/**
 * @author mleesch
 * @since 13.08.2009 16:00:25
 */
public interface Menu extends Messages {
// ------------------------------ FIELDS ------------------------------

    static final Menu MENU = GWT.create(Menu.class);

// -------------------------- OTHER METHODS --------------------------

    String administration();

    String current();

    String help();

    String inspection();

    String start();

    String statistics();

    String templateAdd();

    String templates();
}
