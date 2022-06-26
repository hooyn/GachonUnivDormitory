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
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

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

    public void setMember(Member member){
        this.member = member;
        member.getPosts().add(this);
    }

    public Post(Member member, String title, String content, String category, String hash_first, String hash_second, String hash_third) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.category = category;
        this.view_count = 0;
        this.reply_count = 0;
        this.create_time = LocalDateTime.now();
        this.update_time = LocalDateTime.now();
        this.hash_first = hash_first;
        this.hash_second = hash_second;
        this.hash_third = hash_third;
    }
}
