package gachonUniv.dormitory.request.memberReq;

import lombok.Data;

@Data
public class UpdateNicknameRequest {
    private String nickname;
    private String uuid;
}
