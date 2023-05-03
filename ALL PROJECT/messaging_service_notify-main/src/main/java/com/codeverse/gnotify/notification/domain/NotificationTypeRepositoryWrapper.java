package com.codeverse.gnotify.notification.domain;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class NotificationTypeRepositoryWrapper {

    private final NotificationTypeRepository repository;

    @Autowired
    public NotificationTypeRepositoryWrapper(final NotificationTypeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public NotificationType findOneWithNotFoundDetection(final Long id) {
        final NotificationType notificationType = this.repository.findById(id).orElseThrow(() -> new NotificationTypeNotFoundException(id));
        return notificationType;
    }

    @Transactional(readOnly = true)
    public Page<NotificationType> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<NotificationType> findAll() {
        return this.repository.findAll();
    }

    public void saveAndFlush(final NotificationType notificationType) {
        this.repository.saveAndFlush(notificationType);
    }

    public void delete(final NotificationType notificationType) {
        this.repository.delete(notificationType);
        this.repository.flush();
    }
}
