package gachonUniv.dormitory.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindReplyDto {
    private Long reply_id;
    private String nickname;
    private String content;
    private LocalDateTime create_time;
    private LocalDateTime update_time;

    @QueryProjection
    public FindReplyDto(Long reply_id, String nickname, String content, LocalDateTime create_time, LocalDateTime update_time) {
        this.reply_id = reply_id;
        this.nickname = nickname;
        this.content = content;
        this.create_time = create_time;
        this.update_time = update_time;
    }
}
