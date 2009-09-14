package de.atns.abowind.proto1;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.widgetideas.table.client.FixedWidthFlexTable;
import com.google.gwt.widgetideas.table.client.overrides.FlexTable;
import org.gwt.mosaic.forms.client.builder.ButtonBarBuilder;
import org.gwt.mosaic.ui.client.layout.BorderLayout;
import static org.gwt.mosaic.ui.client.layout.BorderLayout.Region.*;
import org.gwt.mosaic.ui.client.layout.BorderLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.gwt.mosaic.ui.client.treetable.FastTreeTable;
import org.gwt.mosaic.ui.client.treetable.FastTreeTableItem;
import org.gwt.mosaic.ui.client.treetable.ScrollTreeTable;


/**
 * @author tbaum
 * @since 20.08.2009 15:06:20
 */
public class InspectionPanel extends LayoutPanel {
// ------------------------------ FIELDS ------------------------------

    private final TextBox login = new TextBox();
    private final PasswordTextBox password = new PasswordTextBox();

// --------------------------- CONSTRUCTORS ---------------------------

    public InspectionPanel() {
        super(new BorderLayout());
        add(createInfoPanel(), new BorderLayoutData(NORTH));
        add(createInspectionTree(), new BorderLayoutData(CENTER));
        add(createControlButtons(), new BorderLayoutData(SOUTH));
    }

    private Widget createInfoPanel() {
        return new HTML("<h1>Edit Inspection XYZA </h1>");
    }

    private ScrollTreeTable createInspectionTree() {
        ScrollTreeTable scrollTreeTable;

        FastTreeTable dataTable = new FastTreeTable();
        FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
//        FixedWidthFlexTable footerTable = new FixedWidthFlexTable();

        scrollTreeTable = new ScrollTreeTable(dataTable, headerTable);
        //      scrollTreeTable.setFooterTable(footerTable);

        scrollTreeTable.setCellPadding(3);
        scrollTreeTable.setCellSpacing(0);
        scrollTreeTable.setResizePolicy(ScrollTreeTable.ResizePolicy.UNCONSTRAINED);

        dataTable.resize(0, 5);
        // Level 1 headers
        FlexTable.FlexCellFormatter headerFormatter = headerTable.getFlexCellFormatter();
        headerTable.setHTML(0, 0, "Anlage XYZ");
        headerTable.setHTML(0, 1, "StatusGroup");

        headerFormatter.setColSpan(0, 1, 3);
        //     headerTable.setColumnWidth(1, 20);
        headerTable.setHTML(1, 1, "OK");
        headerTable.setHTML(1, 2, "Fail");
        headerTable.setHTML(1, 3, "Comment");
        scrollTreeTable.setColumnWidth(1, 15);


        createRow1(dataTable, "ROOT 1", createUserObject1(),
                createRow2("ch1 1", createUserObject2("yyyy", "xxx")),
                createRow2("ch1 1", createUserObject2("xxx")),
                createRow2("ch1 1", createUserObject2("xxx"))

        );

        createRow1(dataTable, "ROOT 2", createUserObject1(),
                createRow2("ch2 1", createUserObject2("xxx")),
                createRow2("ch2 1", createUserObject2("xxx")),
                createRow2("ch2 1", createUserObject2("xxx")),
                createRow2("ch2 1", createUserObject2("xxx")),
                createRow2("ch2 1", createUserObject2("xxx")),
                createRow2("ch2 1", createUserObject2("xxx")),
                createRow2("ch2 1", createUserObject2("xxx"))

        );
        return scrollTreeTable;
    }

    private FastTreeTableItem createRow1(FastTreeTable dataTable, final String title, Object[] user, FastTreeTableItem... ch) {
        FastTreeTableItem root11 = new FastTreeTableItem(title);

        //    root11.setUserObject(userObject);
        dataTable.addItem(root11);
        for (FastTreeTableItem fastTreeTableItem : ch) {
            root11.addItem(fastTreeTableItem);
        }
        root11.setUserObject(user);
        root11.setState(true);
        return root11;
    }

    private Object[] createUserObject1(final String... arg) {
        final ListBox comboBox = new ListBox();

        for (String s : arg) {
            comboBox.addItem(s);
        }

        final HorizontalPanel buttons = new HorizontalPanel();
        buttons.add(new Button("T", new ClickHandler() {
            public void onClick(ClickEvent event) {
                Window.alert("show commet-box here");
            }
        }));
        buttons.add(new Button("P", new ClickHandler() {
            public void onClick(ClickEvent event) {
                Window.alert("show picture-box here");
            }
        }));

        return new Object[]{
                null,
                new CheckBox(),
                null,
                null,

        };
    }

    private FastTreeTableItem createRow2(final String title, final Object[] userObject) {
        FastTreeTableItem ch1a = new FastTreeTableItem(title);
        ch1a.setUserObject(userObject);
        return ch1a;
    }

    private Object[] createUserObject2(final String... arg) {
        final ListBox comboBox = new ListBox();

        for (String s : arg) {
            comboBox.addItem(s);
        }

        final HorizontalPanel buttons = new HorizontalPanel();
        buttons.add(new Button("T"));
        buttons.add(new Button("P"));

        return new Object[]{
                null,
                new CheckBox(),
                comboBox,
                buttons,

        };
    }

    private Widget createControlButtons() {
        ButtonBarBuilder bbar = new ButtonBarBuilder();
        bbar.addGlue();
        bbar.addGridded(new Button("Close"));
        return bbar.getPanel();
    }
}