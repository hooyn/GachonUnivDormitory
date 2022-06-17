package gachonUniv.dormitory.service;

import gachonUniv.dormitory.domain.Member;
import gachonUniv.dormitory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Iterator;
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
    public CheckMemberResponseDto login(String userID, String userPW) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://smart.gachon.ac.kr:8080/WebJSON";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        org.json.JSONObject jsonObject = new org.json.JSONObject();
        jsonObject.put("fsp_cmd","login");
        jsonObject.put("DVIC_ID","dwFraM1pVhl6mMn4npgL2dtZw7pZxw2lo2uqpm1yuMs=");
        jsonObject.put("fsp_action","UserAction");
        jsonObject.put("USER_ID",userID);
        jsonObject.put("PWD",userPW);
        jsonObject.put("APPS_ID","com.sz.Atwee.gachon");


        HttpEntity<String> requestMessage = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

        JSONObject data = new JSONObject(response.getBody());

        //0이면 인증 완료
        String code = (String) data.get("ErrorCode");

        if(code.equals("0")){
            JSONObject ds_output = (JSONObject) data.get("ds_output");

            //정보 Parsing
            String user_name = (String) ds_output.get("userNm");
            String user_no = (String) ds_output.get("userUniqNo");
            String user_email = (String) ds_output.get("eml");
            String user_tel = (String) ds_output.get("telNo");

            String res = ds_output.get("clubList").toString(); //String으로 저장
            JSONArray clubListArr = new JSONArray(res); //JSONArray는 항상 String으로 초기화를 해주어야 한다.
            JSONObject clubList = clubListArr.getJSONObject(0);
            String user_dept = (String) clubList.get("clubNm");

            return new CheckMemberResponseDto(code, user_name, user_no, user_email, user_tel, user_dept);
        } else {
            return new CheckMemberResponseDto(code, null, null, null, null, null);
        }
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


