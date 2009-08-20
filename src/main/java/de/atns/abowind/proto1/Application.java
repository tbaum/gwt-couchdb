package de.atns.abowind.proto1;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import de.atns.abowind.proto1.action.InspectionStartAction;
import de.atns.abowind.proto1.action.TemplateeditorAction;
import static de.atns.abowind.proto1.constants.Menu.MENU;
import org.gwt.mosaic.ui.client.Viewport;
import org.gwt.mosaic.ui.client.layout.BoxLayout;
import org.gwt.mosaic.ui.client.layout.BoxLayoutData;
import static org.gwt.mosaic.ui.client.layout.BoxLayoutData.FillStyle.BOTH;
import static org.gwt.mosaic.ui.client.layout.BoxLayoutData.FillStyle.HORIZONTAL;
import org.gwt.mosaic.ui.client.layout.FillLayout;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;


/**
 * @author mleesch
 * @since 13.08.2009 15:36:32
 */
public class Application extends Viewport {
// ------------------------------ FIELDS ------------------------------

    private static Application application = null;

    private final LayoutPanel contentPanel = new LayoutPanel(new FillLayout());
    public static final CouchDB DB = CouchDB.create("abowind");

// -------------------------- STATIC METHODS --------------------------

    public static synchronized Application application() {
        if (application == null) {
            application = new Application();
        }
        return application;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private Application() {
        LayoutPanel layoutPanel = getLayoutPanel();

        layoutPanel.setLayout(new BoxLayout(BoxLayout.Orientation.VERTICAL));

        layoutPanel.add(createMenuPanel(), new BoxLayoutData(HORIZONTAL));
        layoutPanel.add(contentPanel, new BoxLayoutData(BOTH));
    }

    private MenuBar createMenuPanel() {
        MenuBar menuBar = new MenuBar();

        menuBar.addItem(MENU.inspection(), menu(
                InspectionStartAction.instance()
        ));

        menuBar.addItem(MENU.administration(), menu(
                TemplateeditorAction.instance()
        ));

        menuBar.addItem(MENU.statistics(), menu(
        ));

        menuBar.addItem(MENU.help(), menu(
        ));


        return menuBar;
    }

    private MenuBar menu(MenuItem... items) {
        MenuBar menu = new MenuBar(true);
        for (MenuItem item : items) {
            menu.addItem(item);
        }
        return menu;
    }

// -------------------------- OTHER METHODS --------------------------

    public void setContent(Widget widget) {
        contentPanel.clear();
        if (widget == null) {
            contentPanel.add(new HTML());
        } else {
            contentPanel.add(widget);
        }
    }
}
