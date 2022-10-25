package com.machinalny.filter;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;


public class OutOfOrderPageFilter extends GenericFilterBean {

    private String maintenancePageFilePath = "/static/out_of_order_page.html";

    public OutOfOrderPageFilter() {
    }

    public OutOfOrderPageFilter(String maintenancePageFilePath) {
        this.maintenancePageFilePath = maintenancePageFilePath;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        logger.info("doFilter is invoked");
        try (InputStream returnPageContent = getClass().getResourceAsStream(maintenancePageFilePath)) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.getOutputStream().write(returnPageContent.readAllBytes());
            logger.info("doFilter is ended");
            res.getOutputStream().flush();
        } catch (IOException ignored) {
        }
    }

    @Override
    public void destroy() {
    }

    public String getMaintenancePageFilePath() {
        return maintenancePageFilePath;
    }
}
