package gachonUniv.dormitory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gachonUniv.dormitory.domain.Reply;
import gachonUniv.dormitory.dto.FindReplyDto;
import gachonUniv.dormitory.dto.QFindReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

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

    public List<FindReplyDto> findByPostId(Long post_id){
        return queryFactory
                .select(new QFindReplyDto(
                        reply.member.nickname,
                        reply.content,
                        reply.create_time,
                        reply.update_time
                ))
                .from(reply)
                .where(reply.post.id.eq(post_id))
                .fetch();
    }


}
