package project.study.customAnnotation.argumentresolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import project.study.constant.WebConst;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.exceptions.authority.NotLoginMemberException;
import project.study.jpaRepository.MemberJpaRepository;

import java.util.Optional;

import static project.study.constant.WebConst.LOGIN_MEMBER;

@Slf4j
@RequiredArgsConstructor
public class SessionLoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasSessionLoginAnnotation = parameter.hasParameterAnnotation(SessionLogin.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasSessionLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("SessionLogin resolveArgument 실행");
        SessionLogin annotation = parameter.getParameterAnnotation(SessionLogin.class);
        boolean required = (annotation != null) ? annotation.required() : false;

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(LOGIN_MEMBER) == null) {
            if (required) throw new NotLoginMemberException(response);
            return null;
        }

        Long memberId = (Long) session.getAttribute(LOGIN_MEMBER);
        Optional<Member> findMember = memberJpaRepository.findById(memberId);
        if (findMember.isEmpty()) {
            if (required) {
                throw new NotLoginMemberException(response);
            } else {
                return null;
            }
        }

        return findMember.get();
    }
}
