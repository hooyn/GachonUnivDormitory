package gachonUniv.dormitory.service;

import gachonUniv.dormitory.domain.Notification;
import gachonUniv.dormitory.dto.FindNotificationDto;
import gachonUniv.dormitory.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    /**
     * 알림 데이터베이스에 저장
     */
    @Transactional
    public Long createNotification(Notification notification){
        return notificationRepository.save(notification);
    }

    /**
     * 나의 알림 조회
     */
    @Transactional(readOnly = true)
    public List<FindNotificationDto> findNotificationByUuid(String uuid, Integer page){
        return notificationRepository.findNotificationByUuid(uuid, page);
    }
}
