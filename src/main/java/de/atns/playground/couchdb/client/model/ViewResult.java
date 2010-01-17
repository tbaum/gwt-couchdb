package de.atns.playground.couchdb.client.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author tbaum
 * @since 13.07.2009 10:08:22
 */
public class ViewResult<T extends JavaScriptObject> extends JavaScriptObject implements Serializable {
// --------------------------- CONSTRUCTORS ---------------------------

    protected ViewResult() {
    }

// -------------------------- OTHER METHODS --------------------------

    public final native int getOffset() /*-{ return this.offset; }-*/;

    public final native int getTotalRows() /*-{ return this.total_rows; }-*/;

    public final List<T> toList() {
        List<T> result = new ArrayList<T>();
        for (ViewResultEntry<T> entry : iterator()) {
            result.add(entry.getValue());
        }
        return result;
    }

    public final Iterable<? extends ViewResultEntry<T>> iterator() {
        return new Iterable<ViewResultEntry<T>>() {
            public Iterator<ViewResultEntry<T>> iterator() {
                final JsArray<ViewResultEntry<T>> r = getRowsRaw();
                return new Iterator<ViewResultEntry<T>>() {
                    int pos = 0;

                    public boolean hasNext() {
                        return pos < r.length();
                    }

                    public ViewResultEntry<T> next() {
                        return r.get(pos++);
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("remove not supported");
                    }
                };
            }
        };
    }

    public final native JsArray<ViewResultEntry<T>> getRowsRaw() /*-{ return this.rows; }-*/;
}