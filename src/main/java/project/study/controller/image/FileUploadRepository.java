package project.study.controller.image;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.study.domain.*;

import java.io.File;

import static project.study.constant.WebConst.*;

@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FileUploadRepository {

    @Value("${file.dir}")
    private String fileDir;
    private final EntityManager em;

    public void saveImage(FileUploadDto data, Class<? extends ImageFileEntityChildren> eClass) {
        ImageFileEntityChildren entity = data.createEntity(eClass);
        em.persist(entity);
    }


    public void editProfile(FileUploadDto data) {
        Member member = (Member) data.getParent();
        if (data.isDefaultImage()) {
            member.setImage(DEFAULT_PROFILE, DEFAULT_PROFILE);
        } else {
            member.setImage(data.getImageUploadName(), data.getImageStoreName());
        }
    }

    public void editRoomImage(FileUploadDto data) {
        Room room = (Room) data.getParent();

        if (data.isDefaultImage()) {
            room.setImage(DEFAULT_ROOM_IMAGE, DEFAULT_ROOM_IMAGE);
        } else {
            room.setImage(data.getImageUploadName(), data.getImageStoreName());
        }
    }

    public void deleteProfile(FileUploadType type, ImageFileEntity parent) {
        String storeName = parent.getStoreImage();
        if (DEFAULT_PROFILE.equals(storeName)) return;

        File profileFile = new File(getFullPath(type, storeName));
        if (profileFile.delete()) {
            log.error("Profile 파일 삭제");
        }

    }

    public void deleteRoomImage(FileUploadType type, ImageFileEntity parent) {
        String storeName = parent.getStoreImage();
        if (DEFAULT_ROOM_IMAGE.equals(storeName)) return;

        File roomImageFile = new File(getFullPath(type, storeName));
        if (roomImageFile.delete()) {
            log.error("RoomImage 파일 삭제");
        }

    }

    private String getFullPath(FileUploadType type, String storeName) {
        return String.format("%s%s/%s", fileDir, type.getDir(), storeName);
    }
}
