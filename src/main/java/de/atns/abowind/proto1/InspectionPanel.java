package de.atns.abowind.proto1;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.widgetideas.table.client.FixedWidthFlexTable;
import com.google.gwt.widgetideas.table.client.overrides.FlexTable;
import de.atns.abowind.proto1.constants.ButtonImages;
import org.gwt.mosaic.forms.client.builder.ButtonBarBuilder;
import org.gwt.mosaic.ui.client.ImageButton;
import org.gwt.mosaic.ui.client.layout.BorderLayout;
import static org.gwt.mosaic.ui.client.layout.BorderLayout.Region.*;
import org.gwt.mosaic.ui.client.layout.BorderLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.gwt.mosaic.ui.client.treetable.FastTreeTable;
import org.gwt.mosaic.ui.client.treetable.FastTreeTableItem;
import org.gwt.mosaic.ui.client.treetable.ScrollTreeTable;

import java.util.ArrayList;
import java.util.List;


/**
 * @author tbaum
 * @since 20.08.2009 15:06:20
 */
public class InspectionPanel extends LayoutPanel {
// ------------------------------ FIELDS ------------------------------

    private final TextBox login = new TextBox();
    private final PasswordTextBox password = new PasswordTextBox();
    private FastTreeTable dataTable;
    private FastTreeTableItem currentItem1;
    private FastTreeTableItem currentItem2;
    private FastTreeTableItem currentItem3;
    private List<CheckBox> currentList1;
    private CheckBox currentCP1;
    private List<CheckBox> currentList2;
    private CheckBox currentCP2;
    private List<CheckBox> currentList3;
    private CheckBox currentCP3;

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

