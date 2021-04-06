package com.epam.hr.domain.controller.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Sets encoding defined in the web.xml config to request and response
 */
public class EncodingFilter extends HttpFilter {
    private static final String ENCODING_PARAMETER_NAME = "encoding";
    private String encoding;

    @Override
    public void init(FilterConfig config) {
        encoding = config.getInitParameter(ENCODING_PARAMETER_NAME);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        String requestEncoding = request.getCharacterEncoding();
        if (encoding != null && !encoding.equalsIgnoreCase(requestEncoding)) {
            request.setCharacterEncoding(encoding);
            response.setCharacterEncoding(encoding);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}