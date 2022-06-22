package gachonUniv.dormitory.request;

import lombok.Data;

@Data
public class UpdateNicknameRequest {
    private String nickname;
    private String uuid;
}
