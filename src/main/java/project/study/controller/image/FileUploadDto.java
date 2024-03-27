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

    public void defaultImageCheck() {
        if (imageUploadName == null || imageStoreName == null || imageUploadName.isEmpty() || imageStoreName.isEmpty()) {

            switch (type) {
                case MEMBER_PROFILE -> imageUploadName = imageStoreName = DEFAULT_PROFILE;
                case ROOM_PROFILE -> imageUploadName = imageStoreName = DEFAULT_ROOM_IMAGE;
            }
        }
    }

    public ImageFileEntityChildren createEntity(Class<? extends ImageFileEntityChildren> eClass) {

        defaultImageCheck();

        if (Profile.class.isAssignableFrom(eClass)) {
            return new Profile((Member) parent, imageUploadName, imageStoreName);

        } else if (RoomImage.class.isAssignableFrom(eClass)) {
            return new RoomImage((Room) parent, imageUploadName, imageStoreName);

        } else if (NotifyImage.class.isAssignableFrom(eClass)) {
            return new NotifyImage((Notify) parent, imageUploadName, imageStoreName);
        }
        return null;
    }


}
