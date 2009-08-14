package de.atns.abowind.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import static de.atns.abowind.client.Application.application;
import de.atns.abowind.client.action.TemplateeditorAction;


public class Proto1 implements EntryPoint {



// --------------------- Interface EntryPoint ---------------------

    public void onModuleLoad() {


        application().attach();


        TemplateeditorAction.instance().execute();

        RootPanel.get("splash").setVisible(false);


    }
}
