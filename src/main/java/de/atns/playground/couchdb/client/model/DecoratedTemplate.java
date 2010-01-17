package de.atns.playground.couchdb.client.model;

import de.atns.playground.couchdb.client.model.Template;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tbaum
 * @since 12.09.2009 21:03:56
 */
public class DecoratedTemplate {

    private final Template raw;
    private final List<Template> rawChildren = new ArrayList<Template>();
    private final List<DecoratedTemplate> children = new ArrayList<DecoratedTemplate>();

    public void dump() {
        dump(0);
    }

    private void dump(int i) {
        System.err.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t".substring(0, i) + raw.getId() + "\t" + raw.getName());
        for (DecoratedTemplate child : children) {
            child.dump(i + 1);
        }
    }

    public DecoratedTemplate(Template raw) {
        this.raw = raw;
    }

    public void addItem(DecoratedTemplate template) {
        children.add(template);
    }
}