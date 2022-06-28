package gachonUniv.dormitory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;
    private LocalDateTime create_time;
    private LocalDateTime update_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void setMember(Member member){
        this.member = member;
        member.getReplies().add(this);
    }

    public void setPost(Post post){
        this.post = post;
        member.getReplies().add(this);
    }

    public Reply(String content, Member member, Post post) {
        this.content = content;
        this.create_time = LocalDateTime.now();
        this.update_time = LocalDateTime.now();
        this.member = member;
        this.post = post;
    }
}
