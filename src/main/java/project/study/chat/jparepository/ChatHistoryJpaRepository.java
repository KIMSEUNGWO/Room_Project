package project.study.chat.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.chat.domain.ChatHistory;

public interface ChatHistoryJpaRepository extends JpaRepository<ChatHistory, Long> {
}
