package gachonUniv.dormitory.request.postReq;

import lombok.Data;

@Data
public class FindPostsRequest {
    private Long id;
    private String uuid;
    private String category;
}
