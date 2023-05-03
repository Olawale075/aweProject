
package com.codeverse.gnotify.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByCode(String code);
}
