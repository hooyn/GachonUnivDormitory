package gachonUniv.dormitory.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindReplyDto {
    private String nickname;
    private String content;
    private LocalDateTime create_time;
    private LocalDateTime update_time;

    @QueryProjection
    public FindReplyDto(String nickname, String content, LocalDateTime create_time, LocalDateTime update_time) {
        this.nickname = nickname;
        this.content = content;
        this.create_time = create_time;
        this.update_time = update_time;
    }
}
