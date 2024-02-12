package project.study.exceptionHandler;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.study.dto.abstractentity.ResponseDto;
import project.study.exceptions.RestFulException;
import project.study.exceptions.kakaologin.SocialException;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RestControllerAdvice
public class RestGlobalExceptionHandler {


    @ExceptionHandler(RestFulException.class)
    public ResponseEntity<ResponseDto> globalRestFulException(RestFulException e) {
        e.printStackTrace();
        log.error("[Global RestFul Exception 발생!] : {}", e.getResponseDto().getMessage());

        return new ResponseEntity<>(e.getResponseDto(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SocialException.class)
    public ResponseEntity<String> globalSocialException(SocialException e) {
        e.printStackTrace();
        log.error("[Global SocialException Exception 발생!]");
        execute(e.getResponse(), e.getAlertMessage());
        return null;
    }

    private String execute(HttpServletResponse response, String alert) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String command = "<script> " + getOption(alert) + " window.self.close(); </script>";
        try (PrintWriter out = response.getWriter()) {
            out.println(command);
            out.flush();
        } catch (IOException e) {
            log.error("Alert IOException 발생!");
        }
        return null;
    }

    private String getOption(String option) {
        // redirect url이 있으면 url로 이동
        if (option == null) {
            return "opener.location.href='/';";
        }
        return String.format("alert('%s');", option);
    }
}
