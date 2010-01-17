package de.atns.playground.couchdb.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MenuItem;

/**
 * @author mleesch
 * @since 13.08.2009 16:13:41
 */
public class ExtendedMenuItem extends MenuItem {
// ------------------------------ FIELDS ------------------------------

    private static final int EVENT_MASK = Event.MOUSEEVENTS | Event.ONCLICK | Event.FOCUSEVENTS | Event.KEYEVENTS;

    private final Command dummyCmd = new Command() {
        public void execute() {
        }
    };
    private Command cmd;
    private boolean enabled = true;

// --------------------------- CONSTRUCTORS ---------------------------

    public ExtendedMenuItem(String text) {
        super(text, (Command) null);
    }

// -------------------------- OTHER METHODS --------------------------

    public void execute() {
        if (enabled) {
            cmd.execute();
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            unsinkEvents(EVENT_MASK);
            //      setStyleName("gwt-MenuItem");
            setCommand(cmd);
        } else {
            sinkEvents(EVENT_MASK);
            //    setStyleName("gwt-MenuItem-disabled");
            setCommand(dummyCmd);
        }
    }

    public void setCommand(Command cmd) {
        super.setCommand(cmd);
        this.cmd = cmd;
    }
}
