package gachonUniv.dormitory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gachonUniv.dormitory.domain.Post;
import gachonUniv.dormitory.domain.QMember;
import gachonUniv.dormitory.domain.QPost;
import gachonUniv.dormitory.dto.FindPostDto;
import gachonUniv.dormitory.dto.QFindPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static gachonUniv.dormitory.domain.QMember.member;
import static gachonUniv.dormitory.domain.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Long save(Post post){
        em.persist(post);
        return post.getId();
    }

    public List<FindPostDto> findAll(){
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

    public Post findOneReturnPost(Long id){
        return queryFactory
                .selectFrom(post)
                .where(post.id.eq(id))
                .fetchOne();
    }

    public List<FindPostDto> findByUuid(String uuid){
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
                .fetch();
    }

    public List<FindPostDto> findByCategory(String category){
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
                .where(post.category.eq(category))
                .fetch();
    }

    public void update(Long id, String title, String content, String category, String[] hash){
        long execute = queryFactory
                .update(post)
                .set(post.id, id)
                .set(post.title, title)
                .set(post.content, content)
                .set(post.category, category)
                .set(post.hash_first, hash[0])
                .set(post.hash_second, hash[1])
                .set(post.hash_third, hash[2])
                .execute();
    }

    public void delete(Long id){
        queryFactory
                .delete(post)
                .where(post.id.eq(id))
                .execute();
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
}
