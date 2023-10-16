package com.c88.common.core.base;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;


@Data
@ToString
public class BaseSetVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Set<T> ids;
}
