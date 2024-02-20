package project.study.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.study.chat.jparepository.ChatHistoryJpaRepository;
import project.study.chat.jparepository.ChatJpaRepository;


@Repository
@Slf4j
@RequiredArgsConstructor
public class ChatRepositoryOracle implements ChatRepository {

    private final ChatJpaRepository chatJpaRepository;
    private final ChatHistoryJpaRepository chatHistoryJpaRepository;




}
