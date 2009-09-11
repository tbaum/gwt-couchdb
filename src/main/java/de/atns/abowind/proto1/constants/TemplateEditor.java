package de.atns.abowind.proto1.constants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

/**
 * @author tbaum
 * @since 14.08.2009 19:18:49
 */
public interface TemplateEditor extends Messages {
// ------------------------------ FIELDS ------------------------------

    static final TemplateEditor ACTION = GWT.create(TemplateEditor.class);

// -------------------------- OTHER METHODS --------------------------

    String create();

    String question();

    String structure();
}
