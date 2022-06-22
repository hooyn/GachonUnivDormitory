package gachonUniv.dormitory.controller;

import gachonUniv.dormitory.domain.Member;
import gachonUniv.dormitory.dto.CertifiedMemberDto;
import gachonUniv.dormitory.dto.FindMemberDto;
import gachonUniv.dormitory.request.FindMemberUUIDRequest;
import gachonUniv.dormitory.request.LoginRequest;
import gachonUniv.dormitory.request.UpdateNicknameRequest;
import gachonUniv.dormitory.request.UpdateProfileRequest;
import gachonUniv.dormitory.response.Response;
import gachonUniv.dormitory.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/login")
    public Response login(@RequestBody LoginRequest request) throws ParseException, IOException {
        Member member = memberService.findMemberUserID(request.getUserID());
        if(member!=null){
            boolean passwordCheck = memberService.passwordDecode(request.getUserPW(), member.getUserPW());
            if(passwordCheck){
                if(member.getNickname()!=null){
                    return new Response(true, HttpStatus.OK.value(), null, "로그인에 성공하셨습니다.");
                } else {
                    return new Response(true, HttpStatus.CREATED.value(), member.getId(), "닉네임 설정이 필요합니다. [학교 인증 성공]");
                }
            } else {
                return new Response(false, HttpStatus.BAD_REQUEST.value(), null, "비밀번호가 틀렸습니다.");
            }

        } else{
            //학교 인증
            CertifiedMemberDto certifiedMember = memberService.certification_univ(request.getUserID(), request.getUserPW());
            //학교인증 성공 & 실패
            if(certifiedMember.getCode().equals("0")){
                //학교 인증을 통해 얻어온 정보로 Member 생성
                Member createMember = new Member(request.getUserID(), memberService.passwordEncode(request.getUserPW()), null, true, null, null);
                //DB에 Member 저장
                UUID createMemberUUID = memberService.createMember(createMember);

                Map<String, String> res = new HashMap<>();
                res.put("Id", String.valueOf(createMemberUUID));
                //profile 파일 생성 / 닉네임과 함께 업데이트 하기
                memberService.createProfile("empty", createMemberUUID);
                return new Response(true, HttpStatus.CREATED.value(), res, "닉네임 설정이 필요합니다. [학교 인증 성공]");
            } else {
                return new Response(false, HttpStatus.BAD_REQUEST.value(), null, "학교 인증에 실패하였습니다.");
            }
        }
    }

    @PostMapping("/profile_load")
    public Response loadProfile(@RequestBody FindMemberUUIDRequest request) throws IOException {
        String data = memberService.readProfile(UUID.fromString(request.getUuid()));

        if(!data.isBlank()){
            return new Response(true, HttpStatus.OK.value(), data, "프로필 정보를 불러왔습니다.");
        } else {
            return new Response(false, HttpStatus.BAD_REQUEST.value(), null, "프로필 정보가 없습니다.");
        }
    }

    @PostMapping("/profile")
    public Response updateProfile(@RequestBody UpdateProfileRequest request) throws IOException {
        memberService.createProfile(request.getProfile_info(), UUID.fromString(request.getUuid()));

        return new Response(true, HttpStatus.OK.value(), null, "프로필 정보를 업데이트 되었습니다.");
    }

    @PostMapping("/nickname")
    public Response updateNickname(@RequestBody UpdateNicknameRequest request){
        String nickname = request.getNickname();
        /**
         * 닉네임에 대한 제약조건
         */

        memberService.changeNickname(request.getUuid(), request.getNickname());
        return new Response(true, HttpStatus.OK.value(), null, "닉네임 정보가 업데이트 되었습니다.");
    }

    @PostMapping("/member")
    public Response findMember(@RequestBody FindMemberUUIDRequest request){
        Member member = memberService.findMemberUUID(request.getUuid());
        FindMemberDto findMember = new FindMemberDto(member.getId(), member.getUserID(),
                member.getUserPW(), member.getNickname(), member.getCertified(),
                member.getToken(), member.getIsAOS());

        return new Response(true, HttpStatus.OK.value(), findMember, "회원이 조회되었습니다.");
    }
}
