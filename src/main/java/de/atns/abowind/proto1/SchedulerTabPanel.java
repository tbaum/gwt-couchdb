package de.atns.abowind.proto1;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import de.atns.abowind.model.DemoData;
import de.atns.abowind.model.UpcomingInspection;
import de.atns.abowind.proto1.action.InspectionAction;
import org.gwt.mosaic.ui.client.DecoratedTabLayoutPanel;
import org.gwt.mosaic.ui.client.ListBox;
import org.gwt.mosaic.ui.client.layout.BorderLayout;
import static org.gwt.mosaic.ui.client.layout.BorderLayout.Region.CENTER;
import org.gwt.mosaic.ui.client.layout.BorderLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.gwt.mosaic.ui.client.list.DefaultListModel;

/**
 * @author tbaum
 * @since 21.08.2009 07:39:49
 */
public class SchedulerTabPanel extends LayoutPanel {
// --------------------------- CONSTRUCTORS ---------------------------

    public SchedulerTabPanel() {
        super(new BorderLayout());

        DecoratedTabLayoutPanel tabPanel = new DecoratedTabLayoutPanel();
        tabPanel.add(createUpcomingTab(), "Upcoming");
        tabPanel.add(new HTML("<h1>TODO</h1>"), "Active");


        tabPanel.selectTab(0);
        //       tabPanel.ensureDebugId("cwTabPanel");
        add(tabPanel, new BorderLayoutData(CENTER));
    }

    private ListBox<UpcomingInspection> createUpcomingTab() {
        ListBox<UpcomingInspection> upcomingList = new ListBox<UpcomingInspection>();
        upcomingList.setCellRenderer(new ListBox.CellRenderer<UpcomingInspection>() {
            public void renderCell(ListBox<UpcomingInspection> listBox, int row, int column, UpcomingInspection item) {
                switch (column) {
                    case 0:
                        listBox.setWidget(row, column, createRichListBoxCell(item));
                        break;
                    default:
                        throw new RuntimeException("Should not happen");
                }
            }
        });

        final DefaultListModel<UpcomingInspection> mod = new DefaultListModel<UpcomingInspection>();
        DemoData.fillUpcomingList(mod);
        upcomingList.setModel(mod);

        return upcomingList;
    }

    private Widget createRichListBoxCell(UpcomingInspection item) {
        final FlexTable table = new FlexTable();
        final FlexTable.FlexCellFormatter cellFormatter = table.getFlexCellFormatter();


        table.setWidth("100%");
        table.setBorderWidth(0);
        table.setCellPadding(3);
        table.setCellSpacing(0);

        cellFormatter.setRowSpan(0, 2, 3);
        //   cellFormatter.setWidth(0, 0, "32px");
        table.setWidget(0, 2, new Button("Start", new ClickHandler() {
            public void onClick(ClickEvent event) {
                InspectionAction.instance().execute();
            }
        }));


        table.setText(0, 0, "SN:");
        table.setHTML(0, 1, "<b>" + item.getSerialNumber() + "</b>");

        table.setText(1, 0, "Name:");
        table.setText(1, 1, item.getName());

        table.setText(2, 0, "Location:");
        table.setText(2, 1, item.getLocation());

        table.setBorderWidth(1);
        return table;
    }
}
