package gachonUniv.dormitory.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String userID;
    private String userPW;
}
