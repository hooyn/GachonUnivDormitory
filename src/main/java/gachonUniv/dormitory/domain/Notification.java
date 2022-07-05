package gachonUniv.dormitory.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {
    @Id @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String content;
    private Boolean isRead;
    private LocalDateTime create_time;

    public void setMember(Member member){
        this.member = member;
        member.getNotifications().add(this);
    }

    public void setPost(Post post){
        this.post = post;
        member.getNotifications().add(this);
    }

    public Notification(Post post, Member member, String title, String content) {
        this.post = post;
        this.member = member;
        this.title = title;
        this.content = content;
        this.isRead = false;
        this.create_time = LocalDateTime.now();
    }
}
