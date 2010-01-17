package de.atns.playground.couchdb.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author mleesch
 * @since 13.08.2009 16:57:15
 */
public abstract class ViewselectionMenuItem extends ExtendedMenuItem {
// ------------------------------ FIELDS ------------------------------

    private Widget contentPanel;

// --------------------------- CONSTRUCTORS ---------------------------

    public ViewselectionMenuItem(String text) {
        super(text);
        setCommand(new Command() {
            public synchronized void execute() {
                if (contentPanel == null) {
                    contentPanel = createContentPanel();
                }

                Application.application().setContent(contentPanel);
            }
        });
    }

    public abstract Widget createContentPanel();
}
