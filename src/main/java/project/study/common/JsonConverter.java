package project.study.common;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import project.study.dto.abstractentity.ResponseDto;

import java.io.IOException;

public class JsonConverter {

    public static void execute(HttpServletResponse response, ResponseDto data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonMapper.jsonToString(data));
        response.getWriter().flush();
    }
}
