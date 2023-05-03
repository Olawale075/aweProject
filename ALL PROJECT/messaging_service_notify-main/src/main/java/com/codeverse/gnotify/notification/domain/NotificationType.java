/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.notification.domain;

import com.codeverse.gnotify.general.domain.AbstractPersistableCustom;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

/**
 *
 * @author Olakunle.Thompson
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Entity
@Table(name = "m_notification_type",
        uniqueConstraints = {
            @UniqueConstraint(name = "notificationTemplate", columnNames = {"notificationTemplate"})})
@TypeDefs({
    @TypeDef(name = "string-array", typeClass = StringArrayType.class),
    @TypeDef(name = "int-array", typeClass = IntArrayType.class),
    @TypeDef(name = "json", typeClass = JsonType.class)
})
public class NotificationType extends AbstractPersistableCustom<Long> implements Serializable {

    @Column(unique = true)
    private NotificationTemplate notificationTemplate;

    @NotNull(message = "Notification configuration is required")
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private NotificationTypeConfiguration notificationTypeConfiguration;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "lastmodified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
}
