package com.machinalny.filter;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class OutOfOrderPageFilterTest {

    private final OutOfOrderPageFilter filter = new OutOfOrderPageFilter();


    @Test
    void outOfOrderPageTest() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(req, res, chain);
        assertThat(res.getStatus()).isEqualTo(503);
        assertThat(res.getErrorMessage()).isEqualTo("Service is currently out of order");
    }
}