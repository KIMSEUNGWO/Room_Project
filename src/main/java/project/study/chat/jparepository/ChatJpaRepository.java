package project.study.chat.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.chat.domain.Chat;

public interface ChatJpaRepository extends JpaRepository<Chat, Long> {
}
