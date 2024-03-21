package project.study.chat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.study.domain.*;
import project.study.chat.dto.ChatDto;
import project.study.chat.jparepository.ChatJpaRepository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Slf4j
public class ChatRepositoryOracle implements ChatRepository {

    private final ChatJpaRepository chatJpaRepository;
    private final JPAQueryFactory query;


    @Override
    public void saveChat(ChatDto chat, Member member, Room room) {
        Chat saveChat = Chat.builder()
                .room(room)
                .sendMember(member)
                .message(chat.getMessage())
                .sendDate(chat.getTime())
                .build();
        chatJpaRepository.save(saveChat);
    }

    @Override
    public List<String> findRecentlyHistoryMemberNickname(Long memberId, Room room) {
        return query.selectDistinct(QChat.chat.sendMember.memberNickname)
            .from(QChat.chat)
            .where(QRoom.room.eq(room).and(QChat.chat.sendMember.memberId.ne(memberId)))
            .limit(100)
            .fetch();
    }

    @Override
    public List<Chat> findByChatHistory(Room room) {
        return query.select(QChat.chat)
            .from(QChat.chat)
            .where(QRoom.room.eq(room))
            .limit(50)
            .fetch();
    }

    @Override
    public Optional<Member> findByMemberNickname(Room room, String nickname) {
        Member member = query.select(QChat.chat.sendMember)
            .from(QChat.chat)
            .where(QRoom.room.eq(room).and(QChat.chat.sendMember.memberNickname.eq(nickname)))
            .fetchFirst();
        if (member == null) return Optional.empty();
        return Optional.of(member);
    }
}
