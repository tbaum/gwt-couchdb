package de.atns.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author tbaum
 * @since 04.08.2009 03:10:47
 */
public class ProxyServlet implements Servlet {
// ------------------------------ FIELDS ------------------------------

    private ServletConfig config;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Servlet ---------------------

    public void init(ServletConfig config) throws ServletException {
        this.config = config;
    }

    public ServletConfig getServletConfig() {
        return config;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();
        if (request.getQueryString() != null) {
            uri += "?" + request.getQueryString();
        }
        final URL url = new URL(request.getScheme(), request.getServerName(), 5984, uri);

        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setAllowUserInteraction(false);

        http.setRequestMethod(request.getMethod());
        http.setInstanceFollowRedirects(false);

        Enumeration enm = request.getHeaderNames();
        while (enm.hasMoreElements()) {
            String hdr = (String) enm.nextElement();


            Enumeration vals = request.getHeaders(hdr);
            while (vals.hasMoreElements()) {
                String val = (String) vals.nextElement();
                if (val != null) {
                    http.addRequestProperty(hdr, val);
                }
            }
        }

        try {
            http.setDoInput(true);
            InputStream in = request.getInputStream();
            if ("POST".equals(request.getMethod())) {
                http.setDoOutput(true);
                copy(in, http.getOutputStream());
            }
            http.connect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        InputStream proxy_in = http.getErrorStream();
        final int code = http.getResponseCode();
        response.setStatus(code, http.getResponseMessage());

        if (proxy_in == null && code == 200) {
            try {
                proxy_in = http.getInputStream();
            }
            catch (Exception e) {
                e.printStackTrace();
                proxy_in = http.getErrorStream();
            }
        }

        int h = 0;
        String hdr = http.getHeaderFieldKey(h);
        String val = http.getHeaderField(h);
        while (hdr != null || val != null) {
            if (hdr != null && val != null) {
                response.addHeader(hdr, val);
            }

            h++;
            hdr = http.getHeaderFieldKey(h);
            val = http.getHeaderField(h);
        }
        if (proxy_in != null)
            copy(proxy_in, response.getOutputStream());
    }

    public String getServletInfo() {
        return "Proxy Servlet";
    }

    public void destroy() {

    }

// -------------------------- OTHER METHODS --------------------------

    private void copy(InputStream in, OutputStream out) throws IOException {
        byte buffer[] = new byte[2 * 8192];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }
}
 