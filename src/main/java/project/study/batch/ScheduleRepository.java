package project.study.batch;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.study.chat.jparepository.ChatJpaRepository;
import project.study.controller.image.FileTypeConverter;
import project.study.controller.image.FileUpload;
import project.study.controller.image.FileUploadType;
import project.study.domain.*;
import project.study.enums.MemberStatusEnum;
import project.study.enums.NotifyStatus;
import project.study.jpaRepository.*;
import project.study.service.JoinRoomService;

import java.util.List;

import static project.study.domain.QNotify.*;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ScheduleRepository {


    private final FileTypeConverter fileTypeConverter;

    private final MemberJpaRepository memberJpaRepository;
    private final BasicJpaRepository basicJpaRepository;
    private final SocialTokenJpaRepository socialTokenJpaRepository;
    private final SocialJpaRepository socialJpaRepository;
    private final ProfileJpaRepository profileJpaRepository;
    private final FreezeJpaRepository freezeJpaRepository;

    private final RoomNoticeJpaRepository roomNoticeJpaRepository;
    private final RoomImageJpaRepository roomImageJpaRepository;
    private final RoomPasswordJpaRepository roomPasswordJpaRepository;
    private final TagJpaRepository tagJpaRepository;
    private final RoomDeleteJpaRepository roomDeleteJpaRepository;
    private final ChatJpaRepository chatJpaRepository;
    private final RoomJpaRepository roomJpaRepository;

    private final JoinRoomService joinRoomService;
    private final JPAQueryFactory query;

    public void deleteJoinRoom(Member member) {
        member.getJoinRoomList().iterator().forEachRemaining(joinRoomService::exitRoom);
    }

    public void deleteAccount(Member member) {
        if (member.isBasicMember()) {
            basicJpaRepository.delete(member.getBasic());
        }
        if (member.isSocialMember()) {
            socialTokenJpaRepository.delete(member.getSocial().getToken());
            socialJpaRepository.delete(member.getSocial());
        }
    }

    public void deleteFreeze(Member member) {
        freezeJpaRepository.deleteAll(member.getFreeze());
    }

    public void deleteMember(Member member) {
        fileTypeConverter.deleteFile(FileUploadType.MEMBER_PROFILE, member);
        profileJpaRepository.delete(member.getProfile());
        memberJpaRepository.delete(member);
    }

    public boolean hasNotifyAsCriminal(Member member) {
        return !query.select(notify.notifyId)
                .from(notify)
                .join(QMember.member, notify.criminal)
                .where(notify.criminal.eq(member).and(notify.notifyStatus.eq(NotifyStatus.처리중)))
                .fetch().isEmpty();
    }

    public void deleteRoom(RoomDelete roomDelete) {
        Room room = roomDelete.getRoom();

        if (room.hasNotice()) {
            roomNoticeJpaRepository.delete(room.getRoomNotice());
        }
        if (room.hasRoomPassword()) {
            roomPasswordJpaRepository.delete(room.getRoomPassword());
        }
        fileTypeConverter.deleteFile(FileUploadType.ROOM_PROFILE, room);
        roomImageJpaRepository.delete(room.getRoomImage());

        tagJpaRepository.deleteAll(room.getTags());
        roomDeleteJpaRepository.delete(roomDelete);

        chatJpaRepository.deleteAll(room.getChatHistory());

        roomJpaRepository.delete(room);
    }
}
