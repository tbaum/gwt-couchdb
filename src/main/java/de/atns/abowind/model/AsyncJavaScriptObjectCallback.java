package de.atns.abowind.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author tbaum
 * @since 04.08.2009 00:28:20
 */
public interface AsyncJavaScriptObjectCallback<T extends JavaScriptObject> extends AsyncCallback<T> {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface AsyncCallback ---------------------

    void onFailure(Throwable caught);

    void onSuccess(T result);
}
