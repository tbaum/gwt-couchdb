package de.atns.playground.couchdb.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.RootPanel;
import org.gwt.beansbinding.core.client.util.GWTBeansBinding;

import static com.allen_sauer.gwt.log.client.Log.debug;
import static com.google.gwt.user.client.DeferredCommand.addCommand;
import static de.atns.playground.couchdb.client.Application.application;

public class Proto1 implements EntryPoint {
// -------------------------- STATIC METHODS --------------------------

    static {
        GWTBeansBinding.init();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface EntryPoint ---------------------

    public void onModuleLoad() {
        Log.setUncaughtExceptionHandler();
        debug("application startup");
        addCommand(new Command() {
            public void execute() {
                application();
                RootPanel.get("splash").setVisible(false);
            }
        });
    }
}
