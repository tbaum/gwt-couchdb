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

    public static ViewOptions create(String... docName) {
        ViewOptions opts = create();
        opts.setStartkey(docName);
        opts.setEndkey(docName);
        return opts;
    }

    public final void setStartkey(String... docName) {
        JsArrayString r = JavaScriptObject.createArray().cast();
        for (int i = 0, docNameLength = docName.length; i < docNameLength; i++) {
            r.set(i, docName[i]);
        }
        setStartkey(r);
    }

    public final void setEndkey(String... docName) {
        JsArrayString r = JavaScriptObject.createArray().cast();
        for (int i = 0, docNameLength = docName.length; i < docNameLength; i++) {
            r.set(i, docName[i]);
        }
        r.<JsArray<JavaScriptObject>>cast().set(docName.length, JavaScriptObject.createObject());
        setEndkey(r);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    protected ViewOptions() {
    }

// -------------------------- OTHER METHODS --------------------------

    public final native void setEndkey(JsArrayString endkey) /*-{ this.endkey=endkey; }-*/;

    public final native void setKey(String key) /*-{ this.key=key; }-*/;

    public final native void setStartkey(JsArrayString startkey) /*-{ this.startkey=startkey; }-*/;
}
