package gachonUniv.dormitory.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void login_test() throws Exception {
        //given
        String data = memberService.login("", "");
        //when
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        System.out.println(data);
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        //then
    }
}
