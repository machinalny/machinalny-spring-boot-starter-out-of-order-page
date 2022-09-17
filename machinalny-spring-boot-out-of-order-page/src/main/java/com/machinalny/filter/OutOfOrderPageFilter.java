package com.machinalny.filter;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;


public class OutOfOrderPageFilter extends GenericFilterBean {

    private String outOfOrderMessage = "Page is currently out of order";
    private Path pathToReturnContent;
    private boolean doReturnPage = false;

    public OutOfOrderPageFilter() {
        this.outOfOrderMessage = "Service is currently out of order";
    }

    public OutOfOrderPageFilter(String outOfOrderMessage) {
        this.outOfOrderMessage = outOfOrderMessage;
    }

    public OutOfOrderPageFilter(Path pathToReturnContent) {
        this.pathToReturnContent = pathToReturnContent;
        this.doReturnPage = true;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("doFilter is invoked");
        if (doReturnPage) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendError(503, this.outOfOrderMessage);
        }
        logger.info("doFilter is ended");

    }

    @Override
    public void destroy() {
    }

    public String getOutOfOrderMessage() {
        return outOfOrderMessage;
    }

    public Path getPathToReturnContent() {
        return pathToReturnContent;
    }

    public boolean isDoReturnPage() {
        return doReturnPage;
    }
}
