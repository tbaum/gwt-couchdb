package de.atns.abowind.proto1.model;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author tbaum
 * @since 06.08.2009 14:09:03
 */
public class ViewResultEntry<T extends JavaScriptObject> extends JavaScriptObject {
// --------------------------- CONSTRUCTORS ---------------------------

    protected ViewResultEntry() {
    }

// -------------------------- OTHER METHODS --------------------------

    public final native String getId() /*-{ return this.id; }-*/;

    public final native JavaScriptObject getKey() /*-{ return this.key; }-*/;

    public final native T getValue() /*-{ return this.value; }-*/;
}
