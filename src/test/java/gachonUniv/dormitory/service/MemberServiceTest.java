package gachonUniv.dormitory.service;

import gachonUniv.dormitory.domain.Member;
import gachonUniv.dormitory.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 학교인증() throws Exception {
        //given
        CheckMemberResponseDto data = memberService.certification_univ("test", "test");
        //when
        System.out.println(data.toString());
        //then
    }

    @Test
    public void 회원등록확인() throws Exception {
        //given
        Member member = new Member("testID", "testPW", "test", true, "test-token", false);
        memberRepository.save(member);
        //when
        Member findMember = memberService.findMember("testID");
        System.out.println(findMember.getId());

        Member unknown = memberService.findMember("unknown");
        System.out.println(unknown);
        //then
    }
}