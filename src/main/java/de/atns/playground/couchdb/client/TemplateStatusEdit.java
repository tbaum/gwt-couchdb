package de.atns.playground.couchdb.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import org.gwt.mosaic.forms.client.layout.FormLayout;
import static org.gwt.mosaic.forms.client.layout.CellConstraints.xy;
import org.gwt.mosaic.ui.client.WindowPanel;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;

/**
 * @author tbaum
 * @since 20.08.2009 15:06:20
 */
public class TemplateStatusEdit extends WindowPanel {
// ------------------------------ FIELDS ------------------------------

    private final TextBox statusName = new TextBox();


// --------------------------- CONSTRUCTORS ---------------------------

    public TemplateStatusEdit() {
        super("Edit template status");
        setResizable(false);
        LayoutPanel formLayout = new LayoutPanel(new FormLayout("p:grow", "p, p, p"));


        LayoutPanel statName = new LayoutPanel(new FormLayout("40px,p", "p"));

        statName.add(new Label("Name"), xy(1, 1));
        statName.add(statusName, xy(2, 1));

        formLayout.add( statName, xy(1,1) );



        LayoutPanel buttonPanel = new LayoutPanel(new FormLayout("left:pref:grow,right:pref:grow", "p"));

        buttonPanel.add(new Button("Save"), xy(1, 1));
        buttonPanel.add(new Button("Cancel", new ClickHandler() {
            public void onClick(final ClickEvent clickEvent) {
             hide(true);
            }
        }), xy(2, 1));

        formLayout.add( buttonPanel, xy(1,3) );



        add(formLayout);
    }
}
