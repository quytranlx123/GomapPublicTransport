package com.quinhat.services.impl;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.quinhat.dto.AdminUserNotificationDTO;
import com.quinhat.dto.NotificationDTO;
import com.quinhat.mapper.AdminUserNotificationMapper;
import com.quinhat.pojo.Notification;
import com.quinhat.pojo.User;
import com.quinhat.pojo.UserNotification;
import com.quinhat.repositories.NotificationRepository;
import com.quinhat.repositories.UserNotificationRepository;
import com.quinhat.repositories.UserRepository;
import com.quinhat.services.UserNotificationService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserNotificationServiceImpl implements UserNotificationService {

    @Autowired
    private UserNotificationRepository userNotificationRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private NotificationRepository notificationRepo;

    @Override
    public List<AdminUserNotificationDTO> getAllUserNotifications() {
        return userNotificationRepo.getAllUserNotifications();
    }

    @Override
    public List<UserNotification> getNotificationsByUserId(Integer userId) {
        return userNotificationRepo.getNotificationsByUserId(userId);
    }

    @Override
    public UserNotification create(UserNotification u) {
        UserNotification un = userNotificationRepo.create(u);

        Firestore db = FirestoreClient.getFirestore();
        // Đẩy thông báo đến user (giả sử userNotification có userId)
        NotificationDTO notificationDTO = new NotificationDTO(
                u.getNotificationId().getId(),
                u.getUserId().getId(),
                u.getNotificationId().getTitle(),
                u.getNotificationId().getMessage(),
                u.getNotificationId().getMessageType(),
                u.getSendAt(),
                u.getIsRead()
        );

        if (u.getUserId() != null) {
            db.collection("notifications").add(notificationDTO);
        }

        return un;
    }

    @Override
    public void save(AdminUserNotificationDTO dto) {

        User user = userRepo.findById(dto.getUserId());
        Notification notification = notificationRepo.findById(dto.getNotificationId());

        if (notification == null || user == null) {
            throw new IllegalArgumentException("Notification và User không tồn tại");
        }

        UserNotification userNotification = AdminUserNotificationMapper.toEntity(dto, notification, user);

        Date now = new Date();
        if (userNotification.getSendAt() == null) {
            userNotification.setSendAt(now);
        }

        userNotification.setIsRead(false);

        userNotificationRepo.save(userNotification);

        if (userNotification.getUserId() != null) {
            NotificationDTO notificationDTO = new NotificationDTO(
                    notification.getId(),
                    user.getId(),
                    notification.getTitle(),
                    notification.getMessage(),
                    notification.getMessageType(),
                    userNotification.getSendAt(),
                    userNotification.getIsRead()
            );

            Firestore db = FirestoreClient.getFirestore();
            db.collection("notifications").add(notificationDTO);
        }
    }

    @Override
    public void delete(List<Integer> ids) {
        userNotificationRepo.delete(ids);
    }

    @Override
    public List<AdminUserNotificationDTO> getUserNotificationsPaginated(int page, int size) {
        return userNotificationRepo.getUserNotificationsPaginated(page, size);
    }

    @Override
    public long countUserNotifications() {
        return userNotificationRepo.countUserNotifications();
    }

    @Override
    public UserNotification findById(int id) {
        return userNotificationRepo.findById(id);
    }

    @Override
    public AdminUserNotificationDTO update(AdminUserNotificationDTO dto) {
        UserNotification existing = userNotificationRepo.findById(dto.getId());
        if (existing == null) {
            throw new IllegalArgumentException("UserNotification not found");
        }

        // Cập nhật ngày giờ nếu có
        if (dto.getSendAt() != null) {
            existing.setSendAt(dto.getSendAt());
        }

        // Cập nhật trạng thái đọc
        existing.setIsRead(dto.isRead());

        // Cập nhật Notification và User nếu có
        if (dto.getNotificationId() != null) {
            Notification un = notificationRepo.findById(dto.getNotificationId());
            if (un == null) {
                throw new IllegalArgumentException("Notification not found with ID = " + dto.getNotificationId());
            }
            existing.setNotificationId(un);
        }

        if (dto.getUserId() != null) {
            User u = userRepo.findById(dto.getUserId());
            if (u == null) {
                throw new IllegalArgumentException("User not found with ID = " + dto.getUserId());
            }
            existing.setUserId(u);
        }

        userNotificationRepo.update(existing);
        return AdminUserNotificationMapper.toDTO(existing);
    }

}
