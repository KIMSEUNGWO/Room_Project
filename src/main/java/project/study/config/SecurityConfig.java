package project.study.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import project.study.common.JsonConverter;
import project.study.config.outh.PrincipalDetailsService;
import project.study.dto.abstractentity.ResponseDto;

import static project.study.common.JsonConverter.execute;
import static project.study.constant.WebConst.*;
import static project.study.enums.Role.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {



    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests( request ->
                request
                    .requestMatchers("/room/**").authenticated()
                    .requestMatchers("/admin/**").hasRole(ADMIN.name())
                    .anyRequest().permitAll()
            );

        http.formLogin(formLogin ->
            formLogin
                .loginProcessingUrl("/login")
                .usernameParameter("login_account")
                .passwordParameter("login_password")
                .successHandler((req, res, auth) -> execute(res, new ResponseDto(OK, "로그인 성공")))
                .failureHandler((req, res, auth) -> execute(res, new ResponseDto(ERROR, "아이디 또는 비밀번호가 잘못되었습니다.")))
            );

        http.logout(logout ->
            logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );

        return http.build();
    }

}
