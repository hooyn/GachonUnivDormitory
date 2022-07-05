package gachonUniv.dormitory.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gachonUniv.dormitory.domain.Post;
import gachonUniv.dormitory.domain.QMember;
import gachonUniv.dormitory.domain.QPost;
import gachonUniv.dormitory.dto.FindPostDto;
import gachonUniv.dormitory.dto.PostSearchCondition;
import gachonUniv.dormitory.dto.QFindPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static gachonUniv.dormitory.domain.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Post findOneReturnPost(Long id){
        return queryFactory
                .selectFrom(post)
                .where(post.id.eq(id))
                .fetchOne();
    }

    public Long save(Post post){
        em.persist(post);
        return post.getId();
    }

    public void update(Long id, String title, String content, String category, String[] hash){
        long execute = queryFactory
                .update(post)
                .set(post.title, title)
                .set(post.content, content)
                .set(post.category, category)
                .set(post.update_time, LocalDateTime.now())
                .set(post.hash_first, hash[0])
                .set(post.hash_second, hash[1])
                .set(post.hash_third, hash[2])
                .where(post.id.eq(id))
                .execute();
    }

    public void delete(Long id){
        queryFactory
                .delete(post)
                .where(post.id.eq(id))
                .execute();
    }

    public List<FindPostDto> findAll(Integer page){
        return queryFactory
                .select(new QFindPostDto(
                        post.member.id.as("uuid"),
                        post.member.nickname,
                        post.id,
                        post.title,
                        post.content,
                        post.category,
                        post.view_count,
                        post.reply_count,
                        post.create_time,
                        post.update_time,
                        post.hash_first,
                        post.hash_second,
                        post.hash_third
                ))
                .from(post)
                .orderBy(post.create_time.desc())
                .offset(0+((page-1)*10))
                .limit(10)
                .fetch();
    }

    public List<FindPostDto> search(PostSearchCondition condition, Integer page){
        return queryFactory
                .select(new QFindPostDto(
                        post.member.id.as("uuid"),
                        post.member.nickname,
                        post.id,
                        post.title,
                        post.content,
                        post.category,
                        post.view_count,
                        post.reply_count,
                        post.create_time,
                        post.update_time,
                        post.hash_first,
                        post.hash_second,
                        post.hash_third
                ))
                .from(post)
                .where(
                        categoryEq(condition.getCategory()),
                        contentEq(condition.getContent())
                )
                .orderBy(post.create_time.desc())
                .offset(0+((page-1)*10))
                .limit(10)
                .fetch();
    }

    public FindPostDto findOne(Long id){
        return queryFactory
                .select(new QFindPostDto(
                        post.member.id.as("uuid"),
                        post.member.nickname,
                        post.id,
                        post.title,
                        post.content,
                        post.category,
                        post.view_count,
                        post.reply_count,
                        post.create_time,
                        post.update_time,
                        post.hash_first,
                        post.hash_second,
                        post.hash_third
                ))
                .from(post)
                .where(post.id.eq(id))
                .fetchOne();
    }

    public List<FindPostDto> findByUuid(String uuid, Integer page){
        return queryFactory
                .select(new QFindPostDto(
                        post.member.id.as("uuid"),
                        post.member.nickname,
                        post.id,
                        post.title,
                        post.content,
                        post.category,
                        post.view_count,
                        post.reply_count,
                        post.create_time,
                        post.update_time,
                        post.hash_first,
                        post.hash_second,
                        post.hash_third
                ))
                .from(post)
                .where(post.member.id.eq(UUID.fromString(uuid)))
                .orderBy(post.create_time.desc())
                .offset(0+((page-1)*10))
                .limit(10)
                .fetch();
    }

    public List<FindPostDto> findPostRecently(Integer page){
        return queryFactory
                .select(new QFindPostDto(
                        post.member.id.as("uuid"),
                        post.member.nickname,
                        post.id,
                        post.title,
                        post.content,
                        post.category,
                        post.view_count,
                        post.reply_count,
                        post.create_time,
                        post.update_time,
                        post.hash_first,
                        post.hash_second,
                        post.hash_third
                ))
                .from(post)
                .orderBy(post.create_time.desc())
                .offset(0+((page-1)*10))
                .limit(10)
                .fetch();
    }

    public boolean checkAuthorization(String uuid, Long id){
        Post post = queryFactory
                .selectFrom(QPost.post)
                .where(QPost.post.member.id.eq(UUID.fromString(uuid)).and(
                        QPost.post.id.eq(id)
                ))
                .fetchOne();

        if(post!=null) return true;
        else return false;
    }

    public boolean checkPost(Long id){
        Post post = queryFactory
                .selectFrom(QPost.post)
                .where(QPost.post.id.eq(id))
                .fetchOne();

        if(post!=null) return true;
        else return false;
    }

    public BooleanExpression categoryEq(String category){
        return StringUtils.hasText(category) ? post.category.eq(category) : null;
    }

    public BooleanExpression contentEq(String content){
        return StringUtils.hasText(content) ? post.content.like("%"+content+"%") : null;
    }
}
