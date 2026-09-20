package com.carclinic.web;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationFieldsDto {

    @NotNull
    private Long customerId;

    @NotNull
    private LocalDateTime notificationDate;

    @NotBlank
    private String message;

    @NotBlank
    private String type;

    @NotBlank
    private String status;

    public NotificationFieldsDto() {
    }

    public NotificationFieldsDto(Long customerId, LocalDateTime notificationDate, String message, String type,
            String status) {
        this.customerId = customerId;
        this.notificationDate = notificationDate;
        this.message = message;
        this.type = type;
        this.status = status;
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
