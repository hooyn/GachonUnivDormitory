package gachonUniv.dormitory.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Data
public class FindPostDto {
    private String uuid;
    private String nickname;

    private Long post_id;
    private String title;
    private String content;
    private String category;

    private int view_count;
    private int reply_count;
    private LocalDateTime create_time;
    private LocalDateTime update_time;

    private String hash_first;
    private String hash_second;
    private String hash_third;

    @QueryProjection
    public FindPostDto(UUID uuid, String nickname, Long post_id, String title, String content, String category, int view_count, int reply_count, LocalDateTime create_time, LocalDateTime update_time, String hash_first, String hash_second, String hash_third) {
        this.uuid = String.valueOf(uuid);
        this.nickname = nickname;
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.view_count = view_count;
        this.reply_count = reply_count;
        this.create_time = create_time;
        this.update_time = update_time;
        this.hash_first = hash_first;
        this.hash_second = hash_second;
        this.hash_third = hash_third;
    }
}