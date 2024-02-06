package project.study.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.study.domain.Freeze;
import project.study.jpaRepository.FreezeJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FreezeRepository {

    private final FreezeJpaRepository freezeJpaRepository;


    public Optional<Freeze> findByMemberId(Long memberId) {
        return freezeJpaRepository.findByMember_MemberId(memberId);
    }

    public void delete(Freeze freeze) {
        freezeJpaRepository.delete(freeze);
    }
}
