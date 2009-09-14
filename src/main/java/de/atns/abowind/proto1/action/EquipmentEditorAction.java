package de.atns.abowind.proto1.action;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import de.atns.abowind.proto1.EquipmentDesignerPanel;
import de.atns.abowind.proto1.ViewselectionMenuItem;
import de.atns.abowind.proto1.constants.Menu;

/**
 * @author mleesch
 * @since 13.08.2009 16:21:45
 */
public class EquipmentEditorAction extends ViewselectionMenuItem {
// ------------------------------ FIELDS ------------------------------

    private static final Menu c = GWT.create(Menu.class);

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
        super(c.equipment());
    }

// -------------------------- OTHER METHODS --------------------------

    public Widget createContentPanel() {
        return new EquipmentDesignerPanel();
    }
}