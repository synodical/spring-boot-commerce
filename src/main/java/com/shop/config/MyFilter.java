package com.shop.config;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.io.IOException;

@Component
public class MyFilter implements Filter {
   @Override
   public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
           throws ServletException, IOException {
      chain.doFilter(request, response);
   }
   @Override
   public void init(final FilterConfig filterConfig) throws ServletException {
      Filter.super.init(filterConfig);
   }
   @Override
   public void destroy() {
      Filter.super.destroy();
   }
}
