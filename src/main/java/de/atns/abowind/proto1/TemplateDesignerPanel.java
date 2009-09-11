package de.atns.abowind.proto1;

import com.allen_sauer.gwt.dnd.client.AbstractDragController;
import com.allen_sauer.gwt.dnd.client.DragController;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.Location;
import com.allen_sauer.gwt.dnd.client.util.WidgetLocation;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.xml.client.Document;
import de.atns.abowind.model.Template;
import de.atns.abowind.model.TemplateSelection;
import de.atns.abowind.model.ViewResult;
import de.atns.abowind.model.ViewResultEntry;
import de.atns.abowind.proto1.constants.ButtonImages;
import de.atns.abowind.proto1.constants.Menu;
import static de.atns.abowind.proto1.constants.TemplateEditor.ACTION;
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
            if (1 > 0) return;

            // may have changed due to scrollIntoView() or developer driven changes
            calcBoundaryOffset();

            int desiredLeft = context.desiredDraggableX - boundaryOffsetX;
            int desiredTop = context.desiredDraggableY - boundaryOffsetY;
            if (getBehaviorConstrainedToBoundaryPanel()) {
                desiredLeft = Math.max(0, Math.min(desiredLeft, dropTargetClientWidth
                        - context.draggable.getOffsetWidth()));
                desiredTop = Math.max(0, Math.min(desiredTop, dropTargetClientHeight
                        - context.draggable.getOffsetHeight()));
            }

            DOMUtil.fastSetElementPosition(movablePanel.getElement(), desiredLeft, desiredTop);
            /*
DropController newDropController = getIntersectDropController(context.mouseX, context.mouseY);
if (context.dropController != newDropController) {
  if (context.dropController != null) {
      context.dropController.onLeave(context);
  }
  context.dropController = newDropController;
  if (context.dropController != null) {
      context.dropController.onEnter(context);
  }
}
            */
            if (context.dropController != null) {
                context.dropController.onMove(context);
            }
        }

        private void calcBoundaryOffset() {
            Location widgetLocation = new WidgetLocation(context.boundaryPanel, null);
            boundaryOffsetX = widgetLocation.getLeft()
                    + DOMUtil.getBorderLeft(context.boundaryPanel.getElement());
            boundaryOffsetY = widgetLocation.getTop()
                    + DOMUtil.getBorderTop(context.boundaryPanel.getElement());
        }

        private int boundaryOffsetX;

        private int boundaryOffsetY;
        private int dropTargetClientHeight;

        private int dropTargetClientWidth;
        private Widget movablePanel;

        @Override
        public void dragEnd() {
            if (1 > 0) return;


            movablePanel.removeFromParent();
            movablePanel = null;
            super.dragEnd();
        }
    };

    private DefaultComboBoxModel<TemplateSelection> templateSelection;
    private CommandAction createNodeActionInside;
    private CommandAction createNodeActionAfter;
    private TreeItem selectedItem;
    private TextBox newStructureName;
    private TextBox newTemplateName;

// --------------------------- CONSTRUCTORS ---------------------------

    public TemplateDesignerPanel() {
        super(new BorderLayout());

        createNodeActionInside = new CommandAction("", new Command() {
            public void execute() {
                //InfoPanel.show("Action", "jipeee");

                if (selectedItem != null) {
                    Object o = selectedItem.getUserObject();
                    if (o instanceof Template) {
                        Template template = (Template) o;

                        Template nt = Template.create();
                        nt.setPath(template.getPath());
                        nt.setName(newStructureName.getText());

                        try {
                            SaveResult res = Application.DB.save(nt);
                            System.err.println("saved nt " + nt.getId());

                            Template nt2 = Application.DB.open(res.getId());
                            JsArrayString pt = nt2.getPath();
                            pt.set(pt.length(), res.getId());

                            Application.DB.save(nt2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        createNodeActionInside.setEnabled(false);
        createNodeActionInside.putValue(Action.SHORT_DESCRIPTION, "A short description");
        createNodeActionInside.putValue(Action.SMALL_ICON, CommandAction.ACTION_IMAGES.cut_action());


        createNodeActionAfter = new CommandAction("", new Command() {
            public void execute() {
                if (selectedItem != null) {
                    Object o = selectedItem.getUserObject();
                    if (o instanceof Template) {
                        Template template = (Template) o;


                        Template nt = Template.create();
                        nt.setName(newStructureName.getText());
                        nt.setPath(template.getPath());

                        try {
                            SaveResult res = Application.DB.save(nt);
                            System.err.println("saved nt " + nt.getId());

                            Template nt2 = Application.DB.open(res.getId());
                            JsArrayString pt = nt2.getPath();
                            pt.set(pt.length() - 1, res.getId());

                            Application.DB.save(nt2);

                            updateData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        createNodeActionAfter.setEnabled(false);
        createNodeActionAfter.putValue(Action.SHORT_DESCRIPTION, "A short description");
        createNodeActionAfter.putValue(Action.SMALL_ICON, CommandAction.ACTION_IMAGES.cut_action());


        currentTemplateTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            public void onSelection
                    (
                            final SelectionEvent<TreeItem> treeItemSelectionEvent) {
                final TreeItem selected = treeItemSelectionEvent.getSelectedItem();
                System.err.println(selected);

                if (selected != null) {
                    createNodeActionAfter.setEnabled(true);
                    createNodeActionInside.setEnabled(true);

                    selectedItem = selected;
                }
            }
        });

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

        LayoutPanel controls = new LayoutPanel(new FormLayout("p, p,3dlu, right:pref:grow,3dlu,p, 3dlu", "p"));
        final Label label = new Label("Choose Template");
        label.setWidth("170px");
        controls.add(label, xy(1,1));
        controls.add(templateSelector, xy(2, 1));
        final Button addButton = new Button(
                createButtonLabel(
                        ButtonImages.BUTTON_IMAGES.addTemplate(),
                        Menu.MENU.templateAdd(),
                        TEXT_ON_RIGHT)
        );


        addButton.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent clickEvent) {

            }
        });


        newTemplateName = new TextBox();
        controls.add(newTemplateName, xy(5, 1));

        controls.add(addButton, xy(6, 1));


//             controls.getElement().
        //final Image image = new Image("loading.gif");
        //image.addMouseListener(new TooltipListener("dsfadsf", 5000));
        //controls.add(image, xy(5, 1));

        dragController.makeDraggable(addButton);


        /*     final Element e = templateSelector.getElement();
           DOM.sinkEvents(e,
                   Event.ONMOUSEMOVE |
                           Event.ONMOUSEOUT |
                           Event.ONMOUSEOVER
           );
           DOM.setEventListener(e, new EventListener() {
               public void onBrowserEvent(final Event event) {
                   System.err.println(event.toString() + " " + event.getType());
               }
           });
        */
//        Window.alert(controls.getElement().toString());

        return controls;
    }

    private void createTree(Tree root, final List<Template> result, String parent, final int dep) {
        for (final Template template : result) {
            final JsArrayString pt = template.getPath();
            if ((pt.length() == (dep + 1) && (dep == 0 || pt.get(dep - 1).equals(parent)))) {
                final Widget button = new Label(template.getName());
                dragController.makeDraggable(button);
                TreeItem it = new TreeItem(button) {
                    @Override public Object getUserObject() {
                        return template;
                    }

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

        newStructureName = new TextBox();

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
                    @Override public Object getUserObject() {
                        return template;
                    }

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

