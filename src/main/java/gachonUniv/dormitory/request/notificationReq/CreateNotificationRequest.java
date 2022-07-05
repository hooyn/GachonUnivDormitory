package gachonUniv.dormitory.request.notificationReq;

import lombok.Data;

@Data
public class CreateNotificationRequest {
    private String uuid;
    private Long post_id;
    private String title;
    private String content;
}
