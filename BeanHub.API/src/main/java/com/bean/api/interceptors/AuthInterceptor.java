package com.bean.api.interceptors;

import com.bean.api.util.ValidateToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return true;
//    }
    private Logger LOG = LoggerFactory.getLogger(AuthInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");
        LOG.info("preHandle invoked...{}:{}     {}"+request.getRequestURI(),request.getMethod(), accessToken);
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

//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
//    }
//
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
//    }

}
