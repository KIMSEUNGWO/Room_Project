package project.study.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.member.dto.RequestJoinRoomDto;
import project.study.chat.domain.Chat;
import project.study.chat.domain.QChat;
import project.study.chat.domain.QChatHistory;
import project.study.chat.dto.ResponseChatHistory;
import project.study.chat.jparepository.ChatJpaRepository;
import project.study.controller.image.FileUpload;
import project.study.controller.image.FileUploadType;
import project.study.domain.*;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.dto.room.ResponseRoomMemberList;
import project.study.enums.AuthorityMemberEnum;
import project.study.enums.PublicEnum;
import project.study.exceptions.authority.joinroom.FullRoomException;
import project.study.exceptions.roomjoin.IllegalRoomException;
import project.study.jpaRepository.RoomJpaRepository;
import project.study.jpaRepository.RoomPasswordJpaRepository;
import project.study.jpaRepository.TagJpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class RoomRepository {

    private final RoomJpaRepository roomJpaRepository;
    private final TagJpaRepository tagJpaRepository;
    private final RoomPasswordJpaRepository roomPasswordJpaRepository;
    private final ChatJpaRepository chatJpaRepository;
    private final FileUpload fileUpload;
    private final JPAQueryFactory query;

    @Autowired
    public RoomRepository(RoomJpaRepository roomJpaRepository, TagJpaRepository tagJpaRepository, RoomPasswordJpaRepository roomPasswordJpaRepository, FileUpload fileUpload, EntityManager em, ChatJpaRepository chatJpaRepository) {
        this.roomJpaRepository = roomJpaRepository;
        this.tagJpaRepository = tagJpaRepository;
        this.chatJpaRepository = chatJpaRepository;
        this.roomPasswordJpaRepository = roomPasswordJpaRepository;
        this.fileUpload = fileUpload;
        this.query = new JPAQueryFactory(em);
    }

    public void validRoomTitle(String title) {

    }

    public void validRoomIntro(String intro) {

    }

    public void validRoomMaxPerson(String max) {

    }

    public void validTagList(List<String> tags) {

    }

    @Transactional
    public Room createRoom(RequestCreateRoomDto data) {
        Room saveRoom = Room.builder()
            .roomTitle(data.getTitle())
            .roomIntro(data.getIntro())
            .roomLimit(Integer.parseInt(data.getMax()))
            .roomPublic(data.getRoomPublic())
            .roomCreateDate(LocalDateTime.now())
            .build();
        return roomJpaRepository.save(saveRoom);
    }
    @Transactional
    public void createRoomImage(RequestCreateRoomDto data, Room room) {
        fileUpload.saveFile(data.getProfile(), FileUploadType.ROOM_PROFILE, room);
    }
    @Transactional
    public void createTags(RequestCreateRoomDto data, Room room) {
        List<String> tags = data.getTags();
        for (String tag : tags) {
            Tag saveTag = Tag.builder()
                .tagName(tag)
                .room(room)
                .build();
            tagJpaRepository.save(saveTag);
        }
    }

    @Transactional
    public void createPassword(RequestCreateRoomDto data, Room room) {
        if (data.getRoomPublic() == PublicEnum.PUBLIC) return;

        RoomPassword saveRoomPassword = RoomPassword.builder()
            .roomPassword(data.getPassword())
            .room(room)
            .build();

        roomPasswordJpaRepository.save(saveRoomPassword);
    }

    public void validFullRoom(RequestJoinRoomDto data) {
        Room room = data.getRoom();
        int maxPerson = room.getRoomLimit();
        int nowPerson = room.getJoinRoomList().size();
        if (nowPerson >= maxPerson) throw new FullRoomException(data.getResponse(), "방이 가득찼습니다.");
    }

    public Long getNumberFormat(String roomIdStr, HttpServletResponse response) {
        try {
            return Long.parseLong(roomIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalRoomException(response, "존재하지 않는 방입니다.");
        }
    }

    public Optional<Room> findById(Long roomId) {
        return roomJpaRepository.findById(roomId);
    }

    public List<ResponseRoomMemberList> getResponseRoomMemberList(Room room) {
        QJoinRoom j = QJoinRoom.joinRoom;
        QMember m = QMember.member;
        QProfile p = QProfile.profile;

        return query.select(Projections.fields(ResponseRoomMemberList.class,
                    m.memberId.as("memberId"),
                    p.profileStoreName.as("image"),
                    m.memberName.as("name"),
                    isManager(j.authorityEnum).as("isManager")
                ))
                .from(j)
                .join(m).on(j.member.eq(m))
                .leftJoin(p).on(p.member.eq(m))
                .where(j.room.eq(room))
                .fetch();
    }

    private BooleanExpression isManager(EnumPath<AuthorityMemberEnum> authorityEnum) {
        return authorityEnum.eq(AuthorityMemberEnum.방장);
    }

    public void createChat(Room room) {
        Chat saveChat = Chat.builder()
                .room(room)
                .build();
        chatJpaRepository.save(saveChat);
    }

    public List<ResponseChatHistory> findByChatHistory(Room room) {
        QRoom r = QRoom.room;
        QChat c = QChat.chat;
        QChatHistory ch = QChatHistory.chatHistory;
        QMember m = QMember.member;
        QProfile p = QProfile.profile;

        return query.select(Projections.fields(ResponseChatHistory.class,
                            m.memberId.as("memberId"),
                            p.profileStoreName.as("image"),
                            ch.message.as("message"),
                            ch.sendDate.as("date")
                            ))
                    .from(ch)
                    .leftJoin(c).on(ch.chat.eq(c))
                    .join(r).on(c.room.eq(r))
                    .join(m).on(ch.sendMember.eq(m))
                    .leftJoin(p).on(p.member.eq(m))
                    .where(r.eq(room))
                    .orderBy(ch.chatHistoryId.asc())
                    .limit(50)
                    .fetch();
    }
}
