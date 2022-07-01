package gachonUniv.dormitory.service;

import gachonUniv.dormitory.domain.Member;
import gachonUniv.dormitory.dto.CertifiedMemberDto;
import gachonUniv.dormitory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 학교 인증 & 데이터 Dto 저장
     */
    @Transactional
    public CertifiedMemberDto certification_univ(String userID, String userPW) throws ParseException {
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

            return new CertifiedMemberDto(code, user_name, user_no, user_email, user_tel, user_dept);
        } else {
            return new CertifiedMemberDto(code, null, null, null, null, null);
        }
    }

    /**
     * 회원 생성
     */
    @Transactional
    public UUID createMember(Member member){
        return memberRepository.save(member);
    }

    /**
     * UUID 회원 조회 *
     */
    @Transactional(readOnly = true)
    public Member findMemberUuid(String uuid){
        return memberRepository.findByUuid(uuid);
    }

    /**
     * userID 회원 조회
     */
    @Transactional(readOnly = true)
    public Member findMemberUserID(String userID){
        return memberRepository.findByUserId(userID);
    }

    /**
     * 전체 회원 조회
     */
    @Transactional(readOnly = true)
    public List<Member> findMembers(){ //-> Dto로 변환하기
        return memberRepository.findAll();
    }

    /**
     * 비밀번호 암호화
     */
    public String passwordEncode(String rawPW){
        String encodedPW = passwordEncoder.encode(rawPW);
        return encodedPW;
    }

    /**
     * 비밀번호 확인
     */
    public boolean passwordDecode(String rawPW, String encodedPW){
        boolean matches = passwordEncoder.matches(rawPW, encodedPW);
        return matches;
    }

    /**
     * 프로필 파일 생성 * 로컬에 프로필 저장을 위한 파일 생성
     */
    public Boolean createProfile(String profile_info, UUID id) throws IOException {
        String filePath = "C:/Users/hooyn/intelliJ-workspace/dormitory_profile_info/"+id+".txt";

        File file = new File(filePath);
        file.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
        writer.write(profile_info);
        writer.close();

        return true;
    }

    /**
     * 프로필 파일 읽기 *
     */
    public String readProfile(UUID id) throws IOException {
        String filePath = "C:/Users/hooyn/intelliJ-workspace/dormitory_profile_info/"+id+".txt";
        File file = new File(filePath);

        String output = "";
        if(file.exists()){
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = null;
            while ((line = reader.readLine()) != null) {
                output += line;
            }

            reader.close();
        }

        return output;
    }

    /**
     * 닉네임 변경
     */
    @Transactional
    public void changeNickname(String uuid, String nickname){
        memberRepository.changeNickname(uuid, nickname);
    }

    /**
     * 닉네임 제약조건 확인
     */
    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname){
        return memberRepository.checkNickname(nickname);
    }

    /**
     * 닉네임 중복 확인
     */
    @Transactional(readOnly = true)
    public boolean checkDuplicateNickname(String nickname){
        return memberRepository.checkDuplicateNickname(nickname);
    }

    /**
     * 토큰 저장
     */
    @Transactional
    public void changeToken(String uuid, String token){
        memberRepository.changeToken(uuid, token);
    }
}


