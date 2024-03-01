package project.study.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;
import project.study.constant.WebConst;
import project.study.domain.Freeze;
import project.study.domain.Member;
import project.study.domain.MockMember;
import project.study.dto.login.requestdto.RequestDefaultLoginDto;
import project.study.exceptions.login.ExpireMemberLoginException;
import project.study.exceptions.login.FreezeMemberLoginException;
import project.study.jpaRepository.FreezeJpaRepository;
import project.study.jpaRepository.MemberJpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static project.study.enums.MemberStatusEnum.*;

@SpringBootTest
@Transactional
class BasicLoginServiceTest {

    @Autowired
    private LoginService loginService;
    @Autowired
    private MockMember mockMember;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private FreezeJpaRepository freezeJpaRepository;

    @Test
    @DisplayName("일반회원 로그인 정상흐름")
    void basicMemberLoginValid() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        // 정상적인 회원
        Member basicMember = mockMember.createMember().setBasic().build();

        RequestDefaultLoginDto data = new RequestDefaultLoginDto();
        data.setAccount("test" + (basicMember.getMemberId() - 1));
        data.setPassword("!@#QWEasd" + (basicMember.getMemberId() - 1));

        // when
        loginService.login(data, session, response);
        Long loginMemberId = (Long) session.getAttribute(WebConst.LOGIN_MEMBER);

        // then
        assertThat(loginMemberId).isEqualTo(basicMember.getMemberId());
    }

    @Test
    @DisplayName("이용정지된 회원은 로그인할 수 없다")
    void basicMemberLoginInValid1() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        // 이용정지기간이 하루 남은 회원
        Member basicMember = mockMember.createMember().setBasic().setFreeze(LocalDateTime.now().plusDays(1)).build();

        RequestDefaultLoginDto data = new RequestDefaultLoginDto();
        data.setAccount("test" + (basicMember.getMemberId() - 1));
        data.setPassword("!@#QWEasd" + (basicMember.getMemberId() - 1));

        // when
        assertThatThrownBy(() -> loginService.login(data, session, response))
            .isInstanceOf(FreezeMemberLoginException.class);
    }

    @Test
    @DisplayName("이용정지 기간이 종료된 회원은 로그인할 수 있다")
    void basicMemberLoginInValid2() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        // 이용정지기간이 어제까지였던 회원
        Member basicMember = mockMember.createMember().setBasic().setFreeze(LocalDateTime.now().minusDays(1)).build();

        RequestDefaultLoginDto data = new RequestDefaultLoginDto();
        data.setAccount("test" + (basicMember.getMemberId() - 1));
        data.setPassword("!@#QWEasd" + (basicMember.getMemberId() - 1));

        // 로그인 이전까지는 이용정지가 지속되어야한다.
        assertThat(basicMember.isFreezeMember()).isTrue();
        Optional<Freeze> beforeFreeze = freezeJpaRepository.findByMember_MemberId(basicMember.getMemberId());
        assertThat(beforeFreeze.isPresent()).isTrue();

        // 예외가 안나야한다.
        assertThatCode(() -> loginService.login(data, session, response))
            .doesNotThrowAnyException();

        // 이용정지가 해제되었다.
        assertThat(basicMember.isFreezeMember()).isFalse();
        Optional<Freeze> afterFreeze = freezeJpaRepository.findByMember_MemberId(basicMember.getMemberId());
        assertThat(afterFreeze.isEmpty()).isTrue();

    }
    @Test
    @DisplayName("탈퇴한 회원은 로그인할 수 없다")
    void basicMemberLoginInValid3() {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        Member basicMember = mockMember.createMember().setBasic().setExpire().build();

        RequestDefaultLoginDto data = new RequestDefaultLoginDto();
        data.setAccount("test" + (basicMember.getMemberId() - 1));
        data.setPassword("!@#QWEasd" + (basicMember.getMemberId() - 1));

        // when
        assertThatThrownBy(() -> loginService.login(data, session, response))
            .isInstanceOf(ExpireMemberLoginException.class);
    }




    @Test
    void basicMemberSignup() {
    }
}