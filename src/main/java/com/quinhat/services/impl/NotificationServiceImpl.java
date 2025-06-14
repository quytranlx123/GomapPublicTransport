package com.quinhat.services.impl;

import com.quinhat.dto.AdminNotificationDTO;
import com.quinhat.mapper.AdminNotificationMapper;
import com.quinhat.pojo.Notification;
import com.quinhat.repositories.NotificationRepository;
import com.quinhat.services.NotificationService;
import com.quinhat.utils.Common;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepo;

    @Override
    public List<AdminNotificationDTO> getAllNotifications() {
        return notificationRepo.getAllNotifications();
    }

    public Notification getNotificationById(Integer id) {
        return notificationRepo.getNotificationById(id);
    }

    public void save(AdminNotificationDTO dto) {
        notificationRepo.save(dto);
    }

    @Override
    public void delete(List<Integer> ids) {
        notificationRepo.delete(ids);
    }

    @Override
    public List<AdminNotificationDTO> getNotificationsPaginated(int page, int size) {
        return notificationRepo.getNotificationsPaginated(page, size);
    }

    @Override
    public long countNotifications() {
        return notificationRepo.countNotifications();
    }

    @Override
    public Notification findById(int id) {
        return notificationRepo.findById(id);
    }

    @Override
    public AdminNotificationDTO update(AdminNotificationDTO dto) {
        Notification existing = notificationRepo.findById(dto.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Notification not found");
        }

        if (dto.getTitle() != null) {
            existing.setTitle(dto.getTitle());
        }
        if (dto.getMessage() != null) {
            existing.setMessage(dto.getMessage());
        }
        if (dto.getMessageType() != null) {
            existing.setMessageType(Common.toLowerCase(dto.getMessageType()));
        }
        // Nếu muốn cho phép cập nhật createdAt:
        // if (dto.getCreatedAt() != null) existing.setCreatedAt(dto.getCreatedAt());

        notificationRepo.update(existing);
        return AdminNotificationMapper.toDTO(existing);
    }

}
