package project.study.config.outh;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.login.requestdto.RequestDefaultLoginDto;

import java.io.IOException;
import java.util.Map;

import static project.study.constant.WebConst.ERROR;

@RequiredArgsConstructor
public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final PrincipalDetailsService principalDetailsService;
    private final BCryptPasswordEncoder encode;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JsonUsernamePasswordAuthenticationFilter 시작");
        if ("application/json".equals(request.getContentType())) {
            try {
                RequestDefaultLoginDto loginDto = objectMapper.readValue(request.getInputStream(), RequestDefaultLoginDto.class);
                String username = loginDto.getAccount();
                String password = loginDto.getPassword();

                System.out.println("username = " + username);
                System.out.println("password = " + password);
                UserDetails user = principalDetailsService.loadUserByUsername(username);
                if (!encode.matches(password, user.getPassword())) {
                    System.out.println("BadCredentialsException");
                    throw new BadCredentialsException("Invalid username or password");
                }
                return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        initResponse(response, HttpStatus.OK);
        ResponseDto responseDto = new ResponseDto("로그인 성공");
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        initResponse(response, HttpStatus.OK);
        ResponseDto responseDto = new ResponseDto(ERROR, "아이디 또는 비밀번호가 잘못되었습니다.");
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        response.getWriter().flush();
    }

    private HttpServletResponse initResponse(HttpServletResponse response, HttpStatus status) {
        response.setStatus(status.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        return response;
    }
}
