package project.study.config.outh;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRefreshProvider {

    private final PrincipalDetailsService principalDetailsService;

    public void refresh(UserDetails userDetails) {
        String email = userDetails.getUsername();

        UserDetails newUserDetails = principalDetailsService.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUserDetails, newUserDetails.getPassword(), newUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("데이터 새로고침");
    }

}
