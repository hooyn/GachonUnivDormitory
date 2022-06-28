package gachonUniv.dormitory.request.replyReq;

import lombok.Data;

@Data
public class WriteReplyRequest {
    private String uuid;
    private Long post_id;
    private String content;
}
