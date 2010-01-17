package de.atns.playground.couchdb.client.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tbaum
 * @since 06.08.2009 14:11:03
 */
public class StatusGroup extends JavaScriptObject {
// -------------------------- STATIC METHODS --------------------------

    public static native StatusGroup create(String id) /*-{
        return {_id:id, type:"statusgroup", options:[]};
    }-*/;

    public static StatusGroup create(final String id, String name ) throws Exception {
        StatusGroup nt = create(id);

        nt.setName(name);
        return nt;
    }

    public final native String getId() /*-{ return this._id; }-*/;




    public final void setStatusOptions(final List<String> statusOptions) {
        JsArrayString pt = getStatusOptions_();
        final int len = statusOptions.size();
        for (int i = 0; i < len; i++) {
            pt.set(i, pt.get(i));
        }
    }

    public final native void setName(String name) /*-{ this.name=name; }-*/;

// --------------------------- CONSTRUCTORS ---------------------------

    protected StatusGroup() {
    }

// -------------------------- OTHER METHODS --------------------------

    public final void addStatusOption(String option) {
        JsArrayString pt = getStatusOptions_();
        pt.set(pt.length() + 1, option);
        setStatusOptions_(pt);
    }

    public final native JsArrayString getStatusOptions_() /*-{ return this.statusOptions; }-*/;

    public final native void setStatusOptions_(final JsArrayString statusOptions) /*-{  this.statusOptions = statusOptions; }-*/;

    public final String dump() {
        return "StatusGroup{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", rev='" + getRev() + '\'' +
                '}';
    }

    public final native String getRev() /*-{ return this._rev; }-*/;


    public final native String getName() /*-{ return this.name; }-*/;

    public final List<String> getStatusOptions() {
        JsArrayString pt = getStatusOptions_();
        final int len = pt.length();
        List<String> rs = new ArrayList<String>(len);
        for (int i = 0; i < len; i++) {
            rs.add(pt.get(i));
        }
        return rs;
    }

}
