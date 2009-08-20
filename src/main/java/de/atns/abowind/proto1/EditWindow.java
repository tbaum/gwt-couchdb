package de.atns.abowind.proto1;

import com.google.gwt.user.client.ui.*;
import org.gwt.mosaic.ui.client.WindowPanel;

/**
 * @author tbaum
 * @since 17.07.2009 14:35:53
 */
public class EditWindow extends WindowPanel {
// ------------------------------ FIELDS ------------------------------

    private ListBox messagesListBox = new ListBox();
    private TextBox prop1TextBox = new TextBox();
    private TextBox prop2TextBox = new TextBox();

// --------------------------- CONSTRUCTORS ---------------------------

    public EditWindow() {
        super("Edit EntityA");
        //model.addListener(this);
        setPixelSize(400, 300);
        center();

        final Grid grid = new Grid(4, 3);

        grid.setWidget(0, 0, new Label("prop1"));
        grid.setWidget(0, 1, prop1TextBox);
        prop1TextBox.setWidth("100%");

        grid.setWidget(1, 0, new Label("prop2"));
        grid.setWidget(1, 1, prop2TextBox);
        prop2TextBox.setWidth("100%");

        grid.setWidget(2, 0, new Label("Items"));
        grid.setWidget(2, 1, messagesListBox);
        messagesListBox.setVisibleItemCount(5);
        messagesListBox.setWidth("100%");
        /*     messagesListBox.addClickHandler(new ClickHandler() {
               public void onClick(ClickEvent event) {
                   int idx = messagesListBox.getSelectedIndex();
                   final String id = messagesListBox.getValue(idx);
                   final EntityB item = model.findItem(Long.parseLong(id));
                   Window.alert(item.toString());
               }
           });
        */

        grid.setWidget(3, 0, new Label("New Item"));
        final TextBox newItemTextBox = new TextBox();

        grid.setWidget(3, 1, newItemTextBox);
        newItemTextBox.setWidth("100%");
        Button addButton = new Button();
        grid.setWidget(3, 2, addButton);
        addButton.setText("Add");
        /*
          addButton.addClickHandler(new ClickHandler() {
              public void onClick(ClickEvent event) {
                  model.add(newItemTextBox.getValue());
              }
          });
        */

        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setWidth("100%");
        horizontalPanel.setSpacing(1);


        Button loadUserAndMessagesButton = new Button();
        horizontalPanel.add(loadUserAndMessagesButton);
        loadUserAndMessagesButton.setText("refresh");
        /*  loadUserAndMessagesButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                model.refresh();
            }
        });
        */

        Button saveUserButton = new Button();
        horizontalPanel.add(saveUserButton);
        saveUserButton.setText("Save");
        /* saveUserButton.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
               model.setProp1(prop1TextBox.getValue());
               model.setProp2(prop2TextBox.getValue());
               model.save();
           }
       });
        */

        final VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(grid);
        verticalPanel.add(horizontalPanel);

        setWidget(verticalPanel);

        //  model.updateDisplay();
    }

// -------------------------- OTHER METHODS --------------------------

    /*  public void setList(Set<EntityB> list) {
        messagesListBox.clear();
        if (list != null) {
            for (EntityB entityB : list) {
                messagesListBox.addItem(entityB.getProp1(), String.valueOf(entityB.getId()));
            }
        }
    }
    */
    public void setProp1(String prop1) {
        prop1TextBox.setText(prop1);
    }

    public void setProp2(String prop2) {
        prop2TextBox.setText(prop2);
    }
}
