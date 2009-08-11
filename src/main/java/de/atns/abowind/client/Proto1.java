package de.atns.abowind.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.RootPanel;
import static com.google.gwt.widgetideas.table.client.SelectionGrid.SelectionPolicy.MULTI_ROW;
import de.atns.abowind.model.Template;
import de.atns.abowind.model.TemplateSelection;
import de.atns.abowind.model.ViewResult;
import de.atns.abowind.model.ViewResultEntry;
import org.gwt.beansbinding.core.client.util.GWTBeansBinding;
import org.gwt.mosaic.ui.client.ComboBox;
import org.gwt.mosaic.ui.client.ListBox;
import org.gwt.mosaic.ui.client.layout.BoxLayout;
import static org.gwt.mosaic.ui.client.layout.BoxLayout.Orientation.VERTICAL;
import org.gwt.mosaic.ui.client.layout.BoxLayoutData;
import static org.gwt.mosaic.ui.client.layout.BoxLayoutData.FillStyle.BOTH;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.gwt.mosaic.ui.client.list.DefaultComboBoxModel;
import org.gwt.mosaic.ui.client.list.ListDataEvent;
import org.gwt.mosaic.ui.client.list.ListDataListener;
import org.gwt.mosaic.ui.client.treetable.FastTreeTable;
import org.gwt.mosaic.ui.client.treetable.FastTreeTableItem;
import org.gwt.mosaic.ui.client.treetable.ScrollTreeTable;
import org.gwt.mosaic.ui.client.treetable.TreeTableLabelProvider;

import java.util.List;


public class Proto1 implements EntryPoint {
// ------------------------------ FIELDS ------------------------------

    private FastTreeTable dataTable = new FastTreeTable();
    private ScrollTreeTable scrollTreeTable = new ScrollTreeTable(dataTable, null);
    private CouchDB db = CouchDB.create("abowind");

// -------------------------- STATIC METHODS --------------------------

    static {
        GWTBeansBinding.init();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface EntryPoint ---------------------

    public void onModuleLoad() {
        scrollTreeTable.setResizePolicy(ScrollTreeTable.ResizePolicy.UNCONSTRAINED);
        LayoutPanel functionBox = new LayoutPanel(new BoxLayout(BoxLayout.Orientation.HORIZONTAL));

        final ComboBox<TemplateSelection> templateSelector = new ComboBox<TemplateSelection>();
        final DefaultComboBoxModel<TemplateSelection> templateSelectionModel = (DefaultComboBoxModel<TemplateSelection>) templateSelector.getModel();

        templateSelector.setCellRenderer(new ComboBox.ComboBoxCellRenderer<TemplateSelection>() {
            public String getDisplayText(TemplateSelection templateSelection) {
                return templateSelection.getName();
            }

            public void renderCell(ListBox<TemplateSelection> templateSelectionListBox, int i, int i1, TemplateSelection templateSelection) {
                templateSelectionListBox.setText(i, i1, templateSelection.getName());
            }
        });

        templateSelectionModel.addListDataListener(new ListDataListener() {
            public void contentsChanged(ListDataEvent listDataEvent) {
                final TemplateSelection item = templateSelectionModel.getSelectedItem();
                if (item != null) {
                    if (item.getId() == null) {
                        //TODO display window for new template-name
                    } else {
                        dataTable.clearAll();
                        dataTable.resize(0, 1);

                        ViewResult<Template> t = db.view("couch/template", ViewOptions.create(item.getId()));
                        createTree(dataTable, t.toList(), item.getId(), 0);
                    }
                }
            }

            public void intervalAdded(ListDataEvent listDataEvent) {
            }

            public void intervalRemoved(ListDataEvent listDataEvent) {
            }
        });

        functionBox.add(templateSelector);

        scrollTreeTable.setColumnWidth(0, 650);

        dataTable.addTreeTableLabelProvider(new TreeTableLabelProvider() {
            public Object getItemLabel(FastTreeTableItem item, int col) {
                Template userObject = (Template) item.getUserObject();
                switch (col) {
                    case 1:
                        return userObject.getName();

                    //TODO add function-buttons here

                    default:
                        return null;
                }
            }
        });

        dataTable.resize(0, 1);
        dataTable.setSelectionPolicy(MULTI_ROW);

        final LayoutPanel vPanel = new LayoutPanel(new BoxLayout(VERTICAL));
        vPanel.setPadding(0);
        vPanel.setWidgetSpacing(5);
        vPanel.add(functionBox);
        vPanel.add(scrollTreeTable, new BoxLayoutData(BOTH));
        vPanel.setHeight("90%");
        vPanel.setWidth("98%");


        RootPanel.get().add(vPanel);
        RootPanel.get("splash").setVisible(false);

        updateData(templateSelectionModel);

        if (templateSelectionModel.size() > 0) {
            templateSelectionModel.setSelectedItem(templateSelectionModel.get(1));
        }
    }

// -------------------------- OTHER METHODS --------------------------

    private void createTree(FastTreeTableItem root, final List<Template> result, String parent, final int dep) {
        for (final Template template : result) {
            final JsArrayString pt = template.getPath();
            if ((pt.length() == (dep + 1) && (dep == 0 || pt.get(dep - 1).equals(parent)))) {
                FastTreeTableItem it = new FastTreeTableItem(template.getName());
                root.addItem(it);
                createTree(it, result, template.getId(), dep + 1);
            }
        }
    }

    private void createTree(FastTreeTable root, final List<Template> result, String parent, final int dep) {
        for (final Template template : result) {
            final JsArrayString pt = template.getPath();
            if ((pt.length() == (dep + 1) && (dep == 0 || pt.get(dep - 1).equals(parent)))) {
                FastTreeTableItem it = new FastTreeTableItem(template.getName());
                root.addItem(it);
                createTree(it, result, template.getId(), dep + 1);
            }
        }
    }

    private void updateData(final DefaultComboBoxModel<TemplateSelection> templateNames) {
        templateNames.clear();
        templateNames.add(new TemplateSelection("create new Template", null));
        ViewResult<Template> vr = db.view("couch/all_templates");
        System.err.println(new JSONObject(vr));


        for (ViewResultEntry<Template> templateViewResultEntry : vr.iterator()) {
            final Template template = templateViewResultEntry.getValue();
            System.err.println("template " + template.getName() + "/" + template.getId());
            TemplateSelection vt = new TemplateSelection(template.getName(), template.getId());
            templateNames.add(vt);
        }
    }
}
