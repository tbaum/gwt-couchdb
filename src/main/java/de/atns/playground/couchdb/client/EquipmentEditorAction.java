package de.atns.playground.couchdb.client;

import com.google.gwt.user.client.ui.Widget;
import de.atns.playground.couchdb.client.EquipmentDesignerPanel;
import de.atns.playground.couchdb.client.ViewselectionMenuItem;

/**
 * @author mleesch
 * @since 13.08.2009 16:21:45
 */
public class EquipmentEditorAction extends ViewselectionMenuItem {
// ------------------------------ FIELDS ------------------------------

    private static ViewselectionMenuItem instance = null;

// -------------------------- STATIC METHODS --------------------------

    public static synchronized ViewselectionMenuItem instance() {
        if (instance == null) {
            instance = new EquipmentEditorAction();
        }
        return instance;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public EquipmentEditorAction() {
        super("Equipment");
    }

// -------------------------- OTHER METHODS --------------------------

    public Widget createContentPanel() {
        return new EquipmentDesignerPanel();
    }
}