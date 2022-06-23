package gachonUniv.dormitory.request.postReq;

import lombok.Data;

@Data
public class WritePostRequest {
    private String uuid;

    private String title;
    private String content;
    private String category;

    private String[] hash;
}
