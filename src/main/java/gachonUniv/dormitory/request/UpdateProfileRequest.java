package gachonUniv.dormitory.request;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String profile_info;
    private String uuid;
}
