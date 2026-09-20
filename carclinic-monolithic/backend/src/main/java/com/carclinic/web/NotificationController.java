package com.carclinic.web;

import java.net.URI;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.carclinic.domain.Notification;
import com.carclinic.domain.NotificationRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
@Validated
public class NotificationController {

    private final NotificationRepository repository;
    private final NotificationMapper notificationMapper;

    public NotificationController(NotificationRepository repository, NotificationMapper notificationMapper) {
        this.repository = repository;
        this.notificationMapper = notificationMapper;
    }

    @GetMapping(produces = "application/json")
    public List<NotificationDto> getNotifications() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(notificationMapper::toNotificationDto)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<NotificationDto> addNotification(@Valid @RequestBody NotificationFieldsDto request) {
        Notification notification = notificationMapper.toNotification(request);
        repository.save(notification);

        NotificationDto response = notificationMapper.toNotificationDto(notification);
        URI location = UriComponentsBuilder.fromPath("/api/notifications/{id}")
                .buildAndExpand(notification.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
}
