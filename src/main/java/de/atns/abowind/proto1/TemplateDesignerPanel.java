package de.atns.abowind.proto1;

import com.allen_sauer.gwt.dnd.client.AbstractDragController;
import com.allen_sauer.gwt.dnd.client.DragController;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.*;
import de.atns.abowind.model.Template;
import de.atns.abowind.model.TemplateSelection;
import de.atns.abowind.model.ViewResult;
import de.atns.abowind.model.ViewResultEntry;
import de.atns.abowind.proto1.constants.ButtonImages;
import de.atns.abowind.proto1.constants.Menu;
import de.atns.abowind.proto1.constants.TemplateEditor;
import org.gwt.mosaic.actions.client.Action;
import org.gwt.mosaic.actions.client.ButtonBindings;
import org.gwt.mosaic.actions.client.CommandAction;
import static org.gwt.mosaic.forms.client.layout.CellConstraints.xy;
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

import java.util.List;

/**
 * @author tbaum
 * @since 12.08.2009 15:04:14
 */
public class TemplateDesignerPanel extends LayoutPanel {
// ------------------------------ FIELDS ------------------------------

    private final Tree currentTemplateTree = new Tree();
    private DragController dragController = new AbstractDragController(this) {
        public void dragMove() {
            System.err.println("dragMove " + getElement());
        }
    };

    private DefaultComboBoxModel<TemplateSelection> templateSelection;

// --------------------------- CONSTRUCTORS ---------------------------

    public TemplateDesignerPanel() {
        super(new BorderLayout());

        add(createControlPanel(), new BorderLayoutData(NORTH, true));
        add(createToolPanel(), new BorderLayoutData(EAST, true));
        add(createTreeTable(), new BorderLayoutData(CENTER));

        updateData();

        if (templateSelection.size() > 0) {
            templateSelection.setSelectedItem(templateSelection.get(0));
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
                final TemplateSelection item = templateSelection.getSelectedItem();

                if (item != null) {
                    currentTemplateTree.removeItems();
                    //        currentTemplateTree.resize(0, 1);

                    ViewResult<Template> t = Application.DB.view("couch/template", ViewOptions.create(item.getId()));
                    createTree(currentTemplateTree, t.toList(), item.getId(), 0);
                }
            }

            public void intervalAdded(ListDataEvent listDataEvent) {
            }

            public void intervalRemoved(ListDataEvent listDataEvent) {
            }
        });
        LayoutPanel controls = new LayoutPanel(new FormLayout("p,3dlu,p", "p"));

        controls.add(templateSelector, xy(1, 1));
        final Button addButton = new Button(
                createButtonLabel(
                        ButtonImages.BUTTON_IMAGES.addTemplate(),
                        Menu.MENU.templateAdd(),
                        TEXT_ON_RIGHT)
        );
        //     addButton.setStyleName("add");
        controls.add(addButton, xy(3, 1));

        dragController.makeDraggable(addButton);

        return controls;
    }

    private void createTree(Tree root, final List<Template> result, String parent, final int dep) {
        for (final Template template : result) {
            final JsArrayString pt = template.getPath();
            if ((pt.length() == (dep + 1) && (dep == 0 || pt.get(dep - 1).equals(parent)))) {
                final Widget button = new Label(template.getName());
                dragController.makeDraggable(button);
                TreeItem it = new TreeItem(button) {
                    /*    @Override public Object getUserObject() {
                        return template;
                    }*/
                };


                root.addItem(it);
                createTree(it, result, template.getId(), dep + 1);
            }
        }
    }

    private Widget createToolPanel() {
        StackLayoutPanel stackPanel = new StackLayoutPanel();
        stackPanel.setWidth("200px");


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
        //lp1.setHeight("40px");


        //      DropController dropController = new AbsolutePositionDropController(lp1);
//        dragController.registerDropController(dropController);
        // dragController.makeDraggable(scrollTreeTable);
        stackPanel.add(lp1, TemplateEditor.ACTION.structure());


        stackPanel.showStack(0);

        return stackPanel;
    }

    private ScrollPanel createTreeTable() {
        return new ScrollPanel(currentTemplateTree);
    }

    private void updateData() {
        templateSelection.clear();
        ViewResult<de.atns.abowind.model.Template> vr = Application.DB.view("couch/all_templates");
        for (ViewResultEntry<Template> templateViewResultEntry : vr.iterator()) {
            final de.atns.abowind.model.Template template = templateViewResultEntry.getValue();
            System.err.println("template " + template.getName() + "/" + template.getId());
            TemplateSelection vt = new TemplateSelection(template.getName(), template.getId());
            templateSelection.add(vt);
        }
    }

// -------------------------- OTHER METHODS --------------------------

    private void createTree(TreeItem root, final List<Template> result, String parent, final int dep) {
        for (final Template template : result) {
            final JsArrayString pt = template.getPath();
            if ((pt.length() == (dep + 1) && (dep == 0 || pt.get(dep - 1).equals(parent)))) {
                final Widget button = new Label(template.getName());
                dragController.makeDraggable(button);
                TreeItem it = new TreeItem(button) {
                    /*    @Override public Object getUserObject() {
                        return template;
                    }*/
                };


                root.addItem(it);
                createTree(it, result, template.getId(), dep + 1);
            }
        }
    }
}
