package de.atns.playground.couchdb.client;

import de.atns.playground.couchdb.client.model.Equipment;
import de.atns.playground.couchdb.client.model.Template;
import de.atns.playground.couchdb.client.model.ViewResult;
import de.atns.playground.couchdb.client.model.ViewResultEntry;
import de.atns.playground.couchdb.client.model.DecoratedTemplate;

import java.util.List;

/**
 * @author tbaum
 * @since 12.09.2009 17:59:20
 */
public class TemplateDao {
// -------------------------- OTHER METHODS --------------------------

    DecoratedTemplate loadTemplate(final String id) {
        final DecoratedTemplate[] root = new DecoratedTemplate[1];
        withTemplate(new Callback<List<Template>>() {
            public void handle(List<Template> template) {
                if (template.size() > 0) {
                    root[0] = new DecoratedTemplate(template.get(0));

                    createTree(template, id, 0, new Callback<DecoratedTemplate>() {
                        public void handle(DecoratedTemplate template1) {
                            root[0].addItem(template1);
                        }
                    });
                }
            }
        }, id);
        return root[0];
    }

    void withTemplate(Callback<List<Template>> cb, final String... id) {
        ViewResult<Template> t = Application.DB.view("couch/template", ViewOptions.create(id));
        cb.handle(t.toList());
    }

    private void createTree(List<Template> result, String parent, int dep, Callback<DecoratedTemplate> cb) {
        for (final Template template : result) {
            final List<String> pt = template.getPath();
            if ((pt.size() == (dep + 1) && (dep == 0 || pt.get(dep - 1).equals(parent)))) {
                //   final TreeItem it = createTreeItem(template);
                final DecoratedTemplate it = new DecoratedTemplate(template);
                cb.handle(it);

                final String id = template.getId();

                createTree(result, id, dep + 1, new Callback<DecoratedTemplate>() {
                    public void handle(DecoratedTemplate template) {
                        it.addItem(template);
                    }
                });
            }
        }
    }

    void withAllTemplates(final Callback<Template> callback) {
        ViewResult<Template> vr = Application.DB.view("couch/template_all");
        for (ViewResultEntry<Template> templateViewResultEntry : vr.iterator()) {
            final Template template = templateViewResultEntry.getValue();
            callback.handle(template);
        }
    }

    void withEquipment(final String id, Callback<Equipment> cb) {
        Equipment t = Application.DB.open(id);
        cb.handle(t);
    }
}
