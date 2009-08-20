package de.atns.abowind.proto1;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

/**
 * @author tbaum
 * @since 11.08.2009 13:56:44
 */
public class ViewOptions extends JavaScriptObject {
// -------------------------- STATIC METHODS --------------------------

    public static ViewOptions create() {
        return JavaScriptObject.createObject().cast();
    }

    public static ViewOptions create(String docName) {
        ViewOptions opts = create();
        opts.setStartkey(docName);
        opts.setEndkey(docName);
        return opts;
    }

    public final void setStartkey(String docName) {
        JsArray r = JavaScriptObject.createArray().cast();
        r.<JsArrayString>cast().set(0, docName);
        setStartkey(r);
    }

    public final void setEndkey(String docName) {
        JsArray<JavaScriptObject> r = JavaScriptObject.createArray().cast();
        r.<JsArrayString>cast().set(0, docName);
        r.set(1, JavaScriptObject.createObject());
        setEndkey(r);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    protected ViewOptions() {
    }

// -------------------------- OTHER METHODS --------------------------

    public final native void setEndkey(JsArray endkey) /*-{ this.endkey=endkey; }-*/;

    public final native void setKey(String key) /*-{ this.key=key; }-*/;

    public final native void setStartkey(JsArray startkey) /*-{ this.startkey=startkey; }-*/;
}
