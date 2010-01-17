package de.atns.playground.couchdb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import org.gwt.beansbinding.core.client.util.GWTBeansBinding;

import static de.atns.playground.couchdb.client.Application.application;


public class Proto1 implements EntryPoint {
// -------------------------- STATIC METHODS --------------------------

    static {
        GWTBeansBinding.init();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface EntryPoint ---------------------

    public void onModuleLoad() {
        application();
        RootPanel.get("splash").setVisible(false);
    }
}
