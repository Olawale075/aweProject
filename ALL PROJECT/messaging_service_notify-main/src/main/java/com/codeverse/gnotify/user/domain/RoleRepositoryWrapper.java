package com.codeverse.gnotify.user.domain;

import com.codeverse.gnotify.user.exception.RoleNotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RoleRepositoryWrapper {

    private final RoleRepository repository;

    @Autowired
    public RoleRepositoryWrapper(final RoleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Role findOneWithNotFoundDetection(final Long id) {
        final Role message = this.repository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
        return message;
    }

    public void saveAndFlush(final Role role) {
        this.repository.saveAndFlush(role);
    }

    public void saveAllAndFlush(final List<Role> role) {
        this.repository.saveAllAndFlush(role);
    }

    public void delete(final Role role) {
        this.repository.delete(role);
        this.repository.flush();
    }

    @Transactional(readOnly = true)
    public Page<Role> findAll(Specification<Role> spec, Pageable pageable) {
        return this.repository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return this.repository.findAll();
    }
}
