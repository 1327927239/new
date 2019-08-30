package com.review.Repository;

import com.review.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepositiry extends JpaRepository<Notification,Integer> {
}
