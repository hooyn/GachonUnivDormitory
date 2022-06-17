package gachonUniv.dormitory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gachonUniv.dormitory.domain.Member;
import gachonUniv.dormitory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 로그인 (다른 서버 통신을 통해 가천대 학생인지 확인)
     */
    @Transactional
    public String login(String userID, String userPW) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://smart.gachon.ac.kr:8080/WebJSON";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("fsp_cmd", "login");
        body.add("DVIC_ID", "dwFraM1pVhl6mMn4npgL2dtZw7pZxw2lo2uqpm1yuMs=");
        body.add("fsp_action", "UserAction");
        body.add("USER_ID", userID);
        body.add("PWD", userPW);
        body.add("APPS_ID", "com.sz.Atwee.gachon");

        HttpEntity<MultiValueMap<String, String>> requestMessage = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

        return response.getBody();
    }

    /**
     * 전체 회원 조회
     */
    @Transactional(readOnly = true)
    public List<Member> findMembers(){ //-> Dto로 변환하기
        return memberRepository.findAll();
    }

    /**
     * UUID에 따른 회원 조회
     */
    @Transactional(readOnly = true)
    public Member findMember(UUID uuid){ //-> Dto로 변환하기
        return memberRepository.findByUuid(uuid);
    }
}


