package de.atns.playground.couchdb.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import de.atns.playground.couchdb.client.model.*;
import org.gwt.mosaic.actions.client.Action;
import org.gwt.mosaic.actions.client.ButtonBindings;
import org.gwt.mosaic.actions.client.CommandAction;
import org.gwt.mosaic.forms.client.layout.FormLayout;
import org.gwt.mosaic.ui.client.ComboBox;
import org.gwt.mosaic.ui.client.ListBox;
import org.gwt.mosaic.ui.client.layout.BorderLayout;
import org.gwt.mosaic.ui.client.layout.BorderLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.gwt.mosaic.ui.client.list.DefaultComboBoxModel;
import org.gwt.mosaic.ui.client.list.ListDataEvent;
import org.gwt.mosaic.ui.client.list.ListDataListener;
import org.gwt.mosaic.ui.client.util.ButtonHelper;

import java.util.ArrayList;
import java.util.List;

import static com.allen_sauer.gwt.log.client.Log.debug;
import static de.atns.playground.couchdb.client.constants.ButtonImages.BUTTON_IMAGES;
import static org.gwt.mosaic.forms.client.layout.CellConstraints.xy;
import static org.gwt.mosaic.ui.client.layout.BorderLayout.Region.*;
import static org.gwt.mosaic.ui.client.util.ButtonHelper.ButtonLabelType.TEXT_ON_RIGHT;
import static org.gwt.mosaic.ui.client.util.ButtonHelper.createButtonLabel;

/**
 * @author tbaum
 * @since 12.08.2009 15:04:14
 */
public class TemplateDesignerPanel extends LayoutPanel {
// ------------------------------ FIELDS ------------------------------

    private final Tree currentTemplateTree = new Tree();

    private final CommandAction createNodeActionInside = createCommand(new Command() {
        public void execute() {
            if (selectedTemplateItem != null) {
                createTemplate(selectedTemplateItem, newStructureName.getText());
            }
        }
    }, BUTTON_IMAGES.insertInside());

    private final CommandAction createStatusEditAction = createCommand(new Command() {
        public void execute() {
            if (selectedTemplateItem != null) {
                debug("Blablubb");
                TemplateStatusEdit test = new TemplateStatusEdit();
                test.showModal();
            }
        }
    }, BUTTON_IMAGES.statusEdit());

    private final CommandAction createStatusAction = createCommand(new Command() {
        public void execute() {
            if (selectedTemplateItem != null) {
                createStatus(selectedTemplateItem, newStatusName.getText());
            }
        }
    }, BUTTON_IMAGES.plugin_add());


    private final TextBox newStructureName = new TextBox();
    private final TextBox newStatusName = new TextBox();
    private final TextBox newTemplateName = new TextBox();
    private final TemplateDao templateDao = Application.application().templateDao();

    private TreeItem selectedTemplateItem;
    private DefaultComboBoxModel<TemplateSelection> templateSelection;
    private DefaultComboBoxModel<StatusGroupSelection> statusGroupSelection;
    private TemplateSelection currentTemplateSelection = null;
    private StatusGroupSelection currentStatusGroupSelection = null;

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

        final ComboBox<TemplateSelection> templateSelector = createTemplateSelector();
        final ComboBox<StatusGroupSelection> statusGroupSelection = createStatusGroupSelector();

