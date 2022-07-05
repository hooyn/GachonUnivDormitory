package gachonUniv.dormitory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gachonUniv.dormitory.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Long save(Notification notification){
        em.persist(notification);
        return notification.getId();
    }
}
