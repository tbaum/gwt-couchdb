package de.atns.abowind.proto1;

/**
 * @author tbaum
 * @since 12.09.2009 17:59:43
 */
public interface Callback<T> {
// -------------------------- OTHER METHODS --------------------------

    void handle(T template);
}
