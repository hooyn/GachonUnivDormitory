package gachonUniv.dormitory.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CheckMemberResponseDto{
    private String code;
    private String user_name;
    private String user_no;
    private String user_email;
    private String user_tel;
    private String user_dept;

    public CheckMemberResponseDto(String code, String user_name, String user_no, String user_email, String user_tel, String user_dept) {
        this.code = code;
        this.user_name = user_name;
        this.user_no = user_no;
        this.user_email = user_email;
        this.user_tel = user_tel;
        this.user_dept = user_dept;
    }
}