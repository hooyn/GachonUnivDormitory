package gachonUniv.dormitory.request.replyReq;

import lombok.Data;

@Data
public class UpdateReplyRequest {
    private String uuid;
    private Long reply_id;
    private String content;
}
