package gachonUniv.dormitory.request.memberReq;

import lombok.Data;

@Data
public class UpdateTokenRequest {
    private String uuid;
    private String token;
}
