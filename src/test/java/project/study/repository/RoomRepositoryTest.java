package project.study.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.study.dto.login.responsedto.Error;
import project.study.dto.login.responsedto.ErrorList;
import project.study.enums.PublicEnum;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("RoomRepository")
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;



    @Test
    @DisplayName("방 제목 검증로직 2~10자 이내")
    void validRoomTitle() {
        // given
        ErrorList errorList = new ErrorList();
        String testTitle1 = null;
        String testTitle2 = "하";
        String testTitle3 = "12345678901";
        String testTitle4 = "정상적인 길이테스트";

        // when
        roomRepository.validRoomTitle(errorList, testTitle1);
        roomRepository.validRoomTitle(errorList, testTitle2);
        roomRepository.validRoomTitle(errorList, testTitle3);
        roomRepository.validRoomTitle(errorList, testTitle4);

        // then
        List<Error> list = errorList.getErrorList();

        assertThat(errorList.hasError()).isTrue();
        assertThat(list.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("방 소개글 검증로직")
    void validRoomIntro() {
        // given
        ErrorList errorList = new ErrorList();
        String intro1 = null;
        String intro2 = "정상범위값".repeat(10);
        String intro3 = "12345".repeat(11);

        // null 가능
        roomRepository.validRoomIntro(errorList, intro1);
        assertThat(errorList.hasError()).isFalse();

        // 50자 이하
        roomRepository.validRoomIntro(errorList, intro2);
        assertThat(errorList.hasError()).isTrue();

        // 50자 이상
        roomRepository.validRoomIntro(errorList, intro3);
        assertThat(errorList.hasError()).isTrue();
    }

    @Test
    @DisplayName("최대인원수 2-6명 확인")
    void validRoomMaxPerson() {
        // given
        ErrorList errorList = new ErrorList();
        String max1 = "2"; // 정상 값
        String max2 = "6"; // 정상 값
        String[] maxArr = {null, "a", "a1", "1", "7"};

        // when
        roomRepository.validRoomMaxPerson(errorList, max1);
        roomRepository.validRoomMaxPerson(errorList, max2);
        assertThat(errorList.hasError()).isFalse();
        assertThat(errorList.getErrorList().size()).isEqualTo(0);

        for (String maxStr : maxArr) {
            roomRepository.validRoomMaxPerson(errorList, maxStr);
        }
        assertThat(errorList.hasError()).isTrue();
        assertThat(errorList.getErrorList().size()).isEqualTo(maxArr.length);
    }

    @Test
    @DisplayName("방 공개여부 및 비밀번호 4-6자 확인")
    void validPublic() {
        // given
        ErrorList errorList = new ErrorList();

        // 정상흐름
        roomRepository.validPublic(errorList, PublicEnum.PUBLIC, null);
        roomRepository.validPublic(errorList, PublicEnum.PRIVATE, "1234");
        roomRepository.validPublic(errorList, PublicEnum.PRIVATE, "123456");
        assertThat(errorList.hasError()).isFalse();

        // 에러
        roomRepository.validPublic(errorList, PublicEnum.PUBLIC, "abcd");
        roomRepository.validPublic(errorList, PublicEnum.PUBLIC, "11");
        roomRepository.validPublic(errorList, PublicEnum.PRIVATE, "1");
        roomRepository.validPublic(errorList, PublicEnum.PRIVATE, "asdfaefawefae");
        roomRepository.validPublic(errorList, PublicEnum.PRIVATE, null);
        roomRepository.validPublic(errorList, null, "1234");
        roomRepository.validPublic(errorList, null, "");
        roomRepository.validPublic(errorList, null, null);
        assertThat(errorList.getErrorList().size()).isEqualTo(8);

    }

    @Test
    @DisplayName("방 설정변경 인원확인")
    void validRoomEditMaxPerson() {
        // given
        ErrorList errorList = new ErrorList();

        // 정상흐름
        roomRepository.validRoomEditMaxPerson(errorList, 1, "2");
        roomRepository.validRoomEditMaxPerson(errorList, 1, "6");
        roomRepository.validRoomEditMaxPerson(errorList, 3, "5");
        roomRepository.validRoomEditMaxPerson(errorList, 6, "6");
        assertThat(errorList.hasError()).isFalse();

        // 예외
        roomRepository.validRoomEditMaxPerson(errorList, 1, "1");
        assertThat(errorList.getErrorList().size()).isEqualTo(1);
        roomRepository.validRoomEditMaxPerson(errorList, 2, "1");
        assertThat(errorList.getErrorList().size()).isEqualTo(2);
        roomRepository.validRoomEditMaxPerson(errorList, 4, "3");
        assertThat(errorList.getErrorList().size()).isEqualTo(3);
        roomRepository.validRoomEditMaxPerson(errorList, 3, "a");
        assertThat(errorList.getErrorList().size()).isEqualTo(4);
        roomRepository.validRoomEditMaxPerson(errorList, 3, "a4");
        assertThat(errorList.getErrorList().size()).isEqualTo(5);
        roomRepository.validRoomEditMaxPerson(errorList, 3, "7");
        assertThat(errorList.getErrorList().size()).isEqualTo(6);
        roomRepository.validRoomEditMaxPerson(errorList, 3, "100");
        assertThat(errorList.getErrorList().size()).isEqualTo(7);
        roomRepository.validRoomEditMaxPerson(errorList, 3, null);
        assertThat(errorList.getErrorList().size()).isEqualTo(8);
        roomRepository.validRoomEditMaxPerson(errorList, 5, "4");
        assertThat(errorList.getErrorList().size()).isEqualTo(9);
    }

    @Test
    void validTagList() {
        // given


        // when


        // then
    }

    @Test
    void validFullRoom() {
        // given


        // when


        // then
    }
}