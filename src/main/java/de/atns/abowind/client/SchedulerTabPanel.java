package de.atns.abowind.client;



import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;

/**
 * @author mleesch
 * @since 11.08.2009 12:39:49
 */
public class SchedulerTabPanel extends Composite {

    public SchedulerTabPanel() {
// Create a tab panel
        final LayoutPanel layoutPanel = new LayoutPanel();
        DecoratedTabPanel tabPanel = new DecoratedTabPanel();
        tabPanel.setWidth("200px");
        tabPanel.setHeight("200px");
        tabPanel.setAnimationEnabled(true);
        layoutPanel.add(tabPanel);

        // Add a home tab
        String[] tabTitles = {"Inactive", "Active", "Upcoming"};

        HTML inactive = new HTML("any inspection which has been completed should be found here (including a visible sign of the effort of synchronisation)");
        tabPanel.add(inactive, tabTitles[0]);

        HTML active = new HTML("here should be a list of inspections to choose to start");
        tabPanel.add(active, tabTitles[1]);

        // Add a tab with an image
        VerticalPanel vPanel = new VerticalPanel();
        HTML upcoming = new HTML("here should be a list of all undone inspections which are not scheduled for the actual day according to the actual usergroup");
//        vPanel.add(Showcase.images.gwtLogo().createImage());
        tabPanel.add(upcoming, tabTitles[2]);


        // Return the content
        tabPanel.selectTab(0);
        tabPanel.ensureDebugId("cwTabPanel");


        initWidget(layoutPanel);
        layoutPanel.layout();
    }


}
