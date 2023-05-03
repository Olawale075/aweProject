/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeverse.gnotify.sms.domain;

import com.codeverse.gnotify.general.domain.AbstractPersistableCustom;
import com.codeverse.gnotify.sms.domain.util.SMSType;
import com.codeverse.gnotify.general.data.TemplateAgnostic;
import com.codeverse.gnotify.sms.domain.util.FileSeparator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
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
@Table(name = "m_sms_template",
        uniqueConstraints = {
            @UniqueConstraint(name = "smsType", columnNames = {"smsType"})})
@TypeDefs({
    @TypeDef(name = "string-array", typeClass = StringArrayType.class),
    @TypeDef(name = "int-array", typeClass = IntArrayType.class),
    @TypeDef(name = "json", typeClass = JsonType.class)
})
public class SMSTemplate extends AbstractPersistableCustom<Long> implements Serializable {

    @NotNull
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private List<TemplateAgnostic> templateAgnostic;

    private SMSType smsType;

    @NotBlank
    private String shortName;

    @NotBlank
    private String sourceFolder;
    @NotBlank
    private String destinationFolder;

    private FileSeparator separator;
    private Boolean skipFileHeader;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "lastmodified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
}
