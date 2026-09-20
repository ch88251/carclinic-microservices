package com.carclinic.web;

import org.springframework.stereotype.Component;

import com.carclinic.domain.Notification;
import com.carclinic.domain.Owner;
import com.carclinic.domain.OwnerRepository;

@Component
public class NotificationMapper {

    private final OwnerRepository ownerRepository;

    public NotificationMapper(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Notification toNotification(NotificationFieldsDto dto) {
        Owner customer = ownerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found: " + dto.getCustomerId()));
        return new Notification(customer, dto.getNotificationDate(), dto.getMessage(), dto.getType(), dto.getStatus());
    }

    public NotificationDto toNotificationDto(Notification notification) {
        Long customerId = notification.getCustomer() != null ? notification.getCustomer().getId() : null;
        return new NotificationDto(notification.getId(), customerId, notification.getNotificationDate(),
                notification.getMessage(), notification.getType(), notification.getStatus());
    }
}
