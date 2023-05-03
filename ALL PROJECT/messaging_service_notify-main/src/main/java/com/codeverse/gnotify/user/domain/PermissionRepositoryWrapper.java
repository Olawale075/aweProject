package com.codeverse.gnotify.user.domain;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PermissionRepositoryWrapper {

    private final PermissionRepository repository;

    @Autowired
    public PermissionRepositoryWrapper(final PermissionRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Permission> findAll() {
        return this.repository.findAll();
    }
}
