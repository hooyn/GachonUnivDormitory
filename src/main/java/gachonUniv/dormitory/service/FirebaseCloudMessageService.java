package gachonUniv.dormitory.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FirebaseCloudMessageService {

    private String FCM_PRIVATE_KEY_PATH = "gachondom-secret-key.json";

    // fcm 기본 설정
    @PostConstruct
    public void init(){
        try{
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials
                                    .fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream()))
                    .build();

            if(FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // push 알림 보내기
    public void sendByTokenList(List<String> tokenList, String title, String content){

        // 메시지 만들기
        List<Message> messages = tokenList.stream().map(token -> Message.builder()
                .putData("time", LocalDateTime.now().toString())
                .setNotification(new Notification(title, content))
                .setToken(token)
                .build()).collect(Collectors.toList());

        // 요청에 대한 응답
        BatchResponse response;
        try{
            response = FirebaseMessaging.getInstance().sendAll(messages);

            if(response.getFailureCount() > 0){
                List<SendResponse> responses = response.getResponses();
                List<String> failedTokens = new ArrayList<>();

                for (int i = 0; i < responses.size(); i++) {
                    if(!responses.get(i).isSuccessful()){
                        failedTokens.add(tokenList.get(i));
                    }
                }
            }
        } catch (FirebaseMessagingException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
