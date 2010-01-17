package de.atns.playground.couchdb.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author tbaum
 * @since 11.08.2009 13:15:18
 */
public class CouchDB extends JavaScriptObject {
// -------------------------- STATIC METHODS --------------------------

    public static native CouchDB create(String name) /*-{ return new $wnd.CouchDB(name); }-*/;

// --------------------------- CONSTRUCTORS ---------------------------

    protected CouchDB() {
    }

// -------------------------- OTHER METHODS --------------------------

    public final native String newUuid() throws Exception /*-{ return this.newUuid(); }-*/;

    //TODO create own exception-tree
    public final native <T extends JavaScriptObject> T open(String id) /*-{ return this.open(id); }-*/;

    public final native <T extends JavaScriptObject> SaveResult save(T document) throws Exception /*-{ return this.save(document); }-*/;

    public final native <T extends JavaScriptObject> T view(String name) /*-{ return this.view(name); }-*/;

    public final native <T extends JavaScriptObject> T view(String name, ViewOptions options) /*-{ return this.view(name,options); }-*/;
}
