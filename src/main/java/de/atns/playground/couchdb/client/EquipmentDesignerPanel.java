package de.atns.playground.couchdb.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import de.atns.playground.couchdb.client.model.Template;
import de.atns.playground.couchdb.client.model.TemplateSelection;
import org.gwt.mosaic.actions.client.Action;
import org.gwt.mosaic.actions.client.ButtonBindings;
import org.gwt.mosaic.actions.client.CommandAction;
import org.gwt.mosaic.forms.client.layout.FormLayout;
import org.gwt.mosaic.ui.client.ComboBox;
import org.gwt.mosaic.ui.client.InfoPanel;
import org.gwt.mosaic.ui.client.ListBox;
import org.gwt.mosaic.ui.client.layout.BoxLayout;
import org.gwt.mosaic.ui.client.layout.BoxLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.gwt.mosaic.ui.client.list.DefaultComboBoxModel;
import org.gwt.mosaic.ui.client.list.ListDataEvent;
import org.gwt.mosaic.ui.client.list.ListDataListener;
import org.gwt.mosaic.ui.client.util.ButtonHelper;

import java.util.*;

import static com.allen_sauer.gwt.log.client.Log.debug;
import static org.gwt.mosaic.forms.client.layout.CellConstraints.xy;

/**
 * @author tbaum
 * @since 12.08.2009 15:04:14
 */
public class EquipmentDesignerPanel extends LayoutPanel {
// ------------------------------ FIELDS ------------------------------

    private final Tree currentTemplateTree = new Tree();
    private final Tree currentEqTree = new Tree();


    private final TextBox newStructureName = new TextBox();
    private final TemplateDao templateDao = Application.application().templateDao();

    private DefaultComboBoxModel<TemplateSelection> templateSelection;
    private TemplateSelection currentSelection = null;
    private Label widget = new Label("todo");
    private TextArea dbg = new TextArea();

    private final Set<String> sect = new HashSet<String>();

// --------------------------- CONSTRUCTORS ---------------------------

    public EquipmentDesignerPanel() {
        super(new BoxLayout(BoxLayout.Orientation.HORIZONTAL));

        try {
            //     add(createToolPanel(), new BorderLayoutData(EAST, true));
            final LayoutPanel t1 = createTreeTable();
            final LayoutPanel t2 = createEqTable();
            add(t1, new BoxLayoutData(BoxLayoutData.FillStyle.VERTICAL, true));
            add(t2, new BoxLayoutData(BoxLayoutData.FillStyle.VERTICAL));


            updateTemplateList1();
            /*   final String id = "eb4cc63c04cc88f3ea3b687883a2f85f";

           templateDao.withEquipment(id, new Callback<Equipment>() {
               public void handle(Equipment equipment) {
                   sect.clear();
                   sect.addAll(equipment.getTemplates());
                   //     final Map<String, String> id2name = new HashMap<String, String>();
               }
           }); */

            createTargetTree();

/*            addCommand(new Command() {
                public void execute() {
                    t1.layout();
                    t2.layout();
                }
            });
  */
            //  templateSelection.setSelectedItem(templateSelection.get(0));
            //   selectItem();
            // final TemplateSelection templateSelection1 = templateSelection.size() > 0 ? templateSelection.get(0) : null;
            //   selectItem(templateSelection1);
        } catch (Exception e) {
            Window.alert(e.getMessage());
        }
    }

    private void createTargetTree() {
        final Map<String, Template> id2temp = new HashMap<String, Template>();
        final Set<String> ids = new HashSet<String>();
        //final Set<Template> templ = new LinkedHashSet<Template>();
        debug("---- update tree ---");
        for (String s : sect) {
            debug("loading template-fragment " + s);
            Template t = Application.DB.open(s);
            final List<String> path = t.getPath();
            templateDao.withTemplate(new Callback<List<Template>>() {
                public void handle(List<Template> template) {
                    for (Template template1 : template) {
                        debug(" ->" + template1.getId() + " " + template1.getName() + " /" + template1.getPath());
                        //   String pa = getPath(id2name, template1.getId());
                        ids.addAll(template1.getPath());
                        id2temp.put(template1.getId(), template1);
                    }
                }
            }, path.toArray(new String[path.size()]));
        }
        List<TemplateDec> tt = new ArrayList<TemplateDec>();

        ids.removeAll(id2temp.keySet());

        for (String id : ids) {
            id2temp.put(id, Application.DB.<Template>open(id));
        }

        for (Template template : id2temp.values()) {
            tt.add(new TemplateDec(template, id2temp));
        }

        Collections.sort(tt, new Comparator<TemplateDec>() {
            public int compare(TemplateDec o1, TemplateDec o2) {
                String p1 = mkp(o1.getPathName());
                String p2 = mkp(o2.getPathName());
                return p1.compareTo(p2);
            }
        });
        String s = "";
        List<String> last = null;
        for (TemplateDec ttx : tt) {
            final List<String> sL = ttx.getPathName();
            if (sL.equals(last)) continue;
            last = sL;
            String sep = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t".substring(0, sL.size());
            //  for (String s1 : sL) {
            //      s += sep + s1;
            //      sep = " > ";
            //  }
            s += sep + sL.get(sL.size() - 1);

            s += "\n";
        }
        dbg.setText(s);
    }

