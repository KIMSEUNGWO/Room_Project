package project.study.controller.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.study.domain.ImageFileEntityChildren;
import project.study.domain.NotifyImage;
import project.study.domain.Profile;
import project.study.domain.RoomImage;

@Getter
@AllArgsConstructor
public enum FileUploadType {

    MEMBER_PROFILE("member_profile", Profile.class),
    ROOM_PROFILE("room_profile", RoomImage.class),
    NOTIFY_IMAGE("notify_image", NotifyImage.class);

    private String dir;
    private Class<? extends ImageFileEntityChildren> aClass;

    public static FileUploadType findDir(String dir) {
        FileUploadType[] values = FileUploadType.values();
        for (FileUploadType value : values) {
            if (value.dir.equals(dir)) {
                return value;
            }
        }
        return null;
    }

}
