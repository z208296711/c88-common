package com.c88.common.core.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseSingleVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(title = "傳遞值")
    private T value;
}
