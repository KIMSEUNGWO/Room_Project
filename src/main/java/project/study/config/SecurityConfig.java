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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.study.config.outh.JsonUsernamePasswordAuthenticationFilter;
import project.study.config.outh.PrincipalDetailsService;
import project.study.config.outh.ValidationFilter;
import project.study.enums.Role;

import static project.study.enums.Role.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalDetailsService principalDetailsService;

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
            );
            // TODO
            // OncePerRequestFilter 를 상속받아 사용했음에도 여러번 호출되는 문제확인 (모든 요청에 여러번 호출됨)
            // 여기에 구현해야할건 로그인 validation 에 있는것들 그대로 가져와야함.
            // input name이 loginAccount -> account 로 변경되면서 로그인화면에서 회원가입 이벤트리스너가 자꾸 작동됨. ( 회원가입 중복확인, 패스워드 일치여부 )
            // usernameParameter account -> loginAccount, password -> loginPassword로 다시 변경시키고 적용시키자!
//            .addFilterAfter(new ValidationFilter(), JsonUsernamePasswordAuthenticationFilter.class);

        http.logout(logout ->
            logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );

        return http.build();
    }

}
