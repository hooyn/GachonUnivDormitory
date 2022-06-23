package gachonUniv.dormitory.request.memberReq;

import lombok.Data;

@Data
public class LoginRequest {
    private String userID;
    private String userPW;
}
