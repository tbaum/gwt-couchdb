package de.atns.abowind.model;

import java.util.List;

/**
 * @author tbaum
 * @since 10.08.2009 13:18:16
 */
public class StatusGroupSelection {
// ------------------------------ FIELDS ------------------------------

    private final String name;
    private final String id;
    private final List<String> options;

// --------------------------- CONSTRUCTORS ---------------------------

    public StatusGroupSelection(String name, String id, final List<String> options) {
        this.name = name;
        this.id = id;
        this.options = options;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getOptions() {
        return options;
    }
}