        LayoutPanel controls = new LayoutPanel(new FormLayout("130px, p,3dlu, right:pref:grow,3dlu,p, 3dlu", "p"));
        controls.add(new Label("Choose Template"), xy(1, 1));
        controls.add(templateSelector, xy(2, 1));
        final Button addButton = new Button(
                createButtonLabel(
                        BUTTON_IMAGES.addTemplate(),
                        "Add Template",
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
                    debug(e.getMessage());
                }
            }
        });


        controls.add(addButton, xy(6, 1));

        /*

        final Element e = templateSelector.getElement();
        DOM.sinkEvents(e, Event.ONMOUSEMOVE | Event.ONMOUSEOUT | Event.ONMOUSEOVER);
        DOM.setEventListener(e, new EventListener() {
            public void onBrowserEvent(final Event event) {
                debug(event.toString() + " " + event.getType());
            }
        });
        */

        return controls;
    }

    private ComboBox<TemplateSelection> createTemplateSelector() {
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
                if (selection != currentTemplateSelection) {
                    selectItem(selection);
                }
            }

            public void intervalAdded(ListDataEvent listDataEvent) {
            }

            public void intervalRemoved(ListDataEvent listDataEvent) {
            }
        });
        return templateSelector;
    }

    private ComboBox<StatusGroupSelection> createStatusGroupSelector() {
        final ComboBox<StatusGroupSelection> statusGroupSelector = new ComboBox<StatusGroupSelection>();
        statusGroupSelection = (DefaultComboBoxModel<StatusGroupSelection>) statusGroupSelector.getModel();

        statusGroupSelector.setCellRenderer(new ComboBox.ComboBoxCellRenderer<StatusGroupSelection>() {
            public String getDisplayText(StatusGroupSelection statusGroupSelection) {
                return statusGroupSelection.getName();
            }

            public void renderCell(ListBox<StatusGroupSelection> statusGroupSelectionListBox, int i, int i1, StatusGroupSelection statusGroupSelection) {
                statusGroupSelectionListBox.setText(i, i1, statusGroupSelection.getName());
            }
        });


        templateSelection.addListDataListener(new ListDataListener() {
            public void contentsChanged(ListDataEvent listDataEvent) {
                StatusGroupSelection selection = statusGroupSelection.getSelectedItem();
                if (selection != currentStatusGroupSelection) {
                    //selectItem(selection);
                }
            }

            public void intervalAdded(ListDataEvent listDataEvent) {
            }

            public void intervalRemoved(ListDataEvent listDataEvent) {
            }
        });

        return statusGroupSelector;
    }

    private Template createTemplate(TreeItem parent, final String name) {
        try {
            debug("creating new Template name=" + name);
            final List<String> path =
                    parent == null || !(parent.getUserObject() instanceof Template)
                            ? new ArrayList<String>()
                            : ((Template) parent.getUserObject()).getPath();

            final String uuid = Application.DB.newUuid();
            debug("creating new Template uuid=" + uuid);
            Template nt = Template.create(uuid, name, path);
            Application.DB.save(nt);

            debug("saved nt " + nt.getId());

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

    private void selectTreeItem(TreeItem selected) {
        if (selectedTemplateItem != selected) {
            if (selectedTemplateItem != null) selectedTemplateItem.removeStyleName("selected");

            selectedTemplateItem = selected;

            if (selectedTemplateItem != null)
                selectedTemplateItem.addStyleName("selected");

            updateStatusGroupSelector();

            createStatusEditAction.setEnabled(selected != null);
            createStatusAction.setEnabled(selected != null);
            createNodeActionInside.setEnabled(selected != null);
        }
    }

    private void updateStatusGroupSelector() {
        statusGroupSelection.clear();

        ViewResult<StatusGroup> t = Application.DB.view("couch/statusgroup_all");
        List<StatusGroup> statusGroups = t.toList();

        for (StatusGroup statusGroup : statusGroups) {
            StatusGroupSelection sgs = new StatusGroupSelection(statusGroup.getName(), statusGroup.getId(), statusGroup.getStatusOptions());

            statusGroupSelection.add(sgs);
        }
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
        LayoutPanel layoutPanel = new LayoutPanel();
        layoutPanel.setWidth("200px");

        //lp1.setHeight("40px");
        //DropController dropController = new AbsolutePositionDropController(lp1);
        //dragController.registerDropController(dropController);
        // dragController.makeDraggable(scrollTreeTable);

        layoutPanel.add(createStructureToolPanel());
        return layoutPanel;
    }

    private LayoutPanel createStructureToolPanel() {
        LayoutPanel lp1 = new LayoutPanel(new FormLayout("p:grow, 5px, 32px", "p, 10px, p, 20px, pref, 5px, p, 15px, p, 5px, p"));


        final ButtonBindings btnActionInside = bindButton(createNodeActionInside);
        final ButtonBindings btnActionStatusEdit = bindButton(createStatusEditAction);
        final ButtonBindings btnActionStatus = bindButton(createStatusAction);


        lp1.add(new Label("Create new templatecategory"), xy(1, 1));
        lp1.add(newStructureName, xy(1, 3));
        lp1.add(btnActionInside.getTarget(), xy(3, 3));

        lp1.add(new Label("Choose status"), xy(1, 5));
        lp1.add(new Label("Create new status"), xy(1, 9));

        lp1.add(new ComboBox(), xy(1, 7));
        lp1.add(btnActionStatusEdit.getTarget(), xy(3, 7));

        lp1.add(newStatusName, xy(1, 11));
        lp1.add(btnActionStatus.getTarget(), xy(3, 11));


        lp1.layout();
        return lp1;
    }

    private ButtonBindings bindButton(final CommandAction commandAction) {
        ButtonBindings bb = new ButtonBindings(commandAction);
        bb.setLabelType(ButtonHelper.ButtonLabelType.TEXT_ON_TOP);
        bb.bind();
        return bb;
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

    private void updateTemplateList1() {
        templateSelection.clear();

        templateDao.withAllTemplates(new Callback<Template>() {
            public void handle(Template template) {
                templateSelection.add(new TemplateSelection(template.getName(), template.getId()));
            }
        });
    }

    private void selectItem(TemplateSelection item) {
        currentTemplateSelection = item;
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

    private void createStatus(final TreeItem selectedItem, final String text) {
        try {
            StatusGroup nt = StatusGroup.create(Application.DB.newUuid(), text);
            Application.DB.save(nt);

            debug("saved StatusGroup " + nt.getId());

            if (selectedItem != null) {
                Template template = (Template) selectedItem.getUserObject();
                template.setStatusGroup(nt.getId());
                Application.DB.save(template);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

