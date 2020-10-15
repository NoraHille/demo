package com.example.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.lang.model.type.ErrorType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class FiltersConfiguration {

    private static final int INTERNAL_PORT = 9091;
    private static final int EXTERNAL_PORT = 9092;

    @Bean(name = "internalRequestsRestrictionFilter")
    public FilterRegistrationBean<OncePerRequestFilter> internalRequestsRestrictionFilter() {
        return createPortFilterForUrlPattern("internalRequestsFilter", INTERNAL_PORT, "/internal/*");
    }

    @Bean(name = "externalRequestsRestrictionFilter")
    public FilterRegistrationBean<OncePerRequestFilter> externalRequestsRestrictionFilter() {
        return createPortFilterForUrlPattern("externalRequestsFilter", EXTERNAL_PORT, "/external/*");
    }


    private static FilterRegistrationBean<OncePerRequestFilter> createPortFilterForUrlPattern(final String filterName,
                                                                                              final Integer targetPort, final String urlPattern) {
        final FilterRegistrationBean<OncePerRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new PortFilter(targetPort));
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filterRegistrationBean.setName(filterName);
        filterRegistrationBean.addUrlPatterns(urlPattern);
        return filterRegistrationBean;
    }

    private static class PortFilter extends OncePerRequestFilter {

        private final Integer allowedPort;

        PortFilter(final Integer allowedPort) {
            this.allowedPort = allowedPort;
        }

        @Override
        protected void doFilterInternal(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final FilterChain filterChain) throws IOException, ServletException {
            if (request.getLocalPort() == allowedPort) {
                filterChain.doFilter(request, response);
            } else {
                response.sendError(404);
            }
        }
    }
}
