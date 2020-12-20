package com.epam.hr.domain.logic.filter;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

public class EncodingFilter extends HttpFilter {
    private static final String ENCODING_PARAMETER_NAME = "encoding";
    private String encoding;

    @Override
    public void init(FilterConfig config) {
        encoding = config.getInitParameter(ENCODING_PARAMETER_NAME);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        String codeRequest = request.getCharacterEncoding();
        if (encoding != null && !encoding.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(encoding);
            response.setCharacterEncoding(encoding);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}