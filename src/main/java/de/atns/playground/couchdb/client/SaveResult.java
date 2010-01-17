package de.atns.playground.couchdb.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author mwolter
 * @since 10.09.2009 18:16:04
 */
public class SaveResult extends JavaScriptObject {
// --------------------------- CONSTRUCTORS ---------------------------

    protected SaveResult() {
    }

// -------------------------- OTHER METHODS --------------------------

    public final String dump() {
        return "SaveResult{id=" + getId() + ", rev=" + getRev() + (isOk() ? ", ok" : ", error=" + getError() + "/" + getReason()) + '}';
    }

    public final native boolean isOk() /*-{ return this.ok; }-*/;

    public final native String getReason() /*-{ return this.reason; }-*/;

    public final native String getError() /*-{ return this.error; }-*/;

    public final native String getRev() /*-{ return this.rev; }-*/;

    public final native String getId() /*-{ return this.id; }-*/;
}
