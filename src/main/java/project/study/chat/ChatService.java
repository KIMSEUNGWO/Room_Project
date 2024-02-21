package project.study.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.study.chat.component.ChatAccessToken;
import project.study.chat.component.ChatCurrentMemberManager;
import project.study.chat.dto.ChatDto;
import project.study.chat.dto.ChatMemberListDto;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.jpaRepository.RoomJpaRepository;

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

    public ChatMemberListDto changeDto(ChatDto chat) {
        ChatMemberListDto data = new ChatMemberListDto();

        data.setRoomId(chat.getRoomId());
        data.setMessage(chat.getMessage());
        data.setTime(chat.getTime());
        data.setType(chat.getType());
        data.setToken(chat.getToken());
        data.setSenderImage(chat.getSenderImage());
        data.setSender(chat.getSender());
        data.setCurrentMemberList(currentMemberManager.getMemberList(chat.getRoomId()));

        return data;
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
}