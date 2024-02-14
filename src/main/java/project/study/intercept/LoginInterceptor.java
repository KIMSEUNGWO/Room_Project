package project.study.intercept;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession();
        if(session.getAttribute("memberId")== null) {
            System.out.println(requestURI);
            response.sendRedirect("/login?redirectURL=" + requestURI);

            return false;
        }

        return true;
    }
}
