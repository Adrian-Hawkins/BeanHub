package com.bean.api.interceptors;

import com.bean.api.util.ValidateToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // String accessToken = request.getHeader("Authorization");
        // if (accessToken != null && accessToken.startsWith("Bearer ")) {
        //     accessToken = accessToken.substring(7);
        //     boolean isValidToken = ValidateToken.validate(accessToken);
        //     if (isValidToken) {
        //         return true;
        //     }
        // }
        // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // return false;
        return true;
    }

}
