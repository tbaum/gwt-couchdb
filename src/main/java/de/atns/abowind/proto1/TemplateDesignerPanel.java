package de.atns.abowind.proto1;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import de.atns.abowind.model.Template;
import de.atns.abowind.model.TemplateSelection;
import static de.atns.abowind.proto1.constants.ButtonImages.BUTTON_IMAGES;
import de.atns.abowind.proto1.constants.Menu;
import static de.atns.abowind.proto1.constants.TemplateEditor.ACTION;
import de.atns.abowind.proto1.model.DecoratedTemplate;
import org.gwt.mosaic.actions.client.Action;
import org.gwt.mosaic.actions.client.ButtonBindings;
import org.gwt.mosaic.actions.client.CommandAction;
import static org.gwt.mosaic.forms.client.layout.CellConstraints.xy;
import static org.gwt.mosaic.forms.client.layout.CellConstraints.xyw;
import org.gwt.mosaic.forms.client.layout.FormLayout;
import org.gwt.mosaic.ui.client.ComboBox;
import org.gwt.mosaic.ui.client.InfoPanel;
import org.gwt.mosaic.ui.client.ListBox;
import org.gwt.mosaic.ui.client.StackLayoutPanel;
import org.gwt.mosaic.ui.client.layout.BorderLayout;
import static org.gwt.mosaic.ui.client.layout.BorderLayout.Region.*;
import org.gwt.mosaic.ui.client.layout.BorderLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.gwt.mosaic.ui.client.list.DefaultComboBoxModel;
import org.gwt.mosaic.ui.client.list.ListDataEvent;
import org.gwt.mosaic.ui.client.list.ListDataListener;
import org.gwt.mosaic.ui.client.util.ButtonHelper;
import static org.gwt.mosaic.ui.client.util.ButtonHelper.ButtonLabelType.TEXT_ON_RIGHT;
import static org.gwt.mosaic.ui.client.util.ButtonHelper.createButtonLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tbaum
 * @since 12.08.2009 15:04:14
 */
public class TemplateDesignerPanel extends LayoutPanel {
// ------------------------------ FIELDS ------------------------------

    private final Tree currentTemplateTree = new Tree();

    private final CommandAction createNodeActionInside = createCommand(new Command() {
        public void execute() {
            if (selectedItem != null) {
                createTemplate(selectedItem, newStructureName.getText());
            }
        }
    }, BUTTON_IMAGES.insertInside());

    private final CommandAction createNodeActionAfter = createCommand(new Command() {
        public void execute() {
            if (selectedItem != null && selectedItem.getParentItem() != null) {
                createTemplate(selectedItem.getParentItem(), newStructureName.getText());
            }
        }
    }, BUTTON_IMAGES.insertAfter());
    private final TextBox newStructureName = new TextBox();
    private final TextBox newTemplateName = new TextBox();
    private final TemplateDao templateDao = Application.application().templateDao();

    private TreeItem selectedItem;
    private DefaultComboBoxModel<TemplateSelection> templateSelection;
    private TemplateSelection currentSelection = null;

// --------------------------- CONSTRUCTORS ---------------------------

    public TemplateDesignerPanel() {
        super(new BorderLayout());

        try {
            add(createControlPanel(), new BorderLayoutData(NORTH, true));
            add(createToolPanel(), new BorderLayoutData(EAST, true));
            add(createTreeTable(), new BorderLayoutData(CENTER));

            updateTemplateList1();
            //  templateSelection.setSelectedItem(templateSelection.get(0));
            //   selectItem();
            final TemplateSelection templateSelection1 = templateSelection.size() > 0 ? templateSelection.get(0) : null;
            selectItem(templateSelection1);
        } catch (Exception e) {
            Window.alert(e.getMessage());
        }
    }

