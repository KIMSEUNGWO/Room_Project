package project.study.config.outh;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.study.domain.Member;
import project.study.jpaRepository.MemberJpaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService loadUserByUsername 실행");
        System.out.println("username = " + username);
        Member member = memberJpaRepository.findByAccount(username).orElseThrow(() -> {
            System.out.println("UsernameNotFoundException");
            return new UsernameNotFoundException("존재하지 않는 회원입니다.");
        });
        return new PrincipalDetails(member);
    }


}
