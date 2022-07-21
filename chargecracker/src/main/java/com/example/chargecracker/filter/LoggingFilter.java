package com.example.chargecracker.filter;

import com.example.chargecracker.dao.StatisticDAO;
import com.example.chargecracker.dao.impl.StatisticDAOImpl;
import com.example.chargecracker.model.Statistic;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();

        if (!uri.contains("/admin")) {
            String method = request.getMethod();
            Statistic statistic = new Statistic();
            statistic.setUri(uri);
            statistic.setOperationType(method);
            statistic.setCreatedAt(new Date());
            statistic.setBusinessOperationName(getOperationName(uri));

            StatisticDAO statisticDAO = new StatisticDAOImpl();
            statisticDAO.create(statistic);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public String getOperationName(String uri) {
        if (uri.equals("/")) {
            return "main page";
        } else if (uri.equals("/report")) {
            return "report page";
        } else if (uri.equals("/login")) {
            return "login page";
        } else if (uri.equals("/registration")) {
            return "registration page";
        } else if (uri.matches("/station/\\d")) {
            return "station info page";
        } else if (uri.equals("/user/favourite-stations")) {
            return "user favourite page";
        } else if (uri.equals("/user/profile")) {
            return "user profile page";
        } else if (uri.equals("/user/create-auto")) {
            return "user create auto page";
        } else if (uri.equals("/user/update-auto")) {
            return "user update auto page";
        } else if (uri.equals("/user-update")) {
            return "user update info page";
        } else if (uri.equals("/user-password-update")) {
            return "user update password page";
        } else if (uri.equals("/user/reservations")) {
            return "user reservations page";
        }
        return null;
    }

    @Bean
    public FilterRegistrationBean<LoggingFilter> filterRegistrationBean() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoggingFilter());
        registrationBean.setEnabled(false);
        return registrationBean;
    }
}
