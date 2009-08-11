package de.atns.abowind.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

import java.util.Arrays;

/**
 * @author tbaum
 * @since 06.08.2009 14:11:03
 */
public class Template extends JavaScriptObject {
// -------------------------- STATIC METHODS --------------------------

    public static native Template create() /*-{
        return {type:"template"};
    }-*/;

// --------------------------- CONSTRUCTORS ---------------------------

    protected Template() {
    }

// -------------------------- OTHER METHODS --------------------------

    public final String dump() {
        return "Template{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", path=" + (getPath() == null ? null : Arrays.asList(getPath())) +
                ", rev='" + getRev() + '\'' +
                '}';
    }

    public final native String getRev() /*-{ return this._rev; }-*/;

    public final native String getName() /*-{ return this.name; }-*/;

    public final native String getId() /*-{ return this._id; }-*/;

    public final native JsArrayString getPath() /*-{ return this.path; }-*/;

    public final native void setName(String name) /*-{ this.name=name; }-*/;

    public final void setPath(String... p) {
        JsArrayString a = JavaScriptObject.createArray().cast();
        for (int i = 0, pLength = p.length; i < pLength; i++) {
            a.set(i, p[i]);
        }
        setPath(a);
    }

    public final native void setPath(JsArrayString path)        /*-{ this.path=path; }-*/;
}
