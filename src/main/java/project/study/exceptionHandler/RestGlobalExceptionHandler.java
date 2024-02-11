package project.study.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.study.dto.abstractentity.ResponseDto;
import project.study.exceptions.RestFulException;

@Slf4j
@RestControllerAdvice
public class RestGlobalExceptionHandler {


    @ExceptionHandler(RestFulException.class)
    public ResponseEntity<ResponseDto> globalRestFulException(RestFulException e) {
        e.printStackTrace();
        log.error("[Global RestFul Exception 발생!] : {}", e.getResponseDto().getMessage());

        return new ResponseEntity<>(e.getResponseDto(), HttpStatus.BAD_REQUEST);
    }
}
