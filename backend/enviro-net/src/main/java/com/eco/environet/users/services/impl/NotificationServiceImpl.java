package com.eco.environet.users.services.impl;

import com.eco.environet.users.dto.NotificationDto;
import com.eco.environet.users.model.Notification;
import com.eco.environet.users.model.User;
import com.eco.environet.users.repository.NotificationRepository;
import com.eco.environet.users.repository.UserRepository;
import com.eco.environet.users.security.auth.JwtService;
import com.eco.environet.users.services.NotificationService;
import com.eco.environet.util.EmailSender;
import com.eco.environet.util.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Value("${baseFrontUrl}")
    private String baseFrontUrl;
    private final JwtService jwtService;
    private final EmailSender emailSender;
    private final UserRepository userRepository;
    private final NotificationRepository repository;

    @Override
    public List<NotificationDto> findAllByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        List<Notification> result = repository.findAllByUserOrderByIsReadAsc(user);
        return Mapper.mapList(result, NotificationDto.class);
    }

    @Override
    public List<NotificationDto> findAllUnreadByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        List<Notification> result = repository.findAllByUserAndIsReadFalse(user);
        return Mapper.mapList(result, NotificationDto.class);
    }

    @Override
    public NotificationDto sendNotification(NotificationDto notificationDto) {
        Notification notification = Mapper.map(notificationDto, Notification.class);
        notification.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        Notification savedNotification = repository.save(notification);
        return Mapper.map(savedNotification, NotificationDto.class);
    }

    @Override
    public NotificationDto sendNotificationAndEmail(NotificationDto notificationDto) {
        NotificationDto savedNotificationDto = sendNotification(notificationDto);
        User user = userRepository.findById(notificationDto.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        sendNotificationEmail(user, savedNotificationDto);
        return savedNotificationDto;
    }
    private void sendNotificationEmail(User user, NotificationDto notificationDto) {
        String emailBody = "Hello " + user.getName() + ",\n\n";
        emailBody += "You have received a new notification on EnviroNet:\n\n";
        emailBody += "Subject: " + notificationDto.getTitle() + "\n";
        emailBody += "Message: " + notificationDto.getDescription() + "\n\n";
        emailBody += "Please login to your EnviroNet account to view the details.\n\n";
        emailBody += "Best regards,\nYour EnviroNet Team";

        emailSender.sendEmail(user.getEmail(), "New Notification on EnviroNet", emailBody);
    }

    @Override
    public NotificationDto readNotification(NotificationDto notificationDto) {
        Notification notification = repository.findById(notificationDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with ID: " + notificationDto.getId()));
        notification.setRead(true);
        Notification updatedNotification = repository.save(notification);
        return Mapper.map(updatedNotification, NotificationDto.class);
    }

    @Override
    public NotificationDto markAsUnread(NotificationDto notificationDto) {
        Notification notification = repository.findById(notificationDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with ID: " + notificationDto.getId()));
        notification.setRead(false);
        Notification updatedNotification = repository.save(notification);
        return Mapper.map(updatedNotification, NotificationDto.class);
    }

    @Override
    public List<NotificationDto> readAllByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        List<Notification> unreadNotifications = repository.findAllByUserAndIsReadFalse(user);
        for (Notification notification : unreadNotifications) {
            notification.setRead(true);
        }
        List<Notification> updatedNotifications = repository.saveAll(unreadNotifications);
        return Mapper.mapList(updatedNotifications, NotificationDto.class);
    }

    @Override
    public void delete(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with ID: " + id));
        repository.delete(notification);
    }
}
