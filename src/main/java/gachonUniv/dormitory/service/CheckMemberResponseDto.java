package gachonUniv.dormitory.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CheckMemberResponseDto{
    private String user_name;
    private String user_no;
    private String user_email;
    private String user_tel;
    private String user_dept;
}