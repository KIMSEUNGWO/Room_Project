package project.study.config.outh;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import project.study.domain.Member;

import java.io.IOException;

public class ValidationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("ValidationFilter 시작");
        if (authentication != null && authentication.isAuthenticated()) {
            PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
            Member member = user.getMember();
        }


        filterChain.doFilter(request, response);
    }
}
