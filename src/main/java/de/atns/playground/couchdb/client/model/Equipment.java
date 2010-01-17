package de.atns.playground.couchdb.client.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tbaum
 * @since 06.08.2009 14:11:03
 */
public class Equipment extends JavaScriptObject {
// -------------------------- STATIC METHODS --------------------------

    public static native Equipment create(String id) /*-{
        return {_id:id, type:"equipment"};
    }-*/;

    public static Equipment create(final String id, String name) throws Exception {
        Equipment nt = create(id);
        nt.setName(name);
        return nt;
    }

    public final native void setName(String name) /*-{ this.name=name; }-*/;

// --------------------------- CONSTRUCTORS ---------------------------

    protected Equipment() {
    }

// -------------------------- OTHER METHODS --------------------------

    public final String dump() {
        return "Template{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", templates=" + getTemplates() +
                ", rev='" + getRev() + '\'' +
                '}';
    }

    public final native String getRev() /*-{ return this._rev; }-*/;

    public final List<String> getTemplates() {
        JsArrayString pt = getTemplates_();
        final int len = pt.length();
        List<String> rs = new ArrayList<String>(len);
        for (int i = 0; i < len; i++) {
            rs.add(pt.get(i));
        }
        return rs;
    }

    public final native JsArrayString getTemplates_() /*-{ return this.templates; }-*/;

    public final native String getName() /*-{ return this.name; }-*/;

    public final native String getId() /*-{ return this._id; }-*/;

    public final void setTemplates(List<String> p) {
        JsArrayString a = JavaScriptObject.createArray().cast();
        for (int i = 0, pLength = p.size(); i < pLength; i++) {
            a.set(i, p.get(i));
        }
        setTemplates_(a);
    }

    public final native void setTemplates_(JsArrayString templates)        /*-{ this.templates=templates; }-*/;
}