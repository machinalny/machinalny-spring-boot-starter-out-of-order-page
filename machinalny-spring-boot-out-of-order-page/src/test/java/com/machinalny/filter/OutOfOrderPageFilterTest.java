package com.machinalny.filter;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class OutOfOrderPageFilterTest {

    private final OutOfOrderPageFilter filter = new OutOfOrderPageFilter();
    private String maintenancePageFilePath = "/static/out_of_order_page.html";
    private String customMaintenancePageFilePath = "/static/out_of_order_page_custom.html";

    @Test
    void outOfOrderPageDefaultTest() throws IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(req, res, chain);
        assertThat(res.isCommitted()).isTrue();
//        assertThat(res.getStatus()).isEqualTo(503);
    }


}