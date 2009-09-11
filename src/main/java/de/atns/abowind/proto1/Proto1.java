package de.atns.abowind.proto1;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import static de.atns.abowind.proto1.Application.application;
import de.atns.abowind.proto1.action.InspectionPoolAction;
import de.atns.abowind.proto1.action.TemplateeditorAction;


public class Proto1 implements EntryPoint {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface EntryPoint ---------------------

    public void onModuleLoad() {
        application().attach();
     //   InspectionPoolAction.instance().execute();
        RootPanel.get("splash").setVisible(false);

        TemplateeditorAction.instance().execute();
        //  InspectionPanel w = new InspectionPanel();
        //    w.showModal();
        //  Application.application().setContent(w);
        //LoginWindow l = new LoginWindow();
        //l.showModal();
    }
}
