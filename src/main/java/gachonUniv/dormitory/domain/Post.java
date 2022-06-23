package gachonUniv.dormitory.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Enumerated(EnumType.STRING)
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
}
