package com.bean.api.util;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("FSDFsd");
        String accessToken = request.getHeader("Authorization");
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7); // Remove "Bearer " prefix
            boolean isValidToken = ValidateToken.validate(accessToken);
            if (isValidToken) {
                return true; // Proceed with the request
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false; // Abort the request
    }
}
