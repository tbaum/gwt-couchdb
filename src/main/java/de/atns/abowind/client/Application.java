package de.atns.abowind.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import de.atns.abowind.client.action.InspectionStartAction;
import de.atns.abowind.client.action.TemplateeditorAction;
import org.gwt.mosaic.ui.client.Viewport;
import org.gwt.mosaic.ui.client.layout.BoxLayout;
import org.gwt.mosaic.ui.client.layout.FillLayout;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;


/**
 * @author mleesch
 * @since 13.08.2009 15:36:32
 */
public class Application extends Viewport {

    private static Application application = null;

    public static synchronized Application application() {
        if (application == null) {
            application = new Application();
        }
        return application;
    }

    private final Constants c = GWT.create(Constants.class);
    private ExtendedMenuItem inspectionStart = InspectionStartAction.instance();
    private LayoutPanel contentPanel = new LayoutPanel(new FillLayout());

    private Application() {
        LayoutPanel layoutPanel = getLayoutPanel();
        ///layoutPanel
        layoutPanel.setLayout(new BoxLayout(BoxLayout.Orientation.VERTICAL));

        layoutPanel.add(createMenuPanel());
        layoutPanel.add(contentPanel);
    }

    public void setContent(Widget widget) {
        contentPanel.clear();
        if (widget == null) {
            contentPanel.add(new HTML());
        } else {
            contentPanel.add(widget);
        }
    }

    private MenuBar createMenuPanel() {
        MenuBar menuBar = new MenuBar();

        menuBar.addItem(c.inspection(), menu(
                InspectionStartAction.instance()
        ));

        menuBar.addItem(c.administration(), menu(
                TemplateeditorAction.instance()
        ));

        menuBar.addItem(c.statistics(), menu(
        ));

        menuBar.addItem(c.help(), menu(
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

}
