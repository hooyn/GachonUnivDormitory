package gachonUniv.dormitory.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FindNotificationDto {
    private Long notification_id;
    private String title;
    private String content;
    private Boolean isRead;
    private LocalDateTime create_time;

    @QueryProjection
    public FindNotificationDto(Long notification_id, String title, String content, Boolean isRead, LocalDateTime create_time) {
        this.notification_id = notification_id;
        this.title = title;
        this.content = content;
        this.isRead = isRead;
        this.create_time = create_time;
    }
}
