package gachonUniv.dormitory.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
public class Response<T> {
    private boolean isSuccess;
    private HttpStatus status;
    private T data;
    private String message;
}


