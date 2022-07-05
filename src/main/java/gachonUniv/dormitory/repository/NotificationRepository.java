package gachonUniv.dormitory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gachonUniv.dormitory.domain.Notification;
import gachonUniv.dormitory.domain.QNotification;
import gachonUniv.dormitory.dto.FindNotificationDto;
import gachonUniv.dormitory.dto.QFindNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static gachonUniv.dormitory.domain.QNotification.notification;
import static gachonUniv.dormitory.domain.QPost.post;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Long save(Notification notification){
        em.persist(notification);
        return notification.getId();
    }

    public List<FindNotificationDto> findNotificationByUuid (String uuid, Integer page){
        return queryFactory
                .select(new QFindNotificationDto(
                        notification.id,
                        notification.title,
                        notification.content,
                        notification.isRead,
                        notification.create_time
                ))
                .from(notification)
                .where(notification.member.id.eq(UUID.fromString(uuid)))
                .orderBy(notification.create_time.desc())
                .offset(0+((page-1)*20))
                .limit(10)
                .fetch();
    }
}
