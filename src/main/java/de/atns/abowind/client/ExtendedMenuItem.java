package de.atns.abowind.client;

import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.Command;

/**
 * @author mleesch
 * @since 13.08.2009 16:13:41
 */
public class ExtendedMenuItem extends MenuItem {

    public ExtendedMenuItem(String text, MenuBar subMenu) {
        super(text, subMenu);
    }

    public ExtendedMenuItem(String text, Command cmd) {
        super(text, cmd);
    }
}
