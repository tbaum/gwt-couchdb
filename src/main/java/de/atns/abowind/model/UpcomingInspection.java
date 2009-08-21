package de.atns.abowind.model;

/**
 * @author tbaum
 * @since 21.08.2009 10:24:31
 */
public class UpcomingInspection {
// ------------------------------ FIELDS ------------------------------

    private final String id;
    private final String serialNumber;
    private final String name;
    private final String location;

// --------------------------- CONSTRUCTORS ---------------------------

    public UpcomingInspection(String id, String serialNumber, String name, String location) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.name = name;
        this.location = location;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}
