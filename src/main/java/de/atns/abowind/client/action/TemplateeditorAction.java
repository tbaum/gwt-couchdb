package de.atns.abowind.client.action;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import de.atns.abowind.client.Constants;
import de.atns.abowind.client.ViewselectionMenuItem;
import de.atns.abowind.client.WepComponentsTemplate;

/**
 * @author mleesch
 * @since 13.08.2009 16:21:45
 */
public class TemplateeditorAction extends ViewselectionMenuItem {
// ------------------------------ FIELDS ------------------------------

    private static final Constants c = GWT.create(Constants.class);

    private static ViewselectionMenuItem instance = null;

// -------------------------- STATIC METHODS --------------------------

    public static synchronized ViewselectionMenuItem instance() {
        if (instance == null) {
            instance = new TemplateeditorAction();
        }
        return instance;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public TemplateeditorAction() {
        super(c.templates());
    }

// -------------------------- OTHER METHODS --------------------------

    public Widget createContentPanel() {
        return new WepComponentsTemplate();
    }
}