    private String mkp(List<String> pathName) {
        String r = "";
        for (String s : pathName) {
            r += (r.length() > 0 ? " : " : "") + s;
        }
        return r;

    }


    // private TreeItem selectedTreeItem;

    private LayoutPanel createTreeTable() {
        LayoutPanel lp = new LayoutPanel(new BoxLayout(BoxLayout.Orientation.VERTICAL));
        lp.add(createControlPanel(), new BoxLayoutData(BoxLayoutData.FillStyle.HORIZONTAL));


        currentTemplateTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            public void onSelection(final SelectionEvent<TreeItem> treeItemSelectionEvent) {
                toggleTreeItem(treeItemSelectionEvent.getSelectedItem());
            }
        });

        lp.add(new ScrollPanel(currentTemplateTree), new BoxLayoutData(BoxLayoutData.FillStyle.BOTH));
        return lp;
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

        LayoutPanel controls = new LayoutPanel(new FormLayout("130px,p", "p"));
        controls.add(new Label("Choose Template"), xy(1, 1));
        controls.add(templateSelector, xy(2, 1));


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

    private void selectItem(TemplateSelection item) {
        currentSelection = item;
        templateSelection.setSelectedItem(item);
        currentTemplateTree.removeItems();

        if (item != null) {
            final String id = item.getId();

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

    private TreeItem createTreeItem(final Template template) {
        final Widget button = new Label(template.getName());
        final TreeItem ti = new TreeItem(button) {
            @Override public Object getUserObject() {
                return template;
            }
        };

        if (sect.contains(template.getId()))
            ti.addStyleName("selected");

        return ti;
    }

    private void toggleTreeItem(TreeItem selected) {
        final Template userObject = (Template) selected.getUserObject();
        if (sect.contains(userObject.getId())) {
            sect.remove(userObject.getId());
            selected.removeStyleName("selected");
        } else {
            sect.add(userObject.getId());
            selected.addStyleName("selected");
        }

        createTargetTree();
        widget.setText(sect.toString());

    }

    private LayoutPanel createEqTable() {
        LayoutPanel l = new LayoutPanel(new BoxLayout(BoxLayout.Orientation.VERTICAL));
        l.add(widget, new BoxLayoutData(BoxLayoutData.FillStyle.HORIZONTAL));
        l.add(dbg, new BoxLayoutData(BoxLayoutData.FillStyle.BOTH));
        //  l.add(new ScrollPanel(currentEqTree), new BoxLayoutData(BoxLayoutData.FillStyle.BOTH));
        return l;
    }

    private void updateTemplateList1() {
        templateSelection.clear();

        templateDao.withAllTemplates(new Callback<Template>() {
            public void handle(Template template) {
                templateSelection.add(new TemplateSelection(template.getName(), template.getId()));
            }
        });
    }

    public String getPath(Map<String, String> cache, String id) {
        if (cache.containsKey(id)) return cache.get(id);

        Template t = Application.DB.open(id);
        cache.put(id, t.getName());
        return t.getName();
    }

// -------------------------- OTHER METHODS --------------------------

    private CommandAction createCommand(Command command, AbstractImagePrototype smallIcon) {
        CommandAction createNodeActionInside = new CommandAction("", command);

        createNodeActionInside.setEnabled(false);
        createNodeActionInside.putValue(Action.SHORT_DESCRIPTION, "A short description");
        createNodeActionInside.putValue(Action.SMALL_ICON, smallIcon);
        return createNodeActionInside;
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

    private void selectItem(String id) {
        for (TemplateSelection selection : templateSelection) {
            if (selection.getId().equals(id)) {
                selectItem(selection);
                return;
            }
        }
    }

    private class TemplateDec {
        private final Template template;
        private final List<String> pathName;

        public List<String> getPathName() {
            return pathName;
        }

        public String getId() {
            return template.getId();
        }

        public List<String> getPath() {
            return template.getPath();
        }

        public String getName() {
            return template.getName();
        }

        private TemplateDec(Template template, Map<String, Template> id2temp) {
            this.template = template;
            ArrayList<String> pn = new ArrayList<String>();
            for (String s : template.getPath()) {
                final Template t = id2temp.get(s);
                pn.add(t != null ? t.getName() : "undefined");
            }
            pn.set(0, "__NEW_STRUCT__");
            pathName = Collections.unmodifiableList(pn);
        }
    }
}