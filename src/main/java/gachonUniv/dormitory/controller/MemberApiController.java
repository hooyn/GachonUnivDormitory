package gachonUniv.dormitory.controller;

import gachonUniv.dormitory.request.LoginRequest;
import gachonUniv.dormitory.response.Response;
import gachonUniv.dormitory.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/login")
    public Response login(@RequestBody LoginRequest request){

        //아이디 검색
            //certified == true 학교인증 완료
                //비밀번호 확인
                    //비밀번호 일치
                        //닉네임 설정 확인
                            //닉네임 설정
                                //return 200
                            //닉네임 미설정
                                //return 201 [닉네임 설정]
                    //비밀번호 불일치
                        //return 301

            //certified == false 학교인증 미완료
                //학교인증
                    //학생 인증 완료
                        //DB에 저장 certified==1
                        //닉네임 설정 return 201 [닉네임 설정]

                    //학생 인증 미완료
                        //return 301
    }
}
