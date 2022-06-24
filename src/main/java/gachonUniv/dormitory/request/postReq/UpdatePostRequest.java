package gachonUniv.dormitory.request.postReq;

import lombok.Data;

@Data
public class UpdatePostRequest {
    private String uuid;

    private Long id;
    private String title;
    private String content;
    private String category;

    private String[] hash;
}
