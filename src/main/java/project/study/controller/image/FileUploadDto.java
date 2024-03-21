package project.study.controller.image;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.study.domain.*;

import static project.study.constant.WebConst.*;

@Getter
@Setter
@Builder
public class FileUploadDto {

    private ImageFileEntity parent;
    private String imageUploadName;
    private String imageStoreName;
    private FileUploadType type;

    public boolean isDefaultImage() {
        return imageUploadName == null || imageStoreName == null;
    }

    public ImageFileEntityChildren createEntity(Class<? extends ImageFileEntityChildren> eClass) {

        if (Profile.class.isAssignableFrom(eClass)) {
            if (isDefaultImage()) return new Profile((Member) parent, DEFAULT_PROFILE, DEFAULT_PROFILE);
            return new Profile((Member) parent, imageUploadName, imageStoreName);

        } else if (RoomImage.class.isAssignableFrom(eClass)) {
            if (isDefaultImage()) return new RoomImage((Room) parent, DEFAULT_ROOM_IMAGE, DEFAULT_ROOM_IMAGE);
            return new RoomImage((Room) parent, imageUploadName, imageStoreName);

        } else if (NotifyImage.class.isAssignableFrom(eClass)) {
            return new NotifyImage((Notify) parent, imageUploadName, imageStoreName);
        }
        return null;
    }


}
