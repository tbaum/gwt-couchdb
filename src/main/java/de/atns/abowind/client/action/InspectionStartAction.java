package de.atns.abowind.client.action;

import de.atns.abowind.client.ExtendedMenuItem;
import de.atns.abowind.client.Application;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

/**
* @author mleesch
* @since 13.08.2009 16:21:45
*/
public class InspectionStartAction extends ExtendedMenuItem {
    private Application application;

    public InspectionStartAction(Application application) {
        super(application.c.inspection(), new Command() {
            public void execute() {
                Window.alert("huch");
            }
        });
        this.application = application;
    }
}
