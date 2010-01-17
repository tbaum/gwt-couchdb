package de.atns.playground.couchdb.client.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tbaum
 * @since 06.08.2009 14:11:03
 */
public class Template extends JavaScriptObject {
// -------------------------- STATIC METHODS --------------------------

    public static native Template create(String id) /*-{
        return {_id:id, type:"template"};
    }-*/;

    public static Template create(final String id, String name, List<String> path) throws Exception {
        Template nt = create(id);
        List<String> p = new ArrayList<String>(path);
        p.add(nt.getId());
        nt.setPath(p);

        nt.setName(name);
        return nt;
    }

    public final native String getId() /*-{ return this._id; }-*/;

    public final void setPath(List<String> p) {
        JsArrayString a = JavaScriptObject.createArray().cast();
        for (int i = 0, pLength = p.size(); i < pLength; i++) {
            a.set(i, p.get(i));
        }
        setPath_(a);
    }

    public final native void setPath_(JsArrayString path)        /*-{ this.path=path; }-*/;

    public final native void setName(String name) /*-{ this.name=name; }-*/;

// --------------------------- CONSTRUCTORS ---------------------------

    public final native void setStatusGroup(String id) /*-{ this.statusGroup=id; }-*/;

    public final native String getStatusGroup() /*-{ return statusGroup; }-*/;


    protected Template() {
    }

// -------------------------- OTHER METHODS --------------------------

    public final String dump() {
        return "Template{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", path=" + getPath() +
                ", rev='" + getRev() + '\'' +
                '}';
    }

    public final native String getRev() /*-{ return this._rev; }-*/;

    public final List<String> getPath() {
        JsArrayString pt = getPath_();
        final int len = pt.length();
        List<String> rs = new ArrayList<String>(len);
        for (int i = 0; i < len; i++) {
            rs.add(pt.get(i));
        }
        return rs;
    }

    public final native JsArrayString getPath_() /*-{ return this.path; }-*/;

    public final native String getName() /*-{ return this.name; }-*/;
}
