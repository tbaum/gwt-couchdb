package de.atns.playground.couchdb.client.model;

/**
 * @author tbaum
 * @since 10.08.2009 13:18:16
 */
public class TemplateSelection {
// ------------------------------ FIELDS ------------------------------

    private final String name;
    private final String id;

// --------------------------- CONSTRUCTORS ---------------------------

    public TemplateSelection(String name, String id) {
        this.name = name;
        this.id = id;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
