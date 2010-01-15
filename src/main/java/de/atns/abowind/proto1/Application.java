package de.atns.abowind.proto1;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import de.atns.abowind.proto1.EquipmentEditorAction;
import de.atns.abowind.proto1.TemplateEditorAction;
import org.gwt.mosaic.ui.client.layout.BoxLayout;
import org.gwt.mosaic.ui.client.layout.BoxLayoutData;
import org.gwt.mosaic.ui.client.layout.FillLayout;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;

import static com.google.gwt.user.client.DeferredCommand.addCommand;
import static org.gwt.mosaic.ui.client.layout.BoxLayoutData.FillStyle.BOTH;


/**
 * @author tbaum
 * @since 13.08.2009 15:36:32
 */
public class Application {
// ------------------------------ FIELDS ------------------------------

    public static final CouchDB DB = CouchDB.create("abowind");
    private static Application application = null;
    private final LayoutPanel contentPanel = new LayoutPanel(new FillLayout());
    private final TemplateDao templateDao = new TemplateDao();
    private LayoutPanel buttons = new LayoutPanel(new BoxLayout(BoxLayout.Orientation.VERTICAL, BoxLayout.Alignment.END));
    private LayoutPanel mp = new LayoutPanel(new BoxLayout(BoxLayout.Alignment.END));
    private LayoutPanel layoutPanel;

// -------------------------- STATIC METHODS --------------------------

    public static synchronized Application application() {
        if (application == null) {
            application = new Application();
        }
        return application;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private Application() {
        layoutPanel = new LayoutPanel();

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
        //   contentPanel.setStyleName("content");
        layoutPanel.add(mp, new BoxLayoutData(BoxLayoutData.FillStyle.HORIZONTAL));
        layoutPanel.setPadding(0);
        layoutPanel.setWidgetSpacing(0);
        showBp();
        RootPanel.get("content").add(layoutPanel);
        layoutPanel.setWidth("1015px");
        //  layoutPanel.setHeight("400px");

        addCommand(new Command() {
            public void execute() {
                fixLayoutSize();
                //      layoutPanel.setVisible(true);
                //     layoutPanel.layout();
            }
        });


        Window.addResizeHandler(new ResizeHandler() {
            public void onResize(ResizeEvent resizeEvent) {
                fixLayoutSize();
            }
        });
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

        buttons.setPadding(20);
        buttons.setWidgetSpacing(20);
        FlexTable logos = new FlexTable();

        logos.addStyleName("logotable");
        logos.setWidget(0, 0, new Image("logo/apache.jpg"));
        logos.setWidget(0, 1, new Image("logo/java.jpg"));
        logos.setWidget(0, 2, new Image("logo/junit.jpg"));

        logos.setWidget(0, 3, new Image("logo/couchdb.png"));
        logos.setWidget(0, 4, new Image("logo/mosaic.png"));
        logos.setWidget(0, 5, new Image("logo/gwt.png"));

        logos.setWidget(0, 6, new Image("logo/mvn.gif"));

        addCommand(new Command() {
            public void execute() {
                buttons.layout();
            }
        });
        buttons.add(logos);
    }

    private void showMp() {
        mp.setVisible(true);
    }

    private void showBp() {
        mp.setVisible(false);
        setContent(buttons);
    }

    public void setContent(Widget widget) {
        contentPanel.clear();
        if (widget == null) {
            contentPanel.add(new HTML());
        } else {
            contentPanel.add(widget);
        }

        addCommand(new Command() {
            public void execute() {
                layoutPanel.layout();
            }
        });
    }

    private void fixLayoutSize() {
        final int height = Window.getClientHeight();
        final int width = Window.getClientWidth();

        layoutPanel.setHeight(String.valueOf(height - 250) + "px");
        layoutPanel.layout();

        final RootPanel bgi = RootPanel.get("bg_image");
        if (bgi != null) {
            ImageElement bge = bgi.getElement().cast();
            bge.setWidth(width);
            bge.setHeight(height);
        }
    }

// -------------------------- OTHER METHODS --------------------------

    public TemplateDao templateDao() {
        return templateDao;
    }
}
