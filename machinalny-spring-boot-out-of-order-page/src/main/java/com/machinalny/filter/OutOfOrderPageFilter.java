package com.machinalny.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;


public class OutOfOrderPageFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(OutOfOrderPageFilter.class);

    private String outOfOrderMessage = "Page is currently out of order";
    private String outOfOrderFilePath;
    private boolean doReturnPage = false;

    public OutOfOrderPageFilter() {
        this.outOfOrderMessage = "Service is currently out of order";
    }

    public OutOfOrderPageFilter(String outOfOrderMessage, String outOfOrderFilePath) {

        LOG.info("Filter was initialised");

        if (nonNull(outOfOrderFilePath)) {
            this.outOfOrderFilePath = outOfOrderFilePath;
            this.doReturnPage = true;
        } else if (nonNull(outOfOrderMessage)) {
            this.outOfOrderMessage = outOfOrderMessage;
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG.info("doFilter is invoked");
        if (doReturnPage){
            chain.doFilter(request, response);
        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendError(503, this.outOfOrderMessage);
        }
        LOG.info("doFilter is ended");

    }

    @Override
    public void destroy() {
    }
}
