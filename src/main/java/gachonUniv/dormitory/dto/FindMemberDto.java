package gachonUniv.dormitory.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class FindMemberDto {
    private UUID uuid;
    private String userID;
    private String userPw;
    private String nickname;
    private Boolean certified;
    private String token;
    private Boolean isAOS;

    public FindMemberDto(UUID uuid, String userID, String userPw, String nickname, Boolean certified, String token, Boolean isAOS) {
        this.uuid = uuid;
        this.userID = userID;
        this.userPw = userPw;
        this.nickname = nickname;
        this.certified = certified;
        this.token = token;
        this.isAOS = isAOS;
    }
}
