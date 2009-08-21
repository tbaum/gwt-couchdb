package de.atns.abowind.model;

import org.gwt.mosaic.ui.client.list.DefaultListModel;

/**
 * @author tbaum
 * @since 21.08.2009 10:24:20
 */
public class DemoData {
// -------------------------- STATIC METHODS --------------------------

    public static void fillUpcomingList(DefaultListModel<UpcomingInspection> model) {
        model.add(new UpcomingInspection("1", "2867abo23", "enercon alpha", "offshore park Heidesheim"));
        model.add(new UpcomingInspection("2", "2867abo24", "enercon alpha", "offshore park Heidesheim"));
        model.add(new UpcomingInspection("3", "2867abo25", "enercon gamma", "offshore park Heidesheim"));
        model.add(new UpcomingInspection("4", "2867abo44", "enercon omega", "offshore park Heidesheim"));
        model.add(new UpcomingInspection("6", "2867abo73a", "enercon omnichron", "offshore park Heidesheim"));
        model.add(new UpcomingInspection("7", "2867abo108-7x", "enercon fluxcompensator", "offshore park Heidesheim"));
        model.add(new UpcomingInspection("8", "2867abo108-8x", "enercon fluxcompensator", "offshore park Heidesheim"));
        model.add(new UpcomingInspection("9", "2867abo26", "enercon alpha", "offshore park Heidesheim"));
        model.add(new UpcomingInspection("10", "2867abo27", "enercon gamma", "offshore park Heidesheim"));
    }
}
