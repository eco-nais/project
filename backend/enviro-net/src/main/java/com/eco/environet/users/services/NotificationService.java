package com.eco.environet.users.services;

import com.eco.environet.users.dto.NotificationDto;
import java.util.List;

public interface NotificationService {
    List<NotificationDto> findAllByUser(Long userId);
    List<NotificationDto> findAllUnreadByUser(Long userId);
    NotificationDto sendNotification(NotificationDto notification);
    NotificationDto sendNotificationAndEmail(NotificationDto notification);
    NotificationDto readNotification(NotificationDto notification);
    NotificationDto markAsUnread(NotificationDto notification);
    List<NotificationDto> readAllByUser(Long userId);
    void delete(Long id);
}
