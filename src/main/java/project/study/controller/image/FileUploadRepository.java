package project.study.controller.image;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.study.domain.*;


@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FileUploadRepository {

    private final EntityManager em;

    public void saveImage(FileUploadDto data) {
        data.defaultImageCheck();
        FileUploadType type = data.getType();
        ImageFileEntityChildren entity = type.createEntity(data);
        em.persist(entity);
    }


    public void editImage(FileUploadDto data) {
        data.defaultImageCheck();

        ImageFileEntity parent = data.getParent();

        parent.setImage(data.getImageUploadName(), data.getImageStoreName());
    }
}
