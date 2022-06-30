package gachonUniv.dormitory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gachonUniv.dormitory.domain.Post;
import gachonUniv.dormitory.domain.QPost;
import gachonUniv.dormitory.domain.QReply;
import gachonUniv.dormitory.domain.Reply;
import gachonUniv.dormitory.dto.FindReplyDto;
import gachonUniv.dormitory.dto.QFindReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static gachonUniv.dormitory.domain.QReply.reply;

@Repository
@RequiredArgsConstructor
public class ReplyRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Long save(Reply reply){
        em.persist(reply);
        return reply.getId();
    }

    public Reply findOneReturnReply(Long id){
        return queryFactory
                .selectFrom(reply)
                .where(reply.id.eq(id))
                .fetchOne();
    }

    public List<FindReplyDto> findByPostId(Long id, Integer page){
        return queryFactory
                .select(new QFindReplyDto(
                        reply.id,
                        reply.member.nickname,
                        reply.content,
                        reply.create_time,
                        reply.update_time
                ))
                .from(reply)
                .where(reply.post.id.eq(id))
                .offset(0+((page-1)*5))
                .limit(5)
                .fetch();
    }

    public Long updateReply(Long id, String content){
        Reply reply = em.find(Reply.class, id);
        reply.setContent(content);
        reply.setUpdate_time(LocalDateTime.now());
        return reply.getId();
    }

    public boolean checkReplyAuthorization(String uuid, Long id){
        Reply reply = queryFactory
                .selectFrom(QReply.reply)
                .where(QReply.reply.member.id.eq(UUID.fromString(uuid)).and(
                        QReply.reply.id.eq(id)
                ))
                .fetchOne();

        if(reply !=null) return true;
        else return false;
    }

}
