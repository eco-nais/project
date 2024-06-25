package com.eco.environet.users.repository;

import com.eco.environet.users.model.Notification;
import com.eco.environet.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserOrderByIsReadAsc(User user);
    List<Notification> findAllByUserAndIsReadFalse(User user);
}
