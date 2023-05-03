package com.codeverse.gnotify.user.domain;

import com.codeverse.gnotify.general.domain.AbstractPersistableCustom;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Entity
@Table(name = "m_permission")
public class Permission extends AbstractPersistableCustom<Long> implements Serializable {

    @Column(name = "grouping", nullable = false, length = 45)
    private String grouping;

    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @Column(name = "entity_name", nullable = true, length = 100)
    private String entityName;

    @Column(name = "action_name", nullable = true, length = 100)
    private String actionName;

    @Column(name = "can_maker_checker", nullable = false)
    private boolean canMakerChecker;

    protected Permission(final String grouping, final String entityName, final String actionName) {
        this.grouping = grouping;
        this.entityName = entityName;
        this.actionName = actionName;
        this.code = actionName + "_" + entityName;
        this.canMakerChecker = false;
    }

    protected Permission() {
        this.grouping = null;
        this.entityName = null;
        this.actionName = null;
        this.code = null;
        this.canMakerChecker = false;
    }

    public boolean hasId(final Long checkId) {
        return this.getId().compareTo(checkId) == 0;
    }

    public boolean hasCode(final String checkCode) {
        return this.code.equalsIgnoreCase(checkCode);
    }

    public String getCode() {
        return this.code;
    }

    public boolean hasMakerCheckerEnabled() {
        return this.canMakerChecker;
    }

    public String getGrouping() {
        return this.grouping;
    }

    public boolean enableMakerChecker(final boolean canMakerChecker) {
        final boolean isUpdatedValueSame = this.canMakerChecker == canMakerChecker;
        this.canMakerChecker = canMakerChecker;

        return !isUpdatedValueSame;
    }
}
