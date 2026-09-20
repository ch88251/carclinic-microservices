package com.carclinic.web;

import java.time.LocalDateTime;

public class NotificationDto {

    private Long id;
    private Long customerId;
    private LocalDateTime notificationDate;
    private String message;
    private String type;
    private String status;

    public NotificationDto() {
    }

    public NotificationDto(Long id, Long customerId, LocalDateTime notificationDate, String message, String type,
            String status) {
        this.id = id;
        this.customerId = customerId;
        this.notificationDate = notificationDate;
        this.message = message;
        this.type = type;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
