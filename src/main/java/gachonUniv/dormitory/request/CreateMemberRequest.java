package gachonUniv.dormitory.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateMemberRequest {
    private String userID;
    private String userPW;
    private String nickname;
    private Boolean certified;
    private String token;
    private Boolean isAOS;
}
