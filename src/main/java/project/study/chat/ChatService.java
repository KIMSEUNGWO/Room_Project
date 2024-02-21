package project.study.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.study.chat.component.ChatAccessToken;
import project.study.chat.component.ChatCurrentMemberManager;
import project.study.chat.dto.ChatDto;
import project.study.chat.dto.ChatMemberListDto;
import project.study.chat.dto.ChatNextManagerDto;
import project.study.chat.dto.ResponseRoomUpdateInfo;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.jpaRepository.RoomJpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final RoomJpaRepository roomJpaRepository;
    private final ChatAccessToken chatAccessToken;
    private final ChatCurrentMemberManager currentMemberManager;

    public Member getMember(Long memberId, Long roomId) {
        Long accessMemberId = chatAccessToken.getMemberId(memberId, roomId);
        Optional<Member> findMember = memberJpaRepository.findById(accessMemberId);
        return findMember.get();
    }

    public void accessCount(ChatDto chat, Member member) {
        currentMemberManager.plus(chat.getRoomId(), member.getMemberNickname());
    }

    public ChatMemberListDto changeToMemberListDto(ChatDto chat) {
        return new ChatMemberListDto(chat, currentMemberManager.getMemberList(chat.getRoomId()));
    }

    public void accessRemove(Long memberId, Long roomId, String nickname) {
        chatAccessToken.remove(memberId);
        currentMemberManager.minus(roomId, nickname);
    }

    public Room findByRoom(Long roomId) {
        return roomJpaRepository.findById(roomId).get();
    }

    public void saveChat(ChatDto chat, Member member, Room room) {
        chatRepository.saveChat(chat, member, room);
    }

    public ResponseRoomUpdateInfo getResponseRoomUpdateInfo(Room room) {
        return ResponseRoomUpdateInfo.builder()
                .isPublic(room.isPublic())
                .title(room.getRoomTitle())
                .max(room.getRoomLimit())
                .build();
    }

    public ChatNextManagerDto exitRoom(Member member, Room room) {
        ChatDto chat = ChatDto.builder()
                .roomId(room.getRoomId())
                .time(LocalDateTime.now())
                .type(MessageType.EXIT)
                .sender(member.getMemberNickname())
                .message(member.getMemberNickname() + "님이 방에서 나가셨습니다.")
                .build();
        Member nextManagerMember = room.getJoinRoomList()
                .stream()
                .filter(x -> !x.getMember().equals(member) && x.getAuthority().isManager())
                .map(x -> x.getMember())
                .findFirst()
                .get();

        return new ChatNextManagerDto(chat, nextManagerMember.getMemberNickname(), nextManagerMember.getMemberId());
    }
}
