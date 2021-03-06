package gachonUniv.dormitory.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "member_id")
    private UUID id;

    private String userID;
    private String userPW;
    private String nickname;
    private Boolean certified;
    private String token;
    private Boolean isAOS;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Notification> notifications = new ArrayList<>();

    /**
     * [Mapping]
     * postList (o)
     * replyList (o)
     * reportList
     * feedbackList
     * notificationList
     */

    public Member(String userID, String userPW, String nickname, Boolean certified, String token, Boolean isAOS) {
        this.userID = userID;
        this.userPW = userPW;
        this.nickname = nickname;
        this.certified = certified;
        this.token = token;
        this.isAOS = isAOS;
    }
}
