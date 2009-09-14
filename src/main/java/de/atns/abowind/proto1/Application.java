package de.atns.abowind.proto1;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import static com.google.gwt.user.client.DeferredCommand.addCommand;
import com.google.gwt.user.client.ui.*;
import de.atns.abowind.proto1.action.EquipmentEditorAction;
import de.atns.abowind.proto1.action.InspectionPoolAction;
import de.atns.abowind.proto1.action.TemplateEditorAction;
import static de.atns.abowind.proto1.constants.Menu.MENU;
import de.atns.abowind.proto1.event.Registry;
import org.gwt.mosaic.ui.client.Viewport;
import org.gwt.mosaic.ui.client.layout.*;
import static org.gwt.mosaic.ui.client.layout.BoxLayoutData.FillStyle.BOTH;


/**
 * @author mleesch
 * @since 13.08.2009 15:36:32
 */
public class Application extends Viewport {
// ------------------------------ FIELDS ------------------------------

    public static final CouchDB DB = CouchDB.create("abowind");
    private static Application application = null;
    private final Registry registry = new Registry();
    private final LayoutPanel contentPanel = new LayoutPanel(new FillLayout());
    private final TemplateDao templateDao = new TemplateDao();
    private LayoutPanel buttons = new LayoutPanel(new BoxLayout(BoxLayout.Orientation.VERTICAL, BoxLayout.Alignment.CENTER));
    private LayoutPanel mp = new LayoutPanel(new BoxLayout(BoxLayout.Alignment.END));
// -------------------------- STATIC METHODS --------------------------

    public static Registry registry() {
        return application.registry;
    }

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
        final Anchor a = new Anchor("(close)");
       a.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent clickEvent) {
               showBp();

           }
       });
       mp.add(a);
        mp.addStyleName("debug");

//        layoutPanel.add(createMenuPanel(), new BoxLayoutData(HORIZONTAL));
        createBp();
        layoutPanel.add(contentPanel, new BoxLayoutData(BOTH));

        layoutPanel.add(mp, new BoxLayoutData(BoxLayoutData.FillStyle.HORIZONTAL));
       layoutPanel.setPadding(0); layoutPanel.setWidgetSpacing(0);
        showBp();
    }

    private void showBp() {
        mp.setVisible(false);
        setContent(buttons);
    }

    private void createBp() {
        buttons.add(new Button("template creation", new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                showMp();
                TemplateEditorAction.instance().execute();
            }
        }), new BoxLayoutData(250, 40));
        buttons.add(new Button("equipment composition", new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                showMp();
                EquipmentEditorAction.instance().execute();
            }
        }), new BoxLayoutData(250, 40));
        buttons.add(new Button("inspection overview / survey", new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                showMp();
                InspectionPoolAction.instance().execute();
            }
        }), new BoxLayoutData(250, 40));
        buttons.setPadding(20);
        buttons.setWidgetSpacing(20);
    }

    private void showMp() {
        mp.setVisible(true);
    }

    private MenuBar createMenuPanel() {
        MenuBar menuBar = new MenuBar();

        menuBar.addItem(MENU.inspection(), menu(
                InspectionPoolAction.instance()
        ));

        menuBar.addItem(MENU.administration(), menu(
                TemplateEditorAction.instance(),
                EquipmentEditorAction.instance()
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

        addCommand(new Command() {
            public void execute() {
                layout();
            }
        });
    }

    public TemplateDao templateDao() {
        return templateDao;
    }
}
