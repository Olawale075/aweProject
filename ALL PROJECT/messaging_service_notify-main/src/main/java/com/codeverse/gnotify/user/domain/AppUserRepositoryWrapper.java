package com.codeverse.gnotify.user.domain;

import com.codeverse.gnotify.user.exception.AppUserNotFoundException;
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
public class AppUserRepositoryWrapper {

    private final AppUserRepository repository;

    @Autowired
    public AppUserRepositoryWrapper(final AppUserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public AppUser findOneWithNotFoundDetection(final Long id) {
        final AppUser appUser = this.repository.findById(id).orElseThrow(() -> new AppUserNotFoundException(id));
        return appUser;
    }

    @Transactional(readOnly = true)
    public AppUser findByUsernameOrEmailOrMobile(final String username, final String email, final String mobile) {
        final AppUser appUser = this.repository.findByUsernameOrEmailOrMobile(username, email, mobile)
                .orElseThrow(() -> new AppUserNotFoundException(username));
        return appUser;
    }

    public void saveAndFlush(final AppUser appUser) {
        this.repository.saveAndFlush(appUser);
    }

    public void saveAllAndFlush(final List<AppUser> appUser) {
        this.repository.saveAllAndFlush(appUser);
    }

    public void delete(final AppUser appUser) {
        this.repository.delete(appUser);
        this.repository.flush();
    }

    @Transactional(readOnly = true)
    public Page<AppUser> findAll(Specification<AppUser> spec, Pageable pageable) {
        return this.repository.findAll(spec, pageable);
    }
}
