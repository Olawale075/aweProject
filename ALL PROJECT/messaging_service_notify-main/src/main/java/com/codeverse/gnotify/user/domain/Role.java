package com.codeverse.gnotify.user.domain;

import com.codeverse.gnotify.general.domain.AbstractPersistableCustom;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Entity
@Table(name = "m_role")
public class Role extends AbstractPersistableCustom<Long> implements Serializable {

    @NotBlank(message = "Role name is required")
    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "is_disabled", nullable = false)
    private Boolean disabled;

    //@NotEmpty(message = "Permission cannot be empty.")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "m_role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();

    @Transient
    private Set<Long> permissionsId = new HashSet<>();

    public static Role fromJson(final String name, final String description) {
        return new Role(name, description);
    }

    protected Role() {
        //
    }

    protected Role(String name, String description) {
        this.name = name.trim();
        this.description = description.trim();
        this.disabled = false;
    }

    public Map<String, Object> update(final String name, final String description) {

        final Map<String, Object> actualChanges = new LinkedHashMap<>(7);

        final String nameParamName = "name";
        if (!StringUtils.equals(name, this.name)) {
            final String newValue = name;
            actualChanges.put(nameParamName, newValue);
            this.name = newValue;
        }

        final String descriptionParamName = "description";
        if (!StringUtils.equals(description, this.description)) {
            final String newValue = description;
            actualChanges.put(descriptionParamName, newValue);
            this.description = newValue;
        }

        return actualChanges;
    }

    public Boolean updatePermission(final Permission permission, final Boolean isSelected) {
        Boolean changed;
        if (isSelected) {
            changed = addPermission(permission);
        } else {
            changed = removePermission(permission);
        }

        return changed;
    }

    private Boolean addPermission(final Permission permission) {
        return this.permissions.add(permission);
    }

    private Boolean removePermission(final Permission permission) {
        return this.permissions.remove(permission);
    }

    public void disableRole() {
        this.disabled = true;
    }

    public void enableRole() {
        this.disabled = false;
    }
}
