package project.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import project.study.authority.admin.authority.*;
import project.study.authority.member.authority.ManagerAuthority;
import project.study.authority.member.authority.ManagerAuthorityImpl;
import project.study.authority.member.authority.MemberAuthority;
import project.study.authority.member.authority.MemberAuthorityImpl;

@Configuration
public class AuthorityConfig {

    @Bean
    @Primary
    public BanAuthority banAuthority() {
        return new BanAuthorityImpl();
    }

    @Bean
    @Primary
    public MemberInfoAuthority memberInfoAuthority() {
        return new MemberInfoAuthorityImpl();
    }
    @Bean
    @Primary
    public NotifyMemberInfoAuthority notifyMemberInfoAuthority() {
        return new NotifyMemberInfoAuthorityImpl();
    }
    @Bean
    @Primary
    public RoomInfoAuthority roomInfoAuthority() {
        return new RoomInfoAuthorityImpl();
    }
    @Bean
    @Primary
    public ManagerAuthority managerAuthority() {
        return new ManagerAuthorityImpl();
    }
    @Bean
    @Primary
    public MemberAuthority memberAuthority() {
        return new MemberAuthorityImpl();
    }
}
