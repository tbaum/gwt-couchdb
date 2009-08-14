package de.atns.abowind.client.action;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import de.atns.abowind.client.Constants;
import de.atns.abowind.client.SchedulerTabPanel;
import de.atns.abowind.client.ViewselectionMenuItem;

/**
 * @author mleesch
 * @since 13.08.2009 16:21:45
 */
public class InspectionStartAction extends ViewselectionMenuItem {
// ------------------------------ FIELDS ------------------------------

    private static final Constants c = GWT.create(Constants.class);

    private static ViewselectionMenuItem instance = null;

// -------------------------- STATIC METHODS --------------------------

    public static synchronized ViewselectionMenuItem instance() {
        if (instance == null) {
            instance = new InspectionStartAction();
        }
        return instance;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private InspectionStartAction() {
        super(c.inspection());
    }

// -------------------------- OTHER METHODS --------------------------

    public Widget createContentPanel() {
        return new SchedulerTabPanel();
    }
}
