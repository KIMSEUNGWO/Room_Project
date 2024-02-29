package project.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.study.authority.admin.dto.RequestNotifyMemberFreezeDto;
import project.study.domain.Freeze;
import project.study.domain.Member;
import project.study.domain.QMember;
import project.study.jpaRepository.FreezeJpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static project.study.domain.QMember.*;

@Repository
@Slf4j
public class FreezeRepository {

    private final FreezeJpaRepository freezeJpaRepository;
    private final JPAQueryFactory query;

    public FreezeRepository(FreezeJpaRepository freezeJpaRepository, EntityManager em) {
        this.freezeJpaRepository = freezeJpaRepository;
        this.query = new JPAQueryFactory(em);
    }

    public Optional<Freeze> findByMemberId(Long memberId) {
        return freezeJpaRepository.findByMember_MemberId(memberId);
    }

    public void delete(Freeze freeze, Member member) {
        freezeJpaRepository.delete(freeze);
        member.changeStatusToNormal();
    }

    public void save(RequestNotifyMemberFreezeDto dto){

        Member member1 = query
            .select(member)
            .from(member)
            .where(member.memberId.eq(dto.getMemberId()))
            .fetchOne();

        Freeze freeze = Freeze.builder()
            .member(member1)
            .freezeEndDate(LocalDateTime.now().plusDays(dto.getFreezePeriod()))
            .freezeReason(dto.getFreezeReason())
            .build();
        freezeJpaRepository.save(freeze);
    }
}