        dataTable = new FastTreeTable();
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
        headerTable.setHTML(0, 0, "WTG XYZ");
        headerTable.setHTML(0, 1, "Status");
        HorizontalPanel hp = new HorizontalPanel();
        hp.add(new Label("Inspection\n24.12.1879"));
        final ImageButton hist = new ImageButton(ButtonImages.BUTTON_IMAGES.inspectionMoreHistory());
        hp.add(hist);
        hist.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                Window.alert("TODO: show more history");
            }
        });

        headerTable.setWidget(0, 2, hp);

        headerFormatter.setColSpan(0, 1, 3);
        //     headerTable.setColumnWidth(1, 20);
        headerTable.setHTML(1, 1, "OK");
        headerTable.setHTML(1, 2, "Fail");
        headerTable.setHTML(1, 3, "Comment");
        scrollTreeTable.setColumnWidth(0, 150);
        scrollTreeTable.setColumnWidth(1, 15);
        scrollTreeTable.setColumnWidth(2, 200);
        scrollTreeTable.setColumnWidth(3, 100);
        scrollTreeTable.setColumnWidth(4, 200);
        scrollTreeTable.setColumnWidth(5, 15);
        scrollTreeTable.getDataTable().getColumnFormatter().addStyleName(4, "history");

        row1("Electric", createUserObject1("2 problems"));
        row2("Lights", createUserObject2("1 problem"));
        row3("Light", createUserObject3("", "Missing", "Out of order", "Damaged", "Not fixed"));
        row3("Emergency Light", createUserObject3("", "Missing", "Out of order", "Damaged", "Not fixed"));
        row3("Emergency torch lamp", createUserObject3("Out of order", "Missing", "Out of order", "Damaged", "Not fixed"));
        row3("Motion detector", createUserObject3("", "Missing", "Out of order"));
        row2("Cables", createUserObject2(""));
        row3("Cable routing", createUserObject3("", "Damaged", "Not fixed", "Must be protected"));
        row3("Bus bar", createUserObject3("", "Damaged", "Not fixed"));
        row3("Grounding", createUserObject3("", "Damaged", "Not fixed"));

        row2("Elevator", createUserObject3("Out of service", "Out of service", "Need to be checked", "Lights"));

        row1("Hydraulic", createUserObject1(""));
        row2("Hydraulic unit", createUserObject2(""));
        row3("Leakage", createUserObject3("", "enter value"));
        row3("Oil level", createUserObject3("", "enter value"));
        row3("Hoses", createUserObject3("", "enter value"));

        row2("Permanent grease system", createUserObject2(""));
        row3("Lubrication", createUserObject3("", "too low", "too high"));
        row3("Grease level", createUserObject3("", "too low", "too high"));
        row3("Hoses", createUserObject3("", "enter value"));
        row3("Leakage", createUserObject3("", "enter value"));


        return scrollTreeTable;
    }

    private void row1(final String title, Object[] user) {
        currentItem1 = new FastTreeTableItem(title);

        //    root11.setUserObject(userObject);
        dataTable.addItem(currentItem1);

        currentItem1.setUserObject(user);
        currentItem1.setState(true);
    }

    private void row2(final String title, Object[] user) {
        currentItem2 = new FastTreeTableItem(title);

        //    root11.setUserObject(userObject);
        currentItem1.addItem(currentItem2);

        currentItem2.setUserObject(user);
        currentItem2.setState(true);
    }

    private Object[] createUserObject1(String msg, final String... arg) {
        final ListBox comboBox = new ListBox();

        for (String s : arg) {
            comboBox.addItem(s);
        }

        createInspButtons();

        final List<CheckBox> childs = new ArrayList<CheckBox>();
        final CheckBox checkBox = new CheckBox();
        currentList1 = childs;
        currentCP1 = checkBox;

        checkBox.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                if (checkBox.isChecked())
                    for (CheckBox child : childs) {
                        child.setChecked(true);
                    }
            }
        });
        return new Object[]{
                null,
                checkBox,
                null,
                null, createHistEntry(msg)

        };
    }


    private Object[] createUserObject2(String msg, final String... arg) {
        final ListBox comboBox = new ListBox();

        for (String s : arg) {
            comboBox.addItem(s);
        }

        createInspButtons();
        final List<CheckBox> childs = new ArrayList<CheckBox>();
        final CheckBox checkBox = new CheckBox();
        currentList2 = childs;
        currentCP2 = checkBox;

        final List<CheckBox> cpgr = currentList1;
        final CheckBox par = currentCP1;

        checkBox.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                if (checkBox.isChecked())
                    for (CheckBox child : childs) {
                        child.setChecked(true);
                    }
            }
        });
        return new Object[]{
                null,
                checkBox,
                null,
                null, createHistEntry(msg)

        };
    }

    private void createInspButtons() {
        final HorizontalPanel buttons = createInspButtons_();
    }

    private HorizontalPanel createInspButtons_() {
        final HorizontalPanel buttons = new HorizontalPanel();
        final ImageButton bt1 = new ImageButton(ButtonImages.BUTTON_IMAGES.inspectionPicture());
        bt1.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Window.alert("show commet-box here");
            }
        });
        final ImageButton bt2 = new ImageButton(ButtonImages.BUTTON_IMAGES.inspectionNoRemark());

        bt2.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RemarkWindow r = new RemarkWindow();
                r.showModal();
            }
        });
        buttons.add(bt1);
        buttons.add(bt2);
        return buttons;
    }

    private void row3(final String title, Object[] user) {
        currentItem3 = new FastTreeTableItem(title);

        //    root11.setUserObject(userObject);
        currentItem2.addItem(currentItem3);

        currentItem3.setUserObject(user);
        currentItem3.setState(true);
    }

    private Object[] createUserObject3(String msg, final String... arg) {
        final ListBox comboBox = new ListBox();
        comboBox.addItem("-select-");
        for (String s : arg) {
            comboBox.addItem(s);
        }

        final HorizontalPanel buttons = createInspButtons_();


        final List<CheckBox> childs = new ArrayList<CheckBox>();
        final CheckBox checkBox = new CheckBox();
        currentList3 = childs;
        currentCP3 = checkBox;

        final List<CheckBox> cpgr = currentList2;
        final CheckBox par = currentCP2;

        cpgr.add(checkBox);
        checkBox.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                if (checkBox.isChecked()) comboBox.setSelectedIndex(0);
                updateCheckboxes(checkBox, par, cpgr);
            }
        });

        comboBox.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent changeEvent) {
                if (comboBox.getSelectedIndex() > 0) {
                    checkBox.setChecked(false);
                    updateCheckboxes(checkBox, par, cpgr);
                }

            }
        });


        return new Object[]{
                null,
                checkBox,
                comboBox,
                buttons, createHistEntry(msg)

        };
    }

    private void updateCheckboxes(CheckBox checkBox, CheckBox par, List<CheckBox> cpgr) {
        if (!checkBox.isChecked())
            par.setChecked(false);
        else {
            for (CheckBox box : cpgr) {
                if (!box.isChecked()) return;
            }
            par.setChecked(true);
        }
    }

    private HorizontalPanel createHistEntry(String msg) {
        HorizontalPanel hp = new HorizontalPanel();
        //  hp.addStyleName("history");
        final ImageButton imageButton = new ImageButton(msg.length() > 0 ? ButtonImages.BUTTON_IMAGES.historyError() : ButtonImages.BUTTON_IMAGES.historyOk());
        hp.add(imageButton);
        hp.add(new Label(msg));
        return hp;
    }

    private Widget createControlButtons() {
        ButtonBarBuilder bbar = new ButtonBarBuilder();
        bbar.addGlue();
        bbar.addGridded(new Button("Close"));
        return bbar.getPanel();
    }
}