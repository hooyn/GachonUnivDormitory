package gachonUniv.dormitory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gachonUniv.dormitory.domain.Member;
import gachonUniv.dormitory.domain.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static gachonUniv.dormitory.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public UUID save(Member member){
        em.persist(member);
        return member.getId();
    }

    public List<Member> findAll(){
        return queryFactory
                .selectFrom(member)
                .fetch();
    }

    public Member findByUuid(String uuid){
        return queryFactory
                .select(member)
                .from(member)
                .where(member.id.eq(UUID.fromString(uuid)))
                .fetchOne();
    }

    public Member findByUserId(String userID){
        return queryFactory
                .selectFrom(member)
                .where(member.userID.eq(userID))
                .fetchOne();
    }

    public void changeNickname(String uuid, String nickname){
        long execute = queryFactory
                .update(member)
                .set(member.nickname, nickname)
                .where(member.id.eq(UUID.fromString(uuid)))
                .execute();
    }

    public void changeToken(String uuid, String token){
        long execute = queryFactory
                .update(member)
                .set(member.token, token)
                .where(member.id.eq(UUID.fromString(uuid)))
                .execute();
    }
}
