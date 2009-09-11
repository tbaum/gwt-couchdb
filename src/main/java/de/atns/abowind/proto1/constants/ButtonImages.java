package de.atns.abowind.proto1.constants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * @author tbaum
 * @since 14.08.2009 18:23:05
 */
public interface ButtonImages extends ImageBundle {
// ------------------------------ FIELDS ------------------------------

    static final ButtonImages BUTTON_IMAGES = GWT.create(ButtonImages.class);

// -------------------------- OTHER METHODS --------------------------

    AbstractImagePrototype addTemplate();
    AbstractImagePrototype insertInside();
    AbstractImagePrototype insertAfter();
}
