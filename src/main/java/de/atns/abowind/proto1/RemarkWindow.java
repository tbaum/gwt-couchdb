package de.atns.abowind.proto1;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import org.gwt.mosaic.forms.client.builder.ButtonBarBuilder;
import org.gwt.mosaic.forms.client.builder.DefaultFormBuilder;
import org.gwt.mosaic.forms.client.layout.FormLayout;
import org.gwt.mosaic.ui.client.WindowPanel;
import org.gwt.mosaic.ui.client.layout.BorderLayout;
import static org.gwt.mosaic.ui.client.layout.BorderLayout.Region.CENTER;
import static org.gwt.mosaic.ui.client.layout.BorderLayout.Region.SOUTH;
import org.gwt.mosaic.ui.client.layout.BorderLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;

/**
 * @author tbaum
 * @since 20.08.2009 15:06:20
 */
public class RemarkWindow extends WindowPanel {
// ------------------------------ FIELDS ------------------------------

    private final TextBox login = new TextBox();
    private final PasswordTextBox password = new PasswordTextBox();

// --------------------------- CONSTRUCTORS ---------------------------

    public RemarkWindow() {
        super("Comment");
        setResizable(false);
        LayoutPanel lp = new LayoutPanel(new BorderLayout());
        lp.setWidgetSpacing(30);

        DefaultFormBuilder formBuilder = new DefaultFormBuilder(new FormLayout("p"));
        final TextArea textArea = new TextArea();
        formBuilder.append(textArea);
        textArea.setWidth("300px");   textArea.setHeight("150px");
        //  formBuilder.append("Password", password);

        ButtonBarBuilder bbar = new ButtonBarBuilder();
        bbar.addGlue();
        bbar.addGridded(
                new Button("Save", new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        RemarkWindow.this.hide();
                    }
                }));
        bbar.addGridded(new Button("Cancel", new ClickHandler() {
            public void onClick(ClickEvent event) {
                RemarkWindow.this.hide();
            }
        })
        );

        lp.add(formBuilder.getPanel(), new BorderLayoutData(CENTER));
        final LayoutPanel layoutPanel = bbar.getPanel();
        lp.add(layoutPanel, new BorderLayoutData(SOUTH));
        add(lp);
    }
}