package de.atns.playground.couchdb.client;

import com.google.gwt.user.client.ui.Widget;
import de.atns.playground.couchdb.client.TemplateDesignerPanel;
import de.atns.playground.couchdb.client.ViewselectionMenuItem;

/**
 * @author mleesch
 * @since 13.08.2009 16:21:45
 */
public class TemplateEditorAction extends ViewselectionMenuItem {
// ------------------------------ FIELDS ------------------------------

    private static ViewselectionMenuItem instance = null;

// -------------------------- STATIC METHODS --------------------------

    public static synchronized ViewselectionMenuItem instance() {
        if (instance == null) {
            instance = new TemplateEditorAction();
        }
        return instance;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public TemplateEditorAction() {
        super("Templates");
    }

// -------------------------- OTHER METHODS --------------------------

    public Widget createContentPanel() {
        return new TemplateDesignerPanel();
    }
}