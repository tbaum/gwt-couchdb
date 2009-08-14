package de.atns.abowind.client.action;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import de.atns.abowind.client.Constants;
import de.atns.abowind.client.ExtendedMenuItem;

/**
 * @author mleesch
 * @since 13.08.2009 16:21:45
 */
public class TemplateeditorAction extends ExtendedMenuItem {

    private static final Constants c = GWT.create(Constants.class);

    private TemplateeditorAction(String title) {
        super(title, new Command() {
            public void execute() {

                Window.alert("huch");

            }
        });
    }

    private static ExtendedMenuItem instance = null;

    public static synchronized ExtendedMenuItem instance() {
        if (instance == null) {
            instance = new TemplateeditorAction(c.templates());
        }
        return instance;
    }
}