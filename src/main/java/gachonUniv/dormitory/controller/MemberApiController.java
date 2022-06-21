package gachonUniv.dormitory.controller;

import gachonUniv.dormitory.domain.Member;
import gachonUniv.dormitory.dto.FindMemberDto;
import gachonUniv.dormitory.request.CreateMemberRequest;
import gachonUniv.dormitory.request.FindOneByUserIdRequest;
import gachonUniv.dormitory.response.Response;
import gachonUniv.dormitory.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/create_member")
    public Response createMember(@RequestBody CreateMemberRequest request){
        Member member = new Member(request.getUserID(), request.getUserPW(), request.getNickname(), request.getCertified(), request.getToken(), request.getIsAOS());
        UUID data = memberService.createMember(member);

        return new Response(true, HttpStatus.OK, "ID = " + data, "회원 한명 조회");
    }

    @PostMapping("/member")
    public Response findOneByUuid(@RequestBody FindOneByUserIdRequest request){
        Member member = memberService.findMember(request.getUserID());

        if(member==null){
            return new Response(true, HttpStatus.BAD_REQUEST, null, "회원 정보를 찾을 수 없음");
        }

        FindMemberDto data = new FindMemberDto(member.getId(), member.getUserID(), member.getNickname(), member.getCertified(), member.getToken(), member.getIsAOS());
        return new Response(true, HttpStatus.OK, data, "회원 한명 조회");
    }
}
