package de.atns.abowind.proto1;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import static de.atns.abowind.proto1.Application.application;
import de.atns.abowind.proto1.action.TemplateEditorAction;
import org.gwt.beansbinding.core.client.util.GWTBeansBinding;


public class Proto1 implements EntryPoint {
// ------------------------ INTERFACE METHODS ------------------------

    static {
        GWTBeansBinding.init();
    }
// --------------------- Interface EntryPoint ---------------------

    public void onModuleLoad() {
        application().attach();
        //   InspectionPoolAction.instance().execute();
        RootPanel.get("splash").setVisible(false);

        //  EquipmentEditorAction.instance().execute();
     //   TemplateEditorAction.instance().execute();
        //  InspectionPoolAction.instance().execute();

        //  InspectionPanel w = new InspectionPanel();
        //    w.showModal();
        //  Application.application().setContent(w);
        //LoginWindow l = new LoginWindow();
        //l.showModal();
    }
}