    private Widget createControlPanel() {
        //    scrollTreeTable.setResizePolicy(UNCONSTRAINED);

        final ComboBox<TemplateSelection> templateSelector = new ComboBox<TemplateSelection>();
        templateSelection = (DefaultComboBoxModel<TemplateSelection>) templateSelector.getModel();

        templateSelector.setCellRenderer(new ComboBox.ComboBoxCellRenderer<TemplateSelection>() {
            public String getDisplayText(TemplateSelection templateSelection) {
                return templateSelection.getName();
            }

            public void renderCell(ListBox<TemplateSelection> templateSelectionListBox, int i, int i1, TemplateSelection templateSelection) {
                templateSelectionListBox.setText(i, i1, templateSelection.getName());
            }
        });


        templateSelection.addListDataListener(new ListDataListener() {
            public void contentsChanged(ListDataEvent listDataEvent) {
                TemplateSelection selection = templateSelection.getSelectedItem();
                if (selection != currentSelection) {
                    selectItem(selection);
                }
            }

            public void intervalAdded(ListDataEvent listDataEvent) {
            }

            public void intervalRemoved(ListDataEvent listDataEvent) {
            }
        });

        LayoutPanel controls = new LayoutPanel(new FormLayout("130px, p,3dlu, right:pref:grow,3dlu,p, 3dlu", "p"));
        controls.add(new Label("Choose Template"), xy(1, 1));
        controls.add(templateSelector, xy(2, 1));
        final Button addButton = new Button(
                createButtonLabel(
                        BUTTON_IMAGES.addTemplate(),
                        Menu.MENU.templateAdd(),
                        TEXT_ON_RIGHT)
        );


        controls.add(newTemplateName, xy(4, 1));

        addButton.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent clickEvent) {
                try {
                    Template template = createTemplate(null, newTemplateName.getText());
                    updateTemplateList1();
                    selectItem(template.getId());
                }
                catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        });


        controls.add(addButton, xy(6, 1));

        /*

        final Element e = templateSelector.getElement();
        DOM.sinkEvents(e, Event.ONMOUSEMOVE | Event.ONMOUSEOUT | Event.ONMOUSEOVER);
        DOM.setEventListener(e, new EventListener() {
            public void onBrowserEvent(final Event event) {
                System.err.println(event.toString() + " " + event.getType());
            }
        });
        */

        return controls;
    }

    private Template createTemplate(TreeItem parent, final String name) {
        try {
            final List<String> path =
                    parent == null || !(parent.getUserObject() instanceof Template)
                            ? new ArrayList<String>()
                            : ((Template) parent.getUserObject()).getPath();

            Template nt = Template.create(Application.DB.newUuid(), name, path);
            Application.DB.save(nt);

            System.err.println("saved nt " + nt.getId());

            if (parent != null) {
                final TreeItem treeItem = createTreeItem(nt);
                parent.addItem(treeItem);
                parent.setState(true);
                selectTreeItem(treeItem);
            }
            return nt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private TreeItem createTreeItem(final Template template) {
        final Widget button = new Label(template.getName());
        return new TreeItem(button) {
            @Override public Object getUserObject() {
                return template;
            }
        };
    }

    private void selectItem(String id) {
        for (TemplateSelection selection : templateSelection) {
            if (selection.getId().equals(id)) {
                selectItem(selection);
                return;
            }
        }
    }

    private Widget createToolPanel() {
        StackLayoutPanel stackPanel = new StackLayoutPanel();
        stackPanel.setWidth("200px");


        //lp1.setHeight("40px");


        //      DropController dropController = new AbsolutePositionDropController(lp1);
//        dragController.registerDropController(dropController);
        // dragController.makeDraggable(scrollTreeTable);
        stackPanel.add(createStructureToolPanel(), ACTION.structure());
        stackPanel.add(createQuestionToplPanel(), ACTION.question());

        stackPanel.showStack(0);

        return stackPanel;
    }

    private LayoutPanel createStructureToolPanel() {
        LayoutPanel lp1 = new LayoutPanel(new FormLayout("p:grow, 5px, 32px, 5px, 32px", "p, 5px, p"));


        final ButtonBindings btnActionInside = new ButtonBindings(createNodeActionInside);
        btnActionInside.setLabelType(ButtonHelper.ButtonLabelType.TEXT_ON_TOP);
        btnActionInside.bind();

        final ButtonBindings btnActionAfter = new ButtonBindings(createNodeActionAfter);
        btnActionAfter.setLabelType(ButtonHelper.ButtonLabelType.TEXT_ON_TOP);
        btnActionAfter.bind();

        lp1.add(new Label("Name"), xyw(1, 1, 5));
        lp1.add(newStructureName, xy(1, 3));
        lp1.add(btnActionInside.getTarget(), xy(3, 3));
        lp1.add(btnActionAfter.getTarget(), xy(5, 3));
        lp1.layout();
        return lp1;
    }

    private LayoutPanel createQuestionToplPanel() {
        LayoutPanel lp1 = new LayoutPanel(new FormLayout("p", "p,3dlu,p"));
        lp1.add(new TextBox(), xy(1, 3));

        final CommandAction createNodeAction = new CommandAction("Hello!", new Command() {
            public void execute() {
                InfoPanel.show("Action", "jipeee");
            }
        });

        createNodeAction.setEnabled(false);
        createNodeAction.putValue(Action.SHORT_DESCRIPTION, "A short description");
        createNodeAction.putValue(Action.SMALL_ICON, CommandAction.ACTION_IMAGES.bell());


        final ButtonBindings btnActionSupport4 = new ButtonBindings(createNodeAction);
        btnActionSupport4.setLabelType(ButtonHelper.ButtonLabelType.TEXT_ON_TOP);
        btnActionSupport4.bind();


        lp1.add(btnActionSupport4.getTarget(), xy(1, 1));
        lp1.layout();
        return lp1;
    }

    // private TreeItem selectedTreeItem;

    private ScrollPanel createTreeTable() {
        currentTemplateTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            public void onSelection(final SelectionEvent<TreeItem> treeItemSelectionEvent) {
                selectTreeItem(treeItemSelectionEvent.getSelectedItem());
            }
        });

        return new ScrollPanel(currentTemplateTree);
    }

    private void selectTreeItem(TreeItem selected) {
        if (selectedItem != selected) {

            if (selectedItem != null) selectedItem.removeStyleName("selected");

            selectedItem = selected;

            if (selectedItem != null)
                selectedItem.addStyleName("selected");


            createNodeActionAfter.setEnabled(selected != null);
            createNodeActionInside.setEnabled(selected != null);


        }
    }

    private void updateTemplateList1() {
        templateSelection.clear();

        templateDao.withAllTemplates(new Callback<Template>() {
            public void handle(Template template) {
                templateSelection.add(new TemplateSelection(template.getName(), template.getId()));
            }
        });
    }

    private void selectItem(TemplateSelection item) {
        currentSelection = item;
        templateSelection.setSelectedItem(item);
        currentTemplateTree.removeItems();

        if (item != null) {
            final String id = item.getId();
            DecoratedTemplate dd = templateDao.loadTemplate(id);
            dd.dump();

            templateDao.withTemplate(new Callback<List<Template>>() {
                public void handle(List<Template> template) {
                    createTree(template, id, 0, new Callback<TreeItem>() {
                        public void handle(TreeItem template1) {
                            currentTemplateTree.addItem(template1);
                        }
                    });
                }
            }, id);
        }
    }

    private void createTree(List<Template> result, String parent, int dep, Callback<TreeItem> cb) {
        for (final Template template : result) {
            final List<String> pt = template.getPath();
            if ((pt.size() == (dep + 1) && (dep == 0 || pt.get(dep - 1).equals(parent)))) {
                final TreeItem it = createTreeItem(template);

                cb.handle(it);

                createTree(result, template.getId(), dep + 1, new Callback<TreeItem>() {
                    public void handle(TreeItem template) {
                        it.addItem(template);
                    }
                });
            }
        }
    }

// -------------------------- OTHER METHODS --------------------------

    private CommandAction createCommand(Command command, AbstractImagePrototype smallIcon) {
        CommandAction createNodeActionInside = new CommandAction("", command);

        createNodeActionInside.setEnabled(false);
        createNodeActionInside.putValue(Action.SHORT_DESCRIPTION, "A short description");
        createNodeActionInside.putValue(Action.SMALL_ICON, smallIcon);
        return createNodeActionInside;
    }
}

