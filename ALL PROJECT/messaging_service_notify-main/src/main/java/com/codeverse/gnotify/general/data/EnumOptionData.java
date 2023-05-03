package com.codeverse.gnotify.general.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author Olakunle.Thompson
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EnumOptionData implements Serializable {

    private final String value;
    private String description;
}
