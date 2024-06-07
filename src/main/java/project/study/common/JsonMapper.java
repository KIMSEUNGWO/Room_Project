package project.study.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.dto.abstractentity.ResponseDto;

@Component
public class JsonMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String jsonToString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

}
